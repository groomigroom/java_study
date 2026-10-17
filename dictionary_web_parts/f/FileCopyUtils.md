`org.springframework.util.FileCopyUtils`는 스프링 부트에서 파일이나 스트림의 데이터를 복사하는 데 사용하는 유틸리티 클래스입니다.

쉽게 말하면, 파일 업로드나 다운로드 기능을 만들 때 데이터의 내용을 다른 파일이나 바이트 배열로 복사할 수 있도록 도와주는 도구입니다.

### 1\. 패키지 이름 분석

```java
org.springframework.util.FileCopyUtils
```

| 구성 요소               | 의미                       |
| ------------------- | ------------------------ |
| org.springframework | 스프링 프레임워크 관련 패키지         |
| util                | 여러 곳에서 사용할 수 있는 유틸리티 기능  |
| FileCopyUtils       | 파일 및 스트림 복사 기능을 제공하는 클래스 |

### 2\. 어떤 기능을 제공할까?

대표적인 메서드는 다음과 같습니다.

| 메서드                             | 기능                      |
| ------------------------------- | ----------------------- |
| copy(byte[], OutputStream)      | 바이트 배열의 데이터를 출력 스트림에 복사 |
| copy(InputStream, OutputStream) | 입력 스트림의 데이터를 출력 스트림에 복사 |
| copy(File, File)                | 파일의 내용을 다른 파일로 복사       |
| copyToByteArray(InputStream)    | 입력 스트림의 데이터를 바이트 배열로 변환 |
| copyToString(Reader)            | 문자 스트림의 내용을 문자열로 변환     |

### 3\. 실제 코드 예제

### 예제 1. 파일 복사하기

원본 파일의 내용을 새로운 파일로 복사하는 예제입니다.

```java
import java.io.File;
import java.io.IOException;
import org.springframework.util.FileCopyUtils;

public class FileExample {

    public static void main(String[] args) throws IOException {
        File source = new File("source.txt");
        File target = new File("copy.txt");

        FileCopyUtils.copy(source, target);
    }
}
```

실행하면 `source.txt`의 내용이 `copy.txt`로 복사됩니다.

단, 원본 파일이 존재하고 읽을 수 있어야 하며, 대상 파일을 생성하거나 쓸 수 있는 경로여야 합니다.

### 예제 2. 업로드된 파일의 데이터를 복사하기

스프링 부트에서 입력 스트림을 출력 스트림으로 복사하는 예제입니다.

```java
import java.io.InputStream;
import java.io.OutputStream;
import java.io.IOException;
import org.springframework.util.FileCopyUtils;

public void copyFile(
        InputStream input,
        OutputStream output
) throws IOException {

    FileCopyUtils.copy(input, output);
}
```

여기서 각 요소의 역할은 다음과 같습니다.

* `InputStream`: 데이터를 읽어 오는 통로

* `OutputStream`: 데이터를 내보내는 통로

* `FileCopyUtils.copy()`: 입력 통로에서 읽은 데이터를 출력 통로로 복사

### 4\. 파일 업로드에서 어떻게 활용할까?

예를 들어 사용자가 게시판에 이미지를 첨부했다고 가정해 보겠습니다.

사용자가 선택한 이미지

MultipartFile

FileCopyUtils.copy()

파일 데이터 복사

서버의 저장 경로

업로드 파일 저장

다만 스프링 부트에서 `MultipartFile`을 업로드할 때는 다음처럼 `transferTo()`를 사용하는 방법도 있습니다.

```java
import org.springframework.web.multipart.MultipartFile;
import java.io.File;
import java.io.IOException;

public void saveFile(
        MultipartFile file
) throws IOException {

    File target = new File("/uploads/" + file.getOriginalFilename());

    file.transferTo(target);
}
```

위 코드는 개념을 보여 주기 위한 예제입니다. 실제 서비스에서는 파일명 중복, 경로 조작, 저장 디렉터리 권한 등을 고려해야 합니다.

### 5\. `FileCopyUtils`와 `File`의 차이

| 구분    | java.io.File          | FileCopyUtils            |
| ----- | --------------------- | ------------------------ |
| 종류    | 자바의 파일 및 경로 표현 클래스    | 스프링의 복사 유틸리티 클래스         |
| 주요 역할 | 파일 경로, 존재 여부, 속성 등 관리 | 파일과 스트림의 데이터 복사          |
| 패키지   | java.io               | org.springframework.util |

핵심 정리: `FileCopyUtils`는 스프링 프레임워크가 제공하는 데이터 복사 도구입니다. 스프링 부트의 파일 업로드, 다운로드, 파일 처리 기능에서 활용할 수 있으며, 파일 경로 자체를 관리하는 `File` 클래스와는 역할이 다릅니다.
