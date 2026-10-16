import org.mybatis.spring.SqlSessionFactoryBean;은 **스프링 환경에서 MyBatis의 `SqlSessionFactory`를 만들어 주는 팩토리 빈(FactoryBean)**입니다. 패키지가 `org.mybatis.spring`인 것에서 알 수 있듯, MyBatis 본체가 아니라 **MyBatis와 스프링을 연결하는 `mybatis-spring` 라이브러리**의 클래스입니다.

## 왜 필요한가?

순수 MyBatis에서는 `SqlSessionFactoryBuilder`로 XML 설정 파일(`mybatis-config.xml`)을 읽어 `SqlSessionFactory`를 만듭니다.

```java
// 순수 MyBatis 방식
InputStream is = Resources.getResourceAsStream("mybatis-config.xml");
SqlSessionFactory factory = new SqlSessionFactoryBuilder().build(is);
```

스프링에서는 이 과정을 **스프링 설정(빈 등록)으로 대체**해야 합니다. DataSource는 스프링이 관리하고, 트랜잭션도 스프링이 관리하기 때문입니다. 그래서 스프링 방식으로 `SqlSessionFactory`를 구성하게 해주는 클래스가 `SqlSessionFactoryBean`입니다.

## 주요 설정 메서드

```java
SqlSessionFactoryBean factoryBean = new SqlSessionFactoryBean();

factoryBean.setDataSource(dataSource);                  // DB 연결 (필수)
factoryBean.setMapperLocations(resources);              // 매퍼 XML 위치
factoryBean.setConfigLocation(                          // mybatis-config.xml 위치
    new ClassPathResource("mybatis-config.xml"));
factoryBean.setTypeAliasesPackage("egovframework.hyb"); // 타입 별칭 패키지
factoryBean.setConfiguration(configuration);            // Configuration 객체 직접 지정
```

| 메서드 | 역할 |
|---|---|
| `setDataSource` | 스프링이 관리하는 DataSource 연결 (필수) |
| `setMapperLocations` | 매퍼 XML 파일들의 위치 |
| `setConfigLocation` | MyBatis 설정 XML 파일 위치 |
| `setTypeAliasesPackage` | VO 클래스를 짧은 이름으로 쓰게 해주는 별칭 패키지 |
| `setConfiguration` | 카멜케이스 변환 등 설정을 자바 코드로 직접 지정 |

## 사용 예시

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

        org.apache.ibatis.session.Configuration config =
            new org.apache.ibatis.session.Configuration();
        config.setMapUnderscoreToCamelCase(true);   // user_name → userName
        factoryBean.setConfiguration(config);

        return factoryBean.getObject();   // 실제 SqlSessionFactory 반환
    }
}
```

마지막 `getObject()`가 이름 그대로 "FactoryBean이 만들어 내는 실제 객체"인 `SqlSessionFactory`를 꺼내는 부분입니다.

## FactoryBean이란?

스프링에서 `FactoryBean`은 **자기 자신이 아니라, 자기가 만들어내는 객체를 빈으로 등록해 주는 특수한 빈**입니다. 그래서 XML로 등록하면 이런 모습입니다.

```xml
<bean id="sqlSessionFactory" class="org.mybatis.spring.SqlSessionFactoryBean">
    <property name="dataSource" ref="dataSource"/>
    <property name="mapperLocations" value="classpath:mapper/**/*.xml"/>
</bean>
```

`id="sqlSessionFactory"`로 주입받으면 `SqlSessionFactoryBean` 자신이 아니라 **`SqlSessionFactory`**가 주입됩니다.

## 전체 흐름

```
DataSource ──────────┐
Mapper XML 위치 ─────┼→ SqlSessionFactoryBean ──getObject()──→ SqlSessionFactory
설정(Configuration) ─┘      (설정을 조립하는 스프링용 빌더)           (빈으로 등록됨)
                                                                        ↓
                                                         @MapperScan, SqlSessionTemplate이 사용
```

## 스프링 부트에서는?

`mybatis-spring-boot-starter`를 쓰면 `application.properties` 설정만으로 **자동 설정(`MybatisAutoConfiguration`)이 내부적으로 `SqlSessionFactoryBean`을 사용**해 `SqlSessionFactory`를 만들어 줍니다. 그래서 직접 쓸 일은 줄어들지만, 다음 경우에는 직접 등장합니다.

- 데이터소스를 **2개 이상** 사용하는 멀티 DB 구성
- 자동 설정으로 안 되는 세밀한 커스터마이징
- eGovFrame 예제처럼 Java Config로 명시적으로 구성하는 경우

## 정리

`SqlSessionFactoryBean`은 **"DataSource, 매퍼 XML 위치, MyBatis 설정을 받아 스프링 환경에 맞는 `SqlSessionFactory`를 조립해 주는 mybatis-spring의 빌더 겸 FactoryBean"**입니다.
