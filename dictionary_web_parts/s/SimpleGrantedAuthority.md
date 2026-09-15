import org.springframework.security.core.authority.SimpleGrantedAuthority;

스프링 부트(Spring Security)에서 SimpleGrantedAuthority는 "사용자에게 부여된 권한(Authority)을 문자열 형태로 단순하게 표현하는 클래스"입니다.
이전 답변에서 살펴보았던 "ROLE_USER", "ROLE_ADMIN" 같은 권한 텍스트를 스프링 시큐리티가 읽을 수 있는 진짜 '권한 객체'로 포장해 주는 상자라고 이해하시면 됩니다.
------------------------------
## 🔍 왜 그냥 문자열("ROLE_USER")을 안 쓰고 이 클래스를 쓰나요?
스프링 시큐리티는 보안의 유연성을 위해 권한을 검사할 때 String 타입이 아니라, GrantedAuthority라는 인터페이스 타입만 인식하도록 설계되어 있습니다.
하지만 매번 인터페이스를 직접 구현(implements)해서 클래스를 만드는 것은 번거롭기 때문에, 스프링 시큐리티가 "문자열 하나만 집어넣으면 권한 객체로 뚝딱 만들어지는 편리한 구현체"를 미리 만들어 둔 것이 바로 SimpleGrantedAuthority입니다.
------------------------------
## 💻 코드로 보는 역할
보통 UserDetailsService에서 DB의 유저 권한 정보를 시큐리티에게 넘겨줄 때 아래와 같이 사용합니다.

// 1. 우리가 정의한 Enum에서 문자열 값을 가져옵니다. (값: "ROLE_USER")
String roleValue = UserRole.USER.getValue(); 
// 2. 스프링 시큐리티가 알아들을 수 있게 SimpleGrantedAuthority 상자에 담습니다.
SimpleGrantedAuthority authority = new SimpleGrantedAuthority(roleValue);
// 3. 시큐리티가 사용하는 권한 리스트에 추가합니다.
List<GrantedAuthority> authorities = new ArrayList<>();
authorities.add(authority);
// 4. 마지막으로 시큐리티의 User 객체에 담아서 반환합니다.return new User(username, password, authorities);

------------------------------
## 💡 요약하자면

* 기능: "ROLE_USER"라는 텍스트를 스프링 시큐리티용 권한 객체로 변환해 줌.
* 이유: 시큐리티 시스템이 사용자의 권한 목록을 검사하고 페이지 접근을 승인/차단할 때, GrantedAuthority 규격의 객체여야만 올바르게 인식하기 때문입니다.


