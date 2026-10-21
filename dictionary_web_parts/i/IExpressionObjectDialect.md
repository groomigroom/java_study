`org.thymeleaf.dialect.IExpressionObjectDialect`는 스프링 부트 자체의 인터페이스가 아니라, Thymeleaf 템플릿에서 `${...}` 표현식으로 사용할 수 있는 사용자 정의 객체(Expression Object)를 추가하기 위한 인터페이스입니다.

앞서 질문하신 `AbstractDialect`와 연결해서 이해하면 쉽습니다.

## 1. 먼저 Expression Object란?

Thymeleaf에서 HTML 템플릿에 데이터를 표시할 때 `${...}` 표현식을 사용합니다.

HTML줄 바꿈코드 복사미리보기

```
<p th:text="${userName}">사용자 이름</p>
```

여기서 `${userName}`은 컨트롤러에서 전달한 데이터를 참조합니다.

그런데 프로젝트에서 사용자 이름이나 전달받은 데이터뿐 아니라, 공통으로 사용할 수 있는 별도의 기능을 템플릿에서 직접 호출하고 싶을 때 Expression Object를 활용할 수 있습니다.

예를 들어 다음과 같은 사용자 정의 객체를 만들 수 있습니다.

HTML줄 바꿈코드 복사미리보기

```
<p th:text="${#myUtils.formatName(userName)}"></p>
```

여기서 `#myUtils`는 개발자가 추가한 Expression Object라고 가정한 것입니다. 실제로 동작하게 하려면 객체와 Dialect를 구현하고 Thymeleaf에 등록해야 합니다.

## 2. `IExpressionObjectDialect`의 역할

이 인터페이스는 Thymeleaf 엔진에 다음과 같은 정보를 제공합니다.

- Expression Object를 생성하거나 제공하는 팩토리
- 템플릿에서 사용할 객체의 이름과 접근 방법

구조를 간단하게 나타내면 다음과 같습니다.

HTML 템플릿

HTML줄 바꿈코드 복사미리보기

```
<span th:text="${#myUtils.formatName(userName)}"></span>
```

IExpressionObjectDialect

표현식 객체 팩토리를 Thymeleaf에 제공

Expression Object Factory

myUtils라는 이름의 객체 제공

사용자 정의 Java 객체

formatName() 등의 기능 실행

## 3. Java 코드에서는 어떻게 구현하나요?

Thymeleaf 3.x 계열을 기준으로 개념을 보여주는 예시입니다.

먼저 템플릿에서 사용할 기능을 가진 객체를 작성합니다.

java
```
public class MyUtils {

    public String formatName(String name) {
        return name == null ? "" : name.trim();
    }
}
```

다음으로 Expression Object를 제공하는 팩토리를 구현합니다.

java
```
import java.util.Collections;
import java.util.Set;

import org.thymeleaf.expression.IExpressionObjectFactory;

public class MyExpressionObjectFactory
        implements IExpressionObjectFactory {

    @Override
    public Set<String> getAllExpressionObjectNames() {
        return Collections.singleton("myUtils");
    }

    @Override
    public Object buildObject(String expressionObjectName) {
        if ("myUtils".equals(expressionObjectName)) {
            return new MyUtils();
        }
        return null;
    }

    @Override
    public boolean isCacheable(String expressionObjectName) {
        return true;
    }
}
```

마지막으로 Dialect에서 이 팩토리를 Thymeleaf에 제공합니다.

java
```
import org.thymeleaf.dialect.AbstractDialect;
import org.thymeleaf.dialect.IExpressionObjectDialect;
import org.thymeleaf.expression.IExpressionObjectFactory;

public class MyDialect extends AbstractDialect
        implements IExpressionObjectDialect {

    private final IExpressionObjectFactory factory =
            new MyExpressionObjectFactory();

    public MyDialect() {
        super("myDialect");
    }

    @Override
    public IExpressionObjectFactory getExpressionObjectFactory() {
        return factory;
    }
}
```

이 Dialect가 Thymeleaf에 등록되어 있다면 다음과 같은 표현식을 사용할 수 있습니다.

HTML줄 바꿈코드 복사미리보기

```
<span th:text="${#myUtils.formatName(userName)}"></span>
```

위 코드는 구조를 이해하기 위한 예시입니다. 실제 프로젝트에서는 사용하는 Thymeleaf 버전에 맞춰 인터페이스 메서드를 확인해야 합니다.

## 4. `AbstractDialect`와 `IExpressionObjectDialect`의 차이

| 구분 | 의미 |
| --- | --- |
| `AbstractDialect` | 사용자 정의 Dialect를 만들기 위한 기반 클래스 |
| `IExpressionObjectDialect` | Dialect가 사용자 정의 표현식 객체를 제공하도록 하는 인터페이스 |
| `IExpressionObjectFactory` | 표현식 객체의 이름과 생성 방법을 제공하는 팩토리 |
| `MyUtils` | 템플릿에서 호출할 실제 Java 기능을 가진 객체 |

즉, `AbstractDialect`는 확장의 기반을 제공하고, `IExpressionObjectDialect`는 그 확장 기능 중에서도 표현식 객체 제공 기능을 추가하는 역할을 합니다.

## 5. 스프링 부트에서 왜 사용하나요?

주로 다음과 같은 공통 기능을 Thymeleaf 템플릿에서 사용하고 싶을 때 활용합니다.

- 날짜 및 숫자 포맷팅
- 문자열 변환 및 검증
- 공통 화면 처리 로직
- 프로젝트에서 정의한 템플릿 전용 유틸리티

다만 스프링 빈을 등록하는 것만으로 `${#myUtils...}` 문법이 자동으로 생기는 것은 아닙니다. 표현식 객체 팩토리를 통해 Thymeleaf에 제공해야 합니다.

한 문장으로 정리하면, `IExpressionObjectDialect`는 Thymeleaf 템플릿에서 `${#객체명.메서드()}` 형태로 사용할 사용자 정의 Java 객체를 등록할 수 있게 해주는 인터페이스입니다.
