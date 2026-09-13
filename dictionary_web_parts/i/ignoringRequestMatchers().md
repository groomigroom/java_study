자바 스프링 부트(스프링 시큐리티)에서 .ignoringRequestMatchers()는 "내가 지정한 특정 URL 경로들에 대해서는 보안 검사(또는 특정 보안 기능)를 적용하지 않고 건너뛰겠다(Ignore)"는 의미를 가집니다. [1, 2] 
이 메서드는 주로 CSRF 보안 설정을 부분적으로 해제할 때나, 특정 요청을 스프링 시큐리티의 감시망에서 완전히 제외할 때 사용됩니다.
어떤 맥락에서 쓰이느냐에 따라 크게 2가지 의미로 나뉩니다.
------------------------------
## 1. CSRF 보호에서 특정 주소만 제외할 때 (가장 많이 씀)
스프링 시큐리티는 기본적으로 POST, PUT, DELETE 같은 상태를 변경하는 요청에 대해 CSRF(사이트 간 요청 위조) 공격 방어 토큰을 요구합니다. [3, 4] 
하지만 외부 시스템과 연동하는 API(예: 결제 완료 콜백 웹훅)나 타사 서비스가 우리 서버로 데이터 요청을 보낼 때는 CSRF 토큰을 함께 보낼 수 없습니다. 이때 "이 주소로 들어오는 요청은 안전하니까 CSRF 검사를 하지 마라"고 설정할 때 사용합니다. [1, 5] 

@Beanpublic SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http
        .csrf(csrf -> csrf
            // /api/webhook 이라는 주소로 들어오는 요청은 CSRF 검사를 생략(Ignore)한다!
            .ignoringRequestMatchers("/api/webhook/**") 
        )
        .authorizeHttpRequests(auth -> auth
            .anyRequest().authenticated()
        );
    return http.build();
}

## 2. 시큐리티 필터 체인 자체를 완전히 건너뛸 때 (WebSecurity)
이전 질문에서 다루었던 AntPathRequestMatcher 등과 함께 사용하여, 정적 파일(CSS, JS, 이미지 등)을 시큐리티의 영향권에서 완전히 제외시키고 싶을 때 사용하기도 합니다. (다만 이 방식은 최근 스프링 시큐리티 버전에서 권장 방식과 문법이 조금씩 다릅니다.) [6, 7] 

@Beanpublic WebSecurityCustomizer webSecurityCustomizer() {
    // 이미지나 CSS 파일 같은 정적 리소스는 시큐리티 필터를 아예 타지 않도록 무시(Ignore)한다!
    return (web) -> web.ignoring().requestMatchers("/css/**", "/js/**", "/images/**");
}

이렇게 하면 로그인 여부와 관계없이 누구나 아무런 보안 처리 비용 없이 빠르게 정적 자원에 접근할 수 있게 됩니다. [6] 
------------------------------
## 💡 앞서 질문하신 내용들과의 연결고리

   1. http.authorizeHttpRequests(...) : "이 주소는 로그인이 필요해/필요 없어"라는 접근 권한을 설정할 때 씀.
   2. AntPathRequestMatcher : "어떤 주소"인지를 스프링 시큐리티가 알아먹을 수 있게 와일드카드(/**) 등으로 주소 형식을 지정할 때 씀.
   3. .ignoringRequestMatchers(...) : 주로 CSRF 설정 등에서 "이 주소는 귀찮은 보안 절차를 건너뛰고(Ignore) 바로 통과시켜줘"라고 할 때 씀. [1, 2, 4] 

혹시 현재 작성 중이신 SecurityConfig 클래스에서 특정 외부 API 요청이 막히거나 403 Forbidden 에러가 발생해서 이 메서드를 찾으시게 된 건가요? 에러 상황이나 구현하려는 API의 특징을 말씀해 주시면 상황에 맞는 정확한 설정 방법을 가이드해 드릴 수 있습니다.

[1] [https://softwaresaramdle.tistory.com](https://softwaresaramdle.tistory.com/41)
[2] [https://velog.io](https://velog.io/@kgb/Spring-Security-%EC%82%AC%EC%9A%A9-%EC%8B%9C-CORs-CSRF)
[3] [https://lifewithcoding.tistory.com](https://lifewithcoding.tistory.com/268)
[4] [https://eesko.tistory.com](https://eesko.tistory.com/333)
[5] [https://dean83.tistory.com](https://dean83.tistory.com/389)
[6] [https://stackoverflow.com](https://stackoverflow.com/questions/76097411/how-can-i-configure-spring-security-6-to-ignore-the-static-resources-folder)
[7] [https://iieunji023.tistory.com](https://iieunji023.tistory.com/24)
