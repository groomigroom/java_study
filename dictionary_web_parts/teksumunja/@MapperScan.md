import org.mybatis.spring.annotation.MapperScan;은 **MyBatis 매퍼 인터페이스를 자동으로 찾아서 스프링 빈으로 등록해 주는 어노테이션**입니다. (`mybatis-spring` / `mybatis-spring-boot-starter` 제공)

## @MapperScan의 의미

MyBatis에서는 SQL을 호출하는 **Mapper 인터페이스**를 만듭니다.

```java
public interface BoardMapper {
    List<BoardVO> selectBoardList();
    BoardVO selectBoard(String id);
}
```

이 인터페이스는 구현 클래스가 없습니다. 실제 SQL은 XML(`BoardMapper.xml`)이나 어노테이션(`@Select`)에 있고, MyBatis가 **런타임에 프록시 객체를 만들어** 구현체 역할을 해 줍니다.

문제는 이 프록시를 스프링 빈으로 등록해야 `@Autowired`로 주입받을 수 있다는 점입니다. 매퍼마다 일일이 `@Bean`을 만들거나 XML로 등록하는 건 번거롭기 때문에, `@MapperScan`이 **지정한 패키지를 스캔해서 매퍼 인터페이스를 전부 자동으로 빈 등록**해 줍니다.

```
@Component 계열 → @ComponentScan 이 찾음
Mapper 인터페이스 → @MapperScan 이 찾음
```

`@ComponentScan`은 인터페이스를 빈으로 등록하지 않기 때문에, 매퍼용으로 별도의 `@MapperScan`이 필요한 것입니다.

## `@MapperScan(basePackages="egovframework.hyb")` 해석

**`egovframework.hyb` 패키지와 그 하위 패키지를 스캔해서 매퍼 인터페이스를 찾아 빈으로 등록하라**는 뜻입니다.

```java
@SpringBootApplication
@MapperScan(basePackages = "egovframework.hyb")
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
```

예를 들어 아래 인터페이스들이 스캔 대상이 됩니다.

```
egovframework.hyb.board.mapper.BoardMapper      ✔
egovframework.hyb.member.mapper.MemberMapper    ✔
egovframework.other.xxx.XxxMapper               ✘ (범위 밖)
```

그 결과 서비스에서 바로 주입받아 쓸 수 있습니다.

```java
@Service
public class BoardServiceImpl implements BoardService {

    @Autowired
    private BoardMapper boardMapper;   // MapperScan 덕분에 주입 가능
}
```

## 주의할 점

**1. 패키지 범위가 너무 넓으면 문제가 생길 수 있습니다.**
`egovframework.hyb` 아래의 모든 인터페이스를 매퍼로 간주하려 하기 때문에, 매퍼가 아닌 일반 인터페이스(예: Service 인터페이스)까지 빈으로 등록되어 오류나 충돌이 날 수 있습니다. 이를 막기 위해 필터를 쓰기도 합니다.

```java
// 특정 어노테이션이 붙은 인터페이스만 스캔
@MapperScan(basePackages = "egovframework.hyb", annotationClass = Mapper.class)
```

eGovFrame에서는 `@Mapper("boardMapper")`처럼 `org.egovframe.rte.psl.dataaccess.mapper.Mapper` 어노테이션을 매퍼에 붙이는 경우가 많고, 이때 `annotationClass`로 지정하면 범위를 정확히 좁힐 수 있습니다.

**2. `@Mapper`와의 차이**

| 방식 | 설명 |
|---|---|
| `@Mapper` | 매퍼 인터페이스 **하나하나**에 붙임 |
| `@MapperScan` | **패키지 단위**로 한 번에 등록 |

Spring Boot의 `mybatis-spring-boot-starter`는 `@MapperScan`이 없으면 `@Mapper`가 붙은 인터페이스를 자동 탐색하기도 합니다. 하지만 `@MapperScan`을 명시하면 어노테이션 없이도 해당 패키지의 모든 매퍼가 등록됩니다.

**3. basePackages 여러 개 지정 가능**

```java
@MapperScan(basePackages = {"egovframework.hyb.board", "egovframework.hyb.member"})
```

## 정리

`@MapperScan(basePackages="egovframework.hyb")`는 **"egovframework.hyb 하위의 MyBatis 매퍼 인터페이스를 모두 찾아 스프링 빈으로 등록해서, `@Autowired`로 주입받아 쓸 수 있게 하라"**는 설정입니다.
