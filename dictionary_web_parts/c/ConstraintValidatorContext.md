`jakarta.validation.ConstraintValidatorContext`는 스프링 부트에서 사용자 정의 유효성 검사(Validation)를 수행할 때 오류 메시지를 설정하거나 검증 오류의 세부 정보를 제어할 수 있도록 제공하는 인터페이스입니다.

쉽게 말하면, `ConstraintValidator`가 입력값을 검사하는 역할이라면, `ConstraintValidatorContext`는 검증에 실패했을 때 어떤 오류 메시지를 표시할지 등을 제어하는 역할을 합니다.

### 1\. ConstraintValidatorContext의 패키지 의미

* `jakarta.validation`: 자바 객체의 유효성 검사를 위한 API 패키지

* `ConstraintValidatorContext`: 유효성 검사 과정의 오류 메시지와 관련된 정보를 다루는 인터페이스

스프링 부트 전용 클래스는 아니며, Jakarta Bean Validation API에서 제공하는 인터페이스입니다.

### 2\. 기본 사용법

예를 들어 아이디가 `user_`로 시작해야 한다는 사용자 정의 검증 규칙을 만들어 보겠습니다.

```java
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class UserIdValidator
        implements ConstraintValidator<ValidUserId, String> {

    @Override
    public boolean isValid(
            String value,
            ConstraintValidatorContext context) {

        if (value == null) {
            return true;
        }

        if (!value.startsWith("user_")) {

            context.disableDefaultConstraintViolation();

            context.buildConstraintViolationWithTemplate(
                    "아이디는 user_로 시작해야 합니다."
            ).addConstraintViolation();

            return false;
        }

        return true;
    }
}
```

### 코드 설명

| 코드                                     | 의미                     |
| -------------------------------------- | ---------------------- |
| ConstraintValidatorContext context     | 검증 과정의 오류 정보를 제어하는 객체  |
| disableDefaultConstraintViolation()    | 기본 오류 메시지의 자동 생성을 비활성화 |
| buildConstraintViolationWithTemplate() | 표시할 사용자 정의 오류 메시지를 설정  |
| addConstraintViolation()               | 설정한 검증 오류를 추가          |
| return false                           | 유효성 검사 실패              |
| return true                            | 유효성 검사 통과              |

여기서는 아이디가 `user_`로 시작하지 않으면 기본 오류 메시지 대신 `"아이디는 user_로 시작해야 합니다."`라는 메시지를 설정합니다.

### 3\. 기본 메시지와 사용자 정의 메시지 비교

기본 메시지

```java
@Constraint(validatedBy = UserIdValidator.class)
public @interface ValidUserId {
    String message() default "아이디 형식이 올바르지 않습니다.";
}
```

애너테이션에 정의된 기본 오류 메시지를 사용할 수 있습니다.

사용자 정의 메시지

```java
context.disableDefaultConstraintViolation();

context.buildConstraintViolationWithTemplate(
    "아이디는 user_로 시작해야 합니다."
).addConstraintViolation();
```

검증 상황에 따라 구체적인 오류 메시지를 직접 설정할 수 있습니다.

주의할 점은 `disableDefaultConstraintViolation()`을 호출하면 기본 오류 메시지가 자동으로 추가되지 않으므로, 사용자 정의 오류를 만들 때는 `addConstraintViolation()`까지 호출해야 한다는 것입니다.

### 4\. ConstraintValidator와의 관계

| 구성 요소                      | 역할                           |
| -------------------------- | ---------------------------- |
| @ValidUserId               | 검증 규칙을 정의하는 애너테이션            |
| ConstraintValidator        | 검증 규칙을 실제 코드로 구현하는 인터페이스     |
| ConstraintValidatorContext | 검증 오류 메시지와 세부 정보를 제어하는 인터페이스 |

핵심 정리: `ConstraintValidatorContext`는 사용자 정의 유효성 검사에서 오류 메시지를 직접 설정하거나 검증 오류를 추가할 때 사용하는 객체입니다. 특히 입력값에 따라 서로 다른 오류 메시지를 보여주고 싶을 때 유용합니다.
