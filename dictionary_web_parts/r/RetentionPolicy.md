`java.lang.annotation.RetentionPolicy`는 스프링부트에서 사용하는 어노테이션의 정보를 어느 시점까지 유지할 것인지 선택하는 열거형(enum) 입니다.

스프링부트 전용 기능은 아니며, 자바에서 기본으로 제공하는 기능입니다. 앞에서 배운 `java.lang.annotation.Retention`과 함께 사용됩니다.

### 1\. RetentionPolicy의 역할

`RetentionPolicy`에는 3가지 상수가 있습니다.

| 상수      | 의미               | 어노테이션 정보의 유지 범위   |
| ------- | ---------------- | ----------------- |
| SOURCE  | 소스 코드 단계까지 유지    | 컴파일 후 제거          |
| CLASS   | 컴파일된 클래스 파일까지 유지 | 기본값               |
| RUNTIME | 프로그램 실행 중에도 유지   | 실행 중 리플렉션으로 조회 가능 |

쉽게 말해 `RetentionPolicy`는 어노테이션 정보를 언제까지 보관할 것인지 선택하는 옵션입니다.

### 2\. 스프링부트 코드로 이해하기

다음은 직접 만든 어노테이션의 정보를 실행 중에도 유지하도록 설정한 예제입니다.

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

여기서 핵심은 다음 코드입니다.

```java
@Retention(RetentionPolicy.RUNTIME)
```

* `@Retention`: 어노테이션의 유지 정책을 지정합니다.

* `RetentionPolicy.RUNTIME`: 실행 중에도 어노테이션 정보를 유지합니다.

그리고 다음 코드는 필드에 어노테이션을 적용한다는 의미입니다.

```java
public class User {

    @MyAnnotation
    private String username;

}
```

### 3\. 세 가지 정책의 차이

① `RetentionPolicy.SOURCE`

소스 코드 단계

어노테이션 정보가 컴파일 후 `.class` 파일에 남지 않습니다. 주로 컴파일 단계에서 처리하는 어노테이션에 사용합니다.

② `RetentionPolicy.CLASS`

컴파일된 클래스 파일 단계

`.class` 파일에 어노테이션 정보가 남습니다. 별도로 유지 정책을 지정하지 않았을 때의 기본값입니다.

③ `RetentionPolicy.RUNTIME`

프로그램 실행 단계

실행 중에도 어노테이션 정보를 읽을 수 있습니다. 스프링이 실행 중 어노테이션을 분석하는 기능을 이해할 때 중요합니다.

### 4\. Retention과 RetentionPolicy의 차이

| 구분              | 역할                            |
| --------------- | ----------------------------- |
| Retention       | 어노테이션의 유지 정책을 지정하는 어노테이션      |
| RetentionPolicy | 유지 정책의 종류를 제공하는 enum          |
| ElementType     | 어노테이션을 적용할 수 있는 위치를 지정하는 enum |

예를 들어 다음 코드를 보면 세 가지 개념을 구분할 수 있습니다.

```java
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface MyAnnotation {
}
```

* `Retention`: 유지 정책을 지정합니다.

* `RetentionPolicy.RUNTIME`: 실행 중까지 유지하도록 선택합니다.

* `ElementType.FIELD`: 필드에 적용하도록 지정합니다.

핵심 정리: `java.lang.annotation.RetentionPolicy`는 자바 어노테이션의 유지 기간을 선택하는 enum이며, `SOURCE`, `CLASS`, `RUNTIME` 세 가지 상수를 제공합니다. 스프링부트에서 어노테이션이 실행 중 어떻게 인식되는지 이해하려면 특히 `RUNTIME`을 알아두는 것이 좋습니다.
