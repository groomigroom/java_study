`org.slf4j.LoggerFactory`는 스프링 부트에서 로그를 기록할 수 있는 `Logger` 객체를 생성하거나 가져오는 역할을 하는 클래스입니다.

앞서 배운 `org.slf4j.Logger`가 로그를 기록하는 도구라면, `LoggerFactory`는 그 도구를 준비해 주는 역할이라고 이해하면 됩니다.

### 1\. 패키지 이름 분석

```java
org.slf4j.LoggerFactory
```

| 구성 요소         | 의미                      |
| ------------- | ----------------------- |
| org.slf4j     | SLF4J 로깅 라이브러리의 패키지     |
| LoggerFactory | Logger 객체를 가져오는 팩토리 클래스 |

### 2\. 실제 스프링 부트 코드에서 사용하기

```java
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private static final Logger log =
            LoggerFactory.getLogger(UserService.class);

    public void login() {
        log.info("로그인을 시작합니다.");
        log.debug("로그인 처리 과정을 확인합니다.");
        log.error("로그인 처리 중 오류가 발생했습니다.");
    }
}
```

핵심 코드는 다음 부분입니다.

```java
private static final Logger log =
        LoggerFactory.getLogger(UserService.class);
```

* `Logger`: 로그를 기록하기 위한 인터페이스입니다.

* `LoggerFactory`: `Logger` 객체를 가져오는 클래스입니다.

* `getLogger(UserService.class)`: `UserService` 클래스에 사용할 Logger를 가져옵니다.

* `log`: 이후 `info()`, `debug()`, `error()` 등의 메서드를 호출할 때 사용하는 변수입니다.

여기서 `UserService.class`를 전달하면 일반적으로 로그에 `UserService`라는 클래스 정보를 표시할 수 있습니다.

### 3\. `Logger`와 `LoggerFactory`의 차이

LoggerFactory

Logger 객체를 가져오는 역할

getLogger(UserService.class)

Logger

info(), debug(), warn(), error() 등으로 로그 기록

### 4\. `System.out.println()`과 비교하면?

일반적인 출력은 다음과 같이 작성합니다.

```java
System.out.println("회원가입이 완료되었습니다.");
```

반면 LoggerFactory를 통해 가져온 Logger는 다음과 같이 사용합니다.

```java
log.info("회원가입이 완료되었습니다.");
```

두 코드 모두 메시지를 출력할 수 있지만, Logger는 로그 레벨을 구분하고 설정에 따라 출력 여부, 형식, 저장 위치 등을 관리할 수 있습니다.

### 5\. 스프링 부트에서는 꼭 직접 작성해야 할까?

반드시 그렇지는 않습니다. Lombok을 사용하는 프로젝트라면 `@Slf4j`로 Logger 선언을 간단하게 만들 수 있습니다.

```java
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class UserService {

    public void login() {
        log.info("로그인을 시작합니다.");
    }
}
```

`@Slf4j`가 컴파일 시 Logger 선언을 생성하므로 `LoggerFactory.getLogger()`를 직접 작성하지 않아도 됩니다.

핵심 정리

* `LoggerFactory` = 로그 기록 도구인 `Logger`를 가져오는 클래스

* `Logger` = 실제 로그 메시지를 기록하는 인터페이스

* `LoggerFactory.getLogger()` = 사용할 Logger를 가져오는 메서드

스프링 부트에서 오류를 추적하거나 서비스의 실행 과정을 확인할 때 이 두 가지를 함께 사용하는 경우가 많습니다.
