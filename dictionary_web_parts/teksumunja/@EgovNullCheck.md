`org.egovframe.rte.ptl.reactive.validation.EgovNullCheck`는 패키지명을 보면 전자정부 표준프레임워크의 Reactive(WebFlux) 환경에서 사용하는 `EgovNullCheck` 클래스야.

다만 `EgovNullCheck`가 정확히 어떤 조건을 검사하고 어떻게 동작하는지는 실제 클래스 구현을 확인해야 확실하게 설명할 수 있어. 전자정부프레임워크의 실행환경에는 Reactive 웹 계층 모듈인 `egovframe-rte-ptl-reactive`가 있어.

GitHub

+1

## 1. 이름을 분해하면

- `org.egovframe.rte` : 전자정부 표준프레임워크 실행환경 관련 패키지
- `ptl.reactive` : 리액티브 웹 표현 계층 관련 패키지
- `validation` : 유효성 검증 관련 패키지
- `EgovNullCheck` : 이름상 `null` 여부를 확인하는 기능과 관련된 클래스

여기서 주의할 점은 `EgovNullCheck`가 앞서 살펴본 `@Pattern`, `@Max` 같은 표준 Bean Validation 어노테이션과 동일한 종류라고 단정할 수는 없다는 거야.

## 2. `@NotNull`과 같은 걸까?

아래처럼 구분하면 이해하기 쉬워.

| 구분 | 의미 |
| --- | --- |
| `@NotNull` | 값이 `null`인지 검사하는 표준 검증 어노테이션 |
| `EgovNullCheck` | 전자정부프레임워크에서 제공하는 클래스. 정확한 검증 방식은 구현 확인 필요 |

따라서 `EgovNullCheck`를 사용하는 코드를 봤다면, 클래스 자체가 어노테이션인지, 일반 클래스인지, 어떤 메서드를 제공하는지 확인하는 것이 중요해.

공식 실행환경 소스는 [eGovFramework/egovframe-runtime](<https://github.com/eGovFramework/egovframe-runtime?utm_source=chatgpt.com>)에서 확인할 수 있어.

GitHub

`EgovNullCheck`가 사용된 실제 코드나 import 아래 부분을 보여주면, 어떤 값에 대해 무엇을 검사하는지 정확히 설명해 줄게.  EgovNullCheck 코드 분석

-----------

`@EgovNullCheck(message="{confirm.required.name}")`에서 `message`는 검증에 실패했을 때 표시할 오류 메시지를 지정하는 속성이야.

java
```
@EgovNullCheck(message = "{confirm.required.name}")
private String name;
```

여기서 각 부분의 의미는 다음과 같아.

- `@EgovNullCheck` : 전자정부프레임워크에서 제공하는 검증 기능을 적용해.
- `message` : 검증 실패 시 사용할 메시지를 지정해.
- `"{confirm.required.name}"` : 실제 오류 문장 자체가 아니라, 보통 메시지 설정 파일에서 해당 키에 대응하는 문장을 찾도록 지정하는 메시지 키야.

## 1. 메시지 설정 파일 예시

예를 들어 메시지 설정 파일에 다음과 같이 정의되어 있다면,

properties
```
confirm.required.name=이름은 필수 입력 항목입니다.
```

검증에 실패했을 때 다음 메시지를 사용할 수 있어.

> 이름은 필수 입력 항목입니다.

즉, `{confirm.required.name}`은 설정 파일에 정의된 메시지를 참조하기 위한 표현이야.

## 2. 메시지 키를 사용하는 이유

메시지를 코드에 직접 작성하는 대신 키로 관리하면 다음과 같은 장점이 있어.

- 오류 메시지를 한곳에서 관리할 수 있어.
- 여러 화면이나 클래스에서 같은 메시지를 재사용할 수 있어.
- 다국어 메시지를 관리하기 편해.

다만 `EgovNullCheck`가 실제로 어떤 메시지 처리 방식을 사용하는지는 해당 어노테이션의 구현에 따라 달라질 수 있어.

핵심은 `message`가 검증 실패 시 사용할 메시지를 지정한다는 것이야. 원하면  메시지 설정 파일 위치 찾기 도 알아볼 수 있어.
