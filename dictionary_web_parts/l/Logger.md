`org.slf4j.Logger`는 스프링(Spring)에서 로그(log)를 기록하기 위해 사용하는 인터페이스입니다.

쉽게 말하면, 프로그램이 실행되는 동안 어떤 작업이 진행되었는지, 오류가 발생했는지 등을 콘솔이나 로그 파일에 기록할 수 있게 해 줍니다.

### 1\. 패키지 이름 분석

```java
org.slf4j.Logger
```

| 구성 요소  | 의미                             |
| ------ | ------------------------------ |
| org    | 자바 패키지의 최상위 이름 중 하나            |
| slf4j  | Simple Logging Facade for Java |
| Logger | 로그를 기록하기 위한 인터페이스              |

SLF4J는 로그를 기록하는 기능과 실제 로그 처리 라이브러리를 연결해 주는 역할을 합니다. 스프링 부트에서는 일반적으로 Logback이 기본 로깅 구현체로 사용됩니다.

### 2\. 실제 코드에서 어떻게 사용할까?

예를 들어 회원가입 기능을 개발한다고 가정해 볼게요.

```java
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private static final Logger log =
            LoggerFactory.getLogger(UserService.class);

    public void join() {
        log.info("회원가입을 시작합니다.");

        try {
            // 회원가입 처리 로직
            log.info("회원가입이 완료되었습니다.");
        } catch (Exception e) {
            log.error("회원가입 중 오류가 발생했습니다.", e);
        }
    }
}
```

여기서 중요한 부분은 세 가지입니다.

* `Logger`: 로그를 기록할 때 사용하는 인터페이스입니다.

* `LoggerFactory.getLogger(UserService.class)`: `UserService` 클래스에서 사용할 Logger 객체를 가져옵니다.

* `log.info()`, `log.error()`: 각각 정보성 메시지와 오류 메시지를 기록합니다.

### 3\. 로그 레벨 5가지

| 메서드         | 의미           | 사용 예시         |
| ----------- | ------------ | ------------- |
| log.trace() | 매우 상세한 실행 정보 | 세부적인 내부 처리 과정 |
| log.debug() | 디버깅 정보       | 변수 값 확인       |
| log.info()  | 일반적인 실행 정보   | 회원가입 성공       |
| log.warn()  | 주의가 필요한 상황   | 예상하지 못한 입력값   |
| log.error() | 오류 발생        | 데이터베이스 연결 실패  |

예를 들어 다음과 같이 작성할 수 있습니다.

```java
log.debug("사용자 ID: {}", userId);
log.info("로그인 성공");
log.warn("로그인 시도 횟수가 많습니다.");
log.error("데이터베이스 처리에 실패했습니다.");
```

`{}`는 로그 메시지에 변수 값을 넣기 위한 자리 표시자입니다.

### 4\. `System.out.println()`과 차이점은?

| 구분    | System.out.println() | Logger                 |
| ----- | -------------------- | ---------------------- |
| 목적    | 간단한 출력               | 운영 및 개발용 로그 기록         |
| 로그 레벨 | 없음                   | TRACE, DEBUG, INFO 등   |
| 출력 제어 | 직접 처리                | 설정으로 레벨과 출력 방식 제어      |
| 예외 정보 | 별도로 출력해야 함           | 예외 객체를 전달해 상세 정보 기록 가능 |
| 실무 활용 | 간단한 테스트              | 스프링 부트의 일반적인 로깅 방식     |

예를 들어 다음 두 코드는 비슷한 메시지를 출력하지만, Logger는 로그 레벨과 출력 설정을 활용할 수 있습니다.

```java
System.out.println("회원가입 완료");
```

```java
log.info("회원가입 완료");
```

### 5\. 스프링 부트에서 왜 중요할까?

실제 웹 애플리케이션을 개발하면 다음과 같은 상황을 확인해야 합니다.

* 사용자가 로그인에 성공했는가?

* 데이터베이스에 데이터가 저장되었는가?

* 특정 API가 호출되었는가?

* 서버에서 예외가 발생했는가?

이때 `org.slf4j.Logger`를 활용하면 프로그램의 실행 과정을 추적하고 오류를 분석하기 쉬워집니다.

핵심 정리: `org.slf4j.Logger`는 스프링 자체의 클래스가 아니라 SLF4J 라이브러리의 인터페이스입니다. 스프링 부트에서는 로그를 체계적으로 기록하고 관리하기 위해 자주 사용합니다.
