스프링부트에서 `import java.io.FileOutputStream;`은 파일에 데이터를 기록하거나 저장하기 위해 사용하는 자바 클래스야.

앞에서 배운 `FileInputStream`과 반대 방향으로 생각하면 이해하기 쉬워.

* `FileInputStream` : 파일에서 데이터를 읽어옴

* `FileOutputStream` : 파일에 데이터를 기록함

둘 다 스프링부트 전용이 아니라 자바에서 기본으로 제공하는 클래스야.

### 1\. `FileOutputStream`의 의미

```java
import java.io.FileOutputStream;
```

| 코드               | 의미                       |
| ---------------- | ------------------------ |
| java             | 자바 기본 패키지                |
| io               | 입출력(Input/Output) 관련 패키지 |
| FileOutputStream | 파일에 바이트 데이터를 기록하는 클래스    |
| import           | 클래스를 가져오는 키워드            |

### 2\. 실제 사용 예제

예를 들어 `example.txt` 파일에 `Hello Spring Boot!`라는 데이터를 저장한다고 가정해 보자.

```java
import java.io.FileOutputStream;
import java.io.IOException;

public class FileTest {

    public static void main(String[] args) throws IOException {

        String text = "Hello Spring Boot!";

        try (FileOutputStream fos =
                 new FileOutputStream("C:/test/example.txt")) {

            fos.write(text.getBytes());
        }
    }
}
```

이 코드를 실행하면 `C:/test/example.txt` 파일에 다음 데이터가 기록돼.

```text
Hello Spring Boot!
```

코드의 핵심을 살펴보자.

* `new FileOutputStream(...)` : 지정한 파일을 출력 대상으로 열어. 파일이 없으면 새로 만들지만, 상위 폴더가 없으면 실패할 수 있어.

* `text.getBytes()` : 문자열을 바이트 배열로 변환해.

* `fos.write(...)` : 바이트 데이터를 파일에 기록해.

* `try (...)` : 작업이 끝나면 파일을 자동으로 닫아 줘.

참고로 기본 생성 방식은 기존 파일이 있다면 내용을 덮어써. 기존 내용 뒤에 추가하려면 다음처럼 작성해.

```java
FileOutputStream fos =
    new FileOutputStream("C:/test/example.txt", true);
```

두 번째 인자인 `true`가 기존 파일에 데이터를 추가한다는 의미야.

### 3\. `File`, `FileInputStream`, `FileOutputStream` 비교

`File`

파일이나 폴더의 경로와 정보를 다뤄.

예: 파일이 존재하는지 확인하기

`FileInputStream`

파일에서 데이터를 읽어와.

예: 이미지 파일의 데이터 읽기

`FileOutputStream`

파일에 데이터를 기록해.

예: 이미지 파일 저장하기

### 4\. 스프링부트에서는 어디에 사용할까?

예를 들어 스프링부트로 게시판을 만들 때 사용자가 이미지를 업로드한다고 생각해 보자.

파일 처리 과정은 다음과 같아.

사용자가 이미지 업로드

브라우저에서 서버로 이미지 전송

스프링부트에서 파일 받기

주로 MultipartFile 사용

파일을 서버에 저장

FileOutputStream 또는 MultipartFile.transferTo() 등 활용

실제 스프링부트의 웹 파일 업로드에서는 `MultipartFile.transferTo()`나 `Files.copy()`를 사용하는 경우도 많아. `FileOutputStream`은 파일에 바이트 데이터를 직접 기록해야 할 때 유용해.

### 핵심 정리

`FileOutputStream` = 파일에 데이터를 출력(기록)하는 클래스

* `FileInputStream` → 파일에서 읽기

* `FileOutputStream` → 파일에 쓰기

여기서 말하는 출력은 화면에 출력한다는 뜻이 아니라, 파일에 데이터를 저장한다는 뜻이야.
