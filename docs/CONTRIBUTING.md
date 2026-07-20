# Contribution & Git Workflow Standards (`CONTRIBUTING.md`)

> **DASP Digital MVP Boilerplate Framework**  
> Git Flow | Conventional Commits | PR Requirements

---

## 🌿 1. Git Branch Strategy

- `main`: Production-ready releases only.
- `develop`: Primary integration branch for upcoming releases.
- `feature/<feature-name>`: New business module or framework enhancement.
- `fix/<bug-name>`: Bug fix branch.
- `hotfix/<version>`: Immediate production hotfix branch.

---

## 📝 2. Conventional Commit Standards

Commit messages MUST follow the format: `<type>(<scope>): <short description>`

### Allowed Types
- `feat`: A new feature or module implementation
- `fix`: A bug fix
- `docs`: Documentation changes
- `style`: Formatting, missing semi-colons, linting fixes
- `refactor`: Refactoring code without behavior change
- `test`: Adding missing unit/integration tests
- `chore`: Build tasks, dependency updates

### Example
```bash
git commit -m "feat(auth): add Redis token blacklist on logout"
git commit -m "fix(rbac): resolve dynamic permission caching fallback"
```

---

## ✅ 3. Pull Request Checklist

Before submitting a Pull Request:
1. `mvn spotless:check checkstyle:check test` passes on backend.
2. `npm run lint` passes on frontend and mobile.
3. Flyway migration script added if database schema was changed.
4. OpenAPI / Swagger documentation updated if new endpoints were exposed.
