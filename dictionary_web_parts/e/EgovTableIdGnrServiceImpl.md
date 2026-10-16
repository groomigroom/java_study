import org.egovframe.rte.fdl.idgnr.impl.EgovTableIdGnrServiceImpl;은 전자정부 표준프레임워크(eGovFrame)의 **ID 생성 서비스(ID Generation)** 중 **테이블 기반 방식**을 구현한 클래스입니다.

## 패키지 구조 해석

- `org.egovframe.rte` : eGovFrame Runtime Environment
- `fdl` : Foundation Layer (공통 기반 계층)
- `idgnr` : ID Generation
- `impl` : 구현체 (인터페이스는 `EgovIdGnrService`)

## 동작 방식

DB에 **ID 전용 테이블**(예: `IDS`)을 두고, 거기에 저장된 "다음에 쓸 값"을 읽어서 증가시키며 ID를 발급합니다.

```
IDS 테이블
TABLE_NAME   | NEXT_ID
-------------+--------
BOARD        | 101
MEMBER       | 2050
```

`BOARD`용 ID가 필요하면 → `101`을 반환하고 → 테이블 값을 `102`로 갱신하는 식입니다.

## 특징

- **blockSize**: 한 번 DB에 접근할 때 여러 개(예: 10개)를 미리 확보해서 메모리에 캐시합니다. DB 접근 횟수가 줄어 성능에 유리하지만, 서버를 재기동하면 캐시된 번호 일부는 건너뛰어(결번) 질 수 있습니다.
- **DB 종류에 상관없이 사용 가능**: 시퀀스를 지원하지 않는 DB에서도 쓸 수 있습니다.
- **prefix / cipers / fillChar**: `BOARD_0000000001` 같은 형태로 접두어와 자릿수, 채움 문자를 지정할 수 있습니다.

## 설정 예시 (XML)

```xml
<bean name="egovIdGnrService"
      class="org.egovframe.rte.fdl.idgnr.impl.EgovTableIdGnrServiceImpl"
      destroy-method="destroy">
    <property name="dataSource" ref="dataSource"/>
    <property name="blockSize" value="10"/>
    <property name="table" value="IDS"/>
    <property name="tableName" value="BOARD"/>
    <property name="prefix" value="BRD_"/>
    <property name="cipers" value="10"/>
    <property name="fillChar" value="0"/>
</bean>
```

## 사용 예시

```java
@Resource(name = "egovIdGnrService")
private EgovIdGnrService idGnrService;

String newId = idGnrService.getNextStringId();   // 예: BRD_0000000001
// 또는
long newId = idGnrService.getNextLongId();
```

## 같은 계열의 다른 구현체

| 클래스 | 방식 |
|---|---|
| `EgovSequenceIdGnrServiceImpl` | DB 시퀀스 사용 (Oracle, PostgreSQL 등) |
| `EgovTableIdGnrServiceImpl` | 별도 ID 테이블 사용 |
| `EgovUUIdGnrServiceImpl` | UUID 생성 |

참고로 예전 버전에서는 패키지가 `egovframework.rte.fdl.idgnr.impl...`이었고, eGovFrame 4.x부터 `org.egovframe.rte...`로 변경되었습니다.
