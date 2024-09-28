# Method Security.

Examples on how to use custom user and UserDetailsService and slice test your application's method security.

## Project Structure

```
├───main
│   ├───java
│   │   └───org
│   │       └───punna
│   │           └───methodsecurity
│   │               │   MethodSecurityApplication.java
│   │               │
│   │               ├───config
│   │               │       SecurityConfig.java
│   │               │
│   │               ├───model
│   │               │       User.java
│   │               │
│   │               ├───repository
│   │               │       UserRepository.java
│   │               │
│   │               └───service
│   │                   │   CustomUserDetailsService.java
│   │                   │
│   │                   └───impl
│   │                           EmployeeService.java
│   │
│   └───resources
│           application.properties
└───test
    └───java
        └───org
            └───punna
                └───methodsecurity
                        MethodSecurityApplicationTests.java

```
[Spring's Method Security](https://docs.spring.io/spring-security/reference/servlet/authorization/method-security.html) \
[Spring Method Security Testing](https://docs.spring.io/spring-security/reference/servlet/test/method.html)

Start from tests to get full understanding.