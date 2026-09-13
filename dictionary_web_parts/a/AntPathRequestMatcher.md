import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
------


AntPathRequestMatcher는 자바 스프링 시큐리티에서 "특정 URL 경로(Path)가 내가 지정한 조건과 일치하는지 확인하는 판별기"를 의미합니다.
여기서 Ant는 과거 자바 빌드 도구였던 Apache Ant에서 사용하던 경로 매칭 스타일(와일드카드 기법)을 그대로 가져왔다는 뜻입니다.
구체적인 의미와 사용 목적을 알기 쉽게 정리해 드릴게요.
------------------------------
## 1. 주요 역할: URL 주소 필터링
웹 애플리케이션을 만들다 보면 "이 주소는 누구나 들어와도 되고, 저 주소는 로그인한 사람만 들어와야 해"라는 규칙을 정해야 합니다. AntPathRequestMatcher는 바로 그 '주소(경로)를 지정하는 기준'이 됩니다.

// 예시: /css 로 시작하는 모든 정적 파일 경로는 보안 검사를 하지 않겠다!new AntPathRequestMatcher("/css/**")

## 2. Ant 스타일 경로 문법 (핵심 와일드카드)
주소를 지정할 때 글자 하나하나를 다 적지 않고, 아래와 같은 특수문자(와일드카드)를 사용해 범위를 지정할 수 있는 것이 큰 특징입니다.

* ? : 글자 1개와 매칭 (예: /page? → /pageA, /pageB 매칭)
* * : 경로 안에서 0개 이상의 글자와 매칭 (단, 슬래시/는 넘어가지 못함)
* 예: /user/* → /user/profile, /user/settings 매칭 가능 (/user/profile/edit은 불가능)
* ** : 경로의 하위 디렉터리 전체와 매칭 (슬래시/를 포함하여 깊이에 상관없이 모두 매칭)
* 예: /admin/** → /admin, /admin/users, /admin/posts/delete 모두 매칭

## 3. 실제 코드에서는 어떻게 쓰이나요?
스프링 부트 3.x 버전 이후(Spring Security 6.x)부터는 코드가 간결해져서 AntPathRequestMatcher를 직접 뉴(new)해서 쓰는 일이 줄었지만, 내부적으로는 여전히 이 방식을 기반으로 작동합니다.
## 예시 1: 정적 자원(CSS, JS, 이미지)이나 특정 페이지 로그인 제외 설정

http.authorizeHttpRequests((auth) -> auth
    // 아래 경로들은 로그인 없이(permitAll) 접근할 수 있도록 지정
    .requestMatchers(new AntPathRequestMatcher("/auth/**")).permitAll()
    .requestMatchers(new AntPathRequestMatcher("/css/**")).permitAll()
    .requestMatchers(new AntPathRequestMatcher("/js/**")).permitAll()
    .anyRequest().authenticated()
);

## 예시 2: HTTP 메서드(GET, POST 등)까지 결합하여 정밀하게 제어할 때
단순히 주소뿐만 아니라, "POST 방식으로 들어오는 /api/board 주소만 제한하겠다"처럼 특정 HTTP 메서드와 조합할 때 아주 유용합니다.

// /api/board 경로의 POST 요청(글쓰기 등)만 확인하는 매처new AntPathRequestMatcher("/api/board", "POST")

------------------------------
## 💡 요약하자면
AntPathRequestMatcher는 스프링 시큐리티에게 "내가 말한 주소 형식(/** 같은 와일드카드 포함)과 일치하는 요청을 찾아내라"고 명령할 때 사용하는 도구입니다.
현재 작성 중이신 스프링 시큐리티 설정 코드에서 특정 주소에 접근 제한(예: 관리자 페이지, 회원 전용 페이지)을 걸거나 제외하고 싶으신가요? 어떤 주소에 어떤 권한을 주고 싶으신지 말씀해 주시면 알맞은 매칭 코드를 바로 작성해 드리겠습니다.


