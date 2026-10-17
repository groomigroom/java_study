org.springframework.stereotype.Repository

스프링 부트에서 `@Repository`는 해당 클래스가 데이터 접근 계층(DAO, Repository 역할)을 담당한다는 것을 스프링에게 알려주는 어노테이션이야.

java
```
import org.springframework.stereotype.Repository;

@Repository
public class UserRepository {

    public void save() {
        System.out.println("사용자 저장");
    }
}
```

이 코드에서 `@Repository`가 어떤 역할을 하는지 살펴보자.

## 1. `@Repository`의 주요 역할

### ① 스프링 빈(Bean)으로 등록

`@Repository`가 붙은 클래스는 스프링의 컴포넌트 스캔 대상이 돼. 스프링 부트가 해당 클래스를 찾아서 빈으로 등록하므로, 다른 클래스에서 의존성 주입을 받아 사용할 수 있어.

java
```
@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
}
```

위 코드처럼 `UserService`에서 `UserRepository`를 생성자 주입으로 사용할 수 있어. 단, 컴포넌트 스캔 범위 안에 클래스가 있어야 해.

### ② 데이터 접근 계층임을 명시

일반적으로 애플리케이션을 다음과 같이 계층별로 나눠서 설계해.

Controller

HTTP 요청 처리

Service

비즈니스 로직 처리

Repository

데이터 조회 및 저장 (DB 접근)

예를 들어 사용자를 조회한다면 다음과 같은 흐름이야.

1. `Controller`: 사용자 조회 요청을 받음
2. `Service`: 사용자 조회에 필요한 로직을 처리함
3. `Repository`: DB에서 사용자 데이터를 조회함

### ③ 데이터 접근 예외를 변환

`@Repository`는 데이터 접근 계층에서 발생하는 특정 예외를 스프링의 `DataAccessException` 계열 예외로 변환하는 기능과도 관련이 있어.

단, 이 기능은 스프링의 예외 변환 기능이 적용되는 환경에서 동작하며, 단순히 어노테이션을 붙였다고 모든 예외가 자동 변환되는 것은 아니야.

## 2. `@Component`, `@Service`, `@Repository`의 차이

| 어노테이션 | 주된 역할 |
| --- | --- |
| `@Component` | 일반적인 스프링 관리 객체 |
| `@Service` | 비즈니스 로직 처리 |
| `@Repository` | 데이터 접근 및 저장 |
| `@Controller` | 웹 요청 처리 |

이 어노테이션들은 모두 스프링의 컴포넌트 스캔을 통해 빈으로 등록될 수 있어. 다만 각 계층의 역할을 명확히 표현한다는 차이가 있어.

## 3. Spring Data JPA에서는 어떻게 사용할까?

Spring Data JPA를 사용한다면 직접 `@Repository` 클래스를 작성하지 않아도 되는 경우가 많아.

java
```
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository
        extends JpaRepository<User, Long> {
}
```

이렇게 `JpaRepository`를 상속하면 스프링 데이터 JPA가 런타임에 구현체를 제공하고, 별도의 구현 코드 없이도 기본적인 CRUD 작업을 할 수 있어.

java
```
userRepository.save(user);       // 저장
userRepository.findById(1L);     // ID로 조회
userRepository.findAll();        // 전체 조회
userRepository.deleteById(1L);   // 삭제
```

이 경우에는 인터페이스에 `@Repository`를 직접 붙이지 않아도 일반적으로 정상 동작해.

핵심 정리: `@Repository`는 단순히 DB에 접근하는 클래스라는 표시를 넘어, 스프링 빈 등록과 데이터 접근 계층의 예외 변환 기능에 관련된 어노테이션이야.

쉽게 기억하면 `@Service`는 비즈니스 로직, `@Repository`는 데이터 접근이라고 이해하면 돼.
