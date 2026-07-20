# Coding Standards & Clean Code Specification (`CODING-STANDARDS.md`)

> **DASP Digital MVP Boilerplate Framework**  
> Standards: Google Java Style Guide | AirBnb React/TS Style Guide | Clean Code Guidelines

---

## ☕ 1. Java & Spring Boot Guidelines

1. **Naming Conventions**:
   - Classes: PascalCase (`CustomerServiceImpl`)
   - Methods & Fields: camelCase (`calculateTotalAmount`)
   - Constants: UPPER_SNAKE_CASE (`DEFAULT_PAGE_SIZE`)
2. **Immutability & Lombok**:
   - Prefer constructor injection over `@Autowired` on fields. Use `@RequiredArgsConstructor`.
   - DTOs should use `@Value` or Java 21 `record` types.
3. **No Raw Types or Wildcards**: Avoid untyped collections or wildcards in generic responses.

---

## ⚛️ 2. React & TypeScript Guidelines

1. **Explicit Return Types**: Functions must declare explicit return types or rely on strict TypeScript inference.
2. **No `any` Type**: Usage of `any` is strictly prohibited. Use `unknown` or explicit generics.
3. **Component Structure**: Single responsibility per file. Export named components or default page exports.

---

## 📱 3. React Native Guidelines

1. **StyleSheets**: Use `StyleSheet.create()` or Tailwind class helpers for performance.
2. **Asynchronous Operations**: Wrap native device hardware calls in standard try/catch blocks with user toast errors.
