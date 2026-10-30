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
