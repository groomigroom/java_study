상위 버전의 타임리프(Thymeleaf)와 Thymeleaf Layout Dialect를 사용하여 헤더(Header) 같은 공통 모듈을 만들고 다른 HTML 파일에 삽입하는 방법은 크개 두 가지가 있습니다.
질문하신 <html layout:decorate="~{layout}"> 방식은 레이아웃 패러다임(전체 틀을 짜두고 본문을 끼워 넣는 방식)이며, 레이아웃 파일 안에 헤더를 포함시키는 것이 일반적입니다. 또 다른 방식은 각 본문 파일에서 헤더만 쏙 가져오는 컴포넌트 패러다임(th:replace 또는 th:insert)입니다.
상황에 맞게 선택하실 수 있도록 두 가지 방법 모두 깔끔하게 정리해 드리겠습니다.
------------------------------
## 방법 1: 레이아웃 파일에 헤더 모듈을 포함하는 방식 (추천)
<html layout:decorate="..."> 구조를 사용할 때 가장 번거롭지 않은 정석적인 방법입니다. 공통 레이아웃 틀에 헤더를 미리 박아두고, 본문(컨텐츠) 파일은 오직 자기 내용만 집중하는 방식입니다.
## 1. 공통 헤더 파일 만들기 (fragments/header.html)
반복해서 사용할 헤더 HTML을 만들고 th:fragment 속성으로 이름을 지정합니다.

<!DOCTYPE html>
<html xmlns:th="http://thymeleaf.org"><!-- headerFragment라는 이름으로 모듈화 -->
<div th:fragment="headerFragment">
    <header style="background-color: #f8f9fa; padding: 20px; text-align: center;">
        <h1>서비스 로고 및 메인 네비게이션</h1>
        <nav>
            <a href="/">홈</a> | <a href="/board">게시판</a> | <a href="/profile">마이페이지</a>
        </nav>
    </header>
</div>
</html>

## 2. 공통 레이아웃 파일 만들기 (layout/defaultLayout.html)
전체 디자인 틀이 되는 파일입니다. 여기에 위에서 만든 헤더 프래그먼트를 불러옵니다(th:replace). 본문이 들어갈 자리는 layout:fragment="content"로 비워둡니다.

<!DOCTYPE html>
<html xmlns:th="http://thymeleaf.org"
      xmlns:layout="http://ultraq.net.nz">
<head>
    <meta charset="UTF-8">
    <title>My Web Application</title>
</head>
<body>

    <!-- 1번에서 만든 헤더 모듈을 여기에 삽입 (폴더경로/파일명 :: 프래그먼트명) -->
    <div th:replace="~{fragments/header :: headerFragment}"></div>

    <!-- 개별 본문 html 파일의 내용이 주입될 공간 -->
    <main layout:fragment="content">
        <!-- 본문 내용 들어옴 -->
    </main>

    <footer>
        <p>© 2026 회사 이름. All rights reserved.</p>
    </footer>

</body>
</html>

## 3. 개별 본문 HTML 파일 작성 (index.html 등)
이제 본문 파일에서는 질문하신 것처럼 레이아웃을 데코레이트하고, 본문 전용 내용만 작성하면 끝입니다. 헤더를 코드로 직접 넣지 않아도 레이아웃을 통해 자동으로 조립됩니다.

<!DOCTYPE html>
<html xmlns:th="http://thymeleaf.org"
      xmlns:layout="http://ultraq.net.nz"
      layout:decorate="~{layout/defaultLayout}"> <!-- 레이아웃 파일 지정 -->
<head>
    <title>메인 페이지</title>
</head>
<body>

    <!-- 레이아웃의 content 자리에 들어갈 내용 정의 -->
    <main layout:fragment="content">
        <h2>안녕하세요! 메인 페이지 방문을 환영합니다.</h2>
        <p>여기는 메인 화면의 독자적인 컨텐츠가 들어가는 곳입니다.</p>
    </main>

</body>
</html>

------------------------------
## 방법 2: 개별 파일에서 헤더만 직접 가져오는 방식 (th:replace)
만약 layout:decorate처럼 거대한 전체 틀을 쓰는 게 부담스럽고, 개별 HTML 파일마다 원하는 위치에 헤더 모듈만 콕 집어서 넣고 싶다면 타임리프 기본 기능인 th:replace나 th:insert를 사용하시면 됩니다.
## 1. 공통 헤더 파일 만들기 (fragments/header.html)
(방법 1의 1번 과정과 동일하게 th:fragment를 설정합니다.)
## 2. 필요한 HTML 파일에서 직접 호출하기 (board.html 등)
레이아웃 데코레이터를 쓰지 않고, 필요한 위치에 아래와 같이 한 줄만 적어주면 해당 위치에 헤더가 삽입됩니다.

<!DOCTYPE html>
<html xmlns:th="http://thymeleaf.org">
<head>
    <meta charset="UTF-8">
    <title>게시판</title>
</head>
<body>

    <!-- 레이아웃 데코레이트 없이, 헤더 모듈만 직접 가져오기 -->
    <div th:replace="~{fragments/header :: headerFragment}"></div>

    <div>
        <h2>게시판 목록</h2>
        <!-- 게시판 내용 -->
    </div>

</body>
</html>

------------------------------
## 💡 핵심 요약 가이드

* ~{폴더명/파일명 :: 프래그먼트명}: 타임리프가 다른 파일을 찾을 때 쓰는 표준 문법 문체(Expression)입니다. 프로젝트의 templates 폴더를 기준으로 경로를 작성하시면 됩니다.
* 프로젝트 설정 확인: layout:decorate 문법을 사용하려면 스프링 부트 프로젝트의 build.gradle에 thymeleaf-layout-dialect 의존성이 추가되어 있어야 정상 작동합니다.

현재 진행 중인 프로젝트 환경에 대해 조금 더 말씀해 주시면 더 맞춤형 코드를 안내해 드릴 수 있습니다.

* 현재 사용 중이신 스프링 부트(Spring Boot) 버전이 어떻게 되시나요? (버전에 따라 데코레이트 문법이 layout:decorator에서 layout:decorate로 변경되었습니다.)
* 프로젝트의 src/main/resources/templates/ 아래의 폴더 구조를 대략적으로 알려주실 수 있나요?


