스프링 부트에서 `jakarta.validation.constraints.Pattern`은 문자열이 지정한 정규표현식(Regex)에 맞는지 검사하는 유효성 검증(Validation) 어노테이션이야.

## 1. 기본 사용법

예를 들어 전화번호 형식을 검사하고 싶다면 다음과 같이 사용할 수 있어.

java
```
import jakarta.validation.constraints.Pattern;

public class UserRequest {

    @Pattern(regexp = "^[0-9]{11}$")
    private String phoneNumber;
}
```

이 코드는 `phoneNumber`가 숫자 11자리로 구성되어 있는지 검사해.

- `"01012345678"` → 통과
- `"010-1234-5678"` → 실패
- `"abc123"` → 실패

## 2. 자주 사용하는 예제

| 검증 목적 | 정규표현식 |
| --- | --- |
| 숫자만 허용 | `^[0-9]+$` |
| 영문자만 허용 | `^[a-zA-Z]+$` |
| 영문자와 숫자만 허용 | `^[a-zA-Z0-9]+$` |
| 숫자 4자리 | `^[0-9]{4}$` |
| 한국 휴대폰 번호 형식 | `^010[0-9]{8}$` |

사용 예시:

java
```
@Pattern(regexp = "^[a-zA-Z0-9]+$",
         message = "영문자와 숫자만 입력할 수 있습니다.")
private String username;
```

검증에 실패하면 `message`에 지정한 메시지를 사용할 수 있어.

## 3. 스프링 부트에서 실제로 검증하려면?

`@Pattern`을 선언하는 것만으로 모든 상황에서 자동 검증되는 것은 아니야. 일반적으로 DTO에 선언하고, 컨트롤러에서 `@Valid`를 사용해 검증을 실행해.

java
```
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {

    @PostMapping("/users")
    public String createUser(
            @Valid @RequestBody UserRequest request) {
        return "성공";
    }
}
```

검증을 사용하려면 보통 `spring-boot-starter-validation` 의존성도 필요해.

gradle
```
implementation 'org.springframework.boot:spring-boot-starter-validation'
```

## 4. 알아둘 점

- `@Pattern`은 기본적으로 문자열의 형식을 검사해.
- `null` 값은 `@Pattern`만으로 거부하지 않아. 필수 입력값이라면 `@NotNull` 또는 문자열에 `@NotBlank`를 함께 사용할 수 있어.
- `jakarta.validation.constraints.Pattern`은 정규표현식 기반 검증을 위한 표준 어노테이션이야.

참고로 스프링 부트 2에서는 주로 `javax.validation.constraints.Pattern`을 사용했고, 스프링 부트 3부터는 `jakarta.validation.constraints.Pattern`을 사용해.

더 알아보고 싶다면  정규표현식 문법 설명 또는  @Pattern과 @Valid의 차이 도 확인해 볼 수 있어.
