@Entity
@EntityListeners(AuditingEntitiyListener.class)
@Table(name = "user_user_entity")
@Getter
@Builder
@NoArgsConstuctor
@AllArgsConstuctor
public class UserEntity {
  @Id
}
