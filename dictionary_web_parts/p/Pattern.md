`java.util.regex.Pattern`은 스프링 부트에서 입력값을 검사하거나 문자열에서 특정 패턴을 찾을 때 사용하는 자바의 정규표현식(Regular Expression) 클래스입니다.

쉽게 말하면, 사용자가 입력한 아이디, 이메일, 전화번호 등이 정해진 형식에 맞는지 확인할 때 활용합니다.

`Pattern`은 스프링 부트 전용 클래스가 아니라 자바 표준 라이브러리에 속합니다. 스프링 부트에서도 그대로 사용할 수 있습니다.

### 1\. 패키지 이름 분석

```java
java.util.regex.Pattern
```

| 구성 요소   | 의미                        |
| ------- | ------------------------- |
| java    | 자바 표준 라이브러리               |
| util    | 유틸리티 기능                   |
| regex   | Regular Expression, 정규표현식 |
| Pattern | 정규표현식을 컴파일하고 패턴을 관리하는 클래스 |

### 2\. 정규표현식이란?

정규표현식은 문자열이 특정 규칙에 맞는지 검사하기 위한 표현식입니다.

| 정규표현식                      | 의미             | 예시            |
| -------------------------- | -------------- | ------------- |
| [0-9]+                     | 숫자가 1개 이상      | 12345         |
| [a-zA-Z]+                  | 영문자가 1개 이상     | Spring        |
| ^[0-9]{4}$                 | 숫자 4자리만 허용     | 1234          |
| ^[a-zA-Z0-9]+$             | 영문자와 숫자만 허용    | user123       |
| \\\\d{3}-\\\\d{4}-\\\\d{4} | 전화번호 형식의 숫자 패턴 | 010-1234-5678 |

### 3\. 실제 스프링 부트 코드 예제

예를 들어 회원가입 시 아이디가 영문자와 숫자로만 구성되어 있는지 확인한다고 가정하겠습니다.

```java
import java.util.regex.Pattern;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    public boolean validateUserId(String userId) {

        Pattern pattern =
            Pattern.compile("^[a-zA-Z0-9]+$");

        return pattern.matcher(userId).matches();
    }
}
```

코드를 한 줄씩 살펴보겠습니다.

* `Pattern.compile("^[a-zA-Z0-9]+$")`: 검사할 정규표현식을 준비합니다.

* `pattern.matcher(userId)`: 검사할 문자열을 지정합니다.

* `.matches()`: 문자열 전체가 패턴과 일치하는지 확인합니다.

실행 결과는 다음과 같습니다.

| 입력값        | 결과    | 이유          |
| ---------- | ----- | ----------- |
| user123    | true  | 영문자와 숫자로 구성 |
| Spring2026 | true  | 영문자와 숫자로 구성 |
| user_123   | false | 밑줄 포함       |
| user 123   | false | 공백 포함       |

### 4\. `Pattern`과 `Matcher`의 차이

정규표현식을 사용할 때 두 클래스를 함께 사용하는 경우가 많습니다.

`Pattern`

검사할 규칙을 준비합니다.

```java
Pattern pattern =
    Pattern.compile("[0-9]+");
```

`Matcher`

준비한 규칙으로 문자열을 검사합니다.

```java
boolean result =
    pattern.matcher("12345").matches();
```

`Pattern`은 규칙을 준비하고, `Matcher`는 그 규칙을 실제 문자열에 적용합니다.

### 5\. 스프링 부트의 `@Pattern`과는 다를까?

이름이 비슷하지만 서로 다릅니다.

| 구분    | java.util.regex.Pattern | jakarta.validation.constraints.Pattern |
| ----- | ----------------------- | -------------------------------------- |
| 종류    | 자바 클래스                  | 유효성 검사 애너테이션                           |
| 사용 목적 | 코드에서 직접 문자열 검사          | DTO 등의 필드 입력값 검증                       |
| 사용 방식 | compile(), matcher()    | @Pattern(regexp = "...")               |

예를 들어 DTO에서 아이디 형식을 검사할 때는 다음과 같이 사용할 수 있습니다.

```java
import jakarta.validation.constraints.Pattern;

public class UserForm {

    @Pattern(
        regexp = "^[a-zA-Z0-9]+$",
        message = "아이디는 영문자와 숫자만 사용할 수 있습니다."
    )
    private String userId;
}
```

이 방식은 스프링의 유효성 검사 기능과 연결해 사용할 수 있으며, 일반적으로 검증을 실행할 때 `@Valid` 또는 `@Validated` 등을 활용합니다.

핵심 정리: `java.util.regex.Pattern`은 문자열이 특정 규칙에 맞는지 검사하기 위한 자바 클래스입니다. 스프링 부트에서는 회원가입, 로그인, 전화번호 검사, 파일명 검사 등 다양한 입력값 검증에 활용할 수 있습니다.
