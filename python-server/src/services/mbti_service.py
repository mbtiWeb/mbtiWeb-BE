import httpx, asyncio
from src.services.java_client import fetch_instruction
from src.services.gpt_service import GPTService

gpt_service = GPTService()

async def summarize_mbti_list(mbti_names: list[str]) -> dict:
    """
    여러 MBTI에 대한 instruction을 자바 서버에서 병렬적으로 가져와
    GPT로 요약하는 함수
    """

    async with httpx.AsyncClient() as client:
        tasks = [fetch_instruction(client, name) for name in mbti_names]

        # 모든 요청이 끝날 때까지 기다림.( 병렬 수행 )
        json_list = await asyncio.gather(*tasks)

        instructions = []
        mbti_details = []
        
        for data in json_list:
            # instruction 추출
            instructions.append(data.get("instruction"))

            # 나머지 데이터 추출
            mbti_details.append({
                "img_url": data.get("img_url"),
                "is_subtype": data.get("is_subtype"),
                "type": data.get("type"),
            })

        # GPT에게 보낼 프롬프트 만들기
        combined_text = "\n\n".join(instructions)

        # GPT로 요약
        summarized_instruction = await gpt_service.generate_summary(combined_text)
        
        return {
            "mbti_list": mbti_details,
            "summarized_instruction": summarized_instruction,
        }