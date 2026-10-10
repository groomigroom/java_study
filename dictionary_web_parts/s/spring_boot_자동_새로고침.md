네, 맞습니다. On 'Update' action과 On frame deactivation 설정은 인텔리제이 유료 버전(Ultimate)에서만 제공하는 Spring Boot 전용 기능입니다.
현재 사용 중이신 무료 버전(Community)에서는 Spring Boot 실행 구성을 정식으로 지원하지 않기 때문에 해당 메뉴가 뜨지 않는 것이 정상입니다.
하지만 무료 버전에서도 대신 사용할 수 있는 자동 새로고침 설정 방법이 있으니 아래 대로 세팅해 보세요!
------------------------------
## 무료 버전(Community)에서 자동 새로고침 세팅하는 법## 1. 인텔리제이 기본 자동 빌드 켜기 (필수)

   1. 상단 메뉴에서 File > Settings (Mac은 IntelliJ IDEA > Settings)로 이동합니다.
   2. 왼쪽 메뉴에서 Build, Execution, Deployment > Compiler를 클릭합니다.
   3. 우측 항목 중 Build project automatically를 찾아 체크하고 적용(Apply)합니다.

## 2. 고급 설정에서 실행 중 빌드 허용하기

   1. 설정(Settings) 창 왼쪽 제일 아래에 있는 Advanced Settings 메뉴로 이동합니다.
   2. Compiler 섹션에서 Allow auto-make to start even if developed application is currently running 항목을 찾아 체크합니다.
   3. OK를 눌러 설정 창을 닫습니다.

## 3. 프로젝트 코드 설정 (build.gradle 또는 pom.xml)
서버가 바뀐 코드를 감지하고 자동으로 재시작하게 하려면 프로젝트 설정에 devtools 라이브러리가 들어가 있어야 합니다.

* Gradle을 사용하는 경우 (build.gradle):
dependencies 블록 안에 아래 코드를 넣고 🐘 모양 버튼(Load Gradle Changes)을 눌러 동기화합니다.

developmentOnly 'org.springframework.boot:spring-boot-devtools'

* Maven을 사용하는 경우 (pom.xml):
dependencies 블록 안에 아래 코드를 넣고 동기화합니다.

<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-devtools</artifactId>
    <optional>true</optional>
</dependency>


------------------------------
## 💡 무료 버전에서 변경 사항을 바로 반영하는 팁
위 설정을 모두 마쳤더라도, 무료 버전에서는 코드를 수정하고 브라우저를 새로고침했을 때 바로 반영이 안 될 수 있습니다.
그럴 때는 인텔리제이 화면에서 수동 빌드 단축키를 한 번 눌러주시면 서버를 껐다 켤 필요 없이 바로 반영됩니다.

* Windows / Linux: Ctrl + F9 (또는 Ctrl + Shift + F9)
* Mac: Cmd + F9 (또는 Cmd + Shift + F9)

설정을 따라 하시는 과정에서 Compiler나 Advanced Settings 메뉴를 찾기 어려우시거나, 코드를 고쳐도 여전히 새로고침이 안 된다면 어떤 부분이 막히는지 말씀해 주세요! 다시 안내해 드리겠습니다.

* 위에 build -> build project 눌러도 됨
