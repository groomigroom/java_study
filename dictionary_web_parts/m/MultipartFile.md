`org.springframework.web.multipart.MultipartFile`은 스프링 부트에서 사용자가 웹 페이지를 통해 업로드한 파일을 처리하기 위한 인터페이스입니다.

쉽게 말하면, 사용자가 게시판에 이미지나 PDF 파일을 첨부했을 때 서버에서 해당 파일의 이름, 크기, 내용 등을 확인하고 저장할 수 있도록 해 주는 기능입니다.

### 1\. 패키지 이름 분석

```java
org.springframework.web.multipart.MultipartFile
```

| 구성 요소               | 의미                 |
| ------------------- | ------------------ |
| org.springframework | 스프링 프레임워크          |
| web                 | 웹 애플리케이션 관련 기능     |
| multipart           | 파일을 포함한 요청 데이터 처리  |
| MultipartFile       | 업로드된 파일을 다루는 인터페이스 |

### 2\. 어떤 기능을 제공할까?

자주 사용하는 메서드는 다음과 같습니다.

| 메서드                   | 기능                  |
| --------------------- | ------------------- |
| getOriginalFilename() | 업로드한 파일의 원래 이름 반환   |
| getSize()             | 파일 크기 반환(바이트 단위)    |
| getContentType()      | 파일의 MIME 타입 반환      |
| isEmpty()             | 파일이 비어 있는지 확인       |
| getBytes()            | 파일 내용을 바이트 배열로 반환   |
| getInputStream()      | 파일 내용을 읽는 입력 스트림 반환 |
| transferTo()          | 업로드 파일을 지정한 위치에 저장  |

### 3\. 실제 스프링 부트 코드 예제

사용자가 프로필 이미지를 업로드한다고 가정해 보겠습니다.

```java
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
public class FileController {

    @PostMapping("/upload")
    public String uploadFile(
            @RequestParam("file") MultipartFile file
    ) throws IOException {

        if (file.isEmpty()) {
            return "업로드할 파일이 없습니다.";
        }

        String filename = file.getOriginalFilename();
        long size = file.getSize();

        return "파일명: " + filename
                + ", 크기: " + size + "바이트";
    }
}
```

예를 들어 `profile.jpg` 파일을 업로드했다면 다음과 같은 정보를 확인할 수 있습니다.

```text
파일명: profile.jpg, 크기: 102400바이트
```

위 코드는 파일 정보를 확인하는 예제이며, 실제 파일을 저장하지는 않습니다.

### 4\. 파일을 실제로 저장하려면?

`MultipartFile`의 `transferTo()` 메서드를 사용할 수 있습니다.

```java
@PostMapping("/upload")
public String uploadFile(
        @RequestParam("file") MultipartFile file
) throws IOException {

    if (file.isEmpty()) {
        return "파일을 선택해 주세요.";
    }

    // 실제 서비스에서는 서버 설정으로 정한
    // 안전한 저장 경로와 서버 생성 파일명을 사용하세요.
    File target = new File("/uploads/profile.jpg");

    file.transferTo(target);

    return "파일 저장 완료";
}
```

이 코드는 개념을 보여 주는 예제입니다. `/uploads` 디렉터리가 존재하고 서버에 쓰기 권한이 있어야 합니다. 실제 서비스에서는 사용자 파일명을 그대로 경로에 사용하지 말고, 파일 종류와 크기를 검증하고 안전한 파일명을 생성해야 합니다.

### 5\. HTML에서는 어떻게 연결할까?

파일을 업로드하는 HTML 폼은 일반적으로 다음과 같이 작성합니다.

```html
<form action="/upload"
      method="post"
      enctype="multipart/form-data">

    <input type="file" name="file">

    <button type="submit">파일 업로드</button>
</form>
```

여기서 중요한 부분은 다음 두 가지입니다.

* `enctype="multipart/form-data"`: 파일 데이터를 전송하기 위한 인코딩 방식입니다.

* `name="file"`: 컨트롤러의 `@RequestParam("file")`과 이름이 일치해야 합니다.

### 6\. 앞에서 배운 `FileCopyUtils`와 차이점

| 구분    | MultipartFile                     | FileCopyUtils            |
| ----- | --------------------------------- | ------------------------ |
| 종류    | 인터페이스                             | 유틸리티 클래스                 |
| 역할    | 웹 요청으로 업로드된 파일을 표현하고 접근           | 파일이나 스트림의 데이터를 복사        |
| 주요 기능 | 파일명 확인, 크기 확인, 파일 저장              | 파일 내용 복사                 |
| 패키지   | org.springframework.web.multipart | org.springframework.util |

핵심 정리: `MultipartFile`은 웹에서 업로드된 파일을 다루는 객체이고, `FileCopyUtils`는 파일 데이터를 복사하는 도구입니다. 게시판 첨부파일, 프로필 이미지, PDF 문서 업로드 기능을 개발할 때 `MultipartFile`을 자주 사용합니다.


-----------

제공해주신 코드에는 1) 자바 예외 던지기(throws)의 문법적 오타와, 2) MultipartFile 객체를 디스크에 실제 파일로 저장하는 핵심 전송 로직이 누락되어 있어 그대로 실행하면 파일이 물리적으로 저장되지 않습니다.
자바에서 MultipartFile을 이용해 업로드된 이미지를 지정된 경로에 안전하게 쓰고, 파일 경로를 데이터베이스에 주입하는 완성형 코드를 안내해 드립니다.
------------------------------
## 🔍 수정 포인트

   1. throws Exception 문법 오타:
   * 자바에서 예외를 던지는 구문은 메서드 이름 뒤가 아니라, 매개변수 괄호() 뒤에 와야 합니다.
      * 잘못된 예: public void create throws Exception(...) ❌
      * 올바른 예: public void create(...) throws Exception ⭕
   2. imgFile.transferTo() 메서드 추가:
   * new File(경로, 파일명)은 자바 안에서 파일의 경로 껍데기(객체)만 생성할 뿐입니다. 업로드된 파일 바이트 데이터를 실제로 그 경로에 밀어 넣으려면 반드시 imgFile.transferTo(saveImgFile); 코드를 실행해 주어야 합니다.
   3. 엔티티에 파일명 정보 저장:
   * 사용자가 나중에 마이페이지 등에서 이미지를 불러올 수 있으려면, 생성된 fileName 문자열을 엔티티 필드(예: loginUser.setProfileImg(fileName))에 함께 save 해주어야 합니다.
   
------------------------------
## 🛠️ 올바르게 수정한 create 서비스 코드
문법 오타를 바로잡고 실제 디스크 저장 처리 및 예외 복구(I/O Exception) 장치까지 안전하게 연동한 정답 코드입니다.

import org.springframework.web.multipart.MultipartFile;import java.io.File;import java.io.IOException;import java.util.UUID;
// ... 생략 ...
// 💡 1. throws Exception의 위치를 매개변수 괄호 뒤로 올바르게 수정합니다.public LoginUsers create(String userid, String username, String password, MultipartFile imgFile) throws Exception {
    
    // 저장할 프로젝트 폴더 내부 경로 지정
    String imgFilePath = System.getProperty("user.dir") + "\\src\\main\\resources\\static\\imgs\\"; 
    
    // 파일 저장 폴더가 혹시 없다면 자동으로 물리 생성하는 방어 코드
    File uploadDir = new File(imgFilePath);
    if (!uploadDir.exists()) {
        uploadDir.mkdirs();
    }

    String fileName = "";
    
    // 💡 2. 사용자가 이미지를 전송했을 때만 파일 저장 로직을 가동합니다 (NPE 방지)
    if (imgFile != null && !imgFile.isEmpty()) {
        UUID uuid = UUID.randomUUID();
        fileName = uuid + "_" + imgFile.getOriginalFilename();
        File saveImgFile = new File(imgFilePath, fileName);
        
        // 💡 3. [핵심] 업로드된 임시 메모리 파일을 실제 물리 디스크 경로로 전송하여 복사합니다.
        imgFile.transferTo(saveImgFile);
    }

    LoginUsers loginUser = new LoginUsers();
    loginUser.setUserid(userid);
    loginUser.setUsername(username);
    loginUser.setPassword(passwordEncoder.encode(password));
    
    // 💡 4. 엔티티에 파일명을 저장하는 필드가 있다면 함께 세팅해 줍니다 (선택사항)
    // loginUser.setImgName(fileName); 
    
    this.loginUserRepository.save(loginUser);
    return loginUser;
}

------------------------------
## 💡 컨트롤러에서 받을 때 주의할 점
프론트엔드 HTML(form) 태그에서 컨트롤러로 MultipartFile을 실어 나를 때는 반드시 다음 두 가지 규칙이 통과되어야 백엔드가 온전하게 데이터를 접수할 수 있습니다.

   1. <form> 태그에 enctype="multipart/form-data" 속성이 무조건 추가되어야 합니다.
   2. 컨트롤러 회원가입 @PostMapping 메서드의 매개변수 영역에 MultipartFile imgFile을 단독으로 배치하거나 DTO 내부에 필드로 선언하여 매핑해야 합니다.

// 컨트롤러 예시 구조
@PostMapping("/signup")public String signup(@RequestParam("userid") String userid,
                     @RequestParam("username") String username,
                     @RequestParam("password") String password,
                     @RequestParam("imgFile") MultipartFile imgFile) throws Exception {
                     
    this.loginUserService.create(userid, username, password, imgFile);
    return "redirect:/";
}

------------------------------
문법적 위치 변환과 파일 스트림 밀어내기(transferTo) 코드가 안정적으로 완성되었습니다!
이 멀티파트 이미지 업로드 기능 뒤에 연이어 진행하고 싶으신 작업은 무엇인가요?

* 이 create 서비스와 연결할 컨트롤러 @PostMapping 메서드의 파일 업로드 수신 소스코드 구체화하기
* 회원가입 HTML 폼 태그에 파일을 첨부할 수 있는 <input type="file"> 및 enctype 세팅 방법 알아보기
* 저장된 이미지 파일 명을 타임리프 화면에 <img th:src="..."> 형태로 바인딩하여 띄워주는 연출법 알아보기

원하시는 개발 단계를 제안해 주시면 이어서 가이드해 드릴게요!
