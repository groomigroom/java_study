Java에서 `Object` 타입은 모든 Java 클래스의 최상위 부모 클래스 타입입니다. 쉽게 말해, 다양한 종류의 객체를 하나의 타입으로 받을 수 있도록 해주는 가장 범용적인 타입입니다.

앞서 살펴보신 전자정부 프레임워크의 `IExpressionObjectFactory` 코드에서도 `Object`가 사용됐죠.

java
```
@Override
public Object buildObject(String name) {
    if ("pagination".equals(name)) {
        return new EgovKrdsPaginationRenderer();
    }
    return null;
}
```

여기서 `Object`가 왜 사용되는지 이해하면 Java의 타입 시스템을 이해하는 데 도움이 됩니다.

## 1. Object는 모든 객체를 담을 수 있습니다

예를 들어 다음과 같이 여러 타입의 값을 받을 수 있습니다.

java
```
Object a = "안녕하세요";  // String
Object b = 100;          // Integer
Object c = 3.14;         // Double
Object d = new ArrayList<>(); // ArrayList
```

각 객체의 실제 타입은 다르지만, 모두 `Object` 타입 변수에 대입할 수 있습니다.

## Object

모든 클래스의 최상위 부모 타입

String

문자열

Integer

정수 객체

Double

실수 객체

ArrayList

리스트 객체

단, `int`, `double`, `boolean` 같은 기본형(primitive type)은 그 자체로 객체가 아니므로 직접 `Object` 변수에 대입하는 것이 아니라, 오토박싱을 통해 `Integer`, `Double`, `Boolean` 등의 객체로 변환되어 대입됩니다.

## 2. Object 타입의 변수는 실제 타입의 기능을 바로 사용할 수 없습니다

이 부분이 중요합니다.

java
```
Object value = "Hello";

System.out.println(value);
```

위 코드는 정상적으로 실행됩니다.

하지만 다음 코드는 컴파일 오류가 발생합니다.

java
```
Object value = "Hello";

int length = value.length(); // 오류
```

왜냐하면 Java 컴파일러는 `value`를 `Object` 타입으로 인식하기 때문입니다. `Object` 클래스에는 `length()` 메서드가 없으므로 호출할 수 없습니다.

실제 객체가 `String`이라는 것을 알고 있다면 형변환을 해야 합니다.

java
```
Object value = "Hello";

String text = (String) value;

int length = text.length();

System.out.println(length); // 5
```

즉, 실제 객체의 타입과 변수가 선언된 타입은 서로 다를 수 있습니다.

## 3. 전자정부 프레임워크에서 Object를 사용하는 이유

앞서 보신 메서드를 다시 살펴보겠습니다.

java
```
public Object buildObject(String name) {
    if ("pagination".equals(name)) {
        return new EgovKrdsPaginationRenderer();
    }
    return null;
}
```

여기서 반환 타입이 `Object`인 이유는 팩토리가 다양한 종류의 표현식 객체를 반환할 수 있도록 하기 위해서입니다.

예를 들어 다음과 같은 객체를 모두 반환할 수 있습니다.

java
```
public Object buildObject(String name) {
    if ("pagination".equals(name)) {
        return new EgovKrdsPaginationRenderer();
    }

    if ("dateUtils".equals(name)) {
        return new DateUtils();
    }

    if ("stringUtils".equals(name)) {
        return new StringUtils();
    }

    return null;
}
```

각 객체의 클래스는 다르지만, 모두 `Object`의 하위 타입이므로 하나의 메서드에서 반환할 수 있습니다.

다만 실제 전자정부 프레임워크 코드가 위와 같은 객체들을 모두 제공한다는 뜻은 아니며, 반환 타입의 의미를 설명하기 위한 예시입니다.

## 4. Object와 제네릭 타입 T의 차이

Java에서는 다음 두 가지 방식도 자주 볼 수 있습니다.

java
```
Object value = "Hello";
```

java
```
String value = "Hello";
```

첫 번째는 다양한 타입의 객체를 받을 수 있지만, 사용할 때 실제 타입에 맞는 형변환이 필요할 수 있습니다.

두 번째는 문자열만 다루므로 `length()` 같은 문자열 메서드를 바로 사용할 수 있습니다.

| 구분 | `Object` | `String` |
| --- | --- | --- |
| 저장할 수 있는 객체 | 다양한 객체 | 문자열 객체 |
| `length()` 호출 | 직접 호출 불가 | 바로 호출 가능 |
| 형변환 필요성 | 실제 타입에 따라 필요 | 일반적으로 불필요 |
| 주요 목적 | 범용적인 객체 처리 | 문자열 처리 |

한 문장으로 정리하면, `Object`는 모든 Java 객체를 범용적으로 다룰 수 있는 최상위 타입이며, 구체적인 기능을 사용하려면 실제 객체의 타입을 알아야 합니다.
