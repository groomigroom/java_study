스프링부트에서 `import java.io.File;`은 자바에서 파일이나 디렉터리를 다루기 위해 사용하는 클래스를 가져오는 코드야.

스프링부트 전용 기능은 아니고, 자바에서 기본으로 제공하는 클래스야. 스프링부트 프로젝트에서도 그대로 사용할 수 있어.

### 1\. `java.io.File;`의 의미

```java
import java.io.File;
```

각 부분의 의미는 다음과 같아.

| 코드     | 의미                       |
| ------ | ------------------------ |
| java   | 자바의 기본 패키지               |
| io     | 입출력(Input/Output) 관련 패키지 |
| File   | 파일과 디렉터리를 다루는 클래스        |
| import | 다른 패키지의 클래스를 가져오는 키워드    |

예를 들어, 파일을 다루려면 `File` 클래스를 사용할 수 있어.

### 2\. 스프링부트에서 사용하는 예제

### 예제 1. 파일이 존재하는지 확인하기

```java
import java.io.File;

public class FileTest {
    public static void main(String[] args) {

        File file = new File("C:/test/example.txt");

        if (file.exists()) {
            System.out.println("파일이 존재합니다.");
        } else {
            System.out.println("파일이 없습니다.");
        }
    }
}
```

코드를 하나씩 살펴보자.

* `new File(...)`: 파일 경로를 나타내는 객체를 생성해.

* `file.exists()`: 해당 경로에 파일이나 디렉터리가 존재하는지 확인해.

* `System.out.println()`: 결과를 출력해.

주의할 점은 `new File()`을 실행한다고 실제 파일이 생성되는 것은 아니라는 거야.

### 예제 2. 디렉터리 확인하기

```java
File folder = new File("C:/upload");

if (folder.isDirectory()) {
    System.out.println("디렉터리입니다.");
}
```

`File` 클래스는 일반 파일뿐만 아니라 폴더도 다룰 수 있어.

### 3\. 스프링부트에서는 어디에 사용할까?

스프링부트에서 `File` 클래스는 주로 다음과 같은 작업에 활용돼.

파일 업로드

사용자가 게시판에 올린 이미지나 첨부파일을 서버의 특정 경로에 저장할 때 활용할 수 있어.

폴더 관리

업로드 폴더가 존재하는지 확인하거나 필요한 디렉터리를 생성할 때 사용해.

파일 확인 및 관리

파일의 이름, 경로, 크기 등을 확인하거나 파일을 삭제하는 작업에 활용할 수 있어.

예를 들어, 게시판에 이미지 업로드 기능을 만든다면 다음처럼 디렉터리를 생성할 수 있어.

```java
import java.io.File;

String uploadPath = "C:/upload";

File uploadFolder = new File(uploadPath);

if (!uploadFolder.exists()) {
    uploadFolder.mkdirs();
}
```

`mkdirs()`는 경로에 필요한 디렉터리가 없으면 생성해 줘.

실제 스프링부트에서는 파일 업로드를 처리할 때 `MultipartFile`과 함께 사용하는 경우도 많아.

### 4\. `File`과 `MultipartFile`의 차이

| 구분    | File             | MultipartFile           |
| ----- | ---------------- | ----------------------- |
| 제공처   | 자바 기본 라이브러리      | 스프링 프레임워크               |
| 주요 역할 | 파일 경로와 파일 시스템 관리 | HTTP 요청으로 전달된 업로드 파일 처리 |
| 예시    | 폴더 존재 여부 확인      | 사용자가 업로드한 이미지 받기        |

핵심 정리: `import java.io.File;`은 스프링부트에서 파일이나 폴더를 관리할 수 있도록 자바의 `File` 클래스를 가져오는 코드야.

혹시 지금 배우는 코드에 `java.io.File`이 포함되어 있다면, 그 코드 전체를 보여줘. 파일 업로드에 쓰이는지, 경로를 확인하는 용도인지 실제 코드 기준으로 설명해 줄게.
