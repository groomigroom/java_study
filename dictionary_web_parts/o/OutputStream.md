스프링부트에서 `import java.io.OutputStream;`은 데이터를 외부로 내보내거나 기록하기 위한 출력 스트림의 기본 클래스야.

앞에서 배운 `InputStream`과 반대 개념이라고 생각하면 이해하기 쉬워.

* `InputStream` → 데이터를 읽어옴

* `OutputStream` → 데이터를 내보내거나 기록함

둘 다 스프링부트 전용이 아니라 자바에서 기본으로 제공하는 클래스야.

### 1\. `OutputStream`의 의미

```java
import java.io.OutputStream;
```

| 코드           | 의미                       |
| ------------ | ------------------------ |
| java         | 자바 기본 패키지                |
| io           | 입출력(Input/Output) 관련 패키지 |
| OutputStream | 바이트 데이터를 출력하기 위한 추상 클래스  |
| import       | 클래스를 가져오는 키워드            |

`OutputStream`은 추상 클래스이기 때문에 일반적으로 직접 객체를 생성하지 않고, 이를 상속한 클래스를 사용해.

### 2\. `OutputStream`과 `FileOutputStream`의 관계

OutputStream

바이트 데이터를 출력하는 공통 기능을 정의하는 부모 클래스

FileOutputStream

파일에 기록

기타 출력 스트림

메모리, 네트워크 등으로 출력

즉, `OutputStream`은 출력 방식의 공통 규칙을 제공하고, `FileOutputStream` 같은 하위 클래스가 실제 출력 대상을 처리해.

### 3\. 실제 자바 코드 예제

문자열을 파일에 저장하는 예제야.

```java
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class FileTest {

    public static void main(String[] args) throws IOException {

        try (OutputStream output =
                 new FileOutputStream("C:/test/example.txt")) {

            String text = "Hello Spring Boot!";

            output.write(text.getBytes(StandardCharsets.UTF_8));
        }
    }
}
```

실행하면 `C:/test/example.txt` 파일에 다음 내용이 저장돼.

```text
Hello Spring Boot!
```

코드에서 중요한 부분은 다음과 같아.

```java
OutputStream output =
    new FileOutputStream("C:/test/example.txt");
```

* `OutputStream` : 변수의 타입이자 부모 클래스

* `new FileOutputStream(...)` : 실제 파일에 데이터를 기록할 객체 생성

* `output.write(...)` : 데이터를 파일에 기록

* `try (...)` : 작업이 끝나면 스트림을 자동으로 닫음

여기서 `StandardCharsets.UTF_8`은 문자열을 UTF-8 바이트로 변환해 한글 등 문자 인코딩 문제를 줄이기 위해 사용해.

### 4\. 스프링부트에서는 언제 사용할까?

파일 저장

이미지, PDF 등의 바이트 데이터를 파일에 기록할 때 사용할 수 있어.

웹 응답 전송

스프링의 `HttpServletResponse`에서 제공하는 출력 스트림을 이용해 파일이나 바이트 데이터를 브라우저로 전송할 수 있어.

파일 복사

`InputStream`으로 원본 데이터를 읽고 `OutputStream`으로 다른 파일에 기록할 수 있어.

### 5\. 지금까지 배운 클래스 한눈에 비교

| 클래스                   | 역할                          |
| --------------------- | --------------------------- |
| File                  | 파일과 폴더의 경로 및 정보 관리          |
| FileInputStream       | 파일에서 바이트 데이터 읽기             |
| FileOutputStream      | 파일에 바이트 데이터 기록              |
| InputStream           | 입력 스트림의 공통 기능을 정의하는 추상 클래스  |
| OutputStream          | 출력 스트림의 공통 기능을 정의하는 추상 클래스  |
| FileNotFoundException | 파일을 찾거나 여는 과정에서 발생할 수 있는 예외 |

핵심 정리: `InputStream`과 `OutputStream`은 각각 입력과 출력을 위한 부모 클래스야. 파일에 한정된 기능이 아니라 다양한 입출력 대상에 공통으로 사용할 수 있는 구조를 제공해.

스프링부트에서 이 클래스가 등장하면 `InputStream`은 데이터를 읽는 방향, `OutputStream`은 데이터를 보내거나 기록하는 방향이라고 이해하면 돼.
