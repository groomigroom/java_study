`org.thymeleaf.dialect.AbstractDialect`는 스프링 부트 자체의 클래스가 아니라, Thymeleaf 템플릿 엔진에서 사용자 정의 Dialect(방언)를 만들 때 사용하는 추상 클래스입니다.

쉽게 말하면, Thymeleaf의 기본 문법인 `th:text`, `th:each` 외에 개발자가 별도의 속성이나 태그 기능을 추가하고 싶을 때 활용할 수 있는 기반 클래스입니다.

## 1. 먼저 Thymeleaf가 무엇인가요?

Thymeleaf는 스프링 부트에서 자주 사용하는 서버 사이드 템플릿 엔진입니다. Java에서 전달한 데이터를 HTML 화면에 표시하는 역할을 합니다.

예를 들어 다음과 같은 HTML이 있습니다.

HTML줄 바꿈코드 복사미리보기

```
<p th:text="${userName}">사용자 이름</p>
```

컨트롤러에서 `userName`에 `"홍길동"`을 전달하면, Thymeleaf가 템플릿을 처리해 브라우저에 표시할 HTML을 생성합니다.

HTML줄 바꿈코드 복사미리보기

```
<p>홍길동</p>
```

여기서 `th:text`는 Thymeleaf가 기본적으로 제공하는 기능입니다.

## 2. 그렇다면 `AbstractDialect`는 무엇인가요?

Dialect는 Thymeleaf의 템플릿 처리 기능을 확장하는 단위입니다.

예를 들어 프로젝트에서 다음과 같은 사용자 정의 문법을 만들고 싶다고 가정해 보겠습니다.

HTML줄 바꿈코드 복사미리보기

```
<div custom:hello="${userName}"></div>
```

이런 사용자 정의 속성을 실제로 처리하려면 관련 프로세서 등을 구현하고 Thymeleaf에 등록해야 합니다.

이때 `AbstractDialect`는 사용자 정의 Dialect를 구현하기 위한 기반 클래스로 활용할 수 있습니다.

Thymeleaf 기본 Dialect

th:text, th:each 등 기본 템플릿 기능

사용자 정의 Dialect

AbstractDialect를 상속해 추가 기능 구현

Thymeleaf 템플릿 처리

등록된 기능을 사용해 HTML 생성

## 3. Java 코드에서는 어떻게 사용하나요?

간단한 예시는 다음과 같습니다.

java
```
import org.thymeleaf.dialect.AbstractDialect;
import org.thymeleaf.processor.IProcessor;

public class CustomDialect extends AbstractDialect {

    public CustomDialect() {
        super("custom");
    }
}
```

위 코드는 `AbstractDialect`의 생성자와 이름 설정을 보여주는 기본 예시입니다. 실제로 사용자 정의 속성이나 태그를 동작시키려면 사용하는 Thymeleaf 버전에 맞게 프로세서 등을 구현하고 등록해야 합니다.

스프링 부트에서는 보통 `@Bean` 등을 통해 사용자 정의 Dialect를 빈으로 등록할 수 있습니다.

java
```
@Bean
public CustomDialect customDialect() {
    return new CustomDialect();
}
```

단, 위 예시는 기본 구조를 설명하기 위한 것이며 실제 프로젝트에서는 필요한 프로세서 구현도 함께 있어야 합니다.

## 4. `AbstractDialect`와 `@Component`는 서로 다른 개념입니다

앞서 살펴보신 전자정부 프레임워크의 `AbstractKrdsPaginationRenderer`와 비교하면 이해하기 쉽습니다.

| 구분 | 역할 |
| --- | --- |
| `AbstractDialect` | Thymeleaf 템플릿 기능을 확장하기 위한 부모 클래스 |
| `extends AbstractDialect` | 사용자 정의 Dialect 구현 |
| `@Component` | 클래스를 스프링 빈으로 등록 |
| `@Bean` | 메서드가 반환하는 객체를 스프링 빈으로 등록 |
| `th:text` | Thymeleaf가 기본 제공하는 템플릿 속성 |

즉, 상속은 기능을 확장하기 위한 방법이고, 빈 등록은 스프링이 해당 객체를 관리하도록 하는 방법입니다.

## 5. 프로젝트에서 이 클래스가 보인다면?

`AbstractDialect`가 프로젝트 코드에 등장하는 경우 다음 사항을 확인하면 좋습니다.

- `extends AbstractDialect`로 상속하는 클래스가 있는지
- 사용자 정의 `IProcessor` 등을 구현하는지
- 해당 Dialect가 스프링 빈으로 등록되어 있는지
- HTML 템플릿에서 사용자 정의 속성이나 태그를 사용하는지

참고로 `org.thymeleaf.dialect.AbstractDialect`의 정확한 메서드와 구현 방식은 사용 중인 Thymeleaf 버전에 따라 확인하는 것이 좋습니다.

한 문장으로 정리하면, `AbstractDialect`는 Thymeleaf에 프로젝트만의 템플릿 문법이나 처리 기능을 추가할 수 있도록 제공되는 추상 기반 클래스입니다.


------

# EgovPaginationDialect()

여기서 `super("EgovPaginationDialect");`는 부모 클래스의 생성자(Constructor)를 호출하는 코드입니다.

앞서 질문하신 `AbstractDialect`와 연결해서 보면 이해하기 쉽습니다.

## 1. 코드부터 살펴보겠습니다

java
```
public EgovPaginationDialect(
        EgovKrdsPaginationRenderer egovKrdsPaginationRenderer) {

    super("EgovPaginationDialect");

    this.egovKrdsPaginationRenderer = egovKrdsPaginationRenderer;
}
```

이 코드는 크게 두 가지 일을 합니다.

- `super("EgovPaginationDialect")`: 부모 클래스의 생성자를 호출하면서 Dialect 이름을 전달합니다.
- `this.egovKrdsPaginationRenderer = egovKrdsPaginationRenderer`: 생성자로 전달받은 렌더러 객체를 현재 객체의 필드에 저장합니다.

## 2. `super()`는 무엇인가요?

Java에서 `super`는 부모 클래스를 가리키는 키워드입니다.

부모 클래스가 다음과 같이 정의되어 있다고 가정해 보겠습니다.

java
```
public abstract class AbstractDialect {

    private final String name;

    public AbstractDialect(String name) {
        this.name = name;
    }
}
```

자식 클래스에서는 다음처럼 부모 생성자를 호출할 수 있습니다.

java
```
public class EgovPaginationDialect extends AbstractDialect {

    public EgovPaginationDialect() {
        super("EgovPaginationDialect");
    }
}
```

이때 `super("EgovPaginationDialect")`가 실행되면 부모 생성자의 `name` 매개변수에 문자열이 전달됩니다.

즉, 부모 클래스가 관리하는 이름을 초기화하는 것입니다.

참고로 실제 Thymeleaf의 `AbstractDialect` 구현은 라이브러리 버전의 소스 코드를 기준으로 확인해야 합니다. 위 코드는 생성자 호출 원리를 설명하기 위한 예시입니다.

## 3. 그렇다면 왜 이름을 전달하나요?

`EgovPaginationDialect`는 앞서 살펴본 것처럼 Thymeleaf의 템플릿 처리 기능을 확장하는 Dialect입니다.

java
```
super("EgovPaginationDialect");
```

이 문자열은 해당 Dialect의 이름을 지정하기 위해 전달하는 값입니다.

여기서 주의할 점은 이 이름이 HTML에서 사용하는 `th:` 같은 속성 접두사와 반드시 같은 의미는 아니라는 것입니다. Dialect 이름과 템플릿 속성의 접두사는 별개의 개념입니다.

## 4. `super`와 `this`의 차이

| 코드 | 의미 |
| --- | --- |
| `super(...)` | 부모 클래스의 생성자 호출 |
| `super.someMethod()` | 부모 클래스의 메서드 호출 |
| `this.field` | 현재 객체의 필드 참조 |
| `this(...)` | 같은 클래스의 다른 생성자 호출 |

따라서 질문하신 코드에서는 다음과 같이 이해하면 됩니다.

java
```
super("EgovPaginationDialect");
```

→ 부모 클래스의 초기화 작업을 수행하면서 Dialect 이름을 전달합니다.

java
```
this.egovKrdsPaginationRenderer = egovKrdsPaginationRenderer;
```

→ 전달받은 페이지네이션 렌더러 객체를 현재 Dialect 객체의 필드에 저장합니다.

한 문장으로 정리하면, `super("EgovPaginationDialect")`는 부모 클래스인 `AbstractDialect`의 생성자를 호출해 이 Dialect의 이름을 초기화하는 코드입니다.
