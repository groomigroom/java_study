스프링부트에서 `import java.io.FileNotFoundException;`은 파일을 찾을 수 없거나 파일을 열 수 없을 때 발생하는 예외를 처리하기 위해 사용하는 클래스야.

지난번에 배운 `File`과 `FileInputStream`을 연결하면 쉽게 이해할 수 있어.

* `File` : 파일의 경로나 정보를 다룸

* `FileInputStream` : 파일의 데이터를 읽음

* `FileNotFoundException` : 파일을 찾거나 여는 과정에서 문제가 발생했을 때 사용하는 예외 클래스

세 가지 모두 스프링부트 전용이 아니라 자바에서 기본으로 제공해.

### 1\. `FileNotFoundException`의 의미

```java
import java.io.FileNotFoundException;
```

`FileNotFoundException`은 `IOException`의 하위 클래스야.

즉, 파일 입출력 작업에서 발생할 수 있는 예외 중 하나야.

예를 들어 다음과 같은 상황에서 발생할 수 있어.

* 지정한 경로에 파일이 없음

* 파일을 열 수 있는 조건이 충족되지 않음

* 파일을 읽기 위해 열려고 했지만 파일 접근에 실패함

### 2\. 실제 사용 예제

존재하지 않는 파일을 읽으려고 하면 어떻게 될까?

```java
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class FileTest {

    public static void main(String[] args) {

        try {
            FileInputStream fis =
                new FileInputStream("C:/test/없는파일.txt");

        } catch (FileNotFoundException e) {
            System.out.println("파일을 찾을 수 없습니다.");
        }
    }
}
```

실행 결과 예시:

```text
파일을 찾을 수 없습니다.
```

코드의 동작 순서를 살펴보자.

1\. 파일 열기 시도

`new FileInputStream(...)`

2\. 파일 열기 실패

FileNotFoundException 발생

3\. 예외 처리

catch 블록 실행

여기서 핵심은 `try-catch`야.

* `try` : 예외가 발생할 가능성이 있는 코드를 실행해.

* `catch` : 지정한 예외가 발생하면 그 예외를 처리해.

* `FileNotFoundException e` : 발생한 예외 정보를 `e`라는 변수로 받아.

### 3\. 스프링부트에서는 언제 사용할까?

예를 들어 스프링부트에서 파일 업로드나 다운로드 기능을 개발한다고 생각해 보자.

```java
try {
    FileInputStream fis =
        new FileInputStream("C:/upload/image.jpg");

} catch (FileNotFoundException e) {
    System.out.println("이미지 파일이 없습니다.");
}
```

서버가 `C:/upload/image.jpg` 파일을 열려고 했는데 해당 파일이 없거나 열 수 없다면 예외 처리가 필요할 수 있어.

다만 실제 웹 서비스에서는 단순히 메시지를 출력하기보다 적절한 로그를 남기거나 사용자에게 오류 응답을 반환하는 방식으로 처리해.

### 4\. `FileNotFoundException`과 `IOException`의 차이

| 구분                    | 의미                                 |
| --------------------- | ---------------------------------- |
| FileNotFoundException | 파일을 찾거나 여는 과정에서 발생하는 특정 예외         |
| IOException           | 파일 읽기·쓰기 등 입출력 작업 전반에서 발생할 수 있는 예외 |

예를 들어 다음처럼 사용할 수 있어.

```java
try {
    FileInputStream fis =
        new FileInputStream("C:/test/example.txt");

} catch (FileNotFoundException e) {
    System.out.println("파일 열기에 실패했습니다.");
} catch (IOException e) {
    System.out.println("입출력 중 오류가 발생했습니다.");
}
```

단, 위 코드처럼 `FileInputStream`만 생성하는 경우에는 `FileNotFoundException`을 처리하면 되고, `IOException`을 처리하려면 해당 예외가 발생할 수 있는 추가 작업이 있어야 해. 또한 `FileNotFoundException`은 `IOException`의 하위 클래스이므로 위와 같이 구체적인 예외를 먼저 처리해야 해.

### 핵심 정리

`FileNotFoundException`은 파일을 열거나 찾는 과정에서 문제가 발생했을 때 사용하는 예외 클래스야.

특히 `FileInputStream`으로 파일을 읽을 때 자주 볼 수 있어.

파일 경로 지정 → 파일 열기 시도 → 실패하면 예외 발생 → catch에서 오류 처리

이 흐름을 이해하면 스프링부트에서 파일 처리 코드를 읽을 때 훨씬 수월해질 거야.
