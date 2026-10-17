`org.egovframe.rte.fdl.property.EgovPropertyService`는 전자정부 표준프레임워크(eGovFrame)에서 외부 설정값이나 시스템 환경에 관련된 속성(Property)을 읽고 관리하기 위해 제공하는 서비스 인터페이스입니다.

표준프레임워크 개발가이드

쉽게 말하면, 자바 코드에 설정값을 직접 작성하는 대신 `properties` 파일이나 Spring Bean 설정에 값을 저장해 두고, 필요할 때 코드에서 가져다 쓰도록 도와주는 기능입니다.

## 1. 어떤 역할을 하나요?

예를 들어 서비스에서 파일 업로드 경로, 페이지 크기, 업무별 설정값 등을 관리한다고 가정해 보겠습니다.

설정 파일 `resource.properties`:

properties
```
upload.path=/data/upload
page.size=20
feature.enabled=true
```

자바 코드에서 이러한 값을 직접 작성하는 대신 `EgovPropertyService`를 통해 가져올 수 있습니다.

주요 목적은 다음과 같습니다.

- 설정값 분리: 소스 코드를 수정하지 않고 설정을 외부 파일에서 관리할 수 있습니다.
- 자료형별 조회: 문자열, 정수, 불리언 등의 값을 조회할 수 있습니다.
- 설정 관리 일원화: 여러 클래스에서 공통 설정값을 일관된 방식으로 사용할 수 있습니다.

## 2. 실제 코드에서는 어떻게 사용하나요?

전자정부프레임워크에서 제공하는 인터페이스를 주입받아 사용합니다.

java
```
import jakarta.annotation.Resource;
import org.egovframe.rte.fdl.property.EgovPropertyService;

public class SampleService {

    @Resource(name = "propertyService")
    private EgovPropertyService propertyService;

    public void test() {
        String path = propertyService.getString("upload.path");
        int pageSize = propertyService.getInt("page.size");
        boolean enabled = propertyService.getBoolean("feature.enabled");
    }
}
```

위 코드는 사용 예시이며, 실제로 동작하려면 해당 이름의 `propertyService` Bean과 설정값이 등록되어 있어야 합니다. 전자정부프레임워크 공식 문서에서도 `@Resource(name = "propertyService")` 방식으로 서비스를 주입하고 `getString()` 등으로 값을 조회하는 예제를 제공합니다.

표준프레임워크 개발가이드

## 3. `EgovPropertyService`는 인터페이스인가요?

네. `EgovPropertyService`는 인터페이스이며, 실제 설정값을 읽고 처리하는 구현체가 별도로 존재합니다.

애플리케이션 코드

설정값 조회 요청

EgovPropertyService

서비스 인터페이스

EgovPropertyServiceImpl

실제 기능을 구현하는 클래스

설정값

properties 파일 또는 Spring Bean 설정

일반적으로 개발자는 구현체를 직접 생성하기보다 Spring 컨테이너에 등록된 Bean을 주입받아 사용합니다.

## 4. Spring Boot의 `@Value`와 차이가 있나요?

비슷한 목적을 가진 기능입니다.

| 구분 | `EgovPropertyService` | Spring `@Value` |
| --- | --- | --- |
| 제공 주체 | 전자정부 표준프레임워크 | Spring |
| 주된 사용법 | 메서드로 설정값 조회 | 필드 등에 값 주입 |
| 자료형 변환 | `getInt()`, `getBoolean()` 등 | Spring의 타입 변환 |
| 설정 관리 | eGovFrame Property Service 설정에 따름 | Spring 설정 체계에 따름 |

Spring Boot에서는 다음과 같이 사용할 수도 있습니다.

properties
```
upload.path=/data/upload
page.size=20
```

java
```
@Value("${upload.path}")
private String uploadPath;

@Value("${page.size}")
private int pageSize;
```

두 방식 모두 설정값을 코드에서 분리하는 데 활용할 수 있습니다. 다만 `EgovPropertyService`는 전자정부프레임워크의 기존 Property Service 기능과 설정 체계를 사용하는 방식이고, `@Value`는 Spring의 기본 설정 주입 방식입니다.

참고로 `org.egovframe...` 패키지는 전자정부 표준프레임워크의 비교적 새로운 패키지 체계이며, 구버전 프로젝트에서는 `egovframework.rte...` 패키지를 사용하는 경우도 있습니다.

공식 문서: [전자정부 표준프레임워크 Property Service 가이드](<https://egovframe.go.kr/docs/5.0/egovframe-runtime/foundation-layer/property/property-service/?utm_source=chatgpt.com>)
