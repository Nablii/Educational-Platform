import lmstudio as lms
from fastapi import FastAPI, HTTPException
from fastapi.middleware.cors import CORSMiddleware
from pydantic import BaseModel

# uvicorn main:app --reload

app = FastAPI()

app.add_middleware(
    CORSMiddleware,
    allow_origins=["http://localhost:3000", "http://localhost:5173"],
    allow_credentials=True,
    allow_methods=["*"], 
    allow_headers=["*"],
)

model = lms.llm()

class QueryRequest(BaseModel):
    input_string: str

class QueryResponse(BaseModel):
    result: str

def pre_input(input_string: str) -> lms.Chat:
    return lms.Chat(input_string)

def validate_input(input_string: str) -> str | bool:
    # Disabled
    return True

def run_query(model, input_string: str) -> str:
    if not validate_input(input_string):
        return "Invalid Input"
    
    chat: lms.Chat = pre_input("Return in a continous paragraph")
    chat.add_user_message(input_string)

    return str(model.respond(chat))

@app.post("/query", response_model=QueryResponse)
def query_endpoint(request: QueryRequest):
    result = run_query(model, request.input_string)

    if result == "Invalid Input":
        raise HTTPException(status_code=400, detail="Invalid Input. Only letters and digits allowed.")

    return QueryResponse(result=result)

if __name__ == "__main__":
    import uvicorn
    uvicorn.run("main:app", host="0.0.0.0", port=9999, reload=True)
