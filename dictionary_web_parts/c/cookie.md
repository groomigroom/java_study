# 쿠키 데이터 구성

## name=value;
* name => 쿠키 이름
* value => 쿠키 값

## Expires=[Date];
* 쿠키의 유효 시간

## Domain=[Domain];
* 쿠키를 필요로 하는 웹 서버 도메인

## Path=[Path];
* 쿠키를 필요로 하는 웹 페이지 경로명

## [Secure];
* 암호화한 경우에만 쿠키 전송 가능

## [HttpOnly];
* HTTP 통신 외에 다른 통신으로 쿠키 전송 불허

## [SameSite=None|Strict|None]
* 크로스 사이트로 전송하는 요청의 경우 쿠키 전송에 제한
