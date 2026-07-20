#!/usr/bin/env bash
# Local Development Environment Bootstrap Script

echo "🚀 Starting DASP Digital MVP Boilerplate Environment Setup..."

if [ ! -f .env ]; then
  echo "📋 Copying .env.example to .env..."
  cp .env.example .env
fi

echo "🐳 Launching PostgreSQL & Redis Containers..."
docker-compose -f docker/docker-compose.yml up -d

echo "✅ Environment setup complete! Access PgAdmin at http://localhost:5050 and RedisInsight at http://localhost:5540"
