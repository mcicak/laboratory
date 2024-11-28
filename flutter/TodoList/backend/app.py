from fastapi import FastAPI, UploadFile, Form, HTTPException, status, Request, Depends
from fastapi.responses import JSONResponse, FileResponse
from fastapi.security import HTTPBearer, HTTPAuthorizationCredentials
from typing import List, Optional
import os
import shutil
from uuid import uuid4
import json
from pydantic import BaseModel

class RegisterUserRequest(BaseModel):
    firstname: str
    lastname: str
    username: str
    password: str

app = FastAPI()

# In-memory storage
notes = {}
users = {}
user_notes = {}  # Maps username to their notes

DATA_FOLDER = "data"
if not os.path.exists(DATA_FOLDER):
    os.makedirs(DATA_FOLDER)

# Security
security = HTTPBearer()

def get_current_user(credentials: HTTPAuthorizationCredentials = Depends(security)):
    token = credentials.credentials
    if token not in users:
        raise HTTPException(status_code=401, detail="Invalid or missing token")
    return token

# Helper functions
def save_image(file: UploadFile) -> str:
    filename = f"{uuid4()}_{file.filename}"
    filepath = os.path.join(DATA_FOLDER, filename)
    with open(filepath, "wb") as buffer:
        shutil.copyfileobj(file.file, buffer)
    return filename

# Notes Endpoints
@app.get("/api/notes")
def get_all_notes(user: str = Depends(get_current_user)):
    return JSONResponse(content=list(user_notes.get(user, {}).values()))

@app.post("/api/notes")
def create_note(
    data: str = Form(...),
    image: UploadFile = None,
    user: str = Depends(get_current_user)
):
    try:
        parsed_data = json.loads(data)
        title = parsed_data.get("title")
        text = parsed_data.get("text")
        if not title or not text:
            raise ValueError("Missing required fields: title or text")
    except (json.JSONDecodeError, ValueError) as e:
        raise HTTPException(status_code=400, detail=f"Invalid data: {str(e)}")

    note_id = str(uuid4())
    image_filename = save_image(image) if image else None
    note = {
        "id": note_id,
        "title": title,
        "text": text,
        "image": image_filename
    }

    if user not in user_notes:
        user_notes[user] = {}
    user_notes[user][note_id] = note
    return JSONResponse(content=note, status_code=status.HTTP_201_CREATED)

@app.delete("/api/notes")
def delete_all_notes(user: str = Depends(get_current_user)):
    user_notes[user] = {}
    return JSONResponse(content={"message": "All notes deleted."})

@app.get("/api/note/{note_id}")
def get_note(note_id: str, user: str = Depends(get_current_user)):
    if note_id not in user_notes.get(user, {}):
        raise HTTPException(status_code=404, detail="Note not found")
    return JSONResponse(content=user_notes[user][note_id])

@app.post("/api/note/{note_id}")
def update_note(
    note_id: str,
    data: str = Form(...),
    image: Optional[UploadFile] = None,
    user: str = Depends(get_current_user)
):
    if note_id not in user_notes.get(user, {}):
        raise HTTPException(status_code=404, detail="Note not found")

    try:
        parsed_data = json.loads(data)
        title = parsed_data.get("title")
        text = parsed_data.get("text")
        if title:
            user_notes[user][note_id]["title"] = title
        if text:
            user_notes[user][note_id]["text"] = text
    except (json.JSONDecodeError, ValueError) as e:
        raise HTTPException(status_code=400, detail=f"Invalid data: {str(e)}")

    if image:
        image_filename = save_image(image)
        user_notes[user][note_id]["image"] = image_filename

    return JSONResponse(content=user_notes[user][note_id])

@app.delete("/api/note/{note_id}")
def delete_note(note_id: str, user: str = Depends(get_current_user)):
    if note_id not in user_notes.get(user, {}):
        raise HTTPException(status_code=404, detail="Note not found")
    del user_notes[user][note_id]
    return JSONResponse(content={"message": f"Note {note_id} deleted."})

# Serve Images
@app.get("/images/{filename}")
def get_image(filename: str):
    filepath = os.path.join(DATA_FOLDER, filename)
    if not os.path.exists(filepath):
        raise HTTPException(status_code=404, detail="Image not found")
    return FileResponse(filepath)

@app.post("/auth/register")
def register_user(request: RegisterUserRequest):
    if request.username in users:
        raise HTTPException(status_code=400, detail="Username already exists")

    users[request.username] = {
        "firstname": request.firstname,
        "lastname": request.lastname,
        "password": request.password
    }

    return JSONResponse(content={"message": "User registered successfully."}, status_code=status.HTTP_201_CREATED)

# Health check endpoint
@app.get("/health")
def health_check():
    return {"status": "OK"}
