`org.springframework.web.bind.WebDataBinder`는 전자정부프레임워크(eGovFrame)에서 사용하는 Spring Framework의 데이터 바인딩 관련 클래스야.

쉽게 말하면, 웹 페이지에서 전달된 데이터를 Java 객체에 자동으로 연결하고 변환해 주는 역할을 해.

## 1. WebDataBinder란?

예를 들어 사용자가 회원가입 화면에서 다음 정보를 입력했다고 가정해 보자.

- 이름: 홍길동
- 나이: 25
- 이메일: test\@test.com

이 데이터가 서버로 전달되면 `WebDataBinder`가 요청 데이터를 Java 객체의 필드에 연결해 주는 역할을 해.

```
public class UserVO {
    private String name;
    private int age;
    private String email;

    // getter, setter
}
```

웹 요청으로 전달된 값이 다음과 같이 객체에 들어갈 수 있어.

```
UserVO user = new UserVO();

user.setName("홍길동");
user.setAge(25);
user.setEmail("test@test.com");
```

이런 데이터 연결과 변환 과정을 \*\*데이터 바인딩(Data Binding)\*\*이라고 해.

## 2. 전자정부프레임워크에서 사용하는 방법

전자정부프레임워크는 Spring 기반이기 때문에 Spring에서 제공하는 `WebDataBinder`를 그대로 사용할 수 있어.

대표적인 사용 예시는 다음과 같아.

```
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.stereotype.Controller;

@Controller
public class UserController {

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        // 데이터 바인딩 설정
    }
}
```

여기서 주요 기능은 다음과 같아.

| 기능       | 설명                           |
| -------- | ---------------------------- |
| 데이터 바인딩  | 요청 데이터를 Java 객체에 연결          |
| 타입 변환    | String을 Integer, Date 등으로 변환 |
| 허용 필드 설정 | 바인딩을 허용할 필드 지정               |
| 필드 제한    | 특정 필드의 자동 바인딩 방지             |
| 검증 연계    | 데이터 검증 과정과 연결                |

## 3. 실제로 왜 사용할까?

예를 들어 다음과 같은 코드가 있다고 해보자.

```
@InitBinder
public void initBinder(WebDataBinder binder) {
    binder.setDisallowedFields("admin");
}
```

이 코드는 사용자가 요청을 보낼 때 `admin`이라는 필드가 자동으로 바인딩되지 않도록 제한하는 역할을 해.

즉, 사용자가 임의로 관리자 권한 값을 전달하는 상황을 방지하는 데 활용할 수 있어.

## 핵심 정리

WebDataBinder = 웹 요청 데이터와 Java 객체를 연결하는 도구

- `org.springframework.web` : Spring 웹 관련 패키지
- `bind` : 데이터 바인딩 관련 패키지
- `WebDataBinder` : 실제 데이터 바인딩을 처리하는 클래스

참고로 `WebDataBinder`는 전자정부프레임워크만의 클래스가 아니라 Spring Framework에서 제공하는 클래스야.

전자정부프레임워크 프로젝트에서 이 import 문을 발견했다면 Spring MVC의 데이터 바인딩 기능을 사용하고 있다고 이해하면 돼.

## registerCustomEditor()
```
스프링 MVC의 org.springframework.web.bind.WebDataBinder 클래스에서 registerCustomEditor() 메서드는 클라이언트(웹 요청)로부터 
전달된 문자열(String) 등의 데이터를 컨트롤러가 원하는 특정 자바 객체 타입으로 변환하기 위한 PropertyEditor를 등록하는 역할을 합니다. 
[1] (https://sundaland.tistory.com/333), [2] (https://frontierdev.tistory.com/275), [3] (https://www.chattiboy.com/java-spring
-boot-webdatabinder-%EC%A0%95%EB%A6%AC-spring-mvc-%EB%8D%B0%EC%9D%B4%ED%84%B0-%EB%B0%94%EC%9D%B8%EB%94%A9-%ED%95%B5%EC%8B%AC/)

1. 주요 의미와 동작 방식

• 데이터 타입 변환 커스텀마이징: HTTP 요청 파라미터는 기본적으로 문자열(String) 형태로 전달됩니다. 이때 특정 타입(예: Date, UUID, 커스텀 객체 등)으로 
자동 변환이 필요할 때 사용합니다. [1] (https://sundaland.tistory.com/333)
• @InitBinder와 함께 사용: 주로 @InitBinder 어노테이션이 붙은 메서드 내부에서 호출되며, 특정 컨트롤러나 전체 컨트롤러에서 공통으로 바인딩 규칙을 
정의할 때 쓰입니다. [1] (https://frontierdev.tistory.com/275), [2] (https://sundaland.tistory.com/333)
• 내부 작동: 클라이언트가 보낸 파라미터가 컨트롤러 메서드의 인자로 바인딩될 때, 스프링은 등록된 PropertyEditor를 참조하여 문자열을 지정한 객체 
타입으로 파싱하여 주입합니다. [1] (https://sundaland.tistory.com/333), [2] (https://www.chattiboy.com/java-spring-boot-webdatabinder-%EC%A0%95%EB%A6%AC-spring-mvc-%EB%8D%B0%EC%9D%B4%ED%84%B0-%EB%B0%94%EC%9D%B8%EB%94%A9-%ED%95%B5%EC%8B%AC/)

2. 사용 예시 코드

java
@Controller
public class MyController {

    // 이 컨트롤러로 요청이 들어오기 전에 WebDataBinder를 초기화
    @InitBinder
    public void initBinder(WebDataBinder binder) {
        // 날짜 포맷 정의
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        dateFormat.setLenient(false);
        
        // java.util.Date 타입으로 변환할 때 CustomDateEditor를 사용하도록 등록
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
    }

    @GetMapping("/example")
    public String example(@RequestParam("dateParam") Date dateParam) {
        // "2026-10-30" 같은 문자열이 Date 객체로 자동 변환되어 주입됨
        return "result";
    }
}
코드를 사용할 때는 주의가 필요합니다.

3. 현대적 대안 (참고)

PropertyEditor는 상태를 유지(Stateful)하므로 멀티스레드 환경에서 안전하지 않은(Thread-safe하지 않은) 단점이 있습니다.
따라서 최근 스프링(Spring 3 이상)에서는 이를 개선한 Converter나 Formatter 인터페이스를 사용해 데이터 변환을 처리하는 방식을 권장합니다.
만약 현재 특정 타입 변환 에러를 해결하고 싶거나, Converter / Formatter로 리팩터링하는 방법이 궁금하다면 말씀해 주세요.
```
