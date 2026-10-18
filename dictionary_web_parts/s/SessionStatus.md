`org.springframework.web.bind.support.SessionStatus`는 스프링 부트에서 현재 컨트롤러가 관리하는 세션 속성(Session Attributes)의 사용이 끝났음을 표시하는 인터페이스입니다.

주로 `@SessionAttributes`와 함께 사용하며, 폼 입력이나 여러 단계로 진행되는 작업에서 세션 데이터를 더 이상 유지할 필요가 없을 때 사용합니다.

## 1. 기본 사용법

java
```
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.bind.annotation.*;

@Controller
@SessionAttributes("userForm")
public class UserController {

    @PostMapping("/user/submit")
    public String submit(
            @ModelAttribute("userForm") UserForm userForm,
            SessionStatus status) {

        // 사용자 정보 처리
        System.out.println(userForm.getName());

        // 세션에 저장된 @SessionAttributes 데이터 사용 종료
        status.setComplete();

        return "redirect:/";
    }
}
```

여기서 핵심은 다음 코드입니다.

java
```
status.setComplete();
```

이 메서드를 호출하면 현재 컨트롤러가 `@SessionAttributes`로 관리하는 세션 데이터의 사용이 완료되었다고 스프링에 알립니다.

## 2. 주요 메서드

| 메서드 | 의미 |
| --- | --- |
| `setComplete()` | 현재 컨트롤러의 세션 처리 완료를 표시하고, 관리 중인 세션 속성을 정리하도록 요청 |
| `isComplete()` | 세션 처리가 완료되었는지 확인 |

## 3. `@SessionAttributes`와 어떤 관계일까?

예를 들어 회원가입 폼을 여러 페이지에 걸쳐 작성한다고 가정해 보겠습니다.

1단계: 회원 정보 입력

폼 데이터를 세션에 보관

2단계: 추가 정보 입력

이전 단계의 데이터 유지

3단계: 가입 완료

`status.setComplete()` 호출 → 해당 컨트롤러가 관리하던 세션 데이터 정리

이때 `@SessionAttributes("userForm")`는 `userForm` 객체를 세션에서 관리하도록 하고, `SessionStatus`는 해당 작업이 끝났을 때 세션 처리를 완료하도록 합니다.

## 4. 주의할 점: 모든 세션을 삭제하는 것은 아니다

이 부분이 중요합니다.

java
```
status.setComplete();
```

이 코드는 현재 컨트롤러가 `@SessionAttributes`로 관리하는 속성을 정리하는 것이지, HTTP 세션에 저장된 모든 데이터를 삭제하는 것은 아닙니다.

예를 들어 다음과 같이 별도로 저장한 속성까지 모두 삭제하는 것은 아닙니다.

java
```
session.setAttribute("loginUser", user);
```

HTTP 세션 자체를 무효화하려면 별도로 다음과 같은 처리가 필요합니다.

java
```
session.invalidate();
```

두 코드의 차이는 다음과 같습니다.

| 구분 | `status.setComplete()` | `session.invalidate()` |
| --- | --- | --- |
| 목적 | 컨트롤러의 세션 처리 완료 | HTTP 세션 전체 무효화 |
| `@SessionAttributes` 데이터 | 관리 대상 데이터 정리 | 세션 전체가 무효화되므로 제거 |
| 로그인 정보 등 다른 세션 데이터 | 일반적으로 유지 | 함께 제거 |

## 정리

- `SessionStatus`는 세션 데이터의 사용 완료를 관리하는 스프링 인터페이스입니다.
- 주로 `@SessionAttributes`와 함께 사용합니다.
- `setComplete()`는 해당 컨트롤러가 관리하는 세션 속성의 정리를 요청합니다.
- 로그아웃처럼 세션 전체를 없애려면 `HttpSession.invalidate()`를 사용합니다.

참고로 `SessionStatus`는 스프링 MVC의 기능이므로, 스프링 부트에서도 별도 구현 없이 컨트롤러 메서드의 매개변수로 주입받아 사용할 수 있습니다.
