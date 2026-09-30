```java
public interface MemberRepository extends JpaRepository<Member, Long> {
    @Query("SELECT m FROM Member m WHERE m.name = :name")
    List<Member> findByName(@Param("name") String name);
}
```

```java
@Query(value = "SELECT * FROM member WHERE name = :name", nativeQuery = true)
List<Member> findByNameNative(@Param("name") String name);
```


두 코드의 핵심 차이는 **JPQL을 사용하느냐, 실제 DB SQL(Native Query)을 사용하느냐**입니다.

 ## 1\. JPQL 방식

```
@Query("SELECT m FROM Member m WHERE m.name = :name")
List<Member> findByName(@Param("name") String name);
```

 여기서 `Member`와 `m.name`은 **DB 테이블/컬럼이 아니라 JPA 엔티티와 엔티티 필드**입니다.

 예를 들어:

```
@Entity
public class Member {
    @Id
    private Long id;

    private String name;
}
```

 라면 JPQL에서는:

```
SELECT m FROM Member m WHERE m.name = :name
```

 처럼 **Java 객체 기준으로 작성**합니다.

 JPA가 이를 DB에 맞는 SQL로 변환합니다. 예를 들어 MySQL이라면 대략:

```
SELECT *
FROM member
WHERE name = ?
```

 형태로 실행됩니다.

 ### 장점

 - DB에 종속적이지 않음
- 엔티티 중심으로 작성
- 테이블명이 변경되어도 `@Table` 매핑이 올바르면 JPQL을 그대로 사용할 수 있음
- JPA의 연관관계 등을 활용하기 좋음

---

 ## 2\. Native Query 방식

```
@Query(
    value = "SELECT * FROM member WHERE name = :name",
    nativeQuery = true
)
List<Member> findByNameNative(@Param("name") String name);
```

 `nativeQuery = true`이므로 `"SELECT * FROM member..."`를 **그대로 DB에 SQL로 전달**합니다.

 여기서는:

```
member
name
```

 이 실제 **DB 테이블명과 컬럼명**이어야 합니다.

 즉, JPQL과 달리:

```
Member
m.name
```

 이 아니라

```
member
name
```

 을 사용합니다.

---

 ## 가장 중요한 차이

 |  | JPQL | Native SQL |
| --- | --- | --- |
| 기준 | 엔티티 | DB 테이블 |
| `Member` | 엔티티 이름 | ❌ |
| `m.name` | 엔티티 필드 | ❌ |
| `member` | ❌ | 테이블 |
| `name` | ❌ | 컬럼 |
| DB 종속성 | 낮음 | 높음 |
| SQL 직접 작성 | X | O |
| 복잡한 DB 전용 SQL | 제한적 | 가능 |
| JPA 연관관계 활용 | 좋음 | 직접 SQL 작성 필요 |

### 예를 들어 테이블 이름이 바뀐다면

```
@Entity
@Table(name = "members")
public class Member {
    ...
}
```

 JPQL은:

```
@Query("SELECT m FROM Member m WHERE m.name = :name")
```

 그대로 사용할 수 있습니다.

 하지만 Native Query는:

```
@Query(
    value = "SELECT * FROM member WHERE name = :name",
    nativeQuery = true
)
```

 `member`가 실제 테이블명이라면 수정해야 합니다.

```
SELECT * FROM members WHERE name = :name
```

---

 ## 그럼 보통 뭘 사용하나?

 단순 조회라면 보통 **JPQL 또는 Spring Data JPA의 메서드 쿼리**를 사용합니다.

 사실 이 경우에는 `@Query`조차 필요 없습니다.

```
public interface MemberRepository extends JpaRepository<Member, Long> {

    List<Member> findByName(String name);
}
```

 Spring Data JPA가 알아서 이름을 분석해서 조회 쿼리를 만들어줍니다.

 즉, 일반적인 경우에는:

```
findByName()
    ↓
JPQL 직접 작성
    ↓
Native SQL
```

 순으로 갈수록 **DB에 가까워진다**고 생각하면 이해하기 쉽습니다.

 **한 줄 요약:**\
 `@Query("SELECT m FROM Member...")`는 **Java 엔티티 기준의 JPQL**, `nativeQuery = true`는 **실제 DB 테이블/컬럼 기준의 SQL**입니다.
