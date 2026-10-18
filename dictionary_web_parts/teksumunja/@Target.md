`java.lang.annotation.Target`은 스프링부트에서 사용하는 어노테이션을 어디에 붙여서 사용할 수 있는지 지정하는 자바의 어노테이션입니다.

스프링부트 전용 기능은 아니며, 자바에서 기본으로 제공하는 기능입니다. 앞에서 배운 `ElementType`과 함께 사용됩니다.

### 1\. Target의 역할

예를 들어 개발자가 `@MyAnnotation`이라는 어노테이션을 만들었다고 가정하겠습니다.

이 어노테이션을 클래스에만 사용할지, 메서드에만 사용할지, 필드에만 사용할지 지정할 때 `@Target`을 사용합니다.

| Target에 지정하는 값              | 의미          | 적용 위치           |
| --------------------------- | ----------- | --------------- |
| ElementType.TYPE            | 타입 선언       | 클래스, 인터페이스, 열거형 |
| ElementType.FIELD           | 필드 선언       | 멤버 변수           |
| ElementType.METHOD          | 메서드 선언      | 메서드             |
| ElementType.PARAMETER       | 매개변수 선언     | 메서드의 매개변수       |
| ElementType.CONSTRUCTOR     | 생성자 선언      | 생성자             |
| ElementType.LOCAL_VARIABLE  | 지역 변수 선언    | 메서드 내부 변수       |
| ElementType.ANNOTATION_TYPE | 어노테이션 선언    | 다른 어노테이션 정의     |
| ElementType.TYPE_USE        | 타입이 사용되는 위치 | 타입 표현식 등        |

### 2\. 실제 코드로 이해하기

다음은 필드에만 사용할 수 있는 어노테이션을 만드는 예제입니다.

```java
import java.lang.annotation.Target;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface MyAnnotation {
}
```

여기서 핵심은 다음 코드입니다.

```java
@Target(ElementType.FIELD)
```

`MyAnnotation`을 필드에만 사용할 수 있도록 제한한다는 의미입니다.

정상적인 사용 예시는 다음과 같습니다.

```java
public class User {

    @MyAnnotation
    private String username;

}
```

반대로 클래스에 붙이면 컴파일 오류가 발생합니다.

```java
@MyAnnotation  // 오류: 클래스에는 사용할 수 없음
public class User {
}
```

클래스에 적용하고 싶다면 다음처럼 변경해야 합니다.

```java
@Target(ElementType.TYPE)
public @interface MyAnnotation {
}
```

이제 클래스에 사용할 수 있습니다.

### 3\. 스프링부트에서는 어떻게 활용할까?

스프링부트에서 사용하는 어노테이션도 각자의 적용 대상 규칙을 가집니다.

예를 들어 다음 코드를 보겠습니다.

```java
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UserController {

    @GetMapping("/users")
    public String users() {
        return "users";
    }
}
```

* `@Controller`: 클래스에 붙여 컨트롤러 역할을 표시합니다.

* `@GetMapping`: 메서드에 붙여 HTTP GET 요청 경로를 지정합니다.

이처럼 어노테이션마다 적용할 수 있는 위치가 다릅니다. `@Target`은 어노테이션을 정의할 때 이러한 규칙을 지정하는 데 사용됩니다.

### 4\. 지금까지 배운 세 가지 개념 비교

| 구분              | 역할              | 예시                         |
| --------------- | --------------- | -------------------------- |
| Target          | 어노테이션을 어디에 붙일까? | @Target(ElementType.FIELD) |
| ElementType     | 적용 위치의 종류를 제공   | FIELD, METHOD, TYPE        |
| Retention       | 유지 정책을 지정       | @Retention(...)            |
| RetentionPolicy | 유지 기간의 종류를 제공   | SOURCE, CLASS, RUNTIME     |

핵심 정리: `java.lang.annotation.Target`은 어노테이션을 사용할 수 있는 위치를 제한하는 기능입니다. 특히 `ElementType`과 함께 사용되며, 개발자가 사용자 정의 어노테이션을 만들거나 스프링부트 어노테이션의 구조를 이해할 때 중요합니다.
