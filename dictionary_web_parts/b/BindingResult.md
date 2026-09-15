import org.springframework.validation.BindingResult;

자바 스프링 부트에서 BindingResult는 데이터 검증(Validation) 및 바인딩의 결과를 담는 인터페이스입니다.
## 1. BindingResult가 의미하는 것

* 결과 저장소: 클라이언트의 입력값이 DTO 객체에 올바르게 들어갔는지, @Valid 검증을 통과했는지에 대한 모든 결과와 에러 정보를 담습니다.
* 프로그램 흐름 제어: 검증 오류가 발생해도 서버가 즉시 예외를 던지며 멈추지 않고, 컨트롤러 메서드 내부에서 개발자가 직접 에러를 확인하고 예외 처리 로직을 작성할 수 있게 해줍니다.
* 위치 제약: 컨트롤러 메서드에서 반드시 검증할 객체(예: @Valid UserDto userDto) 바로 다음에 위치해야 합니다.

## 2. 속해있는 의존성 (Dependency)
BindingResult는 스프링 프레임워크 자체의 핵심 기능에 포함되어 있습니다. 따라서 별도의 Validation 라이브러리 없이 스프링 부트 웹 프로젝트라면 기본적으로 제공됩니다.

* 의존성 이름: Spring Boot Starter Web
* Gradle 추가 코드:

implementation 'org.springframework.boot:spring-boot-starter-web'

* Maven 추가 코드:

<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-web</artifactId>
</dependency>

* 주의: @Valid나 @NotNull 같은 검증 어노테이션을 사용하려면, 이전 답변에서 안내해 드린 spring-boot-starter-validation 의존성이 함께 필요합니다.

------------------------------
현재 개발 중이신 화면이 API 형태(JSON 리턴)인가요, 아니면 타임리프(Thymeleaf) 등을 쓰는 HTML 화면인가요? 환경을 말씀해 주시면 상황에 맞는 BindingResult 활용 코드 예시를 보여드리겠습니다.


bindingResult.hasErrors()
는 오류가 있는 경우인지 검증하는거


# bindingResult.rejectValue()


자바 스프링 부트(Spring Boot)에서 bindingResult.rejectValue()는 컨트롤러로 넘어온 데이터(객체)의 특정 필드에 직접 검증 오류(ValidationError)를 등록할 때 사용하는 메서드입니다. [1, 2] 
쉽게 말해, 사용자가 보낸 입력값에 문제가 있을 때 "이 객체의 이 필드에 이런 에러가 발생했어!"라고 스프링에게 알려주는 역할을 합니다. [1, 3] 
------------------------------
## 1. 왜 사용할까요? 🤔
원래 스프링에서 필드 에러를 등록하려면 아래와 같이 FieldError 객체를 직접 생성해서 주입해야 했습니다. [4] 

// 기존 방식: 코드가 복잡하고 넘겨야 할 인자가 많음
bindingResult.addError(new FieldError("item", "price", item.getPrice(), false, null, null, "가격은 1,000원 이상이어야 합니다."));

하지만 BindingResult는 이미 자신이 검증해야 할 대상 객체가 무엇인지 알고 있습니다. rejectValue()를 사용하면 이 정보를 바탕으로 FieldError를 내부에서 대신 생성해 주므로, 코드가 훨씬 간결해집니다. [1, 5] 

// rejectValue 사용: 훨씬 깔끔하고 직관적임
bindingResult.rejectValue("price", "range", "가격은 1,000원 이상이어야 합니다.");

------------------------------
## 2. 파라미터 구조 🛠️
가장 많이 쓰이는 형태의 파라미터 구성은 다음과 같습니다. [2] 

bindingResult.rejectValue(String field, String errorCode, Object[] errorArgs, String defaultMessage);


* 
* field: 오류가 발생한 객체의 필드명 (예: "price", "email").
* errorCode: 에러 메시지를 찾기 위한 키값(코드). 스프링의 MessageCodesResolver와 결합하여 errors.properties 파일에 정의된 메시지를 동적으로 매핑합니다 (예: "range", "required").
* errorArgs: 에러 메시지 내부에서 사용할 인자(치환값) 배열 (없으면 null).
* defaultMessage: 정의된 에러 코드가 없을 때 화면에 보여줄 기본 메시지. [1, 2, 6, 7, 8] 
* 

------------------------------
## 3. 실제 컨트롤러 사용 예시 💻

@PostMapping("/add")public String addItem(@ModelAttribute Item item, BindingResult bindingResult) {
    
    // 검증 로직 1: 상품 이름이 비어있는지 확인
    if (!StringUtils.hasText(item.getItemName())) {
        bindingResult.rejectValue("itemName", "required", "상품 이름은 필수입니다.");
    }
    
    // 검증 로직 2: 가격 범위 확인
    if (item.getPrice() == null || item.getPrice() < 1000) {
        bindingResult.rejectValue("price", "range", new Object[]{1000}, "최소 1,000원 이상 입력하세요.");
    }

    // 에러가 하나라도 있으면 다시 등록 폼 뷰로 이동
    if (bindingResult.hasErrors()) {
        return "itemForm"; 
    }

    itemService.save(item);
    return "redirect:/items";
}

## 4. 에러 메시지 파일(errors.properties)과의 연동 원리 ⚙️
rejectValue("price", "range", ...)를 호출하면 스프링은 내부적으로 다음과 같은 규칙(우선순위)으로 메시지 파일에서 텍스트를 찾습니다. [4, 7] 

   1. range.item.price (에러코드.객체명.필드명)
   2. range.price (에러코드.필드명)
   3. range.java.lang.Integer (에러코드.필드타입)
   4. range (에러코드)

개발자가 세밀하게 메시지를 제어할 수 있도록 도와주는 스프링만의 편리한 기능입니다. 타임리프(Thymeleaf) 같은 뷰 템플릿과 연동하면 화면의 해당 필드 옆에 오류 메시지를 깔끔하게 띄워줄 수 있습니다. [2, 4] 
------------------------------
혹시 rejectValue()와 함께 자주 쓰이는 reject()(글로벌 에러 처리)의 차이점이 궁금하신가요, 아니면 errors.properties 설정을 연동하는 방법을 자세히 알고 싶으신가요?

[1] [https://amenable.tistory.com](https://amenable.tistory.com/100)
[2] [https://velog.io](https://velog.io/@gudwn357/spring8)
[3] [https://thaud153.tistory.com](https://thaud153.tistory.com/50)
[4] [https://lordofkangs.tistory.com](https://lordofkangs.tistory.com/515)
[5] [https://rebugs.tistory.com](https://rebugs.tistory.com/628)
[6] [https://dingdingmin-back-end-developer.tistory.com](https://dingdingmin-back-end-developer.tistory.com/entry/Springboot-MVC-%ED%8C%8C%ED%97%A4%EC%B9%98%EA%B8%B010-Validation-%EC%9C%A0%ED%9A%A8%EC%84%B1-%EA%B2%80%EC%A6%9D)
[7] [https://nomoreft.tistory.com](https://nomoreft.tistory.com/66)
[8] [https://progyun.tistory.com](https://progyun.tistory.com/234)

bindingResult.rejectValue()에서 rejectValue라는 메서드 이름 자체의 영단어 의미를 직역하면 "값(Value)을 거절하다(Reject)"라는 뜻입니다.
스프링 프레임워크의 맥락에서 이 단어가 가지는 의미를 쪼개서 설명해 드릴게요.
------------------------------
## 1. Reject (거절하다/불합격 처리하다)
사용자가 폼 화면이나 API를 통해 보낸 데이터는 스프링이 자바 객체에 바인딩(주입)합니다.
그 후 개발자가 코드로 검증(Validation)을 하다가 조건에 맞지 않는 데이터를 발견했을 때, "이 데이터는 문제가 있어서 우리 시스템에서 받아들일 수 없어(불합격이야)!" 하고 거절(Reject) 선언을 하는 것입니다.
## 2. Value (값 / 특정 필드의 값)
그냥 객체 전체가 싫다는 게 아니라, 객체 안의 특정 '필드 값'에 문제가 있다고 콕 집어서 지정하는 것입니다.
예를 들어 회원가입 데이터 전체(User 객체)를 거절하는 게 아니라, 그 안의 password라는 특정 필드 값만 거절하고 싶을 때 사용합니다.
------------------------------
## 한 줄 요약 💡

"네가 보낸 이 객체 안에서, 내가 지정한 [특정 필드의 값]은 유효하지 않으니 검증 탈락(Reject)이야!" 라고 스프링 시스템에 공식적으로 등록하는 행위를 의미합니다.


* 거절당한 값은 에러 목록(FieldError)에 기록되며, 스프링은 이 거절된 정보를 바탕으로 화면에 에러 메시지를 띄우거나, 잘못 입력했던 값을 지우지 않고 입력창에 그대로 남겨두어 사용자가 수정할 수 있도록 돕습니다.

혹시 단어 뜻 외에 스프링 내부 동작 원리나 특정 파라미터가 왜 필요한지 더 궁금한 부분이 있으신가요?

# bindingResult.rejectValue(필드명, 오류 코드, 오류 메시지)
