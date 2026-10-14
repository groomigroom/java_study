자바 스프링 부트(Spring Boot)에서 ResponseEntity는 HTTP 요청(Request)에 대한 응답(Response) 데이터와 HTTP 상태 코드를 직접 제어할 수 있게 해주는 클래스입니다.
스프링 MVC에서 REST API를 개발할 때, 클라이언트에게 데이터만 보내는 것이 아니라 "성공했다(200 OK)", "찾을 수 없다(404 Not Found)", "서버 에러다(500 Internal Server Error)" 같은 HTTP 상태 코드와 헤더 정보를 세밀하게 설정하여 반환하기 위해 사용합니다.

💡 주요 구성 요소

ResponseEntity는 크게 3가지 요소를 감싸서(Wrap) 클라이언트에게 보냅니다.
• 상태 코드 (Status): HTTP Status Code (예: 200 OK, 201 Created, 400 Bad Request)
• 헤더 (Headers): HTTP Response Headers (예: Content-Type, Custom-Header)
• 바디 (Body): 클라이언트에게 전달할 실제 데이터 (예: JSON 객체, 문자열 등)

🛠️ 왜 사용할까요? (사용 이유와 예시)

만약 ResponseEntity를 사용하지 않고 일반 객체만 반환하면, 스프링은 무조건 HTTP 상태 코드를 200 OK로만 반환합니다. 하지만 로그인 실패나 데이터 미존재 같은 예외 상황에서는 다른 상태 코드를 주어야 합니다.

1. 일반적인 성공 응답 (200 OK)

java
@GetMapping("/user/{id}")
public ResponseEntity<User> getUser(@PathVariable Long id) {
    User user = userService.findUser(id);
    // 데이터와 함께 200 OK 상태 코드를 반환
    return ResponseEntity.ok(user); 
}
코드를 사용할 때는 주의가 필요합니다.

2. 데이터 생성 성공 응답 (201 Created)

java
@PostMapping("/user")
public ResponseEntity<User> createUser(@RequestBody UserDto userDto) {
    User newUser = userService.register(userDto);
    // 201 Created 상태 코드와 생성된 데이터를 반환
    return new ResponseEntity<>(newUser, HttpStatus.CREATED);
}
코드를 사용할 때는 주의가 필요합니다.

3. 조건에 따른 예외 처리 (404 Not Found)

java
@GetMapping("/item/{id}")
public ResponseEntity<?> getItem(@PathVariable Long id) {
    Item item = itemService.findItem(id);
    
    if (item == null) {
        // 아이템이 없으면 404 Not Found 코드를 반환
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("해당 상품을 찾을 수 없습니다.");
    }
    
    return ResponseEntity.ok(item);
}
코드를 사용할 때는 주의가 필요합니다.

📊 요약

• ResponseEntity는 단순한 데이터뿐만 아니라 HTTP 프로토콜의 규격에 맞는 완전한 응답을 만들 때 사용합니다.
• RESTful API를 설계할 때 클라이언트와의 명확한 소통(성공, 실패 여부 및 이유 전달)을 위해 필수적으로 사용되는 스프링의 핵심 클래스입니다.
추가로 ResponseEntity를 활용한 구체적인 에러 처리 방법이나 빌더 패턴(Builder Pattern) 사용법이 궁금하시면 언제든 말씀해 주세요!
