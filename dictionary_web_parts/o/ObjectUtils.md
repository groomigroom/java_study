`org.springframework.util.ObjectUtils`는 스프링 부트에서 객체(Object), 배열(Array) 등의 상태를 쉽게 확인하고 처리할 수 있도록 도와주는 유틸리티 클래스입니다.

쉽게 말하면, 객체가 `null`인지, 배열이 비어 있는지, 두 객체의 값이 같은지 등을 간단하게 검사할 수 있도록 도와줍니다.

### 1\. ObjectUtils의 주요 기능

| 메서드                               | 의미                       |
| --------------------------------- | ------------------------ |
| ObjectUtils.isEmpty(obj)          | 객체가 비어 있는지 확인            |
| ObjectUtils.isArray(obj)          | 객체가 배열인지 확인              |
| ObjectUtils.nullSafeEquals(a, b)  | null이 포함되어도 안전하게 두 객체 비교 |
| ObjectUtils.nullSafeHashCode(obj) | null을 고려하여 해시 코드 계산      |
| ObjectUtils.toObjectArray(obj)    | 객체를 객체 배열로 변환            |
| ObjectUtils.identityToString(obj) | 객체의 식별 정보를 문자열로 반환       |

### 2\. 실습 예제

### 예제 1. 객체가 비어 있는지 확인하기

```java
import org.springframework.util.ObjectUtils;

public class ObjectUtilsExample {
    public static void main(String[] args) {
        String name = null;
        String text = "";

        System.out.println(ObjectUtils.isEmpty(name));
        System.out.println(ObjectUtils.isEmpty(text));
    }
}
```

실행 결과:

```text
true
true
```

* `name`은 `null`이므로 `true`

* `text`는 빈 문자열이므로 `true`

주의할 점은 `ObjectUtils.isEmpty()`가 일반적인 문자열이나 컬렉션의 모든 종류를 동일하게 처리하지는 않는다는 것입니다. 예를 들어 빈 문자열은 확인하지만, 공백만 있는 문자열 `" "`은 비어 있다고 판단하지 않습니다.

### 예제 2. 두 객체 비교하기

```java
import org.springframework.util.ObjectUtils;

public class ObjectUtilsExample {
    public static void main(String[] args) {
        String a = null;
        String b = null;

        System.out.println(ObjectUtils.nullSafeEquals(a, b));
    }
}
```

실행 결과:

```text
true
```

두 값이 모두 `null`이어도 오류 없이 비교할 수 있습니다.

### 예제 3. 배열인지 확인하기

```java
import org.springframework.util.ObjectUtils;

public class ObjectUtilsExample {
    public static void main(String[] args) {
        String[] names = {"철수", "영희"};
        String name = "철수";

        System.out.println(ObjectUtils.isArray(names));
        System.out.println(ObjectUtils.isArray(name));
    }
}
```

실행 결과:

```text
true
false
```

### 3\. 스프링 부트에서 왜 사용할까?

예를 들어 회원가입 기능을 구현한다고 가정해 보겠습니다.

```java
import org.springframework.util.ObjectUtils;

public boolean checkUser(String[] userInfo) {
    if (ObjectUtils.isEmpty(userInfo)) {
        return false;
    }

    return true;
}
```

이 코드는 전달받은 `userInfo`가 `null`이거나 빈 배열이면 `false`를 반환합니다.

스프링 부트에서는 다음과 같은 상황에서 활용할 수 있습니다.

* 파일 업로드: 전달된 파일 배열이 비어 있는지 확인

* 데이터 처리: 객체나 배열의 값 확인

* 조건 검사: 객체가 `null`인지 또는 비어 있는지 검사

* 객체 비교: 두 객체의 값이 같은지 확인

참고로 `ObjectUtils`는 스프링 부트 전용 클래스가 아니라 스프링 프레임워크에서 제공하는 유틸리티 클래스이며, 일반적으로 `spring-core` 라이브러리에 포함되어 있습니다.

### 4\. 비슷한 클래스와 차이점

| 클래스             | 주요 용도                   |
| --------------- | ----------------------- |
| ObjectUtils     | 객체, 배열의 상태 확인 및 비교      |
| StringUtils     | 문자열 처리 및 검사             |
| CollectionUtils | List, Set, Map 등 컬렉션 처리 |
| FileCopyUtils   | 파일 및 스트림 데이터 복사         |

핵심 정리: `org.springframework.util.ObjectUtils`는 스프링 부트에서 객체와 배열을 안전하고 간편하게 검사하고 비교하기 위해 사용하는 유틸리티 클래스입니다.
