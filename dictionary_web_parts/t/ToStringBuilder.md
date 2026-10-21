스프링 부트에서 `org.apache.commons.lang3.builder.ToStringBuilder`는 자바 객체의 필드 값을 문자열로 변환할 때 사용하는 Apache Commons Lang 라이브러리의 클래스야.

스프링 부트 전용 기능은 아니고, 자바 객체를 로그로 출력하거나 디버깅할 때 유용하게 사용할 수 있어.

## 1. 기본 사용법

예를 들어 다음과 같은 `User` 클래스가 있다고 해 보자.

java
```
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class User {
    private String name = "Kim";
    private int age = 25;

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(
            this,
            ToStringStyle.SHORT_PREFIX_STYLE
        );
    }
}
```

다음과 같이 객체를 출력하면,

java
```
User user = new User();
System.out.println(user);
```

출력 결과는 대략 다음과 같아.

text
```
User[name=Kim,age=25]
```

즉, 객체의 필드 값을 사람이 읽기 쉬운 문자열로 만들어 주는 거야.

## 2. 주요 사용 방식

`ToStringBuilder`는 여러 스타일로 문자열을 만들 수 있어.

`ToStringStyle.DEFAULT_STYLE`

기본 스타일

`User@1a2b3c[name=Kim,age=25]`

`ToStringStyle.SHORT_PREFIX_STYLE`

클래스 이름과 필드 표시

`User[name=Kim,age=25]`

`ToStringStyle.JSON_STYLE`

JSON과 유사한 형태

`{name=Kim,age=25}`

사용할 때는 다음과 같이 지정하면 돼.

java
```
ToStringBuilder.reflectionToString(
    this,
    ToStringStyle.SHORT_PREFIX_STYLE
);
```

## 3. `toString()`과 무슨 차이일까?

| 구분 | `toString()` | `ToStringBuilder` |
| --- | --- | --- |
| 역할 | 객체를 문자열로 표현 | 문자열 표현을 쉽게 생성 |
| 필드 출력 | 직접 구현 가능 | 필드 정보를 자동으로 포함 가능 |
| 사용 목적 | 객체의 문자열 표현 전반 | 디버깅, 로그 출력 등 |
| 필수 여부 | `Object`에서 상속됨 | 선택적으로 사용 |

`ToStringBuilder`를 사용하면 모든 필드를 일일이 문자열에 연결하는 코드를 작성하지 않아도 돼.

## 4. 스프링 부트 프로젝트에서 주의할 점

- 로그 출력: 객체 내부의 값을 확인할 때 유용해.
- 민감 정보: 비밀번호, 토큰, 개인정보 등이 출력되지 않도록 주의해야 해.
- 성능: `reflectionToString()`은 리플렉션을 사용하므로, 대량 호출이나 성능이 중요한 코드에서는 주의하는 것이 좋아.
- 라이브러리: 프로젝트에서 Apache Commons Lang 3 의존성을 사용해야 해. Gradle에서는 다음과 같이 추가할 수 있어.

gradle
```
implementation 'org.apache.commons:commons-lang3'
```

버전은 프로젝트의 Spring Boot 의존성 관리 설정에 맡기거나 필요한 버전을 지정하면 돼.

한마디로 정리하면: `ToStringBuilder`는 자바 객체의 필드들을 문자열로 편리하게 변환해서 로그나 디버깅에 활용하도록 도와주는 클래스야.

원하면  ToStringBuilder와 Lombok @ToString 비교 도 설명해 줄게.
