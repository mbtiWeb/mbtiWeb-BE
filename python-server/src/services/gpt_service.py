import os
from dotenv import load_dotenv
from langchain_openai import ChatOpenAI
from langchain_core.prompts import ChatPromptTemplate
from langchain_core.output_parsers import JsonOutputParser
from pydantic import BaseModel, Field

load_dotenv()
OPENAI_API = os.getenv("OPENAI_API")

# LLM 출력 데이터 구조 정의
class MBTISummaryOutput(BaseModel):
    paragraph_1: str = Field(description="1문단: 전체적인 성격의 핵심 정의와 페르소나 (예: 조용한 예술가형, 감성적 탐험가 등)")
    paragraph_2: str = Field(description="2문단: 내적 가치관, 판단 기준, 삶을 대하는 태도 (예: 현재의 순간 중요시, 즉흥성)")
    paragraph_3: str = Field(description="3문단: 대인관계 스타일, 타인을 대하는 태도 및 애정 표현 방식")
    paragraph_4: str = Field(description="4문단: 스트레스 상황에서의 반응, 취약점, 부정적 행동 패턴")
    paragraph_5: str = Field(description="5문단: 이들에게 성공의 의미, 추구하는 삶의 목표 (예: 내적 자유, 미적 감각)")
    paragraph_6: str = Field(description="6문단: 종합적인 결론 및 해당 유형에 대한 따뜻한 조언이나 요약")

class GPTService:
    def __init__(self):
        self.llm = ChatOpenAI(
            model="gpt-4o-mini",
            api_key=OPENAI_API,
            temperature=0.7,
        )

        self.parser = JsonOutputParser(pydantic_object=MBTISummaryOutput)
        self.chain = self.create_summary_chain()

    def create_summary_chain(self):
        system_message = (
            "당신은 심층 심리학 및 MBTI 분석 전문가입니다. "
            "단순히 특정 MBTI 유형을 정의하는 것이 아니라, "
            "사용자가 가진 '주요 성향(Main Type)'과 '잠재/서브 성향(Subtype)'이 "
            "어떻게 얽혀서 하나의 독특한 입체적 자아를 형성하는지 분석하는 데 탁월합니다. "
            "입력된 정보들을 화학적으로 결합하여, 세상에 하나뿐인 페르소나를 그려내세요."
        )

        user_message = (
            "다음은 사용자의 성격을 구성하는 여러 MBTI 및 서브 성향 데이터입니다.\n"
            "이 데이터들을 종합하여 총 6개의 문단으로 구성된 JSON 분석 보고서를 작성하세요.\n"
            "---------------------\n"
            "{context}\n"
            "---------------------\n\n"
            "**[필수 작성 지침]**\n"
            "1. **단순 나열 금지**: 'ISFP는 ~하고, ENTP는 ~하다' 식으로 따로 설명하지 마세요. 두 성향이 섞였을 때 나타나는 **제3의 복합적인 모습**을 묘사하세요.\n"
            "   (예: '당신은 조용한 예술가(ISFP)의 감성을 지녔지만, 때로는 대담한 승부사(ENTP)의 기질이 발휘되어 의외의 추진력을 보여줍니다.')\n"
            "2. **대상 지칭**: 'ISFP는...'이라고 3인칭으로 정의하지 말고, **'이 유형의 사람은'** 혹은 **'당신은'**과 같이 구체적인 인물을 묘사하듯 서술하세요.\n"
            "3. **어조**: 깊이 있고, 서정적이며, 통찰력 있는 문체를 유지하세요.\n"
            "4. **구조 준수**: 각 문단의 주제(가치관, 대인관계, 스트레스 등)를 정확히 지키세요.\n"
            "5. 반드시 아래의 JSON 포맷 규칙을 지키세요.\n"
            "{format_instructions}"
        )

        prompt = ChatPromptTemplate.from_messages([
            ("system", system_message),
            ("user", user_message),
        ])

        return prompt | self.llm | self.parser

    async def generate_summary(self, text: str) -> str:
        return await self.chain.ainvoke({
            "context": text,
            "format_instructions": self.parser.get_format_instructions()
        })