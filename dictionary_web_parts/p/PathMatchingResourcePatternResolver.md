org.springframework.core.io.support.PathMatchingResourcePatternResolver;는 **와일드카드 패턴(`*`, `**`)으로 파일/리소스를 한꺼번에 찾아주는 스프링의 리소스 검색 도구**입니다. 앞에서 본 `SqlSessionFactory` 설정 코드에 나왔던 바로 그 클래스입니다.

## 왜 필요한가?

스프링은 파일을 `Resource`라는 객체로 다룹니다. 파일 하나는 쉽게 가져올 수 있지만, **"mapper 폴더 아래 모든 XML 파일"** 같은 패턴 검색은 별도 도구가 필요합니다. 그 역할을 하는 것이 이 클래스입니다.

```java
Resource[] resources = new PathMatchingResourcePatternResolver()
        .getResources("classpath:mapper/**/*.xml");
```

→ `src/main/resources/mapper/` 아래의 모든 `.xml` 파일(하위 폴더 포함)을 `Resource[]` 배열로 반환합니다.

## 패턴 문법

| 패턴 | 의미 |
|---|---|
| `?` | 문자 1개 |
| `*` | 폴더 안의 임의 문자열 (하위 폴더 제외) |
| `**` | 하위 폴더까지 포함한 모든 경로 |

```
classpath:mapper/*.xml        → mapper 폴더 바로 아래의 xml만
classpath:mapper/**/*.xml     → mapper 하위 모든 폴더의 xml
```

## 접두어의 의미

| 접두어 | 의미 |
|---|---|
| `classpath:` | 현재 프로젝트의 클래스패스에서 검색 (첫 번째 매칭 위치만) |
| `classpath*:` | 모든 클래스패스(의존 라이브러리 jar 포함)에서 검색 |
| `file:` | 파일 시스템의 실제 경로 |

```java
"classpath:egovframework/mapper/**/*.xml"   // 내 프로젝트만
"classpath*:mapper/**/*.xml"                // jar 안의 것까지 모두
"file:/home/app/mapper/*.xml"               // 서버의 실제 경로
```

## MyBatis 설정에서의 사용

```java
@Bean
public SqlSessionFactory sqlSessionFactory(DataSource dataSource) throws Exception {
    SqlSessionFactoryBean factoryBean = new SqlSessionFactoryBean();
    factoryBean.setDataSource(dataSource);
    factoryBean.setMapperLocations(
        new PathMatchingResourcePatternResolver()
            .getResources("classpath:mapper/**/*.xml"));   // 매퍼 XML 전부 등록
    return factoryBean.getObject();
}
```

`setMapperLocations()`는 `Resource[]`를 받기 때문에, 이 클래스로 XML 파일 목록을 찾아서 넘겨주는 것입니다. 이렇게 하면 매퍼 XML을 새로 추가해도 설정을 고칠 필요가 없습니다.

## 참고

`application.properties`의 `mybatis.mapper-locations=classpath:mapper/**/*.xml`도 내부적으로 같은 방식으로 처리됩니다. 즉 스프링 부트 자동 설정을 쓰면 이 클래스를 직접 쓸 일이 없고, **수동으로 `SqlSessionFactory`를 구성할 때** 등장합니다.

## 정리

`PathMatchingResourcePatternResolver`는 **"`classpath:mapper/**/*.xml` 같은 패턴에 해당하는 리소스 파일들을 찾아서 배열로 돌려주는 스프링의 유틸리티"**이며, MyBatis 설정에서는 매퍼 XML 파일들을 일괄 등록하는 데 쓰입니다.
