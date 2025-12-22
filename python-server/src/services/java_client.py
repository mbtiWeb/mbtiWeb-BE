import httpx, asyncio
import os
from dotenv import load_dotenv

load_dotenv()
JAVA_API = os.getenv("JAVA_API")

async def fetch_instruction(client: httpx.AsyncClient, mbti_name:str) -> str:
    """
    단일 MBTI에 대한 instruction을 자바 서버에서 가져오는 함수
    """

    response = await client.get(f"{JAVA_API}/api/mbti/{mbti_name}")
    data = response.json()

    result = {
        "img_url": data.get("img_url"),
        "is_subtype": data.get("is_subtype"),
        "instruction": data.get("instruction"),
        "type": data.get("type"),
    }

    return result