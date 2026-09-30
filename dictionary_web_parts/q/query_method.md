자바 스프링 데이터 JPA(Spring Data JPA)에서 findByOriginalLinkOrderByWriteDateAsc와 같은 형태의 메서드 이름을 쿼리 메서드(Query Method) 또는 메서드 이름으로 쿼리 생성(Query Creation from Method Names) 기능이라고 부릅니다.
조금 더 구체적으로 각 부분의 명칭과 동작 원리를 나누면 다음과 같습니다.
## 1. 주요 명칭

* 쿼리 메서드 (Query Method): 인터페이스에 규칙에 맞는 메서드 이름만 선언하면, 스프링이 이를 분석해 자동으로 SQL(JPQL) 쿼리를 만들어주는 기능 전체를 뜻합니다.
* 메서드 이름 분석 (Method Name Parsing): 스프링 데이터 JPA가 메서드 이름을 단어별로 쪼개서 SELECT, WHERE, ORDER BY 문법으로 변환하는 과정을 말합니다.

## 2. 메서드 이름 구조 분석
작성하신 findByOriginalLinkOrderByWriteDateAsc는 내부적으로 다음과 같이 쪼개져서 인식됩니다.

* findBy (도입부): 조회를 시작하겠다는 키워드입니다. (SQL의 SELECT ... WHERE에 해당)
* OriginalLink (조건절/키워드): 엔티티의 originalLink 필드 값을 기준으로 필터링하겠다는 뜻입니다. (SQL의 WHERE original_link = ?)
* OrderBy (정렬 도입부): 정렬 조건을 추가하겠다는 키워드입니다. (SQL의 ORDER BY)
* WriteDate (정렬 필드): 엔티티의 writeDate 필드를 기준으로 정렬하겠다는 뜻입니다.
* Asc (정렬 방향): 오름차순으로 정렬하겠다는 뜻입니다. (SQL의 ASC, 내림차순은 Desc)

## 3. 실제 Repository 작성 예시
실제 스프링부트 프로젝트에서는 다음과 같이 인터페이스에 선언하여 사용합니다.

import org.springframework.data.jpa.repository.JpaRepository;import java.util.List;
public interface BoardRepository extends JpaRepository<Board, Long> {
    
    // originalLink로 찾아서 writeDate 기준 오름차순 정렬하여 리스트 반환
    List<Board> findByOriginalLinkOrderByWriteDateAsc(String originalLink);
}

스프링 데이터 JPA의 쿼리 메서드에 대해 더 궁금한 점이 있으신가요? 필요하시다면 아래 내용을 추가로 도와드릴 수 있습니다.

* findBy 외에 사용할 수 있는 다른 키워드(예: countBy, existsBy, deleteBy)
* And나 Or, Containing 같은 다양한 조건 조건자(Predicate) 사용법
* 메서드 이름이 너무 길어질 때 대안으로 사용하는 @Query 어노테이션 작성법


*  [SpringBoot] JPA 쿼리 메서드 키워드 정리 및 예제 https://share.google/sZw19oLQtEhg9g2IH



-----------------------------------------------------------

# 예시 22

## entity

```java
@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Users {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 50)
    private String name;

    @Column(length = 50)
    private String email;

    @Column(length = 20)
    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Column(length = 50)
    private String likeColor;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
```

------------------------------------------------------

## repository

```java
@Repository
public interface UsersRepository extends JpaRepository<Users, Long> {
    // 이름으로 검색하는 기능
    List<Users> findByName(String name);

    // 색상 값을 받아서 그 중 처음으로 발견되는 3개의 데이터를 출력
    List<Users> findTop3ByLikeColor(String color);

    // 남자이면서 색상이 Yellow
    List<Users> findByGenderAndLikeColor(Gender gender, String color);

    // 범위 검색
    // 최근 7일 이내 자료를 읽어오고 싶을 때(오늘 빼고)
    List<Users> findByCreatedAtBetween(LocalDateTime start, LocalDateTime end);
}

```

```sql
SELECT * FROM users WHERE name = ?
SELECT * FROM users WHERE like_color = ? LIMIT 3
SELECT * FROM users WHERE gender = ? AND like_color = ?
```
