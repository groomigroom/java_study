import org.springframework.security.access.prepost.PreAuthorize;

스프링 부트(Spring Boot)에서 @PreAuthorize("isAuthenticated()")는 "이 메서드(또는 컨트롤러)는 로그인한(인증된) 사용자만 호출할 수 있게 하라"는 의미입니다. 스프링 시큐리티(Spring Security)가 제공하는 권한 제어 기능 중 하나입니다. [1, 2] 
핵심 내용을 요약하면 다음과 같습니다.
## 1. 주요 기능

* 
* 로그인 여부 확인: 현재 요청을 보낸 사용자가 로그인을 완료한 상태인지 검사합니다. [3] 
* 접근 제한:
* 로그인한 사용자: 정상적으로 메서드가 실행되거나 해당 페이지로 이동합니다.
   * 비로그인(익명) 사용자: 메서드 실행을 차단하고, 설정에 따라 로그인 페이지로 강제 리다이렉트시키거나 403 Forbidden 에러를 반환합니다. [1, 2] 
* 

## 2. 왜 사용하나요?
주로 로그인 정보(principal 객체)를 사용하는 기능(글쓰기, 마이페이지, 회원 정보 수정 등)에서 비로그인 사용자가 접근해 에러(NullPointerException 등)가 발생하는 것을 원천 차단하기 위해 사용합니다. [1, 2] 
## 3. 코드 예시

@Controller
@RequestMapping("/question")public class QuestionController {

    @PreAuthorize("isAuthenticated()") // 🔒 로그인 안 하면 이 메서드 자체를 실행하지 못함
    @GetMapping("/create")
    public String questionCreate() {
        return "question_form";
    }
}

## 4. 필수 설정 (주의점)
이 어노테이션을 작동시키려면 스프링 시큐리티 설정 클래스(SecurityConfig) 상단에 @EnableMethodSecurity 어노테이션이 추가되어 있어야 합니다. (구버전 버전에서는 @EnableGlobalMethodSecurity(prePostEnabled = true) 사용). [1] 
------------------------------
추가로 특정 등급(예: 관리자)만 접근하게 만들고 싶다면 @PreAuthorize("hasRole('ROLE_ADMIN')")과 같은 방식으로 확장하여 사용할 수 있습니다. [1] 
혹시 현재 프로젝트에 이 어노테이션을 적용하는 과정에서 동작하지 않거나 에러가 발생하는 부분이 있으신가요? 상황을 말씀해 주시면 설정을 도와드릴게요!

[1] [https://itsjhstory.tistory.com](https://itsjhstory.tistory.com/51)
[2] [https://wikidocs.net](https://wikidocs.net/162330)
[3] [https://rebugs.tistory.com](https://rebugs.tistory.com/898)
