import org.springframework.web.server.ResponseStatusException;


스프링 부트에서 ResponseStatusException은 "개발자가 원하는 특정 HTTP 상태 코드(Status Code)와 에러 메시지를 클라이언트에게 가장 쉽고 빠르게 반환하기 위해 사용하는 예외 클래스"입니다. [1, 2]
스프링 5(스프링 부트 2.x)부터 도입되었으며, 복잡한 설정 없이 코드 한 줄로 에러 응답을 처리할 수 있다는 장점이 있습니다. [1, 2]
## 1. 주요 기능과 장점

* 직관적인 상태 코드 지정: 404 Not Found, 400 Bad Request 등 원하는 HTTP 상태 코드를 즉시 지정할 수 있습니다. [1, 2]
* 간결함: 과거처럼 @ResponseStatus 어노테이션을 붙인 별도의 커스텀 예외 클래스를 일일이 만들지 않아도 됩니다. [2]
* 유연함: 동일한 예외 상황이라도 발생한 위치나 조건에 따라 서로 다른 상태 코드와 에러 메시지를 다르게 던질 수 있습니다. [2]

## 2. 코드 예시
주로 데이터베이스에서 특정 데이터를 조회했을 때, 값이 존재하지 않는 경우(404) 예외를 발생시키기 위해 자주 사용됩니다. [1, 3]

@GetMapping("/question/{id}")public Question getQuestion(@PathVariable("id") Long id) {
    return this.questionService.findById(id)
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "해당 질문을 찾을 수 없습니다."));
        // 👆 404 상태 코드와 함께 지정한 메시지가 클라이언트에게 전송됩니다.
}

## 3. 기존 방식(@ResponseStatus)과의 비교

| 특징 | 기존 방식 (@ResponseStatus) | 최신 방식 (ResponseStatusException) |
|---|---|---|
| 구현 방식 | 커스텀 예외 클래스를 새로 만들어야 함 [2] | 필요할 때 코드 한 줄로 바로 생성 가능 [2] |
| 결합도 | 예외 클래스와 HTTP 상태 코드가 강하게 결합됨 [2] | 하나의 예외 상황에 다양한 상태 코드 적용 가능 [2] |
| 코드 가독성 | 예외 클래스가 많아져 프로젝트 구조가 복잡해짐 [2] | 예외가 발생하는 로직 안에서 바로 확인 가능 [2] |

## 💡 실무 팁 (application.properties 설정)
기본적으로 스프링 부트는 보안을 위해 클라이언트에게 에러 메시지(message)를 숨깁니다. ResponseStatusException에 넣은 메시지가 화면이나 API 응답에 정상적으로 보이게 하려면 설정 파일에 아래 옵션을 추가해야 합니다.

server.error.include-message=always


