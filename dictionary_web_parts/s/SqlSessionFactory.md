import org.apache.ibatis.session.SqlSessionFactory;는 **MyBatis의 핵심 객체로, SQL을 실행할 수 있는 `SqlSession`을 만들어내는 공장(Factory)**입니다. 패키지가 `org.apache.ibatis`인 것은 MyBatis의 옛 이름이 iBATIS였기 때문입니다.

## 역할

MyBatis가 SQL을 실행하려면 다음 정보가 모두 준비되어야 합니다.

- 어떤 DB에 연결할지 (DataSource)
- 매퍼 XML 파일은 어디에 있는지
- 타입 별칭, 카멜케이스 변환 같은 설정은 무엇인지

`SqlSessionFactory`는 이 모든 설정을 **한 번 읽어서 들고 있다가**, 필요할 때마다 `SqlSession`을 만들어 줍니다.

```
설정(DataSource, Mapper XML, Config)
        ↓
  SqlSessionFactory   ← 애플리케이션 전체에서 단 1개 (싱글톤)
        ↓ openSession()
    SqlSession        ← SQL 실행 단위 (요청/트랜잭션마다 생성)
        ↓
  Mapper 프록시 → SQL 실행
```

## 핵심 기능

| 기능 | 설명 |
|---|---|
| `SqlSession` 생성 | `openSession()`으로 SQL 실행용 세션 생성 |
| 설정 보관 | `Configuration` 객체(매퍼 정보, 별칭, 타입핸들러 등)를 보유 |
| 매퍼 등록 | 매퍼 XML/인터페이스를 파싱해서 SQL 정보를 메모리에 적재 |

순수 MyBatis에서는 이렇게 직접 사용합니다.

```java
try (SqlSession session = sqlSessionFactory.openSession()) {
    BoardMapper mapper = session.getMapper(BoardMapper.class);
    List<BoardVO> list = mapper.selectBoardList();
}
```

## 스프링 부트에서는?

스프링 환경에서는 `mybatis-spring`이 `SqlSessionFactoryBean`을 통해 `SqlSessionFactory`를 만들고, 세션 열고 닫기와 트랜잭션 연동도 알아서 처리합니다. 그래서 개발자는 `SqlSession`을 직접 다룰 일이 거의 없고, 매퍼만 주입받아 사용합니다.

**1) 자동 설정 (`mybatis-spring-boot-starter`)**
`application.properties`만 작성하면 `SqlSessionFactory`가 자동으로 생성됩니다.

```properties
mybatis.mapper-locations=classpath:mapper/**/*.xml
mybatis.type-aliases-package=egovframework.hyb
mybatis.configuration.map-underscore-to-camel-case=true
```

**2) 수동 설정 (Java Config)**
eGovFrame 프로젝트에서 자주 보이는 형태입니다.

```java
@Configuration
public class MyBatisConfig {

    @Bean
    public SqlSessionFactory sqlSessionFactory(DataSource dataSource) throws Exception {
        SqlSessionFactoryBean factoryBean = new SqlSessionFactoryBean();
        factoryBean.setDataSource(dataSource);
        factoryBean.setMapperLocations(
            new PathMatchingResourcePatternResolver()
                .getResources("classpath:mapper/**/*.xml"));
        return factoryBean.getObject();
    }
}
```

여기서 `SqlSessionFactoryBean`은 `SqlSessionFactory`를 만들어 주는 스프링용 빌더이고, `getObject()`가 실제 `SqlSessionFactory`를 반환합니다.

## 다른 구성요소와의 관계

```
DataSource ──────────┐
Mapper XML 위치 ─────┼→ SqlSessionFactory ──→ SqlSessionTemplate
설정(Configuration) ─┘                         (스레드 안전한 SqlSession,
                                                스프링이 주입해 줌)
                                                    ↑
@MapperScan ──→ 매퍼 프록시 빈 생성 시 내부적으로 사용
```

- **`@MapperScan`**: 스캔한 매퍼 인터페이스를 빈으로 만들 때 `SqlSessionFactory`를 사용해 프록시를 연결합니다. 즉 `@MapperScan`이 동작하려면 `SqlSessionFactory` 빈이 반드시 존재해야 합니다.
- **`SqlSessionTemplate`**: `SqlSessionFactory`를 감싼 스프링용 `SqlSession` 구현체로, 스레드 안전하고 스프링 트랜잭션과 연동됩니다. `EgovAbstractMapper`가 내부적으로 이것을 사용합니다.

## 정리

`SqlSessionFactory`는 **"DB 연결 정보와 매퍼 설정을 모두 담고 있다가 SQL 실행용 세션을 만들어 주는 MyBatis의 중심 객체"**이며, 스프링 부트에서는 자동 또는 수동으로 빈으로 등록되어 `@MapperScan`과 매퍼 주입이 동작하는 기반이 됩니다.
