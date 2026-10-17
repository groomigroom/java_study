`org.egovframe.rte.psl.dataaccess.EgovAbstractMapper`는 전자정부프레임워크(eGovFrame)에서 MyBatis를 이용해 데이터베이스에 접근할 때 사용하는 공통 추상 클래스야.

쉽게 말하면, SQL을 직접 실행하는 코드를 매번 작성하지 않고, 공통적인 DB 접근 기능을 상속받아 사용하도록 만들어진 클래스라고 이해하면 돼.

## 1. 패키지와 클래스 이름의 의미

java
```
org.egovframe.rte.psl.dataaccess.EgovAbstractMapper
```

각 부분을 나누면 다음과 같아.

- `org.egovframe`: 전자정부프레임워크 관련 패키지
- `rte`: Runtime Environment, 실행 환경
- `psl`: Persistence Layer, 데이터 영속성 계층
- `dataaccess`: 데이터 접근 관련 기능
- `EgovAbstractMapper`: 전자정부프레임워크에서 제공하는 추상 Mapper 클래스

즉, 전자정부프레임워크의 데이터 접근 계층에서 사용하는 Mapper 기반 클래스라는 의미야.

## 2. 실제 코드에서는 어떻게 사용할까?

예를 들어 다음과 같이 작성할 수 있어. 전자정부프레임워크 공식 MyBatis 가이드에서도 이 방식을 안내하고 있어. 표준프레임워크 개발가이드

+1

java
```
import org.springframework.stereotype.Repository;
import org.egovframe.rte.psl.dataaccess.EgovAbstractMapper;

@Repository("userMapper")
public class UserMapper extends EgovAbstractMapper {

    public UserVO selectUser(String userId) {
        return (UserVO) selectOne(
            "UserMapper.selectUser",
            userId
        );
    }
}
```

여기서 중요한 부분을 살펴보자.

- `@Repository("userMapper")`: 해당 클래스를 스프링 빈으로 등록해.
- `extends EgovAbstractMapper`: 전자정부프레임워크에서 제공하는 공통 Mapper 기능을 상속받아.
- `selectOne(...)`: 지정한 SQL을 실행하고 단일 조회 결과를 반환해.
- `"UserMapper.selectUser"`: 실행할 SQL의 ID를 지정해. 일반적으로 MyBatis XML의 네임스페이스와 SQL ID를 조합한 값이야.

SQL은 보통 별도의 XML 파일에 작성해.

xml
```
<mapper namespace="UserMapper">

    <select id="selectUser"
            parameterType="string"
            resultType="UserVO">
        SELECT *
        FROM USERS
        WHERE USER_ID = #{userId}
    </select>

</mapper>
```

실제 프로젝트에서는 `UserVO`의 패키지 및 MyBatis 타입 별칭 설정에 따라 `resultType` 등을 조정해야 해.

## 3. 앞서 본 `@Repository`와 무슨 차이가 있을까?

| 구분 | `@Repository` | `EgovAbstractMapper` |
| --- | --- | --- |
| 종류 | 어노테이션 | 추상 클래스 |
| 주요 역할 | 스프링 빈 등록, 데이터 접근 계층 표시 | 공통 DB 접근 메서드 제공 |
| 사용 방법 | 클래스에 붙임 | 클래스로 상속받음 |
| 사용 예시 | `@Repository` | `extends EgovAbstractMapper` |

둘은 서로 대체하는 관계가 아니야. 함께 사용해서 데이터 접근 클래스를 구성할 수 있어.

## 4. Spring Boot와 함께 사용할 때 알아둘 점

`EgovAbstractMapper`는 Spring Boot 자체에서 제공하는 클래스가 아니라 전자정부프레임워크의 데이터 접근 라이브러리에서 제공하는 클래스야.

따라서 사용하려면 프로젝트에 해당 라이브러리와 MyBatis 관련 설정이 갖춰져 있어야 해. 표준프레임워크 개발가이드

+1

참고로 최근 프로젝트에서는 `EgovAbstractMapper`를 상속하는 클래스 방식 대신 MyBatis Mapper 인터페이스를 사용하는 방식도 가능해.

한 줄 요약: `EgovAbstractMapper`는 전자정부프레임워크에서 SQL 조회·등록·수정·삭제를 편리하게 수행하도록 공통 기능을 제공하는 기반 클래스야. `@Repository`가 클래스를 스프링에 등록하는 역할이라면, `EgovAbstractMapper`는 DB 접근 기능을 제공하는 역할이라고 이해하면 돼.
