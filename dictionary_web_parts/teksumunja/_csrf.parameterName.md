1. _csrf.parameterName 의 의미

* 역할: 서버(스프링 시큐리티)가 CSRF 토큰을 식별하기 위해 기대하는 HTML input 태그의 name 속성값입니다.
* 실제 렌더링 결과: 기본값은 대부분 _csrf 입니다.쉽게 말해: "스프링 시큐리티야, 내가 지금 보낼 CSRF 토큰의 이름표(Key)는 _csrf 야"라고 지정하는 것입니다.
