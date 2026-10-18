`java.lang.annotation.Retention`은 스프링부트에서 사용하는 어노테이션의 정보를 어느 시점까지 유지할 것인지 지정하는 자바의 어노테이션입니다.

스프링부트 전용 기능은 아니며, 자바에서 기본으로 제공하는 기능입니다. 스프링부트에서 어노테이션을 직접 만들거나 동작 원리를 이해할 때 중요합니다.

### 1\. Retention의 역할

예를 들어 개발자가 `@MyAnnotation`이라는 어노테이션을 만들었다고 가정하겠습니다.

이 어노테이션의 정보가 소스 코드에만 남을지, 컴파일된 파일에도 남을지, 프로그램 실행 중에도 남을지 결정해야 합니다.

이때 `@Retention`을 사용합니다.

| 유지 정책                   | 의미                            |
| ----------------------- | ----------------------------- |
| RetentionPolicy.SOURCE  | 소스 코드에만 유지하고 컴파일 후 제거         |
| RetentionPolicy.CLASS   | 컴파일된 .class 파일까지 유지. 기본값      |
| RetentionPolicy.RUNTIME | 프로그램 실행 중에도 유지하여 리플렉션으로 확인 가능 |

### 2\. 실제 코드로 이해하기

다음은 실행 중에도 어노테이션 정보를 유지하는 예제입니다.

```java
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.annotation.ElementType;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface MyAnnotation {
}
```

각 코드의 의미는 다음과 같습니다.

* `@Retention(RetentionPolicy.RUNTIME)` 프로그램이 실행되는 동안에도 어노테이션 정보를 유지합니다.

* `@Target(ElementType.FIELD)` 필드에만 사용할 수 있도록 제한합니다.

* `public @interface MyAnnotation` 사용자 정의 어노테이션을 선언합니다.

이제 클래스의 필드에 적용할 수 있습니다.

```java
public class User {

    @MyAnnotation
    private String username;

}
```

### 3\. RUNTIME은 어떻게 활용할까?

`RUNTIME`으로 지정하면 자바의 리플렉션(Reflection)을 이용해 실행 중에 어노테이션을 확인할 수 있습니다.

```java
import java.lang.reflect.Field;

public class Main {
    public static void main(String[] args)
            throws Exception {

        Field field =
            User.class.getDeclaredField("username");

        boolean result =
            field.isAnnotationPresent(MyAnnotation.class);

        System.out.println(result);
    }
}
```

실행 결과는 다음과 같습니다.

```text
true
```

`username` 필드에 `@MyAnnotation`이 붙어 있다는 사실을 프로그램 실행 중에 확인한 것입니다.

### 4\. 스프링부트에서는 왜 중요할까?

스프링부트와 스프링 프레임워크는 어노테이션을 분석하여 여러 기능을 처리합니다.

예를 들어 다음과 같은 어노테이션이 있습니다.

* `@Component`: 스프링이 관리할 객체를 표시

* `@Autowired`: 의존성 주입에 활용

* `@Transactional`: 트랜잭션 처리에 활용

* `@Entity`: JPA 엔티티를 표시

이러한 어노테이션의 실제 동작은 각각의 정의와 스프링의 처리 방식에 따라 결정됩니다. 실행 중 어노테이션을 읽어야 하는 기능에서는 `RetentionPolicy.RUNTIME`이 특히 중요합니다.

### 5\. ElementType과 Retention 비교

앞에서 배운 `ElementType`과 비교하면 쉽게 이해할 수 있습니다.

| 구분                           | 역할                    | 예시                  |
| ---------------------------- | --------------------- | ------------------- |
| @Target + ElementType        | 어노테이션을 어디에 붙일 수 있는가?  | 클래스, 필드, 메서드        |
| @Retention + RetentionPolicy | 어노테이션 정보를 언제까지 유지하는가? | 소스 코드, 컴파일 파일, 실행 중 |

핵심 정리: `java.lang.annotation.Retention`은 어노테이션 정보의 유지 기간을 지정합니다. 특히 `@Retention(RetentionPolicy.RUNTIME)`은 실행 중에도 어노테이션을 확인할 수 있게 해 주므로, 스프링부트의 어노테이션 기반 기능을 이해하는 데 중요한 개념입니다.
