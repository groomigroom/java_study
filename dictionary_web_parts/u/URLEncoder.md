스프링부트에서 `import java.net.URLEncoder;`는 URL에 데이터를 안전하게 포함할 수 있도록 문자열을 인코딩하는 자바 클래스야.

쉽게 말하면, 한글이나 공백, 특수문자 등이 포함된 데이터를 URL에 넣을 때 URL 규칙에 맞는 형태로 변환해 주는 역할이야.

스프링부트 전용 기능이 아니라 자바에서 기본으로 제공하는 클래스야.

### 1\. `URLEncoder`의 의미

```java
import java.net.URLEncoder;
```

| 코드         | 의미                         |
| ---------- | -------------------------- |
| java       | 자바 기본 패키지                  |
| net        | 네트워크 관련 패키지                |
| URLEncoder | 문자열을 URL 인코딩 형식으로 변환하는 클래스 |
| import     | 클래스를 가져오는 키워드              |

예를 들어, URL에 다음과 같은 검색어를 전달한다고 생각해 보자.

```text
스프링 부트 강의
```

이 문자열에는 한글과 공백이 포함되어 있어. URL의 쿼리 파라미터에 넣을 때는 인코딩이 필요할 수 있어.

### 2\. 실제 사용 예제

```java
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

public class UrlTest {

    public static void main(String[] args) {

        String text = "스프링 부트 강의";

        String encoded = URLEncoder.encode(
            text,
            StandardCharsets.UTF_8
        );

        System.out.println(encoded);
    }
}
```

실행 결과는 다음과 같아.

```text
%EC%8A%A4%ED%94%84%EB%A7%81+%EB%B6%80%ED%8A%B8+%EA%B0%95%EC%9D%98
```

한글이 `%EC...` 형태로 바뀌고, 공백은 `+`로 변환된 것을 볼 수 있어.

여기서 `StandardCharsets.UTF_8`은 문자열을 UTF-8 문자 인코딩 방식으로 처리하겠다는 의미야.

### 3\. 스프링부트에서는 언제 사용할까?

예를 들어 검색 기능을 개발한다고 가정해 보자.

사용자가 검색창에 다음과 같이 입력했어.

```text
강아지 용품 추천
```

이 검색어를 URL의 쿼리 파라미터로 전달할 수 있어.

```text
/search?keyword=강아지 용품 추천
```

하지만 공백이나 특수문자가 포함된 값을 안전하게 전달하려면 인코딩하는 것이 좋아.

```java
String keyword = "강아지 용품 추천";

String encodedKeyword = URLEncoder.encode(
    keyword,
    StandardCharsets.UTF_8
);

String url = "https://example.com/search?keyword="
             + encodedKeyword;
```

이렇게 하면 URL에 사용할 수 있는 형태로 검색어를 변환할 수 있어.

스프링부트에서는 다음과 같은 상황에서 활용할 수 있어.

* 검색어를 URL 파라미터로 전달할 때

* 외부 API를 호출할 때

* 한글이 포함된 파일명을 URL에 포함할 때

* URL에 포함할 파라미터 값을 구성할 때

### 4\. `URLEncoder`와 `URLDecoder`의 차이

| 클래스        | 역할            | 예시     |
| ---------- | ------------- | ------ |
| URLEncoder | 문자열을 인코딩      | 공백 → + |
| URLDecoder | 인코딩된 문자열을 디코딩 | + → 공백 |

예를 들어 다음과 같아.

```java
String encoded = URLEncoder.encode(
    "강아지 용품",
    StandardCharsets.UTF_8
);

String decoded = URLDecoder.decode(
    encoded,
    StandardCharsets.UTF_8
);
```

`decoded`에는 원래 문자열인 `강아지 용품`이 들어가.

### 5\. 주의할 점

`URLEncoder`는 일반적인 URL 전체를 변환하는 클래스가 아니야. 주로 HTML 폼 방식(`application/x-www-form-urlencoded`)의 쿼리 파라미터 값을 인코딩할 때 사용해.

예를 들어 다음 URL이 있다면,

```text
/search?keyword=강아지 용품&category=사료
```

`keyword` 값만 인코딩해야 해. URL 전체를 한꺼번에 인코딩하면 `/`, `?`, `&`, `=` 같은 URL 구분 기호까지 변환될 수 있어.

또한 경로(path) 부분을 인코딩할 때는 `URLEncoder`를 그대로 사용하는 것이 적절하지 않을 수 있어.

핵심 정리

* `File` → 파일과 폴더 관리

* `InputStream` → 데이터 읽기

* `OutputStream` → 데이터 출력 및 기록

* `URLEncoder` → URL에 포함할 문자열 인코딩

즉, 스프링부트에서 `URLEncoder`를 발견했다면 한글이나 공백 등이 포함된 데이터를 URL의 파라미터로 전달하기 위해 변환하는 코드일 가능성이 높아.
