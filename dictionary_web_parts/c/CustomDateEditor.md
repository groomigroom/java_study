`org.springframework.beans.propertyeditors.CustomDateEditor`는 Spring Framework에서 날짜(Date) 형식의 데이터를 변환하고 처리하기 위해 제공하는 클래스야.

전자정부프레임워크(eGovFrame)에서도 Spring Framework를 기반으로 하기 때문에 사용할 수 있어.

쉽게 말하면, 웹 화면에서 전달받은 날짜 문자열을 Java의 `Date` 객체로 변환해 주는 역할이야.

## 1. 어떤 역할을 하는 클래스일까?

예를 들어 웹 화면에서 다음과 같은 날짜를 입력했다고 해보자.

```
2026-10-30
```

그런데 Java에서는 날짜를 단순한 문자열(String)이 아닌 `Date` 객체로 처리해야 하는 경우가 있어.

이때 `CustomDateEditor`를 사용하면 문자열을 날짜 객체로 변환할 수 있어.

데이터 변환 과정

웹 화면에서 입력한 날짜

`2026-10-30` (String)

CustomDateEditor

Java에서 사용하는 날짜 객체

`Date` 객체

## 2. 전자정부프레임워크에서 사용하는 이유

전자정부프레임워크의 Spring MVC에서는 사용자가 입력한 데이터를 Controller로 전달하는 과정에서 데이터 형식을 변환해야 해.

예를 들어 다음과 같은 경우야.

- 게시글 작성일
- 검색 시작일과 종료일
- 회원 생년월일
- 게시글 등록 날짜

이런 날짜 데이터를 자동으로 변환하도록 설정할 때 사용해.

## 3. 실제 코드 예시

전자정부프레임워크에서 다음과 같이 사용할 수 있어.

```
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;

@InitBinder
public void initBinder(WebDataBinder binder) {

    SimpleDateFormat dateFormat =
        new SimpleDateFormat("yyyy-MM-dd");

    binder.registerCustomEditor(
        Date.class,
        new CustomDateEditor(dateFormat, true)
    );
}
```

코드를 하나씩 살펴보자.

| 코드                     | 의미                   |
| ---------------------- | -------------------- |
| `SimpleDateFormat`     | 날짜 형식 지정             |
| `"yyyy-MM-dd"`         | 2026-10-30 형식        |
| `WebDataBinder`        | 웹 요청 데이터를 객체에 바인딩    |
| `registerCustomEditor` | 사용자 정의 데이터 변환기 등록    |
| `Date.class`           | Date 타입에 적용          |
| `CustomDateEditor`     | 문자열을 Date로 변환        |
| `true`                 | 빈 문자열을 허용하고 null로 처리 |

[image](https://www.google.com/s2/favicons?domain=https://docs.spring.io\&sz=32)

Home+1

## 4. 핵심 정리

## 시험이나 실무에서 기억할 내용

CustomDateEditor = 날짜 형식 변환 클래스

- String → Date 변환
- 날짜 형식 지정 가능
- 빈 문자열 처리 가능
- Spring MVC의 데이터 바인딩 과정에서 사용

참고로 이 클래스는 전자정부프레임워크만의 기능이 아니라 Spring Framework에서 제공하는 클래스야.

공식 문서: [Spring CustomDateEditor API](https://docs.spring.io/spring-framework/docs/6.2.15/javadoc-api/org/springframework/beans/propertyeditors/CustomDateEditor.html?utm_source=chatgpt.com)

혹시 전자정부프레임워크 프로젝트에서 이 import문을 발견한 거라면, 해당 코드가 있는 Controller나 `@InitBinder` 부분까지 보여줘. 실제 프로젝트에서 왜 사용하는지 한 줄씩 설명해 줄게.
