`java.sql.Timestamp`는 스프링 부트(Spring Boot)에서 날짜와 시간을 함께 표현하고, 특히 데이터베이스의 날짜·시간 데이터를 처리할 때 사용하는 자바 클래스입니다.

쉽게 말하면, 게시글 작성 시간이나 회원 가입 시간처럼 특정 데이터가 언제 생성되었는지 기록할 때 사용할 수 있습니다.

### 1\. `java.sql.Timestamp`의 의미

```java
import java.sql.Timestamp;
```

* `java.sql` : 자바에서 데이터베이스 관련 기능을 제공하는 패키지

* `Timestamp` : 날짜와 시간, 나노초 단위의 소수점 이하 초까지 표현할 수 있는 클래스

예를 들어 다음과 같은 시간을 저장할 수 있습니다.

날짜

2026-10-18

시간

07:11:30

Timestamp 예시: `2026-10-18 07:11:30.123456789`

### 2\. 스프링 부트에서 사용하는 예제

게시글의 작성 시간을 기록하는 상황을 살펴보겠습니다.

```java
import java.sql.Timestamp;

public class WriteEntity {

    private Integer id;

    private String content;

    private Timestamp createDate;
}
```

각 변수의 의미는 다음과 같습니다.

| 변수         | 의미              |
| ---------- | --------------- |
| id         | 게시글 번호          |
| content    | 게시글 내용          |
| createDate | 게시글이 작성된 날짜와 시간 |

예를 들어 사용자가 게시글을 작성하면 `createDate`에 작성 시각을 저장할 수 있습니다.

### 3\. 현재 시간을 저장하는 방법

```java
import java.sql.Timestamp;

Timestamp now = new Timestamp(System.currentTimeMillis());

System.out.println(now);
```

실행 결과 예시:

```text
2026-10-18 07:11:30.123
```

코드를 하나씩 살펴보겠습니다.

* `System.currentTimeMillis()` : 1970년 1월 1일 00:00:00 UTC부터 현재까지 경과한 시간을 밀리초로 반환합니다.

* `new Timestamp(...)` : 밀리초 값을 `Timestamp` 객체로 변환합니다.

* `System.out.println(now)` : 날짜와 시간을 출력합니다.

즉, 현재 시각을 가져와 데이터베이스에 저장할 수 있는 형태로 만드는 것입니다.

### 4\. MySQL과 연결하면 어떻게 될까요?

예를 들어 MySQL 테이블에 다음과 같은 데이터가 있다고 가정하겠습니다.

| id  | content  | create_date         |
| --- | -------- | ------------------- |
| 1   | 첫 번째 게시글 | 2026-10-18 07:11:30 |
| 2   | 두 번째 게시글 | 2026-10-18 07:15:10 |

`create_date`처럼 날짜와 시간을 저장하는 컬럼에는 MySQL의 `DATETIME` 또는 `TIMESTAMP` 자료형을 사용할 수 있습니다. 둘은 시간대 처리 방식 등에서 차이가 있습니다.

참고로 자바의 `java.sql.Timestamp`와 MySQL의 `TIMESTAMP`는 이름은 같지만 서로 다른 개념입니다.

### 5\. `Timestamp`와 `LocalDateTime`의 차이

스프링 부트에서 날짜와 시간을 다룰 때는 `java.time.LocalDateTime`도 많이 사용합니다.

| 구분             | Timestamp      | LocalDateTime  |
| -------------- | -------------- | -------------- |
| 패키지            | java.sql       | java.time      |
| 주요 목적          | JDBC 데이터베이스 연동 | 일반적인 날짜·시간 처리  |
| 나노초 정밀도        | 지원             | 지원             |
| 스프링 부트 JPA 엔티티 | 사용 가능          | 일반적으로 권장되는 선택지 |

새로운 스프링 부트 프로젝트에서 JPA 엔티티의 작성 시간을 표현한다면 다음처럼 작성할 수 있습니다.

```java
import java.time.LocalDateTime;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class WriteEntity {

    @Id
    private Integer id;

    private String content;

    private LocalDateTime createDate;
}
```

정리하면: `java.sql.Timestamp`는 스프링 부트에서 게시글 작성 시간, 회원 가입 시간, 데이터 수정 시간 등을 표현하고 데이터베이스와 연동할 때 사용할 수 있는 클래스입니다. 새로운 JPA 프로젝트라면 특별한 이유가 없는 한 `LocalDateTime`부터 익히는 것을 추천합니다.
