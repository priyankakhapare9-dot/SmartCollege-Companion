
# SmartCollege Companion App

A reconstructed full-stack academic management starter based on the project description provided by the user.

## Stack
- Frontend: React + Vite
- Backend: Java 17, Spring Boot 3.3.5, Spring Security, Spring Data JPA
- Database: MySQL 8
- Authentication: JWT bearer tokens
- AI assistant: optional Ollama integration (local model required)

## Included modules
- Register/login with STUDENT, FACULTY and ADMIN roles
- Role-protected REST API
- Attendance records
- Study notes metadata and file upload/download (UUID file names)
- Timetable entries
- College notices
- EduBot endpoint connected to local Ollama

This is a newly reconstructed implementation, not a recovered copy of the deleted original project. Review and test it in your environment before using it for a submission or interview. OAuth2 social login is not wired in this starter; JWT login is implemented. The Ollama endpoint is optional and requires Ollama running locally.

## Requirements
- JDK 17
- Maven 3.9+
- Node.js 20+
- MySQL 8+
- (Optional) Ollama installed with a model pulled, e.g. `ollama pull llama3.2`

## Database
Create the database:
```sql
CREATE DATABASE smartcollege;
```
Set your MySQL username/password in `backend/src/main/resources/application.properties`, or set environment variables `DB_USERNAME` and `DB_PASSWORD`.

## Run backend
```bash
cd backend
mvn spring-boot:run
```
Backend: http://localhost:8080

## Run frontend
In another terminal:
```bash
cd frontend
npm install
npm run dev
```
Frontend: http://localhost:5173

## Demo account
Register a new account from the UI. Public registration creates STUDENT users only.
For faculty/admin accounts, update the `role` column in MySQL after registering:
```sql
UPDATE users SET role='ADMIN' WHERE email='your-email@example.com';
UPDATE users SET role='FACULTY' WHERE email='faculty@example.com';
```
Log out and log in again to receive a token with the updated role.

## Main API endpoints
All endpoints are under `/api`.
- `POST /auth/register` – register student
- `POST /auth/login` – login
- `GET /students/me` – current user profile
- `GET /attendance` – list attendance (authenticated)
- `POST /attendance` – create attendance (FACULTY/ADMIN)
- `GET /notes` – list notes
- `POST /notes` – upload note (FACULTY/ADMIN, multipart field `file`, plus `title`, `subject`)
- `GET /notes/{id}/download` – download note
- `GET /timetable` – list timetable
- `POST /timetable` – create timetable entry (FACULTY/ADMIN)
- `GET /notices` – list notices
- `POST /notices` – create notice (FACULTY/ADMIN)
- `POST /edubot/chat` – ask EduBot (authenticated; Ollama required)

## Important configuration
- JWT secret is a development placeholder. Replace `app.jwt.secret` with a long random secret before deployment.
- Uploaded files are stored in `backend/uploads/`.
- CORS is configured for `http://localhost:5173`.
- This project is for learning/demo use. Add validation, pagination, audit logging, production secrets management and stronger authorization rules before deployment.
