스프링부트에서 `import java.io.InputStream;`은 데이터를 읽어오기 위한 입력 스트림의 기본 클래스야.

앞에서 배운 `FileInputStream`과 연결해서 이해하면 쉬워. `FileInputStream`이 파일에서 데이터를 읽는 구체적인 클래스라면, `InputStream`은 데이터를 읽는 여러 클래스가 공통으로 사용하는 부모 클래스야.

### 1\. `InputStream`의 의미

```java
import java.io.InputStream;
```

| 코드          | 의미                       |
| ----------- | ------------------------ |
| java        | 자바 기본 패키지                |
| io          | 입출력(Input/Output) 관련 패키지 |
| InputStream | 바이트 데이터를 읽기 위한 추상 클래스    |
| import      | 클래스를 가져오는 키워드            |

여기서 중요한 점은 `InputStream`이 추상 클래스(abstract class) 라는 거야. 따라서 일반적으로 `new InputStream()`처럼 직접 객체를 생성하는 것이 아니라, 이를 상속한 클래스를 사용해.

### 2\. `InputStream`과 `FileInputStream`의 관계

InputStream

데이터 읽기의 공통 기능을 정의하는 부모 클래스

FileInputStream

파일에서 읽기

기타 입력 스트림

메모리, 네트워크 등에서 읽기

예를 들어 다음과 같은 코드가 있어.

```java
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.IOException;

public class FileTest {

    public static void main(String[] args) throws IOException {

        try (InputStream input =
                 new FileInputStream("C:/test/example.txt")) {

            int data;

            while ((data = input.read()) != -1) {
                System.out.print((char) data);
            }
        }
    }
}
```

여기서 핵심은 다음 부분이야.

```java
InputStream input =
    new FileInputStream("C:/test/example.txt");
```

* `InputStream` : 변수의 타입이자 부모 클래스

* `new FileInputStream(...)` : 실제 파일을 읽는 객체 생성

* `input.read()` : 파일의 데이터를 읽음

부모 클래스인 `InputStream` 타입으로 변수를 선언하면 다른 종류의 입력 스트림으로도 교체하기 쉬워져. 이를 자바의 다형성과 연결해서 이해할 수 있어.

### 3\. 스프링부트에서는 어디에 사용할까?

스프링부트에서는 다양한 종류의 데이터를 읽을 때 활용할 수 있어.

![Document file - free Icon PNG, SVG](https://images.openai.com/static-rsc-4/vAOzhYuL0koM0kqq673PNsxARuMCiC4Tw4hCk9iS4EHn6M07hcgYxRsY6VVd-zX6_GsHzuxuvCXIMajiTv2dM-j7RlVNYe3OaCKXeiKurs9haY5GP_StX7gcgNzgQhe72ft1ISKb5aDXs2ZvpeLc1VKgDDS_kP3PZETdSER7xjklpcB-ev3fKe_qv6Z8U2B4?purpose=fullsize)

파일 읽기

`FileInputStream`을 `InputStream` 타입으로 받아 파일 데이터를 처리할 수 있어.

웹 요청이나 네트워크 데이터 읽기

HTTP 응답 본문처럼 입력 스트림으로 제공되는 데이터를 읽을 수 있어.

이미지나 PDF 데이터 처리

파일의 내용을 바이트 단위로 읽어 다른 곳으로 전달하거나 처리할 수 있어.

### 4\. 지금까지 배운 클래스 비교

| 클래스                   | 핵심 역할                         |
| --------------------- | ----------------------------- |
| File                  | 파일이나 폴더의 경로와 정보 관리            |
| FileInputStream       | 파일에서 바이트 데이터 읽기               |
| FileOutputStream      | 파일에 바이트 데이터 기록                |
| InputStream           | 여러 입력 스트림의 공통 기능을 정의하는 추상 클래스 |
| FileNotFoundException | 파일을 찾거나 여는 과정에서 발생할 수 있는 예외   |

핵심 정리: `InputStream`은 특정 파일만을 위한 클래스가 아니라, 다양한 곳에서 바이트 데이터를 읽는 방식을 공통으로 정의하는 클래스야.

스프링부트 코드를 읽을 때 `InputStream`이 보인다면, 우선 “어떤 데이터의 입력을 받아 읽는구나” 라고 이해하면 돼.
