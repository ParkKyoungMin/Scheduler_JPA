# 📅 Schedule Manager API

스케줄 관리 시스템의 API 명세서입니다.

---

## **📌 1. 유저(User) API**
| 메서드 | 엔드포인트 | 설명 | 요청 값 (Request) | 응답 값 (Response) | 인증 필요 |
|--------|------------|------|------------------|------------------|----------|
| `POST` | `/users/register` | 회원가입 | `{ "username": "John", "email": "john@example.com", "password": "123456" }` | `{ "id": 1, "username": "John", "email": "john@example.com", "createdAt": "2025-02-13", "updatedAt": "2025-02-13" }` | ❌ |
| `POST` | `/users/login` | 로그인 (세션 생성) | `{ "email": "john@example.com", "password": "123456" }` | `HTTP 200 OK` (세션 유지) | ❌ |
| `GET` | `/users` | 전체 유저 목록 조회 | - | `[ { "id": 1, "username": "John", "email": "john@example.com" } ]` | ✅ |
| `GET` | `/users/{id}` | 특정 유저 조회 | - | `{ "id": 1, "username": "John", "email": "john@example.com" }` | ✅ |
| `PUT` | `/users/{id}` | 유저 정보 수정 | `{ "username": "Updated John", "email": "new@example.com" }` | `{ "id": 1, "username": "Updated John", "email": "new@example.com", "updatedAt": "2025-02-13" }` | ✅ |
| `DELETE` | `/users/{id}` | 유저 삭제 | - | `HTTP 204 No Content` | ✅ |

---

## **📌 2. 일정(Schedule) API**
| 메서드 | 엔드포인트 | 설명 | 요청 값 (Request) | 응답 값 (Response) | 인증 필요 |
|--------|------------|------|------------------|------------------|----------|
| `POST` | `/schedules` | 일정 생성 | `{ "userId": 1, "title": "회의", "content": "프로젝트 논의" }` | `{ "id": 1, "user": { "id": 1, "username": "John" }, "title": "회의", "content": "프로젝트 논의", "createdAt": "2025-02-13", "updatedAt": "2025-02-13" }` | ✅ |
| `GET` | `/schedules` | 전체 일정 조회 | - | `[ { "id": 1, "title": "회의", "content": "프로젝트 논의", "createdAt": "2025-02-13" } ]` | ✅ |
| `GET` | `/schedules/{id}` | 특정 일정 조회 | - | `{ "id": 1, "title": "회의", "content": "프로젝트 논의", "createdAt": "2025-02-13" }` | ✅ |
| `PUT` | `/schedules/{id}` | 일정 수정 | `{ "title": "업데이트된 회의", "content": "오후 4시로 변경" }` | `{ "id": 1, "title": "업데이트된 회의", "content": "오후 4시로 변경", "updatedAt": "2025-02-13" }` | ✅ |
| `DELETE` | `/schedules/{id}` | 일정 삭제 | - | `HTTP 204 No Content` | ✅ |

---

## **📌 3. 인증 & 세션 관리**
| 메서드 | 엔드포인트 | 설명 | 요청 값 (Request) | 응답 값 (Response) |
|--------|------------|------|------------------|------------------|
| `POST` | `/users/login` | 로그인 (세션 생성) | `{ "email": "john@example.com", "password": "123456" }` | `HTTP 200 OK` (세션 유지) |
| `POST` | `/users/logout` | 로그아웃 (세션 삭제) | - | `HTTP 200 OK` (세션 삭제됨) |

---

## **📌 4. 인증(Authorization) 규칙**
- `✅` 표시된 API는 **로그인한 사용자만 접근 가능** (세션 필요)
- `❌` 표시된 API는 **인증 없이 접근 가능** (회원가입, 로그인)

---

## **📌 5. ERD (Entity Relationship Diagram)**
![ERD](./docs/erd.png)  

---

## **📌 6. 데이터베이스 테이블 생성 (`schedule.sql`)**
```sql
CREATE TABLE user (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(255) NOT NULL UNIQUE,
    email VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

CREATE TABLE schedule (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT NOT NULL,
    title VARCHAR(255) NOT NULL,
    content TEXT NOT NULL,
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES user(id) ON DELETE CASCADE
);
