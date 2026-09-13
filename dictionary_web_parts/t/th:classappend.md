
Thymeleaf에서 th:classappend는 HTML 요소의 기존 class 속성을 덮어쓰지 않고, 새로운 CSS 클래스를 동적으로 추가(Append)할 때 사용하는 속성입니다. [1] 
기본 th:class를 사용하면 기존에 선언되어 있던 HTML 클래스가 모두 사라지고 타임리프의 값으로 교체되지만, th:classappend는 기존 클래스를 그대로 유지하면서 뒤에 space(공백)와 함께 새로운 클래스를 덧붙여줍니다. [2, 3] 
------------------------------
## 💡 주요 사용법 및 예시
주로 서버에서 넘어온 데이터나 특정 조건에 따라 버튼의 활성화(active), 비활성화(disabled), 혹은 스타일 변경을 제어할 때 삼항 연산자와 함께 자주 사용됩니다. [2, 3] 
## 1. 조건에 따라 클래스 추가하기 (삼항 연산자)
특정 조건이 참(true)일 때만 클래스를 추가하고, 거짓일 때는 아무것도 추가하지 않는 방식입니다. [3] 

<!-- ${isActive}가 true이면 'active' 클래스가 추가됩니다 -->
<li class="nav-item" th:classappend="${isActive} ? 'active'">🏠 홈</li>
<!-- 삼항 연산자의 false 영역을 생략하거나 빈 문자열('')을 주면 됩니다 -->
<button class="btn btn-primary" th:classappend="${isError} ? 'bg-danger' : ''">전송</button>


* 
* 결과 (참일 때): <li class="nav-item active">🏠 홈</li>
* 결과 (거짓일 때): <li class="nav-item">🏠 홈</li> [1] 
* 

## 2. 값의 일치 여부에 따라 클래스 추가하기
현재 페이지의 메뉴 위치나 특정 값의 상태에 따라 스타일을 부여할 때 유용합니다. [4] 

<!-- 현재 선택된 메뉴(currentMenu)가 'board'와 같으면 active 클래스를 추가 -->
<a class="menu-link" th:classappend="${currentMenu == 'board'} ? 'active'">게시판</a>

## 3. 단순 텍스트/변수 값 추가하기
조건문 없이 서버에서 넘겨받은 클래스명 자체를 그대로 덧붙일 수도 있습니다. [4] 

<!-- ${themeColor}의 값이 'dark-mode'인 경우 -->
<div class="container" th:classappend="${themeColor}">내용</div>


* 
* 결과: <div class="container dark-mode">내용</div>
* 

------------------------------
## 🆚 th:class vs th:classappend 차이점 요약
두 속성의 가장 큰 차이는 기존 HTML 클래스의 보존 여부입니다. [1, 5] 

| 속성 | 기존 class 속성 보존 여부 | 주 용도 |
|---|---|---|
| th:class | ❌ 덮어씀 (기존 클래스 소멸) | 클래스 전체를 통째로 바꿀 때 |
| th:classappend | ⭕ 유지함 (기존 클래스 뒤에 추가) | 부트스트랩 등 기존 디자인 뼈대를 유지하고 상태 클래스만 넣을 때 |


* 
* 더 자세한 다큐먼트나 가이드라인이 필요하다면 Thymeleaf 공식 튜토리얼 문서를 참고해 보실 수 있습니다. [1] 
* 


[1] [https://cornarong.tistory.com](https://cornarong.tistory.com/44)
[2] [https://kylo8.tistory.com](https://kylo8.tistory.com/entry/Thymeleaf-Thymeleaf-th-%EB%AC%B8%EB%B2%95-%EC%A0%95%EB%A6%AC-thclassappend-thtext-thutext-thif-thunless-%EC%82%AC%EC%9A%A9%EB%B2%95)
[3] [https://somnote.tistory.com](https://somnote.tistory.com/182)
[4] [https://solbel.tistory.com](https://solbel.tistory.com/1410)
[5] [https://hajoung56.tistory.com](https://hajoung56.tistory.com/102)
