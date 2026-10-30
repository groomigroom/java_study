`java.util.Locale`은 스프링 부트(Spring Boot)에서 언어와 국가 등의 지역 설정 정보를 표현하는 자바 클래스입니다.

쉽게 말하면, 웹사이트에 접속한 사용자가 한국어를 사용하는지, 영어를 사용하는지에 따라 화면에 표시되는 언어를 바꾸거나 날짜와 숫자를 해당 지역의 방식으로 표현할 때 사용합니다.

### 1\. `java.util.Locale`의 의미

```java
import java.util.Locale;
```

* `java.util` : 자바에서 다양한 유틸리티 기능을 제공하는 패키지

* `Locale` : 언어, 국가, 지역별 표현 방식 등의 정보를 나타내는 클래스

예를 들어 다음과 같은 지역 설정을 표현할 수 있습니다.

![Flag South Korea emoji - Free Download PNG & SVG | Streamline](https://images.openai.com/static-rsc-4/Y9NQH-TpKbUelNBCE74LS1fliMo3ydqcI4nMkV2vbVnIHpf7DmX13h1ve61O8KcRb3OSjnvOvRNRxfHaTzgMbyUzPhEZMg7d7uMTCmx60NlbdxYg7zorwlB1SFxu3qVr0NtzcxMejw6G3-zOKl2iRJuPWmNoVG9pe9q7Ht4HgDCm0Cm_05_OCVE9tKFiXyMU?purpose=fullsize)

한국어 설정

`Locale.KOREA` 또는 `Locale.KOREAN`

언어: 한국어 / 국가: 대한민국

![🇺🇸 Flag: United States on Toss Face (토스페이스) 1.6](https://images.openai.com/static-rsc-4/nnrFzGzXTUV6-eFQgdnBXL2QSrHtl8kK7GGv6VzOWCGL8uAWM6e6gSrFMoVgU7YliFBkgV-oUMqw2mES-fyxm-NIdPJALqffOLIoMQQHpAzLqJS9vIhZg5pm6EiGEY2LXKH-oAccs7mLKOhsCK9ikLfoy7Rm4qyhrFp9wjw1K5_22PY786pkxQKL6Sa6m-JX?purpose=fullsize)

미국 영어 설정

`Locale.US`

언어: 영어 / 국가: 미국

![🇯🇵 Flag: Japan Emoji: Meaning & Usage](https://images.openai.com/static-rsc-4/IvfXmR1Aqh2Y-oxdj923W9uG_Tzcb1Yw_raxUcnpmJ0rwdcxyqVquWQxddnMpUnwpChC9udzQY2plfDQFaB-uVUTT1iR4nIcdPsdgzqM6tUkibRZe2cdVUrmg72E2BM1nRiJiAkDLpBmk1qbUPwk9KezF6BuhzTwwnD2LqupKof1_BbouFCp2kc7Mdnie4JH?purpose=fullsize)

일본어 설정

`Locale.JAPAN` 또는 `Locale.JAPANESE`

언어: 일본어 / 국가: 일본

여기서 주의할 점은 `Locale.KOREA`는 한국이라는 국가 설정이고, `Locale.KOREAN`은 한국어라는 언어 설정이라는 것입니다.

### 2\. 자바 코드로 사용해 보기

```java
import java.util.Locale;

public class LocaleExample {
    public static void main(String[] args) {

        Locale korea = Locale.KOREA;
        Locale usa = Locale.US;

        System.out.println(korea);
        System.out.println(usa);
    }
}
```

실행 결과는 다음과 같습니다.

```text
ko_KR
en_US
```

* `ko_KR` : 한국어(`ko`) + 대한민국(`KR`)

* `en_US` : 영어(`en`) + 미국(`US`)

즉, `Locale` 객체에 어떤 언어와 국가를 설정했는지 확인할 수 있습니다.

### 3\. 스프링 부트에서 사용하는 예제

스프링 부트로 다국어 웹사이트를 만든다고 가정하겠습니다.

```java
import java.util.Locale;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LanguageController {

    @GetMapping("/language")
    public String language() {

        Locale locale = Locale.KOREA;

        return locale.getDisplayLanguage();
    }
}
```

브라우저에서 `/language` 주소로 요청하면 설정된 언어의 표시 이름인 `한국어`를 반환합니다.

주요 메서드는 다음과 같습니다.

| 메서드                  | 의미                 |
| -------------------- | ------------------ |
| Locale.KOREA         | 대한민국 지역 설정         |
| Locale.US            | 미국 지역 설정           |
| getLanguage()        | 언어 코드 반환           |
| getCountry()         | 국가 코드 반환           |
| getDisplayLanguage() | 사람이 읽기 쉬운 언어 이름 반환 |
| getDisplayCountry()  | 사람이 읽기 쉬운 국가 이름 반환 |

예를 들어 다음과 같이 사용할 수 있습니다.

```java
Locale locale = Locale.KOREA;

System.out.println(locale.getLanguage());        // ko
System.out.println(locale.getCountry());         // KR
System.out.println(locale.getDisplayLanguage()); // 한국어
System.out.println(locale.getDisplayCountry());  // 대한민국
```

### 4\. 스프링 부트의 다국어 기능과 연결하기

실제 프로젝트에서는 `Locale`을 스프링의 국제화(i18n) 기능과 함께 사용하는 경우가 많습니다.

예를 들어 게시판에 다음과 같은 메시지가 있다고 가정하겠습니다.

한국어 화면

로그인에 성공했습니다.

영어 화면

Login successful.

스프링 부트에서는 `messages_ko.properties`, `messages_en.properties` 같은 메시지 파일과 `MessageSource`를 이용해 언어별 문구를 관리할 수 있습니다. 이때 `Locale`은 어느 언어의 메시지를 선택할 것인지 알려주는 기준으로 사용됩니다.

다만 위의 간단한 예제에서 `Locale.KOREA`를 직접 지정한 것은 고정 설정입니다. 실제 웹사이트에서는 요청 헤더나 사용자의 언어 선택에 따라 스프링이 `Locale`을 결정하도록 구성할 수 있습니다.

핵심 정리

* `java.util.Locale`은 언어와 국가 등 지역 설정을 나타내는 클래스입니다.

* 스프링 부트에서는 다국어 메시지, 날짜·숫자의 지역별 표현 등에 활용할 수 있습니다.

* `Locale` 자체가 번역을 수행하는 것은 아니며, 어떤 언어와 지역을 사용할지 알려주는 역할을 합니다.

쉽게 비유하면, `Locale`은 웹사이트에 “이 사용자의 화면을 어떤 언어와 지역 기준으로 보여줄까?” 를 알려주는 설정 정보입니다.


`Locale.getDefault()`는 현재 프로그램이 실행되는 컴퓨터(운영체제)에 설정된 기본 지역 및 언어 정보를 가져오는 메서드야.

쉽게 말하면, 컴퓨터가 한국어 환경인지 영어 환경인지 확인하는 기능이야.

### 1. 기본 사용법

```
import java.util.Locale;

public class Main {
    public static void main(String[] args) {
        Locale locale = Locale.getDefault();

        System.out.println(locale);
    }
}
```

한국어 환경에서 실행하면 다음과 비슷한 결과가 나올 수 있어.

```
ko_KR
```

- `ko` : Korean (한국어)
- `KR` : Korea (대한민국)

### 2. 주요 메서드

| 메서드                    | 의미            | 예시    |
| ---------------------- | ------------- | ----- |
| `Locale.getDefault()`  | 기본 지역 정보 가져오기 | ko_KR |
| `getLanguage()`        | 언어 코드 가져오기    | ko    |
| `getCountry()`         | 국가 코드 가져오기    | KR    |
| `getDisplayLanguage()` | 언어 이름 가져오기    | 한국어   |
| `getDisplayCountry()`  | 국가 이름 가져오기    | 대한민국  |

### 3. 실제 활용 예시

```
Locale locale = Locale.getDefault();

if (locale.getLanguage().equals("ko")) {
    System.out.println("안녕하세요");
} else {
    System.out.println("Hello");
}
```

한국어 환경이면 `안녕하세요`, 그 외에는 `Hello`가 출력돼.

### 시험 핵심 정리

암기 포인트

- `Locale` : 지역 및 언어 설정 정보를 관리하는 클래스
- `Locale.getDefault()` : 시스템의 기본 Locale 정보를 반환
- `getLanguage()` : 언어 코드 반환
- `getCountry()` : 국가 코드 반환

한 줄 암기: `Locale.getDefault()` = 현재 시스템의 기본 언어·국가 설정 가져오기!
