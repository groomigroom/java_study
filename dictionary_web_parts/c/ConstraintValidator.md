`jakarta.validation.ConstraintValidator`는 스프링 부트에서 사용자 정의 유효성 검사(Validation)를 구현할 때 사용하는 인터페이스입니다.

쉽게 말하면, 사용자가 입력한 값이 개발자가 정한 조건에 맞는지 직접 검사할 수 있도록 해줍니다.

예를 들어 회원가입에서 아이디에 특정 규칙을 적용하거나, 입력한 값이 사업자등록번호 형식에 맞는지 검사할 때 사용할 수 있습니다.

### 1\. ConstraintValidator의 의미

패키지와 이름을 나누어 보면 다음과 같습니다.

* `jakarta.validation`: 자바 객체의 유효성 검사를 위한 API 패키지

* `ConstraintValidator`: 사용자 정의 검증 로직을 구현하기 위한 인터페이스

스프링 부트에서는 Jakarta Bean Validation을 활용하여 입력값을 검사할 수 있습니다.

### 2\. 기본 사용법

예를 들어 아이디가 반드시 `user_`로 시작해야 한다는 규칙을 만들어 보겠습니다.

1단계. 사용자 정의 검증 애너테이션 만들기

`ValidUserId.java`

```java
import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.*;

@Constraint(validatedBy = UserIdValidator.class)
@Target({
    ElementType.FIELD,
    ElementType.PARAMETER
})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidUserId {
    String message() default "아이디는 user_로 시작해야 합니다.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
```

2단계. ConstraintValidator 구현하기

`UserIdValidator.java`

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

        return value.startsWith("user_");
    }
}
```

3단계. DTO에 적용하기

`UserForm.java`

```java
import jakarta.validation.constraints.NotBlank;

public class UserForm {

    @NotBlank(message = "아이디를 입력하세요.")
    @ValidUserId
    private String userid;

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }
}
```

이제 `userid`에 `user_123`을 입력하면 사용자 정의 검증을 통과하지만, `admin123`을 입력하면 실패합니다.

여기서 `value == null`일 때 `true`를 반환하는 이유는 접두사 검사와 필수 입력 검사를 분리하기 위해서입니다. `@NotBlank`가 필수 입력 여부를 담당합니다.

### 3\. ConstraintValidator의 핵심 메서드

```java
boolean isValid(
    String value,
    ConstraintValidatorContext context
);
```

| 구성 요소     | 의미                            |
| --------- | ----------------------------- |
| isValid() | 실제 유효성 검사 수행                  |
| value     | 검사할 실제 입력값                    |
| context   | 검증 과정에서 오류 메시지 등의 정보를 제어하는 객체 |
| true 반환   | 유효성 검사 통과                     |
| false 반환  | 유효성 검사 실패                     |

`ConstraintValidator<ValidUserId, String>`에서 첫 번째 타입인 `ValidUserId`는 검사 규칙을 정의한 애너테이션이고, 두 번째 타입인 `String`은 검사할 값의 자료형입니다.

### 4\. 스프링 부트에서 사용하는 이유

* 회원가입 시 아이디 형식 검사

* 비밀번호 규칙 검사

* 전화번호 형식 검사

* 특정 업무 규칙에 따른 입력값 검사

* 여러 DTO에서 공통으로 사용하는 검증 로직 재사용

단, 위 예제는 인터페이스를 구현하는 코드입니다. 실제로 `@Valid` 또는 `@Validated`를 통해 검증하려면 프로젝트에 Bean Validation 구현체가 설정되어 있어야 합니다. 일반적으로 스프링 부트에서는 `spring-boot-starter-validation` 의존성을 사용합니다.

핵심 정리: `ConstraintValidator`는 개발자가 직접 정의한 검증 규칙을 실제 코드로 구현하는 인터페이스입니다. `@Constraint` 애너테이션과 연결하면 스프링 부트의 DTO나 요청 파라미터에 사용자 정의 유효성 검사를 적용할 수 있습니다.
