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

  @Column (name = "password", nullable = false)
  private String password;

  @Column (name = "is_lock", nullable = false)
  private Boolean isLock;

  @Column (name = "is_social", nullable = false)
  private Boolean isSocial;

  @Enumerated(EnumType.STRING)
  @Column(name = "social_provider_type")
  private SocialProviderType socialProviderType;

  @Enumerated(EnumType.STRING)
  @Column(name = "role_type", nullable = false)
  private UserRoleType roleType;

  @Column(name = "nickname")
  private String nickname;

  @Column (name = "email)
  private String email;

  @CreatedDate
  @Column (name = "created_date", updatable = false)
  private LocalDateTime createdDate;

  @LastModifiedDate
  @Column (name = "updated_date")
  private LocalDateTime updatedDate;
}
