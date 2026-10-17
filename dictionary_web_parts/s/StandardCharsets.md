스프링부트에서 `import java.nio.charset.StandardCharsets;`는 문자열을 바이트로 변환하거나 바이트를 문자열로 변환할 때 사용할 표준 문자 인코딩을 제공하는 클래스야.

앞에서 배운 `URLEncoder`와 함께 자주 사용해. 특히 한글이 깨지지 않도록 UTF-8 인코딩을 지정할 때 유용해.

### 1\. `StandardCharsets`의 의미

```java
import java.nio.charset.StandardCharsets;
```

| 코드               | 의미                      |
| ---------------- | ----------------------- |
| java             | 자바 기본 패키지               |
| nio              | 새로운 입출력 관련 패키지          |
| charset          | 문자 인코딩 관련 패키지           |
| StandardCharsets | 표준 문자 인코딩을 상수로 제공하는 클래스 |

`StandardCharsets`는 자바에서 미리 정의해 둔 문자 인코딩 이름들을 제공해.

대표적으로 다음과 같은 값이 있어.

* `StandardCharsets.UTF_8` : UTF-8 인코딩

* `StandardCharsets.UTF_16` : UTF-16 인코딩

* `StandardCharsets.ISO_8859_1` : ISO-8859-1 인코딩

실제 웹 개발에서는 UTF-8을 자주 사용해.

### 2\. 실제 사용 예제

### 예제 1. 문자열을 바이트로 변환하기

```java
import java.nio.charset.StandardCharsets;

public class CharsetTest {
    public static void main(String[] args) {

        String text = "안녕하세요";

        byte[] bytes = text.getBytes(StandardCharsets.UTF_8);

        System.out.println(bytes.length);
    }
}
```

여기서 핵심 코드는 다음과 같아.

```java
text.getBytes(StandardCharsets.UTF_8);
```

문자열 `안녕하세요`를 UTF-8 인코딩 방식으로 바이트 배열로 변환해.

한글은 UTF-8에서 일반적으로 글자당 3바이트를 사용하므로, 위 문자열은 15바이트가 돼.

### 예제 2. 바이트를 문자열로 변환하기

반대로 바이트 배열을 다시 문자열로 만들 수도 있어.

```java
import java.nio.charset.StandardCharsets;

public class CharsetTest {
    public static void main(String[] args) {

        String text = "안녕하세요";

        byte[] bytes = text.getBytes(StandardCharsets.UTF_8);

        String result = new String(
            bytes,
            StandardCharsets.UTF_8
        );

        System.out.println(result);
    }
}
```

실행 결과:

```text
안녕하세요
```

문자열을 바이트로 변환할 때와 다시 문자열로 변환할 때 같은 인코딩을 사용해야 글자가 올바르게 복원돼.

### 3\. `URLEncoder`와 함께 사용하는 이유

앞에서 배운 URL 인코딩 예제를 다시 살펴보자.

```java
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

String keyword = "강아지 용품";

String encoded = URLEncoder.encode(
    keyword,
    StandardCharsets.UTF_8
);
```

이 코드에서 각 클래스의 역할은 달라.

`URLEncoder`

URL 파라미터에 적합한 형태로 문자열을 인코딩해.

`StandardCharsets.UTF_8`

문자열을 처리할 때 사용할 문자 인코딩 방식을 지정해.

즉, `URLEncoder`가 변환 작업을 수행하고, `StandardCharsets.UTF_8`은 그 작업에서 사용할 문자 인코딩 방식을 지정하는 거야.

### 4\. 스프링부트에서 언제 사용할까?

| 상황           | 사용 예                                            |
| ------------ | ----------------------------------------------- |
| URL 파라미터 인코딩 | URLEncoder.encode(text, StandardCharsets.UTF_8) |
| 문자열을 바이트로 변환 | text.getBytes(StandardCharsets.UTF_8)           |
| 바이트를 문자열로 변환 | new String(bytes, StandardCharsets.UTF_8)       |
| 파일 내용 읽기·쓰기  | UTF-8 인코딩 지정                                    |

핵심 정리: `StandardCharsets`는 데이터를 직접 변환하는 클래스라기보다, 변환할 때 사용할 표준 문자 인코딩을 제공하는 클래스야.

지금까지 배운 클래스들을 연결하면 `FileInputStream`과 `FileOutputStream`은 바이트 데이터를 읽고 쓰고, `StandardCharsets`는 그 바이트를 문자열로 변환할 때 어떤 문자 인코딩을 사용할지 지정하는 데 활용할 수 있어.
