전자정부표준프레임워크(eGovFrame)에서 `org.egovframe.rte.psl.dataaccess.mapper.EgovMapper`는 MyBatis를 이용해 데이터베이스에 접근할 때 사용하는 전자정부프레임워크의 Mapper 인터페이스입니다.

쉽게 말해, SQL을 실행하고 데이터베이스에서 데이터를 조회하거나 저장하는 역할을 하는 객체와 관련된 인터페이스예요.

## 1. 패키지명을 나눠서 이해하기

`org.egovframe.rte.psl.dataaccess.mapper.EgovMapper`

| 패키지 | 의미 |
| --- | --- |
| `org.egovframe` | 전자정부표준프레임워크 관련 패키지 |
| `rte` | Runtime Environment, 실행 환경 |
| `psl` | Persistence Layer, 영속성 계층 관련 영역 |
| `dataaccess` | 데이터 접근 기능 |
| `mapper` | SQL과 자바 코드를 연결하는 Mapper 관련 영역 |
| `EgovMapper` | 전자정부프레임워크에서 제공하는 Mapper 인터페이스 |

## 2. 실제 코드에서는 어떻게 사용될까?

예를 들어 다음과 같은 코드가 있다고 해볼게요.

java
```
import org.egovframe.rte.psl.dataaccess.mapper.EgovMapper;

@Mapper("sampleMapper")
public interface SampleMapper extends EgovMapper {

    SampleVO selectSample(SampleVO vo) throws Exception;

    void insertSample(SampleVO vo) throws Exception;
}
```

이 코드의 역할은 다음과 같습니다.

- `SampleMapper`: 샘플 데이터에 대한 DB 작업을 정의합니다.
- `extends EgovMapper`: 전자정부프레임워크에서 제공하는 Mapper 인터페이스를 상속합니다.
- `selectSample()`: 데이터를 조회합니다.
- `insertSample()`: 데이터를 삽입합니다.

실제 SQL은 보통 MyBatis XML 파일이나 별도로 구성된 SQL 매핑을 통해 연결됩니다.

## 3. 앞에서 본 `sampleMapper.selectSample(vo)`와의 관계

앞서 보여주신 서비스 코드를 기준으로 보면 다음과 같은 구조입니다.

Service 계층

`selectSample(vo)`

Mapper 계층

`sampleMapper.selectSample(vo)`

MyBatis SQL 매핑

XML 등에 정의된 SQL 실행

데이터베이스

즉, 서비스에서 직접 SQL을 실행하는 대신 Mapper를 호출하고, Mapper가 SQL과 연결되는 구조입니다.

## 4. `EgovMapper`와 `@Mapper`는 다를까?

네, 서로 다른 개념입니다.

- `EgovMapper`: 전자정부프레임워크에서 제공하는 인터페이스입니다.
- `@Mapper`: 해당 인터페이스가 MyBatis Mapper임을 표시하는 어노테이션입니다. 전자정부프레임워크 프로젝트에서는 별도의 전용 어노테이션을 사용할 수도 있습니다.

참고로 `EgovMapper`를 상속한다고 해서 SQL이 자동으로 작성되는 것은 아닙니다. 실제 메서드와 SQL의 연결은 MyBatis 설정 및 매핑 구성에 따라 이루어집니다.

전자정부프레임워크 버전에 따라 세부적인 구현은 달라질 수 있습니다.

원하시면  EgovMapper와 일반 MyBatis Mapper 비교 또는  Mapper XML과 자바 메서드 연결 원리를 이어서 설명해 드릴게요.
