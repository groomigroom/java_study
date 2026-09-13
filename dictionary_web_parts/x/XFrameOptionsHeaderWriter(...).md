* 역할: XFrameOptionsHeaderWriter(...)는 HTTP 응답 헤더에 X-Frame-Options 문구를 자동으로 작성해 주는 클래스를 생성합니다.
* 설명: X-Frame-Options는 브라우저에게 "우리 웹페이지를 다른 사이트의 <iframe> 이나 <frame> 태그 안에 집어넣어 보여줄 수 있는지"를 결정하게 하는 보안 헤더입니다. 

# new XFrameOptionsHeaderWriter(XFrameOptionsHeaderWriter.XFrameOptionsMode.SAMEORIGIN) 에서

------

* 역할: XFrameOptionsMode를 X-Frame-Options 헤더의 값을 SAMEORIGIN으로 지정하는 열거형(Enum) 상수입니다.
* 설명: SAMEORIGIN은 "도메인이 같은(동일한 출처의) 페이지 내에서만 우리 웹페이지를 iframe으로 넣을 수 있다"는 뜻입니다. 예를 들어 내 사이트 주소가 example.com이라면, ://example.com 안에서는 ://example.com를 iframe으로 띄울 수 있지만, 해커의 사이트인 hacker.com에서는 내 사이트를 iframe으로 띄울 수 없게 차단합니다. 
