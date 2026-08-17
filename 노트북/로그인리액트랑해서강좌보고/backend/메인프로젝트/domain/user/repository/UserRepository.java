패키지 뭐라 뜨게

JpaRepository<UserEntity 이거 두가지 뜨게;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
    Boolean existsByUsername (String username);
}
