`org.apache.commons.lang3.StringUtils`는 스프링 부트(Spring Boot)에서 문자열을 검사하고, 비교하고, 자르고, 변환하는 작업을 편리하게 해주는 유틸리티 클래스입니다.

예를 들어 회원가입 기능에서 아이디가 비어 있는지 확인하거나, 사용자가 입력한 문자열 앞뒤의 공백을 제거할 때 활용할 수 있습니다.

### 1\. `StringUtils`의 의미

```java
import org.apache.commons.lang3.StringUtils;
```

각 부분을 살펴보겠습니다.

* `org.apache.commons.lang3` : Apache Commons Lang 3 라이브러리의 패키지입니다.

* `StringUtils` : 문자열을 다루는 다양한 메서드를 제공하는 클래스입니다.

쉽게 말하면, 자바의 기본 `String` 클래스에서 자주 사용하는 문자열 처리 작업을 더 편리하게 수행하도록 도와주는 도구입니다.

### 2\. 스프링 부트에서 사용하는 예제

회원가입 화면에서 사용자가 아이디를 입력한다고 가정하겠습니다.

```java
import org.apache.commons.lang3.StringUtils;

public class StringExample {

    public static void main(String[] args) {

        String userid = "  groomi  ";

        System.out.println(
            StringUtils.isEmpty(userid)
        );

        System.out.println(
            StringUtils.isBlank(userid)
        );

        System.out.println(
            StringUtils.trim(userid)
        );
    }
}
```

실행 결과는 다음과 같습니다.

```text
false
false
groomi
```

왜 이런 결과가 나오는지 알아보겠습니다.

* `isEmpty(userid)` : 문자열의 길이가 0인지 검사합니다. 공백이 있으므로 `false`입니다.

* `isBlank(userid)` : 문자열이 비어 있거나 공백 문자만 있는지 검사합니다. `groomi`라는 문자가 있으므로 `false`입니다.

* `trim(userid)` : 문자열 앞뒤의 공백을 제거합니다.

### 3\. 자주 사용하는 메서드 7가지

아래는 스프링 부트 프로젝트에서 알아두면 유용한 메서드입니다.

### 직접 실행해 보는 문자열 처리

문자열을 입력하고 메서드를 선택하면 결과를 확인할 수 있습니다.

입력 문자열

메서드 선택

`isEmpty()` — 비어 있는 문자열인지

`isBlank()` — 비어 있거나 공백만 있는지

`trim()` — 앞뒤 공백 제거

`upperCase()` — 대문자로 변환

`lowerCase()` — 소문자로 변환

`contains()` — 특정 문자열 포함 여부

`defaultIfBlank()` — 공백이면 기본값 반환

실행 결과

### hello

Java의 해당 메서드와 같은 기본 동작을 보여주는 예제입니다.

실제 코드에서는 다음과 같이 사용합니다.

| 메서드                             | 기능              | 예시                    |
| ------------------------------- | --------------- | --------------------- |
| isEmpty(str)                    | 길이가 0인지 확인      | "" → true             |
| isBlank(str)                    | 비어 있거나 공백뿐인지 확인 | " " → true            |
| trim(str)                       | 앞뒤 공백 제거        | " hi " → "hi"         |
| upperCase(str)                  | 대문자로 변환         | "hello" → "HELLO"     |
| lowerCase(str)                  | 소문자로 변환         | "HELLO" → "hello"     |
| contains(str, search)           | 특정 문자열 포함 여부    | "hello"에 "ell" → true |
| defaultIfBlank(str, defaultStr) | 공백이면 기본값 반환     | " " → "기본값"           |

주의할 점은 `StringUtils.isEmpty(" ")`는 `false`이지만, `StringUtils.isBlank(" ")`는 `true`라는 것입니다.

### 4\. 회원가입 기능에 적용하기

스프링 부트에서 사용자가 입력한 아이디를 검사하는 간단한 예제입니다.

```java
import org.apache.commons.lang3.StringUtils;

public boolean checkUserid(String userid) {

    if (StringUtils.isBlank(userid)) {
        return false;
    }

    return true;
}
```

이 메서드는 다음과 같이 동작합니다.

* `null` → `false` 반환

* `""` → `false` 반환

* `" "` → `false` 반환

* `"groomi"` → `true` 반환

즉, 아이디가 입력되지 않았거나 공백만 입력되었다면 유효하지 않은 값으로 판단합니다.

실제 회원가입에서는 이 검사 외에도 아이디 형식, 중복 여부 등을 추가로 확인해야 합니다.

### 5\. 라이브러리 설치가 필요한가요?

`StringUtils`는 자바 기본 클래스가 아니라 Apache Commons Lang 라이브러리에 포함되어 있습니다.

Gradle 프로젝트라면 `build.gradle`의 `dependencies`에 다음을 추가할 수 있습니다.

```gradle
implementation 'org.apache.commons:commons-lang3:3.18.0'
```

프로젝트에 이미 해당 라이브러리가 포함되어 있다면 다시 추가할 필요는 없습니다.

참고로 자바 기본 `String` 클래스에도 `isBlank()`, `trim()`, `contains()` 등이 있습니다. 따라서 모든 작업에 `StringUtils`가 반드시 필요한 것은 아닙니다. 특히 `StringUtils.isBlank()`는 `null`까지 안전하게 검사할 수 있다는 장점이 있습니다.

핵심 정리: `StringUtils`는 스프링 부트에서 회원가입, 로그인, 검색, 게시글 작성 등의 기능을 구현할 때 문자열을 안전하고 편리하게 처리하도록 도와주는 클래스입니다.
