    implementation 'org.springframework.boot:spring-boot-starter-security'
    implementation 'org.thymeleaf.extras:thymeleaf-extras-springsecurity7' -> 4.0 이상


* spring security는 기본적으로 인증되지 않은 사용자가 웹 서비스를 사용할 수 없게끔 만든다.

```
setContinueChainBeforeSuccessfulAuthentication은 스프링 시큐리티(Spring Security)의 AbstractAuthenticationProcessingFilter 
(및 이를 상속하는 UsernamePasswordAuthenticationFilter 등)에서 인증 성공 후 필터 체인의 남은 단계를 계속 진행할지 여부를 설정하는 메서드입니다. 
[1] (https://www.egovframe.go.kr/wiki/doku.php?id=egovframework:rte3:fdl:server_security:upgrade), 
[2] (https://stackoverflow.com/questions/73420642/spring-security-redirecting-after-successful-authentication)

주요 의미와 동작 방식

• true로 설정할 때:
인증이 성공해도 필터 체인을 즉시 종료하지 않고, 다음 필터(chain.doFilter(request, response))로 요청을 계속 넘깁니다.
• false로 설정할 때 (기본값):
인증이 성공하면 해당 필터에서 처리를 끝내고, 보통 설정된 성공 핸들러(AuthenticationSuccessHandler)에 의해 리다이렉트되거나 
응답을 반환하면서 필터 체인이 중단됩니다.
[1] (https://stackoverflow.com/questions/73420642/spring-security-redirecting-after-successful-authentication),
[2] (https://www.egovframe.go.kr/wiki/doku.php?id=egovframework:rte3:fdl:server_security:upgrade)

전자정부 표준프레임워크에서의 사용 맥락

전자정부 표준프레임워크의 서버 시큐리티(Server Security) 가이드 및 업그레이드 문서에서는 커스텀 인증 필터 처리나 기존 로그인 연동 시 이 값을 제어합니다. 
[1] (https://www.egovframe.go.kr/wiki/doku.php?id=egovframework:rte3:fdl:server_security:upgrade)
• 왜 사용할까?
일반적인 웹 폼 로그인(formLogin)이 아니라, 커스텀 로직이나 특정 SSO/외부 연동 구조에서 로그인 인증 처리를 거친 후에도 필터 체인의 후속 작업을
계속해서 타야 할 때 true로 설정해 체인을 유지합니다. [1] (https://www.egovframe.go.kr/wiki/doku.php?id=egovframework:rte3:fdl:server_security:upgrade),
[2] (https://stackoverflow.com/questions/73420642/spring-security-redirecting-after-successful-authentication)
• 반면, 일반적인 로그인 필터처럼 인증 후 즉시 페이지를 이동시키거나 응답을 끝맺어야 하는 표준적인 환경에서는 false로 두어 불필요한 후속 필터 실행을 방지합니다.
[1] (https://www.egovframe.go.kr/wiki/doku.php?id=egovframework:rte3:fdl:server_security:upgrade)
이 설정과 관련하여 현재 구축 중인 로그인 방식(일반 폼 로그인 vs 커스텀 필터/SSO 등)에 대해 알려주시면 더 구체적인 적용 코드를 안내해 드릴 수 있습니다.
```

```
전자정부프레임워크의 기반인 Spring Security에서 doFilter는 클라이언트의 요청(Request)이
서블릿이나 다음 보안 필터로 넘어가기 전후에 보안 검사 및 처리 로직을 실행하고,
다음 체인으로 요청을 전달하는 핵심 메서드입니다.
[1] (https://curiousjinan.tistory.com/entry/spring-filterchain-dofilter), [2] (https://seongsu.me/post/20)

1. doFilter의 기본 개념과 역할

• 요청 가로채기: 클라이언트가 서버로 보낸 HTTP 요청을 서블릿(DispatcherServlet 등)이나
비즈니스 로직에 도달하기 전에 먼저 가로챕니다. [1] (https://seongsu.me/post/20)
• 보안 처리 실행: 로그인 인증(Authentication), 권한 부여(Authorization), CSRF 검증,
세션 체크 등 Spring Security의 다양한 보안 필터 로직을 이 메서드 내부에서 수행합니다.
• 다음 필터로 전달 (chain.doFilter()):
	• 현재 필터의 작업이 끝나면 chain.doFilter(request, response)를 호출하여 다음 보안 필터
또는 최종 목적지인 Controller로 요청을 넘겨줍니다.
	• 만약 이 메서드를 호출하지 않으면 요청이 멈추어(차단되어) 다음 단계로 진행되지 못하고 응답이 반환됩니다.
[1] (https://curiousjinan.tistory.com/entry/spring-filterchain-dofilter)

2. 전자정부프레임워크(eGovFrame)에서의 의미

• 서블릿 기반 보안 적용: 전자정부프레임워크의 Server Security Service는 표준 Spring Security를 활용하며,
DB 기반의 JDBC 인증 및 권한 제어를 필터 체인 형태로 처리합니다.
[1] (https://www.egovframe.go.kr/wiki/doku.php?id=egovframework:rte4.0:fdl:server_security),
[2] (https://www.egovframe.go.kr/wiki/doku.php?id=egovframework:rte4.2:fdl:server_security:architecture)
• 요청 전/후 공통 보안 로직: 사용자가 특정 URL에 접근할 때, doFilter를 통해 "이 사용자가 로그인했는가?",
"이 자원에 접근할 권한이 있는가?"를 실시간으로 검증하는 관문 역할을 합니다. [1] (https://seongsu.me/post/20)
혹시 특정 커스텀 필터를 구현하는 중이신가요, 아니면 전자정부프레임워크의 보안 설정(context-security.xml 등)
에 대해 구체적인 코드 분석이 필요하신가요? 편하게 말씀해 주세요.
```
