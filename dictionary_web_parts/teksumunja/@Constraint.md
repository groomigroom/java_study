`jakarta.validation.Constraint`는 스프링부트에서 사용자 정의 유효성 검사 어노테이션을 만들 때, 해당 어노테이션이 어떤 검증 규칙을 사용하는지 지정하는 메타 어노테이션입니다.

주로 사용자가 입력한 아이디, 비밀번호, 이메일, 게시글 내용 등이 정해진 조건을 만족하는지 검사할 때 관련됩니다.

스프링부트 전용 기능은 아니며, Jakarta Bean Validation 명세에서 제공하는 기능입니다.

### 1\. Constraint의 역할

예를 들어 회원가입 화면에서 사용자가 아이디를 입력한다고 가정하겠습니다.

* 아이디가 비어 있으면 안 됨

* 아이디는 5자 이상이어야 함

* 아이디에 특수문자를 사용할 수 없음

이러한 입력 조건을 검증할 때 스프링부트에서는 `@NotBlank`, `@Size`, `@Pattern` 같은 어노테이션을 사용할 수 있습니다.

그렇다면 개발자가 직접 `@ValidUsername`이라는 사용자 정의 어노테이션을 만들려면 어떻게 해야 할까요?

이때 `@Constraint`를 사용합니다.

### 2\. 실제 코드로 이해하기

사용자 이름이 영문자와 숫자로만 구성되어 있는지 검사하는 어노테이션을 만들어 보겠습니다.

먼저 사용자 정의 어노테이션을 작성합니다.

```java
import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = UsernameValidator.class)
@Target({
    ElementType.FIELD,
    ElementType.PARAMETER
})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidUsername {

    String message() default "아이디는 영문자와 숫자만 가능합니다.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
```

여기서 핵심은 다음 코드입니다.

```java
@Constraint(validatedBy = UsernameValidator.class)
```

* `@Constraint`: 사용자 정의 어노테이션을 유효성 검사 제약 조건으로 등록합니다.

* `validatedBy`: 실제 검증을 수행할 클래스를 지정합니다.

* `UsernameValidator.class`: 아이디가 조건에 맞는지 검사할 클래스입니다.

즉, `@Constraint` 자체가 아이디를 검사하는 것은 아닙니다. 어떤 검증 클래스를 사용하여 검사할지 연결하는 역할을 합니다.

### 3\. 실제 검증을 수행하는 클래스

이제 아이디를 검사하는 `UsernameValidator` 클래스를 작성합니다.

```java
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class UsernameValidator
        implements ConstraintValidator<ValidUsername, String> {

    @Override
    public boolean isValid(
            String value,
            ConstraintValidatorContext context) {

        if (value == null) {
            return true;
        }

        return value.matches("[a-zA-Z0-9]+");
    }
}
```

코드의 의미는 다음과 같습니다.

* `ConstraintValidator`: 유효성 검사 로직을 구현하기 위한 인터페이스입니다.

* `ValidUsername`: 검증에 사용할 사용자 정의 어노테이션입니다.

* `String`: 검사 대상 데이터의 자료형입니다.

* `isValid()`: 실제 검증을 수행하는 메서드입니다.

* `value.matches("[a-zA-Z0-9]+")`: 영문자와 숫자로만 구성되어 있는지 검사합니다.

참고로 위 예제에서 `null`은 별도로 허용합니다. 값이 비어 있으면 안 된다는 조건까지 필요하다면 `@NotBlank` 등을 추가해야 합니다.

### 4\. 스프링부트에서 사용하는 방법

예를 들어 회원가입 정보를 전달받는 DTO에 적용할 수 있습니다.

```java
import jakarta.validation.constraints.NotBlank;

public class SignupForm {

    @NotBlank(message = "아이디를 입력하세요.")
    @ValidUsername
    private String username;

    // getter, setter
}
```

컨트롤러에서는 다음과 같이 검증할 수 있습니다.

```java
@PostMapping("/signup")
public String signup(
        @Valid @ModelAttribute SignupForm form,
        BindingResult bindingResult) {

    if (bindingResult.hasErrors()) {
        return "signup";
    }

    return "success";
}
```

* `@Valid`: DTO의 유효성 검사를 실행하도록 요청합니다.

* `BindingResult`: 검사 결과에 오류가 있는지 확인합니다.

* `hasErrors()`: 검증 오류가 있는지 확인합니다.

이 방식이 동작하려면 스프링부트에서 Bean Validation 구현체가 설정되어 있어야 합니다. 일반적으로 `spring-boot-starter-validation` 의존성을 사용합니다.

### 5\. Constraint와 관련 개념 비교

| 개념                  | 역할                                |
| ------------------- | --------------------------------- |
| @Constraint         | 사용자 정의 검증 어노테이션을 검증 로직과 연결        |
| ConstraintValidator | 실제 검증 로직을 구현하는 인터페이스              |
| @Valid              | 객체의 유효성 검사를 실행하도록 요청              |
| @NotBlank           | 문자열이 null이 아니고 공백만으로 구성되지 않았는지 검사 |
| @Size               | 문자열이나 컬렉션 등의 크기 제한                |
| BindingResult       | 스프링 MVC에서 검증 오류 결과 확인             |

핵심 정리: `jakarta.validation.Constraint`는 사용자 정의 유효성 검사 어노테이션을 만들 때 사용하는 메타 어노테이션입니다. `@Constraint(validatedBy = ...)`로 검증 클래스를 연결하고, 실제 검사 작업은 `ConstraintValidator` 구현체가 담당합니다.
