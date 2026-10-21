`org.thymeleaf.context.IExpressionContext`는 스프링 부트 자체의 인터페이스가 아니라, Thymeleaf가 `${...}` 같은 표현식을 처리할 때 필요한 실행 환경 정보를 제공하는 인터페이스입니다.

쉽게 말하면, Thymeleaf가 HTML 템플릿을 처리하는 동안 현재 어떤 변수와 객체를 사용할 수 있는지 확인할 수 있도록 도와주는 역할입니다.

## 1. IExpressionContext는 어떤 역할을 하나요?

예를 들어 Thymeleaf 템플릿에 다음 코드가 있다고 가정하겠습니다.

HTML줄 바꿈코드 복사미리보기

```
<p th:text="${userName}"></p>
```

Thymeleaf는 `${userName}`을 처리하기 위해 현재 컨텍스트에서 `userName`이라는 변수를 찾아야 합니다.

이때 표현식 처리에 필요한 컨텍스트 정보를 제공하는 인터페이스 중 하나가 `IExpressionContext`입니다.

주요 역할은 다음과 같습니다.

- 표현식에서 사용할 변수 조회
- 표현식 객체(Expression Object) 조회
- 변수와 표현식 객체의 존재 여부 확인
- 현재 처리 환경의 로케일(Locale) 확인

단, 구체적인 메서드와 동작은 사용 중인 Thymeleaf 버전에 따라 확인해야 합니다.

## 2. 실제 코드에서는 어떻게 사용하나요?

대표적으로 다음과 같은 메서드가 있습니다.

| 메서드 | 의미 |
| --- | --- |
| `getVariable(name)` | 지정한 이름의 변수 조회 |
| `containsVariable(name)` | 지정한 이름의 변수가 존재하는지 확인 |
| `getExpressionObjects()` | 표현식 객체를 제공하는 컨테이너 조회 |
| `getLocale()` | 현재 컨텍스트의 로케일 조회 |

예를 들어 다음과 같이 사용할 수 있습니다.

java
```
Object value = context.getVariable("userName");

if (context.containsVariable("userName")) {
    System.out.println(value);
}
```

이 코드는 컨텍스트에서 `userName` 변수를 조회하는 개념적인 예시입니다. `context`는 해당 인터페이스를 구현한 실제 객체여야 합니다.

## 3. `IExpressionContext`와 `IExpressionObjectFactory`는 어떻게 다른가요?

앞서 질문하신 `IExpressionObjectFactory`와 비교하면 차이가 명확합니다.

### IExpressionContext

표현식 실행 환경

현재 사용할 수 있는 변수, 객체, 로케일 등의 정보를 제공

### IExpressionObjectFactory

표현식 객체 제공 방식

#dates, #strings 또는 사용자 정의 객체 등을 어떤 이름으로 제공할지 정의

예를 들어 템플릿에서 다음 표현식을 처리한다고 해보겠습니다.

HTML줄 바꿈코드 복사미리보기

```
<span th:text="${#myUtils.formatName(userName)}"></span>
```

- `IExpressionContext`는 `userName` 같은 변수와 표현식 객체에 접근하는 환경을 제공합니다.
- `IExpressionObjectFactory`는 `myUtils`라는 표현식 객체를 생성하거나 제공하는 역할을 합니다.

즉, 컨텍스트는 표현식을 실행하는 환경이고, 팩토리는 표현식에서 사용할 객체를 제공하는 방법입니다.

## 4. 전자정부 프레임워크의 페이지네이션과 연결하면?

앞서 살펴보신 `EgovPaginationDialect`에서 페이지네이션 렌더러를 표현식 객체로 제공한다고 가정해 보겠습니다.

HTML줄 바꿈코드 복사미리보기

```
<div th:utext="${#pagination.renderPagination(paginationInfo, 'linkPage')}"></div>
```

이 표현식을 처리할 때 Thymeleaf는 다음 정보를 활용합니다.

1. `paginationInfo` 변수 조회
2. `#pagination` 표현식 객체 조회
3. `renderPagination()` 호출
4. 반환된 HTML 문자열 처리

이 과정에서 컨텍스트는 표현식 처리에 필요한 변수와 객체에 접근할 수 있도록 지원합니다.

다만 위 HTML은 앞선 설명을 바탕으로 작성한 개념적인 예시이며, 실제 프로젝트의 객체 이름과 표현식은 `EgovPaginationDialect` 구현을 확인해야 합니다.

정리하면, `IExpressionContext`는 Thymeleaf가 템플릿의 표현식을 처리할 때 변수와 표현식 객체 등의 실행 환경 정보에 접근할 수 있도록 해주는 인터페이스입니다.
