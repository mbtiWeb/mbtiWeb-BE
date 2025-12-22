from typing import List
from pydantic import BaseModel

class MBTINameRequest(BaseModel):
    mbti_names: List[str]