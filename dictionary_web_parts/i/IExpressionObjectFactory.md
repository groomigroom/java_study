`org.thymeleaf.expression.IExpressionObjectFactory`는 스프링 부트 자체의 인터페이스가 아니라, Thymeleaf 템플릿에서 사용할 표현식 객체(Expression Object)를 어떤 이름으로 제공하고 어떻게 생성할지 정의하는 인터페이스입니다.

앞서 질문하신 `IExpressionObjectDialect`와 연결해서 이해하면 쉽습니다.

## 1. 어떤 역할을 하나요?

예를 들어 Thymeleaf HTML에서 다음과 같은 코드를 사용하고 싶다고 가정해 보겠습니다.

HTML줄 바꿈코드 복사미리보기

```
<span th:text="${#myUtils.formatName(userName)}"></span>
```

여기서 `#myUtils`는 Thymeleaf의 표현식 객체입니다.

이 객체를 사용하려면 Thymeleaf에 다음 정보를 알려줘야 합니다.

- `myUtils`라는 이름의 객체를 사용할 수 있다.
- 해당 객체가 필요할 때 어떤 Java 객체를 제공할지 알고 있다.
- 해당 객체를 캐시해도 되는지 판단할 수 있다.

이런 역할을 정의하는 인터페이스가 `IExpressionObjectFactory`입니다.

## 2. 주요 메서드

Thymeleaf 3.x 계열에서 일반적으로 사용하는 메서드는 다음과 같습니다.

| 메서드 | 역할 |
| --- | --- |
| `getAllExpressionObjectNames()` | 제공할 표현식 객체 이름 목록 반환 |
| `buildObject(String name)` | 요청된 이름에 해당하는 객체 생성 또는 반환 |
| `isCacheable(String name)` | 해당 객체를 캐시할 수 있는지 지정 |

예를 들어 다음과 같이 구현할 수 있습니다.

java
```
public class MyExpressionObjectFactory
        implements IExpressionObjectFactory {

    @Override
    public Set<String> getAllExpressionObjectNames() {
        return Collections.singleton("myUtils");
    }

    @Override
    public Object buildObject(String name) {
        if ("myUtils".equals(name)) {
            return new MyUtils();
        }
        return null;
    }

    @Override
    public boolean isCacheable(String name) {
        return true;
    }
}
```

이 코드는 `myUtils`라는 이름으로 `MyUtils` 객체를 제공하는 예시입니다. 실제 구현에서는 객체의 상태와 생명주기에 맞춰 캐시 여부를 결정해야 합니다.

## 3. 앞서 살펴본 `IExpressionObjectDialect`와의 관계

두 인터페이스의 역할은 다릅니다.

IExpressionObjectDialect

Thymeleaf에 표현식 객체 팩토리를 제공

IExpressionObjectFactory

객체 이름 목록과 객체 생성 방법 정의

Expression Object

HTML 템플릿에서 $#myUtils처럼 사용

즉, `IExpressionObjectDialect`가 팩토리를 Thymeleaf에 연결하는 역할이라면, `IExpressionObjectFactory`는 실제로 어떤 객체를 제공할지 정의하는 역할입니다.

## 4. 전자정부 프레임워크의 페이지네이션에서는 어떻게 활용될까요?

앞서 공유하신 `EgovKrdsPaginationRenderer`와 `EgovPaginationDialect`를 기준으로 보면, 페이지네이션 렌더러를 Thymeleaf 표현식 객체로 제공하도록 구성할 수 있습니다.

예를 들어 팩토리에서 `pagination`이라는 이름으로 렌더러를 제공한다면 다음과 같은 형태로 사용할 수 있습니다.

HTML줄 바꿈코드 복사미리보기

```
<div th:utext="${#pagination.renderPagination(paginationInfo, 'linkPage')}"></div>
```

이 코드는 개념적인 예시이며, 실제 전자정부 프레임워크에서 사용하는 객체 이름과 HTML 문법은 `EgovPaginationDialect` 및 팩토리 구현을 확인해야 합니다.

여기서 `th:utext`는 반환된 문자열을 HTML로 해석해 출력합니다. 따라서 렌더러가 반환하는 HTML을 그대로 출력할 때 사용될 수 있지만, 신뢰할 수 없는 사용자 입력이 HTML에 포함되면 XSS 취약점이 발생할 수 있으므로 주의해야 합니다.

정리하면, `IExpressionObjectFactory`는 Thymeleaf 템플릿에서 사용할 Java 객체의 이름과 생성 방식을 정의하는 인터페이스입니다. 전자정부 프레임워크에서는 이를 이용해 페이지네이션 렌더러 같은 기능을 템플릿에서 사용할 수 있도록 연결할 수 있습니다.
