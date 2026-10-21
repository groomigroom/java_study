스프링 부트에서 `jakarta.validation.constraints.Max`는 숫자 값이 지정한 최댓값 이하인지 검사하는 유효성 검증(Validation) 어노테이션이야.

## 1. 기본 사용법

java
```
import jakarta.validation.constraints.Max;

public class UserRequest {

    @Max(100)
    private int age;
}
```

이 코드는 `age`가 100 이하인지 검사해.

| 입력값 | 검증 결과 |
| --- | --- |
| `20` | 통과 |
| `100` | 통과 |
| `101` | 실패 |
| `-1` | 통과 |

`@Max(100)`은 최댓값만 제한하므로, 음수를 허용하지 않으려면 `@Min(0)`도 함께 사용하면 돼.

java
```
@Min(0)
@Max(100)
private int age;
```

이렇게 하면 `0~100` 범위의 값만 허용해.

## 2. 스프링 부트에서 검증하는 방법

DTO에 어노테이션을 선언하고, 컨트롤러에서 `@Valid`를 사용해 검증할 수 있어.

java
```
public class UserRequest {

    @Max(value = 100, message = "최대 100까지 입력할 수 있습니다.")
    private Integer score;
}
```

java
```
@PostMapping("/users")
public String createUser(@Valid @RequestBody UserRequest request) {
    return "성공";
}
```

검증 기능을 사용하려면 일반적으로 `spring-boot-starter-validation` 의존성이 필요해.

## 3. 알아둘 점

- `@Max(100)`은 100 이하를 의미해. 100도 허용해.
- `@Max`는 `int`, `long`, `double` 같은 모든 숫자 타입에 적용되는 것은 아니야. 정수 타입 및 `BigDecimal`, `BigInteger` 등 지원되는 숫자 타입에 사용해야 해. `float`과 `double`에는 적용할 수 없어.
- `null` 값은 `@Max`만으로 거부하지 않아. 필수값이라면 `@NotNull`을 함께 사용해.
- `jakarta.validation.constraints.Max`는 스프링 부트 3 이상에서 사용하는 패키지명이야.

참고로 최소값을 제한하는 `@Min`과 최댓값을 제한하는 `@Max`를 함께 사용하면 숫자의 허용 범위를 지정할 수 있어.

원하면  @Min과 @Max의 차이 도 알아볼 수 있어.
