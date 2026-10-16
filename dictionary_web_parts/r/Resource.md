import org.springframework.core.io.Resource;는 **파일, 클래스패스 파일, URL 등 다양한 위치에 있는 자원을 하나의 통일된 방식으로 다루게 해주는 스프링의 인터페이스**입니다. 앞에서 본 `PathMatchingResourcePatternResolver.getResources()`가 반환하던 `Resource[]`의 바로 그 타입입니다.

## 왜 필요한가?

자바에서 파일을 읽는 방법은 위치마다 제각각입니다.

```java
new File("/home/app/a.xml");               // 파일 시스템
getClass().getResourceAsStream("/a.xml");  // 클래스패스
new URL("http://example.com/a.xml");       // URL
```

위치마다 API가 달라서 코드가 복잡해집니다. 스프링은 이를 `Resource`라는 **하나의 추상화**로 감싸서, 어디에 있든 같은 방식으로 읽을 수 있게 했습니다.

## 주요 메서드

```java
Resource resource = new ClassPathResource("mapper/BoardMapper.xml");

resource.exists();          // 존재 여부
resource.getFilename();     // 파일명: BoardMapper.xml
resource.getInputStream();  // 내용을 읽을 InputStream
resource.getFile();         // File 객체 (jar 내부 파일이면 불가)
resource.getURL();          // URL
```

| 메서드 | 역할 |
|---|---|
| `exists()` | 리소스 존재 여부 확인 |
| `getInputStream()` | 내용을 읽기 위한 스트림 반환 |
| `getFilename()` | 파일 이름 반환 |
| `getFile()` / `getURL()` | File / URL 형태로 변환 |

## 주요 구현체

| 클래스 | 대상 위치 |
|---|---|
| `ClassPathResource` | 클래스패스 (`src/main/resources` 등) |
| `FileSystemResource` | 파일 시스템의 실제 경로 |
| `UrlResource` | URL (http, file 등) |
| `ByteArrayResource` | 메모리상의 바이트 배열 |

## 스프링에서 주입받는 방법

`@Value`로 접두어와 함께 경로를 적으면 스프링이 알맞은 구현체를 골라 `Resource`로 주입해 줍니다.

```java
@Value("classpath:config/sample.txt")
private Resource sampleFile;

@Value("file:/home/app/data.csv")
private Resource csvFile;
```

## MyBatis 설정에서의 역할

앞서 본 코드에서 `Resource`가 이렇게 쓰였습니다.

```java
Resource[] resources = new PathMatchingResourcePatternResolver()
        .getResources("classpath:mapper/**/*.xml");   // 패턴에 맞는 파일들을 Resource 배열로

factoryBean.setMapperLocations(resources);            // 매퍼 XML 목록 등록
```

`setMapperLocations`의 파라미터 타입이 `Resource...`이므로, 패턴으로 찾은 XML 파일들을 `Resource` 배열 형태로 넘기는 것입니다. `setConfigLocation(new ClassPathResource("mybatis-config.xml"))`에서도 같은 타입이 쓰입니다.

## 관계 정리

```
PathMatchingResourcePatternResolver  ──(검색 도구)──→  Resource[]  ──→  SqlSessionFactoryBean
   "classpath:mapper/**/*.xml"                    (찾아낸 파일들)       setMapperLocations()
```

## 정리

`org.springframework.core.io.Resource`는 **"클래스패스, 파일 시스템, URL 등 어디에 있든 동일한 방식으로 접근할 수 있게 해주는 스프링의 리소스 추상화 인터페이스"**이며, MyBatis 설정에서는 매퍼 XML 파일이나 설정 파일을 가리키는 타입으로 쓰입니다.
