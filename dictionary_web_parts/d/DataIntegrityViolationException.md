* import org.springframework.dao.DataIntegrityViolationException;

스프링 부트(Spring Boot)에서 DataIntegrityViolationException은 "데이터 무결성 제약 조건(Data Integrity Constraint)을 위반했다"는 것을 의미합니다.
쉽게 말해, 데이터베이스(DB)에 값을 저장하거나 수정하려고 할 때, DB가 세워둔 규칙(제약 조건)을 깨뜨려서 발생한 에러입니다.
가장 자주 발생하는 원인들과 해결법을 그룹별로 정리해 드립니다.
## 1. 가장 흔한 발생 원인 4가지

* 중복된 값 입력 (Unique 제약 조건 위반)
* 상황: 이미 가입된 이메일이나 아이디(ID)로 다시 회원가입을 시도할 때 발생합니다.
   * 예시: User 테이블의 email 컬럼이 UNIQUE로 설정되어 있는데, 동일한 이메일을 넣으려고 한 경우.
* 빈 값(Null) 입력 (Not Null 제약 조건 위반)
* 상황: DB 설계상 반드시 값이 들어가야 하는 필수 컬럼인데, null이나 빈 값을 넣고 저장하려 할 때 발생합니다.
   * 예시: 필수 약관 동의 여부나 비밀번호 필드가 비어있는 상태로 DB에 저장을 시도한 경우.
* 존재하지 않는 부모 데이터 참조 (Foreign Key 외래키 제약 조건 위반)
* 상황: 연관 관계가 있는 데이터를 저장할 때, 참조하려는 대상이 존재하지 않으면 발생합니다.
   * 예시: 게시글(Post)을 저장할 때, 존재하지 않는 회원 번호(User ID)를 작성자 ID로 지정한 경우.
* 데이터 길이 초과 (Length 제한 위반)
* 상황: DB 컬럼이 수용할 수 있는 글자 수나 크기보다 더 큰 데이터를 넣으려고 할 때 발생합니다.
   * 예시: DB에는 VARCHAR(10)으로 10글자 제한을 걸어두었는데, 20글자짜리 문자열을 저장하려 한 경우.

------------------------------
## 2. 스프링 부트에서 이 에러의 특징

* DB 에러의 변환 체인: 실제로는 MySQL, Oracle 같은 DB가 SQLException을 먼저 던집니다. 스프링은 개발자가 특정 DB 기술에 종속되지 않도록, 이 에러를 추상화된 DataIntegrityViolationException으로 변환해서 던져줍니다.
* HTTP 상태 코드: 이 에러가 서버 내부에서 처리되지 않고 사용자에게 그대로 방출되면, 스프링 부트는 기본적으로 HTTP 500 (Internal Server Error)를 반환합니다. 하지만 실제 원인은 유저의 잘못된 입력(중복 아이디 등)인 경우가 많으므로, HTTP 400 (Bad Request) 등으로 예외 처리를 해주는 것이 좋습니다.

------------------------------
## 3. 실무에서의 해결 및 예외 처리 방법 (ControllerAdvice)
사용자에게 500 에러 페이지를 보여주지 않으려면, @RestControllerAdvice를 이용해 이 예외를 붙잡아 깔끔한 에러 메시지로 바꿔주어야 합니다.

@RestControllerAdvicepublic class GlobalExceptionHandler {

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<String> handleDataIntegrityViolation(DataIntegrityViolationException e) {
        // 에러 로그 기록
        // 어떤 제약조건이 깨졌는지 e.getMessage()나 e.getCause()로 확인할 수 있습니다.
        
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body("데이터 제약 조건을 위반했습니다. 입력 값을 확인해주세요. (예: 중복된 데이터, 필수값 누락 등)");
    }
}

------------------------------
현재 개발 중이신 화면(예: 회원가입 폼)에서 정확히 어떤 행위를 할 때 이 에러가 발생했나요? 에러 로그의 Caused by: 아랫부분을 보여주시면 어떤 제약 조건이 깨진 건지 정확히 진단해 드리겠습니다.

