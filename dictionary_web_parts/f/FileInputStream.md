스프링부트에서 `import java.io.FileInputStream;`은 파일에 저장된 데이터를 읽어오기 위해 사용하는 자바 클래스야.

지난번에 설명한 `java.io.File`과 비교하면 이해하기 쉬워.

* `File` : 파일의 경로나 존재 여부 등을 관리

* `FileInputStream` : 파일 안에 저장된 데이터를 읽음

둘 다 스프링부트 전용 기능이 아니라 자바에서 기본으로 제공하는 클래스야.

### 1\. `FileInputStream`의 의미

```java
import java.io.FileInputStream;
```

각 부분을 살펴보면 다음과 같아.

| 코드              | 의미                              |
| --------------- | ------------------------------- |
| java            | 자바의 기본 패키지                      |
| io              | 입출력(Input/Output) 관련 패키지        |
| FileInputStream | 파일에서 데이터를 읽는 클래스                |
| import          | 다른 패키지의 클래스를 사용할 수 있도록 가져오는 키워드 |

### 2\. 실제 사용 예제

예를 들어 `C:/test/example.txt` 파일에 다음 내용이 있다고 가정해 보자.

```text
Hello Spring Boot!
```

이 파일의 내용을 읽으려면 다음과 같이 작성할 수 있어.

```java
import java.io.FileInputStream;
import java.io.IOException;

public class FileTest {

    public static void main(String[] args) throws IOException {

        FileInputStream fis =
                new FileInputStream("C:/test/example.txt");

        int data;

        while ((data = fis.read()) != -1) {
            System.out.print((char) data);
        }

        fis.close();
    }
}
```

실행 결과는 다음과 같아.

```text
Hello Spring Boot!
```

### 코드의 동작 순서

1\. 파일 열기

new FileInputStream(파일 경로)

2\. 데이터 읽기

read()로 바이트 단위로 읽기

3\. 데이터 출력

읽은 값을 문자로 변환해 출력

4\. 파일 닫기

close()로 자원 해제

여기서 중요한 메서드는 세 가지야.

* `read()` : 파일의 데이터를 1바이트씩 읽어. 파일 끝에 도달하면 `-1`을 반환해.

* `close()` : 파일을 읽기 위해 사용한 자원을 해제해.

* `new FileInputStream(...)` : 지정한 경로의 파일을 읽기 위해 열어. 파일이 없으면 `FileNotFoundException`이 발생할 수 있어.

실제 코드에서는 파일이 예외가 발생해도 닫히도록 `try-with-resources`를 사용하는 것이 좋아.

```java
try (FileInputStream fis =
         new FileInputStream("C:/test/example.txt")) {

    int data;

    while ((data = fis.read()) != -1) {
        System.out.print((char) data);
    }
}
```

### 3\. 스프링부트에서는 언제 사용할까?

| 사용 상황          | 설명                       |
| -------------- | ------------------------ |
| 이미지 파일 읽기      | 이미지 파일의 바이트 데이터 읽기       |
| PDF 파일 읽기      | PDF 파일의 원본 데이터 읽기        |
| 파일 복사          | 원본 파일의 데이터를 읽어 다른 파일에 기록 |
| 설정 및 데이터 파일 처리 | 파일의 내용을 읽어 프로그램에서 사용     |

참고로 스프링부트에서 사용자가 웹페이지에 파일을 업로드하는 기능을 만들 때는 일반적으로 `MultipartFile`을 사용하고, 파일을 직접 읽을 때는 `FileInputStream` 등을 사용할 수 있어.

### 4\. `File`과 `FileInputStream`의 차이

### `File`

```java
File file = new File("C:/test/example.txt");
```

파일의 경로를 나타내고 존재 여부, 크기 등을 확인하는 데 사용해.

### `FileInputStream`

```java
FileInputStream fis =
    new FileInputStream("C:/test/example.txt");
```

파일을 열어 실제 데이터를 읽는 데 사용해.

핵심은 `File`은 파일을 가리키고, `FileInputStream`은 그 파일의 데이터를 읽는다는 것이야.

추가로, 텍스트 파일의 한글을 읽을 때는 `FileInputStream`만으로 문자를 처리하기보다 `InputStreamReader`에 `UTF-8` 인코딩을 지정하거나 `Files.readString()`을 사용하는 편이 안전해. `FileInputStream`은 기본적으로 바이트를 읽기 때문이야.
