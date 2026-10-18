`jakarta.validation.Payload`는 스프링부트에서 유효성 검사(Validation) 제약 조건에 추가적인 메타데이터를 전달하기 위해 사용하는 인터페이스입니다.

스프링부트 전용 기능은 아니며, Jakarta Bean Validation에서 제공하는 기능입니다. 일반적인 입력값 검증에서는 자주 직접 사용할 필요가 없지만, 사용자 정의 검증 어노테이션을 만들 때 알아두면 좋습니다.

### 1\. Payload의 역할

예를 들어 회원가입에서 아이디가 비어 있는지 검사한다고 가정하겠습니다.

```java
@NotBlank(message = "아이디를 입력하세요.")
private String username;
```

여기서 `message`는 검증에 실패했을 때 보여줄 오류 메시지입니다.

그렇다면 검증 실패에 추가적인 분류 정보도 붙이고 싶다면 어떻게 할까요?

예를 들어 다음과 같이 구분할 수 있습니다.

* 일반적인 검증 오류

* 심각도가 높은 검증 오류

* 특정 업무에서 별도로 처리해야 하는 검증 오류

이러한 추가 정보를 전달하는 데 `Payload`를 활용할 수 있습니다. 다만 실제 분류나 처리 방식은 개발자가 별도로 구현해야 합니다.

### 2\. 사용자 정의 어노테이션에서의 사용법

앞에서 배운 `@Constraint`와 연결해서 살펴보겠습니다.

```java
import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = UsernameValidator.class)
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidUsername {

    String message() default "아이디 형식이 올바르지 않습니다.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
```

여기서 다음 코드가 핵심입니다.

```java
Class<? extends Payload>[] payload() default {};
```

각 부분의 의미는 다음과 같습니다.

* `Payload`: 추가 메타데이터를 표시하기 위한 인터페이스입니다.

* `Class<? extends Payload>[]`: `Payload`를 구현한 클래스들의 타입을 배열로 받습니다.

* `default {}`: 별도로 지정하지 않으면 빈 배열을 사용합니다.

이 `payload()` 메서드는 Bean Validation에서 제약 조건 어노테이션을 정의할 때 사용하는 표준적인 구성 요소입니다.

### 3\. 실제로 Payload를 지정하는 예제

먼저 추가 정보의 종류를 나타내는 클래스를 만듭니다.

```java
import jakarta.validation.Payload;

public class CriticalError implements Payload {
}
```

이제 사용자 정의 어노테이션을 사용할 때 지정할 수 있습니다.

```java
@ValidUsername(
    payload = CriticalError.class
)
private String username;
```

이렇게 하면 해당 제약 조건에 `CriticalError`라는 페이로드 유형을 연결할 수 있습니다.

단, 이것만으로 오류가 자동으로 심각한 오류로 처리되거나 사용자에게 별도의 메시지가 표시되지는 않습니다. 검증 결과에서 페이로드를 읽고 활용하는 추가 로직이 필요합니다.

### 4\. Payload와 다른 개념 비교

| 항목                  | 역할                    |
| ------------------- | --------------------- |
| message()           | 검증 실패 시 사용할 오류 메시지    |
| groups()            | 검증 그룹 지정              |
| payload()           | 검증 제약 조건에 추가 메타데이터 연결 |
| ConstraintValidator | 실제 유효성 검사 수행          |

핵심 정리: `jakarta.validation.Payload`는 유효성 검사 결과 자체를 반환하거나 검증을 수행하는 기능이 아니라, 제약 조건에 추가적인 메타데이터를 연결하기 위한 인터페이스입니다. 일반적인 스프링부트 입력 검증에서는 `message()`와 `groups()`를 더 자주 사용하며, `payload()`는 특별한 검증 분류가 필요할 때 활용할 수 있습니다.
