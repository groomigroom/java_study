`jakarta.annotation.Resource`는 스프링 부트에서 다른 객체(Bean)를 이름이나 타입을 기준으로 찾아서 자동으로 주입할 때 사용하는 어노테이션이야.

앞서 살펴본 `AcceleratorAPIDAO`, `EgovAbstractServiceImpl`과 연결하면 이해하기 쉬워. 서비스 클래스에서 DAO 객체를 직접 생성하지 않고, 스프링이 관리하는 DAO 객체를 자동으로 연결하는 데 사용할 수 있어.

## 1. `import jakarta.annotation.Resource;`의 의미

java
```
import jakarta.annotation.Resource;
```

이 코드는 `Resource` 어노테이션을 사용하기 위해 해당 클래스를 가져오는 거야.

- `jakarta.annotation`: Jakarta EE에서 제공하는 공통 어노테이션 패키지
- `Resource`: 의존성 주입(Dependency Injection, DI)에 사용하는 어노테이션

스프링 부트에서는 이 어노테이션을 지원하며, 스프링이 관리하는 객체를 다른 클래스에 주입할 수 있어.

참고로 구버전 프로젝트에서는 다음 패키지를 사용하는 경우도 있어.

java
```
import javax.annotation.Resource;
```

일반적으로 Spring Boot 3.x 이상에서는 `jakarta.annotation.Resource`를 사용하고, Spring Boot 2.x 기반 프로젝트에서는 `javax.annotation.Resource`를 사용하는 경우가 많아.

## 2. `@Resource(name="AcceleratorAPIDAO")`의 의미

다음 코드를 살펴보자.

java
```
@Resource(name = "AcceleratorAPIDAO")
private AcceleratorAPIDAO acceleratorAPIDAO;
```

이 코드는 크게 세 부분으로 나눌 수 있어.

| 코드 | 의미 |
| --- | --- |
| `@Resource` | 의존성 주입을 요청하는 어노테이션 |
| `name = "AcceleratorAPIDAO"` | 주입할 스프링 빈의 이름을 지정 |
| `private AcceleratorAPIDAO acceleratorAPIDAO` | 주입받은 DAO 객체를 저장할 필드 |

쉽게 말하면 다음과 같은 뜻이야.

> 스프링 컨테이너에서 이름이 `AcceleratorAPIDAO`인 빈을 찾아서 `acceleratorAPIDAO` 필드에 연결해 줘.

여기서 빈 이름과 Java 클래스 이름은 서로 다른 개념이라는 점이 중요해.

## 3. 실제로 어떻게 동작할까?

예를 들어 DAO 클래스가 다음과 같다고 해보자.

java
```
public class AcceleratorAPIDAO
        extends EgovAbstractMapper {

    public List<?> selectAcceleratorInfoList(
            AcceleratorAPIVO searchVO) {

        return selectList(
            "acceleratorAPIDAO.selectAcceleratorInfoList",
            searchVO
        );
    }
}
```

그리고 서비스 클래스에서 다음과 같이 작성할 수 있어.

java
```
@Service("EgovAcceleratorAPIService")
public class EgovAcceleratorAPIServiceImpl
        extends EgovAbstractServiceImpl {

    @Resource(name = "AcceleratorAPIDAO")
    private AcceleratorAPIDAO acceleratorAPIDAO;

    public List<?> getAcceleratorInfoList(
            AcceleratorAPIVO searchVO) {

        return acceleratorAPIDAO
                .selectAcceleratorInfoList(searchVO);
    }
}
```

이 코드의 실행 흐름은 다음과 같아.

Spring 컨테이너

등록된 Bean을 관리

`@Resource(name = "AcceleratorAPIDAO")`

이름이 일치하는 Bean을 검색

`acceleratorAPIDAO` 필드에 주입

DAO 메서드를 호출할 수 있게 됨

`selectAcceleratorInfoList()` 실행

MyBatis를 통해 DB 조회

즉, 서비스 클래스에서 다음과 같이 DAO 객체를 직접 생성할 필요가 없어.

java
```
// 직접 생성하는 방식: 일반적으로 스프링 관리 객체에는 권장하지 않음
AcceleratorAPIDAO dao = new AcceleratorAPIDAO();
```

스프링 컨테이너가 관리하는 객체를 주입받으면 설정과 생명주기를 스프링에 맡길 수 있어.

## 4. 그런데 DAO에 `@Repository`가 없으면 어떻게 될까?

앞서 보여 준 `AcceleratorAPIDAO` 코드에는 `@Repository`가 없었지.

java
```
public class AcceleratorAPIDAO extends EgovAbstractMapper {
    // ...
}
```

이 경우에도 `@Resource(name = "AcceleratorAPIDAO")`가 동작할 수 있어. 다만 해당 DAO가 스프링 빈으로 등록되어 있어야 해.

예를 들어 다음 중 하나의 방식으로 등록되어 있을 수 있어.

### 방법 A. `@Repository`로 등록

java
```
@Repository("AcceleratorAPIDAO")
public class AcceleratorAPIDAO
        extends EgovAbstractMapper {
}
```

이렇게 작성하면 컴포넌트 스캔 범위에 포함되는 경우 `AcceleratorAPIDAO`라는 이름으로 빈이 등록돼.

### 방법 B. XML 설정으로 등록

xml
```
<bean id="AcceleratorAPIDAO"
      class="egovframework.hyb.mbl.acl.service.impl.AcceleratorAPIDAO"/>
```

이 방식은 XML에서 빈 이름과 실제 클래스 경로를 지정하는 방법이야. 위 클래스 경로는 예시이므로 실제 패키지에 맞춰야 해.

전자정부프레임워크의 기존 프로젝트는 XML 설정으로 DAO를 등록하는 경우도 있어.

주의: DAO가 빈으로 등록되어 있지 않거나, 지정한 이름의 빈을 찾을 수 없다면 주입에 실패할 수 있어.

## 5. `@Resource`와 `@Autowired`의 차이

둘 다 의존성 주입에 사용하지만 기본적으로 빈을 찾는 방식이 달라.

| 구분 | `@Resource` | `@Autowired` |
| --- | --- | --- |
| 기본 탐색 기준 | 이름 우선 | 타입 우선 |
| 이름 직접 지정 | `name` 속성 사용 | `@Qualifier` 사용 |
| 제공 출처 | Jakarta 어노테이션 | Spring Framework |
| 예시 | `@Resource(name = "AcceleratorAPIDAO")` | `@Autowired` |

예를 들어 다음 코드는 타입을 기준으로 DAO를 주입받는 일반적인 방법이야.

java
```
@Autowired
private AcceleratorAPIDAO acceleratorAPIDAO;
```

반면 다음 코드는 이름을 명시해.

java
```
@Resource(name = "AcceleratorAPIDAO")
private AcceleratorAPIDAO acceleratorAPIDAO;
```

같은 타입의 빈이 여러 개 등록되어 있을 때, 이름을 명시하면 어떤 빈을 사용할지 더 명확하게 지정할 수 있어.

## 6. 꼭 기억할 점

`@Resource(name = "AcceleratorAPIDAO")`에서 `AcceleratorAPIDAO`는 반드시 클래스 이름과 일치해야 하는 문자열은 아니야. 실제로 스프링 컨테이너에 등록된 빈 이름과 일치해야 해.

예를 들어 다음과 같이 등록했다면,

java
```
@Repository("myAcceleratorDAO")
public class AcceleratorAPIDAO {
}
```

주입할 때도 다음과 같이 이름을 맞춰야 해.

java
```
@Resource(name = "myAcceleratorDAO")
private AcceleratorAPIDAO acceleratorAPIDAO;
```

정리하면, `@Resource(name = "AcceleratorAPIDAO")`는 전자정부프레임워크 전용 어노테이션이 아니라 Jakarta의 의존성 주입 어노테이션이며, 스프링 부트에서도 사용할 수 있어. 이 코드는 이름이 `AcceleratorAPIDAO`인 스프링 빈을 찾아 해당 필드에 연결하라는 의미야.
