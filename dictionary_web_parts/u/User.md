import org.springframework.security.core.userdetails.User;


import org.springframework.security.core.userdetails.User;는 스프링 시큐리티가 제공하는 "로그인 인증용 사용자 정보(UserDetails)의 기본 구현체 클래스"를 가져오겠다는 의미입니다.
우리가 만든 서비스의 DB 테이블(예: SiteUser 엔티티)과 스프링 시큐리티 시스템 사이에서 "시큐리티 전용 규격으로 포장된 유저 객체" 역할을 합니다.
------------------------------
## 🔍 왜 이 클래스가 필요한가요?
스프링 시큐리티는 인증을 진행할 때 우리가 만든 SiteUser 클래스의 내부 구조(필드명이 username인지 userId인지 등)를 알지 못합니다. 시큐리티는 오직 UserDetails라는 규격(인터페이스)만 이해할 수 있습니다.
이 규격을 맞추기 위해 개발자가 직접 클래스를 구현해도 되지만, 스프링 시큐리티가 아이디, 비밀번호, 권한 리스트만 넣으면 작동하는 완성형 클래스인 User를 미리 만들어 두었기에 이를 가져다 쓰는 것입니다.
------------------------------
## 💻 코드로 보는 역할과 주의점
보통 UserDetailsService 구현체 내부에서 다음과 같이 인증 완료용 유저 객체를 만들어 리턴할 때 사용합니다.

import org.springframework.security.core.userdetails.User; // 👈 바로 이 클래스!import org.springframework.security.core.userdetails.UserDetails;
// ... (생략)

@Overridepublic UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    SiteUser siteUser = userRepository.findByUsername(username)
            .orElseThrow(() -> new UsernameNotFoundException("유저 없음"));

    List<GrantedAuthority> authorities = new ArrayList<>();
    authorities.add(new SimpleGrantedAuthority(siteUser.getRole().getValue()));

    // DB에서 꺼낸 아이디, 암호화된 비밀번호, 권한 리스트를 넣어서 시큐리티용 User 객체를 생성
    return new User(siteUser.getUsername(), siteUser.getPassword(), authorities); 
}

## ⚠️ 이름 충돌(Name Collision) 주의하기!
프로젝트를 진행하다 보면 내가 직접 만든 회원 테이블 엔티티 클래스명도 User이고, 스프링 시큐리티 클래스명도 User인 경우가 많습니다.
만약 두 클래스를 한 파일에서 동시에 쓰면 자바가 헷갈려하므로, 보통 내가 만든 엔티티 클래스명을 SiteUser나 Member로 다르게 짓거나, 시큐리티의 User를 사용할 때 패키지 경로를 전체 다 적어주는 방식으로 해결합니다.

// 이름이 겹칠 때 패키지 명을 전부 적어 표현하는 예시return new org.springframework.security.core.userdetails.User(username, password, authorities);

------------------------------
## 💡 요약하자면

* 정체: 스프링 시큐리티가 로그인 검증 시 사용하는 표준 유저 정보 클래스입니다.
* 역할: 우리가 DB에서 조회한 회원 정보(아이디, 패스워드, 권한)를 시큐리티가 알아들을 수 있는 형태로 규격을 맞춰 포장하는 상자입니다.

------------------------------
현재 로그인 처리를 완료하고 로그인한 사용자의 정보(예: 현재 로그인한 사람의 닉네임이나 ID)를 컨트롤러나 서비스에서 꺼내 쓰는 작업을 구상 중이신가요? User 객체를 통해 세션 정보를 다루는 방법이나 다음 단계에 대해 궁금한 점이 있다면 언제든 말씀해 주세요!

