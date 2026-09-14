스프링 부트(스프링 시큐리티)에서 Principal은 "현재 로그인(인증)된 사용자의 정보를 담고 있는 자바의 표준 인터페이스"입니다.
쉽게 말해, 컨트롤러나 서비스단에서 "지금 이 요청을 보낸 사용자가 대체 누구지?"를 알고 싶을 때 꺼내 쓰는 '로그인 유저 신분증'이라고 이해하시면 됩니다 [User].
------------------------------
## 🔍 핵심 역할: 어떤 특징이 있나요?

   1. 현재 로그인된 유저 확인: 로그인하지 않은 상태(비로그인)라면 Principal 객체는 null이 됩니다. 반대로 객체가 존재한다면 "누군가 로그인에 성공했구나"를 알 수 있습니다.
   2. 자바 표준 규격: java.security.Principal에 속해 있는 인터페이스로, 스프링에만 종속된 것이 아니라 자바 자체에서 인증된 주체(Subject)를 나타낼 때 쓰는 표준 규격입니다 [User].

------------------------------
## 💻 코드로 보는 활용 방법
가장 흔하게 쓰이는 곳은 컨트롤러(Controller) 메서드입니다.
스프링 부트는 컨트롤러 매개변수에 Principal을 적어두기만 하면, 현재 로그인한 사용자의 정보를 알아서 주입해 줍니다.
## 🛠️ 실무 활용 예시 (질문, 답변 작성 시 작성자 저장하기)
질문자님이 구현 중이신 '점프 투 스프링부트' 등의 프로젝트에서 게시글을 저장할 때 보통 아래와 같이 사용합니다.

import java.security.Principal; // 👈 자바 표준 인터페이스 임포트

@Controllerpublic class QuestionController {

    @PostMapping("/question/create")
    public String questionCreate(@RequestParam String content, Principal principal) {
        
        // 1. 로그인한 유저의 아이디(username)를 꺼냅니다.
        String username = principal.getName(); 
        
        // 2. 꺼낸 아이디를 가지고 DB에서 실제 유저 엔티티를 조회합니다.
        SiteUser siteUser = this.userService.getUser(username);
        
        // 3. 질문을 생성할 때 작성자(siteUser) 정보도 함께 저장합니다.
        this.questionService.create(content, siteUser);
        
        return "redirect:/question/list";
    }
}

------------------------------
## 💡 주요 메서드: .getName()
Principal 인터페이스는 매우 단순하게 설계되어 있어서 꺼낼 수 있는 정보가 많지 않습니다. 대표적으로 사용하는 메서드는 단 하나입니다.

* principal.getName(): 로그인할 때 사용한 사용자의 아이디(username)를 문자열(String)로 반환합니다.

만약 아이디 외에 이메일, 닉네임, 권한 등 더 많은 정보를 컨트롤러에서 바로 꺼내 쓰고 싶다면, Principal 대신 스프링 시큐리티 전용 어노테이션인 @AuthenticationPrincipal을 사용하여 내가 만든 구체적인 유저 객체(또는 UserDetails)로 매핑 받아 사용할 수도 있습니다.
------------------------------
## 💡 요약하자면

* 정체: 현재 로그인한 사용자를 식별할 수 있게 해주는 자바 표준 신분증 객체입니다 [User].
* 용도: 컨트롤러 메서드의 파라미터로 지정하여 현재 로그인한 사람의 아이디(getName())를 알아내고, 이를 바탕으로 글쓰기/댓글 달기 등의 권한 로직을 처리할 때 사용합니다.

------------------------------
현재 질문이나 답변 엔티티에 작성자(Author) 정보를 연결하는 단계를 진행 중이신가요?
Principal을 사용할 때 로그인을 안 한 유저가 접근하면 NullPointerException 에러가 발생할 수 있는데, 이를 방지하기 위한 화면단 설정(@PreAuthorize)이나 예외 처리에 대해 더 궁금한 점이 있다면 언제든 편하게 말씀해 주세요!

