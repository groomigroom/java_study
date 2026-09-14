import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;

스프링 부트에서 @EnableMethodSecurity(prePostEnabled = true)는 "메서드 수준에서 권한 검사를 할 수 있도록 스프링 시큐리티 기능을 활성화하겠다"는 의미입니다.
방금 질문하신 @PreAuthorize 같은 어노테이션들이 실제로 동작하도록 스위치를 켜주는 역할을 합니다.
## 1. 주요 역할과 의미

* 메서드 보안 활성화: URL 단위(예: /admin/**)로 권한을 검사하는 방식 외에, Java 코드의 메서드 단위로 권한을 세밀하게 제어할 수 있게 만듭니다.
* prePostEnabled = true: 사전(@PreAuthorize) 및 사후(@PostAuthorize) 권한 검사 어노테이션을 감지하고 동작시키라는 옵션입니다.

## 2. 버전별 차이점 (중요 변화)
스프링 부트 버전(정확히는 스프링 시큐리티 버전)에 따라 작성 방식에 차이가 있습니다.

* 스프링 부트 3.x 이상 (Security 6.x):
* @EnableMethodSecurity만 적어도 prePostEnabled = true가 기본값으로 적용됩니다. 따라서 괄호 안의 내용을 생략하고 @EnableMethodSecurity만 적어도 무방합니다.
* 스프링 부트 2.x 이하 (Security 5.x):
* 과거에는 @EnableGlobalMethodSecurity(prePostEnabled = true)라는 긴 이름의 어노테이션을 사용했습니다. 이 방식은 현재 deprecated(삭제 예정) 되었으므로 최신 버전에서는 사용하지 않는 것이 좋습니다.

## 3. 코드 적용 위치
보통 스프링 시큐리티 설정을 담당하는 SecurityConfig 클래스 상단에 아래와 같이 붙여서 사용합니다.

@Configuration
@EnableWebSecurity
@EnableMethodSecurity // 👈 이 스위치를 켜야 @PreAuthorize가 작동합니다!public class SecurityConfig {
    // 시큐리티 필터 체인 설정 등...
}

------------------------------
현재 연동하려는 프로젝트의 스프링 부트 버전(2.x인지 3.x인지)에 따라 어노테이션 형태를 맞춰주는 것이 좋습니다. 혹시 해당 설정을 넣었음에도 @PreAuthorize가 작동하지 않는 오류를 겪고 계신가요?


