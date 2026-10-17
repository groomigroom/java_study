`org.egovframe.rte.fdl.cmmn.EgovAbstractServiceImpl`은 전자정부프레임워크(eGovFrame)에서 서비스(Service) 계층을 구현할 때 상속받아 사용하는 공통 추상 클래스야.

쉽게 말하면, 앞서 살펴본 `EgovAbstractMapper`가 DB 접근 기능을 제공한다면, `EgovAbstractServiceImpl`은 서비스 계층에서 공통 기능을 활용할 수 있도록 기반을 제공하는 클래스야.

## 1. 패키지 이름부터 해석하기

java
```
org.egovframe.rte.fdl.cmmn.EgovAbstractServiceImpl
```

- `org.egovframe`: 전자정부프레임워크 관련 패키지
- `rte`: Runtime Environment, 실행 환경
- `fdl`: Foundation Layer, 기반 계층
- `cmmn`: Common, 공통 기능
- `EgovAbstractServiceImpl`: 전자정부프레임워크의 공통 서비스 구현 기반 클래스

즉, 전자정부프레임워크에서 서비스 계층의 공통 기능을 제공하는 클래스라고 이해하면 돼.

## 2. 실제 코드에서는 어떻게 사용할까?

예를 들어 다음과 같은 코드가 있다고 해보자.

java
```
import org.egovframe.rte.fdl.cmmn.EgovAbstractServiceImpl;
import org.springframework.stereotype.Service;

@Service("acceleratorAPIService")
public class AcceleratorAPIServiceImpl
        extends EgovAbstractServiceImpl {

    public void processAcceleratorInfo() {
        // 가속도 정보 처리 로직
    }
}
```

각 부분의 의미는 다음과 같아.

| 코드 | 의미 |
| --- | --- |
| `@Service` | 클래스를 스프링의 서비스 빈으로 등록 |
| `AcceleratorAPIServiceImpl` | 실제 서비스 로직을 구현하는 클래스 |
| `extends EgovAbstractServiceImpl` | 전자정부프레임워크의 공통 서비스 기능 상속 |
| `processAcceleratorInfo()` | 개발자가 구현한 업무 처리 메서드 |

여기서 `@Service`와 `extends EgovAbstractServiceImpl`은 서로 다른 역할을 해.

- `@Service`는 스프링이 해당 클래스를 빈으로 관리하도록 표시해.
- `EgovAbstractServiceImpl` 상속은 전자정부프레임워크에서 제공하는 공통 서비스 기능을 사용할 수 있도록 해.

## 3. 앞서 본 DAO와는 어떤 차이가 있을까?

앞서 살펴본 `AcceleratorAPIDAO`와 비교하면 이해하기 쉬워.

### DAO 계층

데이터베이스 접근 담당

java
```
public class AcceleratorAPIDAO
        extends EgovAbstractMapper {
    // SQL 조회, 등록, 삭제
}
```

예: `selectList()`, `insert()`, `delete()`

### Service 계층

업무 로직 처리 담당

java
```
public class AcceleratorAPIServiceImpl
        extends EgovAbstractServiceImpl {
    // 업무 규칙 및 처리 흐름
}
```

예: 입력값 검증, 여러 DAO 호출, 업무 규칙 적용

예를 들어 가속도 센서 정보를 등록하는 작업이라면 다음처럼 역할을 나눌 수 있어.

1. Controller: 사용자나 클라이언트의 요청을 받음
2. Service: 등록에 필요한 업무 로직을 처리함
3. DAO: SQL을 실행해 DB에 데이터를 저장함
4. Database: 실제 데이터를 보관함

## 4. `EgovAbstractServiceImpl`을 상속하면 어떤 기능이 생길까?

전자정부프레임워크 버전과 설정에 따라 제공되는 기능을 확인해야 하지만, 이 클래스는 공통 서비스 구현을 위한 기반 클래스야.

여기서 중요한 점은 상속했다고 해서 DB 조회·등록 기능이 자동으로 생기는 것은 아니라는 것이야.

DB 작업은 일반적으로 DAO나 Mapper를 통해 수행하고, 서비스 클래스는 이들을 호출해 업무 흐름을 구성해.

예를 들어 다음과 같아.

java
```
@Service("acceleratorAPIService")
public class AcceleratorAPIServiceImpl
        extends EgovAbstractServiceImpl {

    private final AcceleratorAPIDAO dao;

    public AcceleratorAPIServiceImpl(AcceleratorAPIDAO dao) {
        this.dao = dao;
    }

    public List<?> getAcceleratorInfoList(
            AcceleratorAPIVO searchVO) {

        return dao.selectAcceleratorInfoList(searchVO);
    }
}
```

위 예시는 서비스에서 DAO를 호출하는 구조를 보여줘. 실제 프로젝트에서 이 생성자 주입 방식이 동작하려면 DAO가 스프링 빈으로 등록되어 있어야 해.

## 5. 꼭 상속해야 할까?

반드시 모든 서비스 클래스가 이 클래스를 상속해야 하는 것은 아니야.

- 전자정부프레임워크의 공통 서비스 기능이 필요하다면 상속할 수 있어.
- 일반적인 스프링 서비스라면 `@Service`만으로도 서비스 빈을 등록할 수 있어.
- 실제 상속 필요 여부는 프로젝트의 전자정부프레임워크 버전과 공통 기능 사용 여부에 따라 결정돼.

핵심 정리

- `EgovAbstractMapper`: DB 접근 기능을 위한 기반 클래스
- `EgovAbstractServiceImpl`: 서비스 계층의 공통 기능을 위한 기반 클래스
- `@Repository`: 데이터 접근 계층의 스프링 빈 등록 및 역할 표시
- `@Service`: 서비스 계층의 스프링 빈 등록 및 역할 표시

즉, `EgovAbstractServiceImpl`은 전자정부프레임워크에서 서비스 클래스를 작성할 때 공통 서비스 기능을 활용하기 위한 부모 클래스라고 이해하면 돼.
