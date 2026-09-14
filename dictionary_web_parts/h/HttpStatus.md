스프링 부트에서 HttpStatus는 HTTP 요청에 대한 서버의 응답 상태를 나타내는 'HTTP 상태 코드'들을 모아둔 자바의 열거형(Enum) 클래스입니다.
서버가 클라이언트(브라우저나 모바일 앱)의 요청을 처리한 후, "성공했다", "데이터가 없다", "로그인이 필요하다", "서버에 오류가 났다" 등의 결과를 표준화된 숫자로 전달할 때 사용합니다.
## 1. 왜 숫자가 아닌 HttpStatus 객체를 쓰나요?
404나 200 같은 숫자를 자바 코드에 직접 적으면(상수 매직 넘버), 오타가 날 수도 있고 코드의 의미를 한눈에 파악하기 어렵습니다.

* 404 대신 ➡️ HttpStatus.NOT_FOUND
* 200 대신 ➡️ HttpStatus.OK

이처럼 가독성을 높이고 프로그래밍 실수를 줄이기 위해 스프링이 미리 정의해 둔 바구니라고 생각하시면 됩니다.
## 2. 가장 자주 쓰는 주요 HttpStatus 종류

| 상태 코드 | HttpStatus 상수명 | 의미 | 활용 상황 |
|---|---|---|---|
| 200 | HttpStatus.OK | 성공 | 데이터 조회, 수정 등이 정상 처리됨 |
| 201 | HttpStatus.CREATED | 생성됨 | 회원가입, 글쓰기 등 새로운 데이터 생성 성공 |
| 400 | HttpStatus.BAD_REQUEST | 잘못된 요청 | 파라미터 누락, 입력값 형식 오류 |
| 401 | HttpStatus.UNAUTHORIZED | 인증 자격 없음 | 로그인이 필요한 서비스에 비로그인 접근 |
| 403 | HttpStatus.FORBIDDEN | 권한 거부 | 로그인했으나 접근 권한이 없음 (예: 일반 회원이 관리자 페이지 접근) |
| 404 | HttpStatus.NOT_FOUND | 찾을 수 없음 | 요청한 URL이 없거나 데이터베이스에 해당 데이터가 없음 |
| 500 | HttpStatus.INTERNAL_SERVER_ERROR | 서버 내부 오류 | 자바 코드 내부에서 예외(Exception)가 발생해 터짐 |

## 3. 실제 코드 활용 예시 (앞서 보신 예시와의 연계)
방금 질문하셨던 ResponseStatusException과 함께 아래처럼 매개변수로 조합하여 사용합니다.

// "404 상태 코드(HttpStatus.NOT_FOUND)"와 메시지를 함께 실어서 예외를 던집니다.throw new ResponseStatusException(HttpStatus.NOT_FOUND, "게시글이 존재하지 않습니다.");

REST API를 만들 때 응답 전체를 감싸는 ResponseEntity와도 찰떡궁합으로 쓰입니다.

@GetMapping("/check")public ResponseEntity<String> checkStatus() {
    // 클라이언트에게 200 OK 상태 코드와 함께 "정상"이라는 메시지 반환
    return new ResponseEntity<>("정상", HttpStatus.OK); 
}


