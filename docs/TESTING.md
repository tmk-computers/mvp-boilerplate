# Testing Strategy Specification (`TESTING.md`)

> **DASP Digital MVP Boilerplate Framework**  
> Unit Testing | Mockito | Spring Integration Tests | Code Coverage

---

## 🧪 1. Backend Testing Pyramid

```text
       / \
      /   \     End-to-End Tests (Postman / RestAssured)
     /-----\
    /       \   Integration Tests (@SpringBootTest, Testcontainers)
   /---------\
  /           \ Unit Tests (JUnit 5, Mockito, @DataJpaTest)
 /-------------\
```

### Unit Test Example (Service Layer)
```java
@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserMapper userMapper;

    @InjectMocks
    private UserServiceImpl userService;

    @Test
    void shouldReturnUserResponse_WhenValidIdGiven() {
        UUID userId = UUID.randomUUID();
        User user = new User();
        user.setId(userId);
        
        when(userRepository.findByIdAndIsDeletedFalse(userId)).thenReturn(Optional.of(user));
        
        UserResponseDto response = userService.findById(userId);
        
        assertNotNull(response);
        verify(userRepository).findByIdAndIsDeletedFalse(userId);
    }
}
```

---

## 🚦 2. Running Automated Tests

```bash
# Backend unit & integration tests
cd backend
./mvnw test

# Web Frontend linter and type check
cd frontend
npm run lint

# Mobile type safety check
cd mobile
npx tsc --noEmit
```
