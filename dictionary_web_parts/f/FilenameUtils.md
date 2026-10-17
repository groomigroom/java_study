`org.apache.commons.io.FilenameUtils`는 스프링 부트(Spring Boot)에서 파일 이름, 확장자, 파일 경로 등을 편리하게 처리하도록 도와주는 클래스입니다.

예를 들어 사용자가 이미지 파일을 업로드했을 때 파일 이름에서 `jpg` 확장자를 추출하거나, 경로에서 파일 이름만 분리할 때 사용할 수 있습니다.

### 1\. `FilenameUtils`의 의미

```java
import org.apache.commons.io.FilenameUtils;
```

각 부분을 살펴보겠습니다.

* `org.apache.commons.io` : Apache Commons IO 라이브러리의 패키지입니다. 파일과 입출력 관련 기능을 제공합니다.

* `FilenameUtils` : 파일 이름과 경로를 다루는 유틸리티 클래스입니다.

즉, 파일을 직접 읽거나 저장하는 클래스가 아니라, 파일 이름과 경로를 분석하고 가공하는 클래스입니다.

### 2\. 스프링 부트에서 사용하는 예제

게시판에 이미지 업로드 기능을 만든다고 가정하겠습니다.

파일 이름이 다음과 같다고 해보겠습니다.

```text
/board/images/profile.jpg
```

profile.jpg

파일 경로: /board/images/profile.jpg

파일 이름

profile.jpg

확장자

jpg

이름만

profile

`FilenameUtils`를 사용하면 이런 정보를 간단하게 추출할 수 있습니다.

### 3\. 주요 메서드 알아보기

먼저 Apache Commons IO 라이브러리가 프로젝트에 추가되어 있어야 합니다.

Gradle을 사용한다면 `build.gradle`의 `dependencies`에 다음 코드를 추가합니다.

```gradle
implementation 'commons-io:commons-io:2.20.0'
```

그다음 다음 예제를 실행할 수 있습니다.

```java
import org.apache.commons.io.FilenameUtils;

public class FileExample {

    public static void main(String[] args) {

        String path = "/board/images/profile.jpg";

        // 파일 이름 추출
        System.out.println(
            FilenameUtils.getName(path)
        );

        // 확장자 추출
        System.out.println(
            FilenameUtils.getExtension(path)
        );

        // 확장자를 제외한 파일 이름 추출
        System.out.println(
            FilenameUtils.getBaseName(path)
        );

        // 디렉터리 경로 추출
        System.out.println(
            FilenameUtils.getFullPath(path)
        );
    }
}
```

실행 결과는 다음과 같습니다.

```text
profile.jpg
jpg
profile
/board/images/
```

### 메서드별 의미

| 메서드                   | 기능                   | 결과                    |
| --------------------- | -------------------- | --------------------- |
| getName(path)         | 파일 이름 전체 추출          | profile.jpg           |
| getExtension(path)    | 확장자 추출               | jpg                   |
| getBaseName(path)     | 확장자를 제외한 파일 이름 추출    | profile               |
| getFullPath(path)     | 디렉터리 경로 추출           | /board/images/        |
| getPath(path)         | 마지막 파일 이름을 제외한 경로 추출 | board/images/         |
| removeExtension(path) | 확장자를 제거한 전체 경로 반환    | /board/images/profile |

### 4\. 실제 스프링 부트 파일 업로드에서 활용하기

예를 들어 사용자가 `dog.jpg`라는 이미지를 업로드했다고 가정하겠습니다.

```java
import org.apache.commons.io.FilenameUtils;

String originalFilename = "dog.jpg";

String extension =
    FilenameUtils.getExtension(originalFilename);

if (extension.equalsIgnoreCase("jpg")
        || extension.equalsIgnoreCase("png")) {

    System.out.println("허용된 확장자입니다.");
}
```

이 코드는 확장자가 `jpg` 또는 `png`인지 확인하는 간단한 예제입니다.

다만 확장자 검사만으로는 파일의 실제 형식이나 안전성을 보장할 수 없습니다. 실제 업로드 기능에서는 파일 크기, 실제 파일 형식, 저장 경로 등을 추가로 검증해야 합니다.

또한 `FilenameUtils`는 스프링 부트에 기본 포함되는 클래스가 아니라 Apache Commons IO 라이브러리에 포함된 클래스입니다.

### 5\. 비슷한 자바 클래스와 차이점

| 클래스                      | 주요 역할                          |
| ------------------------ | ------------------------------ |
| java.io.File             | 파일 및 디렉터리 경로를 표현하고 파일 관련 작업 수행 |
| java.io.FileInputStream  | 파일에서 바이트 데이터 읽기                |
| java.io.FileOutputStream | 파일에 바이트 데이터 쓰기                 |
| FilenameUtils            | 파일 이름, 확장자, 경로 분석 및 가공         |

예를 들어 다음처럼 역할이 다릅니다.

* `FilenameUtils` → `dog.jpg`에서 `jpg` 추출

* `FileInputStream` → `dog.jpg`의 실제 데이터 읽기

* `FileOutputStream` → 파일 데이터를 다른 파일에 쓰기

핵심 정리: `FilenameUtils`는 스프링 부트에서 파일 업로드, 이미지 관리, 첨부파일 처리 등을 구현할 때 파일 이름과 경로를 쉽게 다루도록 도와주는 유틸리티 클래스입니다.
