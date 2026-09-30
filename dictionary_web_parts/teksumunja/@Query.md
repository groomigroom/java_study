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
