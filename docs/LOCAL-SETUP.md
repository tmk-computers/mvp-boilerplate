# Local Development & Setup Guide (`LOCAL-SETUP.md`)

> **DASP Digital MVP Boilerplate Framework**  
> Complete step-by-step guide for setting up and running the framework locally on Windows, macOS, or Linux.

---

## 📋 1. Prerequisites Checklist

Ensure the following tools are installed on your machine before beginning:

| Tool | Minimum Version | Installation Link / Command |
| :--- | :--- | :--- |
| **Git** | `v2.40+` | [git-scm.com](https://git-scm.com/) |
| **Java JDK** | `21 LTS` (OpenJDK / Temurin) | [adoptium.net](https://adoptium.net/) |
| **Node.js** | `v20 LTS` or `v22` | [nodejs.org](https://nodejs.org/) |
| **Docker Desktop** | `v24.0+` with Compose v2 | [docker.com](https://www.docker.com/) |
| **Maven** | `v3.9+` (Optional, `./mvnw` included) | `mvn -version` |

---

## 🛠️ 2. Step-by-Step Local Setup

### Step 2.1: Clone Repository & Create `.env`
```bash
git clone https://github.com/dasp-digital/dasp-mvp-boilerplate.git
cd dasp-mvp-boilerplate
cp .env.example .env
```

### Step 2.2: Launch Local Database & Redis Containers
Start PostgreSQL 16, Redis 7, PgAdmin 4, and Redis Insight using Docker Compose:
```bash
docker-compose -f docker/docker-compose.yml up -d
```
*Verify containers are healthy:*
```bash
docker-compose -f docker/docker-compose.yml ps
```

*Local Admin Tools:*
- **PgAdmin 4**: [http://localhost:5050](http://localhost:5050) (`admin@dasp.com` / `admin123`)
- **Redis Insight**: [http://localhost:5540](http://localhost:5540)

---

### Step 2.3: Launch Backend Application (Spring Boot 3)
```bash
cd backend
./mvnw spring-boot:run
```
*(On Windows PowerShell: `.\mvnw.cmd spring-boot:run`)*

On startup, **Flyway** automatically executes schema migrations (`V1__initial_schema.sql`, `V2__seed_rbac_data.sql`, `V3__ai_framework_schema.sql`).

*Verify Backend Endpoints:*
- **Swagger API Portal**: [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)
- **Actuator Health**: [http://localhost:8080/actuator/health](http://localhost:8080/actuator/health)

---

### Step 2.4: Launch Web Frontend Application (React + Vite)
Open a new terminal tab:
```bash
cd frontend
npm install
npm run dev
```
*Access Web Dashboard:* [http://localhost:5173](http://localhost:5173)

#### Default Admin Login Credentials:
- **Email**: `admin@dasp.com`
- **Password**: `Admin@123`

---

### Step 2.5: Launch Mobile App (React Native)
Open a new terminal tab:
```bash
cd mobile
npm install
npm start
```
*(Press `a` for Android Emulator or `i` for iOS Simulator)*

---

## 🤖 3. Setting Up Local Zero-Cost AI (Ollama)

To run AI models locally without third-party API costs:

1. Download and install [Ollama](https://ollama.com/).
2. Pull Llama 3.2 open-source model:
   ```bash
   ollama run llama3.2
   ```
3. Update your `.env` file:
   ```env
   DEFAULT_AI_PROVIDER=OLLAMA
   OLLAMA_BASE_URL=http://localhost:11434
   ```
4. Test AI chat locally via Web UI or curl:
   ```bash
   curl -X POST http://localhost:8080/api/v1/ai/chat \
     -H "Content-Type: application/json" \
     -d '{"prompt": "Hello AI!", "providerType": "OLLAMA"}'
   ```

---

## 🔍 4. Troubleshooting Common Local Issues

| Issue | Cause | Solution |
| :--- | :--- | :--- |
| **Port 5432 / 8080 in use** | A local Postgres or Tomcat is running outside Docker | Stop external services (`sudo service postgresql stop`) or change port mapping in `docker-compose.yml` / `application.yml` |
| **Flyway Checksum Error** | A migration file was modified after execution | Run `scripts/seed-db.sh` or drop the local database volume (`docker-compose down -v`) |
| **JWT 401 Unauthorized** | Token expired or invalid signature | Clear `localStorage` in browser devtools or log in again |
| **CORS Error in Web UI** | Frontend URL not whitelisted | Ensure `dasp.security.jwt.cors.allowed-origins` includes `http://localhost:5173` |
