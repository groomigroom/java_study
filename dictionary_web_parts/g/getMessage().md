자바 스프링 부트(Spring Boot)에서 getMessage()는 문맥에 따라 크게 두 가지 의미로 사용됩니다.
가장 흔하게 쓰이는 1) 예외(Exception)의 에러 메시지를 가져오는 기능과, 검증(Validation) 흐름에서 쓰이는 2) 다국어 메시지 설정 파일에서 문구를 가져오는 기능이 있습니다.
------------------------------
## 1. 예외(Exception) 객체의 에러 메시지 가져오기
가장 일반적인 자바 표준 기능입니다. try-catch문이나 @ExceptionHandler를 이용해 예외를 처리할 때, 발생한 예외 내부에 저장된 구체적인 에러 원인 메시지를 반환합니다.

@RestControllerAdvicepublic class GlobalExceptionHandler {

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handleIllegalArgument(IllegalArgumentException ex) {
        // ex.getMessage()는 예외 생성 시 던진 메시지(예: "잘못된 ID입니다.")를 리턴합니다.
        return ResponseEntity.badRequest().body(ex.getMessage());
    }
}


* 언제 쓰나요? 예외가 왜 발생했는지 로그를 남기거나, 클라이언트에게 에러 원인을 텍스트로 전달할 때 사용합니다.

------------------------------
## 2. MessageSource를 통한 다국어/국제화(i18n) 메시지 가져오기
스프링 프레임워크가 제공하는 MessageSource 인터페이스의 getMessage() 메서드입니다. 하드코딩된 문자열 대신, messages.properties 같은 설정 파일에서 사용자 언어 환경(Locale)에 맞는 텍스트를 동적으로 가져올 때 사용합니다.
## 사용 구조

String getMessage(String code, @Nullable Object[] args, Locale locale);

## 실제 사용 예시
1. 메시지 파일 설정 (src/main/resources/messages.properties)

welcome.message=안녕하세요, {0}님! 방문을 환영합니다.

2. 자바 컨트롤러나 서비스에서 사용

@Autowiredprivate MessageSource messageSource;

@GetMapping("/welcome")public String welcome(@RequestParam String name, Locale locale) {
    // welcome.message 코드를 찾고, {0} 자리에 name 변수를 채워 넣습니다.
    String text = messageSource.getMessage("welcome.message", new Object[]{name}, locale);
    return text; // 결과: "안녕하세요, 홍길동님! 방문을 환영합니다."

------------------------------
## 3. 앞서 질문하신 BindingResult와 연관된 getMessage()
이전 질문의 검증(Validation) 오류 흐름에서도 FieldError나 ObjectError 객체 내부의 getMessage()를 호출할 수 있습니다.
스프링이 검증 오류를 잡은 뒤 errors.properties를 매칭하여 최종적으로 완성한 한국어/영어 에러 문구 자체를 꺼낼 때 사용합니다.

if (bindingResult.hasErrors()) {
    for (ObjectError error : bindingResult.getGlobalErrors()) {
        // errors.properties에 등록된 최종 에러 메시지를 꺼냄
        System.out.println(error.getDefaultMessage()); 
    }
}

------------------------------
## 요약 비교 테이블

| 사용 문맥 | 호출 대상 | 기능 | 반환값 예시 |
|---|---|---|---|
| 예외 처리 | Exception 객체 | 발생한 오류의 자체 메시지 반환 | "NullPointerException이 발생했습니다." |
| 다국어/메시지 관리 | MessageSource 빈 | messages.properties에서 해당 언어 문구 매핑 | "안녕하세요, 관리자님!" |
| 검증(Validation) | FieldError 객체 등 | 검증 실패 원인 메시지 반환 | "상품 가격은 10,000원 이상이어야 합니다." |

현재 코드를 작성하시면서 어떤 클래스나 객체 뒤에 .getMessage()를 붙여서 사용하고 계시나요? 혹은 에러가 발생한 전체 코드 문맥을 조금만 보여주시면 정확히 어떤 기능을 하는지 짚어드리겠습니다.
궁금하신 getMessage()가:

* catch(Exception e) 안의 e.getMessage() 인가요?
* MessageSource가 제공하는 국제화 기능인가요?
* 검증 오류(BindingResult) 결과에서 메시지를 뽑아내는 과정인가요?


