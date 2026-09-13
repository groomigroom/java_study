import org.springframework.security.core.userdetails.UserDetailsService;

스프링 부트(Spring Boot) 보안의 핵심인 스프링 시큐리티(Spring Security)에서 UserDetailsService는 "로그인할 때 입력한 아이디(username)를 가지고 회원 정보를 데이터베이스(DB)에서 찾아오는 인터페이스"를 의미합니다.
간단히 말해, "스프링 시큐리티가 로그인 검증을 할 수 있도록 DB에서 유저 정보를 긁어다 주는 심부름꾼"이라고 이해하시면 가장 정확합니다.
------------------------------
## 🔍 핵심 역할: 어떤 흐름으로 작동하나요?
사용자가 로그인 폼에 아이디(Username)와 비밀번호(Password)를 입력하고 로그인 버튼을 누르면 다음과 같은 일이 일어납니다.

   1. 시큐리티가 아이디 접수: 스프링 시큐리티가 사용자가 입력한 아이디를 가로챕니다.
   2. UserDetailsService에게 요청: 시큐리티가 UserDetailsService에게 *"이 아이디를 가진 회원 정보 좀 DB에서 찾아서 가져와 봐"*라고 시킵니다.
   3. DB 조회 후 반환: UserDetailsService는 DB에서 회원 정보를 조회한 뒤, 시큐리티가 이해할 수 있는 규격 형태인 UserDetails 객체로 변환하여 시큐리티에게 돌려줍니다.
   4. 비밀번호 검증: 정보를 넘겨받은 스프링 시큐리티가 내부적으로 사용자가 입력한 비밀번호와 DB에서 가져온 암호화된 비밀번호가 일치하는지 자동으로 검증합니다.

------------------------------
## 💻 실제 코드로 보면 어떻게 생겼나요?
UserDetailsService는 자바의 인터페이스(Interface)이며, 내부에 단 하나의 메서드만 가지고 있습니다.

public interface UserDetailsService {
    // 유저네임(아이디)을 주면 유저 상세정보(UserDetails)를 리턴하는 메서드
    UserDetails loadUserByUsername(String username) throws UsernameNotFoundException;
}

따라서 개발자는 이 인터페이스를 구현(implements)하는 클래스를 직접 만들어서 사용해야 합니다.
## 🛠️ 실무 구현 예시 (점프 투 스프링부트 스타일)
질문자님이 이전에 보여주신 UserRole과 연계하여 보통 아래와 같이 구현합니다.

@RequiredArgsConstructor
@Servicepublic class SecurityUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository; // DB 조회를 위한 리포지토리

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // 1. DB에서 아이디로 유저를 찾음
        SiteUser siteUser = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("사용자를 찾을 수 없습니다."));

        // 2. 유저의 권한(Role)을 스프링 시큐리티 규칙에 맞게 리스트로 변환 (ADMIN, USER 등)
        List<GrantedAuthority> authorities = new ArrayList<>();
        if ("admin".equals(username)) {
            authorities.add(new SimpleGrantedAuthority(UserRole.ADMIN.getValue())); // "ROLE_ADMIN"
        } else {
            authorities.add(new SimpleGrantedAuthority(UserRole.USER.getValue()));  // "ROLE_USER"
        }

        // 3. 시큐리티가 인증에 사용할 수 있도록 UserDetails 객체(시큐리티가 제공하는 기본 User 클래스)를 만들어 반환
        return new User(siteUser.getUsername(), siteUser.getPassword(), authorities);
    }
}

------------------------------
## 💡 요약하자면

* 하는 일: 사용자가 입력한 username으로 DB에서 사용자 정보(UserDetails)를 조회하는 일.
* 왜 쓰나요?: 스프링 시큐리티는 회원 정보가 MySQL에 있는지, Oracle에 있는지, 메모리에 있는지 알지 못합니다. 개발자가 UserDetailsService를 구현해 둠으로써 시큐리티와 우리 서비스의 DB를 연결해 주는 다리 역할을 담당하게 됩니다.

------------------------------
현재 만들고 계신 로그인 기능에서 loadUserByUsername 메서드를 구현하는 중이신가요, 아니면 시큐리티 설정 클래스(SecurityConfig)에 연결하는 중이신가요? 막히는 코드가 있다면 편하게 공유해 주세요!

