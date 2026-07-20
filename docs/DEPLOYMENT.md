# Deployment & Operations Guide (`DEPLOYMENT.md`)

> **DASP Digital MVP Boilerplate Framework**  
> Containerization: Docker Compose | Reverse Proxy: Nginx | Production Readiness

---

## 🐳 1. Docker Compose Infrastructure Setup

The framework includes an enterprise `docker-compose.yml` orchestrating all backend services:

```yaml
version: '3.8'

services:
  postgres:
    image: postgres:16-alpine
    container_name: dasp-postgres
    environment:
      POSTGRES_DB: dasp_db
      POSTGRES_USER: dasp_user
      POSTGRES_PASSWORD: dasp_password
    ports:
      - "5432:5432"
    volumes:
      - postgres_data:/var/lib/postgresql/data
    healthcheck:
      test: ["CMD-SHELL", "pg_isready -U dasp_user -d dasp_db"]
      interval: 10s
      timeout: 5s
      retries: 5

  redis:
    image: redis:7-alpine
    container_name: dasp-redis
    command: redis-server --appendonly yes
    ports:
      - "6379:6379"
    volumes:
      - redis_data:/data
    healthcheck:
      test: ["CMD", "redis-cli", "ping"]
      interval: 10s
      timeout: 5s
      retries: 5

  pgadmin:
    image: dpage/pgadmin4
    container_name: dasp-pgadmin
    environment:
      PGADMIN_DEFAULT_EMAIL: admin@dasp.com
      PGADMIN_DEFAULT_PASSWORD: admin123
    ports:
      - "5050:80"

  redis-insight:
    image: redis/redisinsight:latest
    container_name: dasp-redisinsight
    ports:
      - "5540:5540"

volumes:
  postgres_data:
  redis_data:
```

---

## 🌐 2. Production Nginx Reverse Proxy Configuration

```nginx
server {
    listen 80;
    server_name api.daspdigital.com;
    return 301 https://$host$request_uri;
}

server {
    listen 443 ssl http2;
    server_name api.daspdigital.com;

    ssl_certificate /etc/letsencrypt/live/api.daspdigital.com/fullchain.pem;
    ssl_certificate_key /etc/letsencrypt/live/api.daspdigital.com/privkey.pem;

    location / {
        proxy_pass http://localhost:8080;
        proxy_set_header Host $host;
        proxy_set_header X-Real-IP $remote_addr;
        proxy_set_header X-Forwarded-For $proxy_add_x_forwarded_for;
        proxy_set_header X-Forwarded-Proto $scheme;
    }
}
```

---

## 📈 3. Actuator Health Monitoring & Prometheus Metrics

Spring Boot Actuator endpoints are exposed at:
- Health Check: `GET /actuator/health`
- Prometheus Metrics: `GET /actuator/prometheus`
