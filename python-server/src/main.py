from fastapi import FastAPI
from fastapi.middleware.cors import CORSMiddleware
import os
from dotenv import load_dotenv
import httpx
from src.schemas.mbti import MBTINameRequest
from src.services.mbti_service import summarize_mbti_list

app = FastAPI()

# CORS 설정
app.add_middleware(
    CORSMiddleware,
    allow_origins=["*"],
    allow_credentials=True,
    allow_methods=["*"],
    allow_headers=["*"],
)

# 자바 
load_dotenv()
JAVA_API = os.getenv("JAVA_API")

@app.post("/api/mbti/summarize")
async def summarize_handler(request: MBTINameRequest):
    return await summarize_mbti_list(request.mbti_names)