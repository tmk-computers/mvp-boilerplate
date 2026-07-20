#!/usr/bin/env bash
# Database Re-seed script

echo "🌱 Triggering Flyway Migration & Seed Scripts..."
cd backend && ./mvnw flyway:migrate
echo "✅ Database seed completed!"
