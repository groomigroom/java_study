    implementation 'org.springframework.boot:spring-boot-starter-security'
    implementation 'org.thymeleaf.extras:thymeleaf-extras-springsecurity7' -> 4.0 이상

* spring security는 기본적으로 인증되지 않은 사용자가 웹 서비스를 사용할 수 없게끔 만든다.


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
