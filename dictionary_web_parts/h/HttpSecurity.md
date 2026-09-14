import org.springframework.security.config.annotation.web.builders.HttpSecurity;

HttpSecurity는 스프링 시큐리티에서 웹 기반 보안 설정을 구성하는 핵심 빌더 클래스입니다. [1] 
## 1. HttpSecurity의 의미

* 보안 컨트롤러: 특정 HTTP 요청에 대해 어떤 보안 규칙(인증/인가)을 적용할지 세부적으로 설정합니다.
* 메서드 체이닝: URL별 접근 권한 설정, 로그인/로그아웃 페이지 지정, CSRF 보호 활성화 등을 직관적인 메서드로 연결하여 구성합니다.
* 체인 생성기: 앞서 질문하신 SecurityFilterChain을 최종적으로 빌드(http.build())하여 반환하는 역할을 합니다. [2] 

## 2. 속해 있는 스타터 의존성 (Dependency)
HttpSecurity 역시 SecurityFilterChain과 동일하게 Spring Security 라이브러리에 포함되어 있습니다.
## Gradle (build.gradle)

implementation 'org.springframework.boot:spring-boot-starter-security'

## Maven (pom.xml)

<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-security</artifactId>
</dependency>

------------------------------

##  .formLogin()

* .formLogin 메서드는 스프링 시큐리티의 로그인 설정을 담당하는 부분

* 예시
 .formLogin((formLogin) -> formLogin
                .loginPage("/user/login")
                .defaultSuccessUrl("/"))
        ;

* 로그인 페이지의 URL은 /user/login이고
* 로그인 성공 시에 이동할 페이지는 루트 URL(/)임을 의미한다


------------------------

##  .logout()

 .logout((logout) -> logout
                .logoutRequestMatcher(new AntPathRequestMatcher("/user/logout"))
                .logoutSuccessUrl("/")
                .invalidateHttpSession(true))


* .logout(...)역할: 스프링 시큐리티가 제공하는 로그아웃 관련 보안 설정을 시작하는 메서드입니다.
* .logoutRequestMatcher(PathPatternRequestMatcher.pathPattern(HttpMethod.GET, "/user/logout"))역할: 로그아웃을 실행할 특정 URL 주소와 HTTP 메서드를 지정합니다.
* PathPatternRequestMatcher.pathPattern(...): 스프링 부트에서 경로를 매칭할 때 쓰는 고성능 경로 매칭 방식입니다.
* HttpMethod.GET: 로그아웃 요청을 GET 방식으로 받겠다고 명시합니다.
* .logoutSuccessUrl("/")역할: 로그아웃이 성공적으로 완료된 후 사용자를 이동시킬(리다이렉트) URL 주소를 지정합니다.
