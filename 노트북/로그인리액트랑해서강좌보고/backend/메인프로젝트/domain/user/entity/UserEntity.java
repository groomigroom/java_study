@Entity
@EntityListeners(AuditingEntitiyListener.class)
@Table(name = "user_user_entity")
@Getter
@Builder
@NoArgsConstuctor
@AllArgsConstuctor
public class UserEntity {
  @Id @GenteratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column (name = "username", unique = true, nullable = false, updatable = false)
  private String username;

  
}
