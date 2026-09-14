```
[파일명:build.gradle]
(... 생략 ...)
dependencies {
    (... 생략 ...)
    implementation 'org.commonmark:commonmark:0.21.0'
}
(... 생략 ...)
```
commonmark는 이와 같이 0.21.0이라는 버전을 지정해야 한다. 왜냐하면 스프링 부트의 라이브러리 관리 방식 때문이다. 스프링 부트가 내부적으로 관리하는 라이브러리에 포함되면 버전 정보가 필요 없고 포함되지 않으면 버전 정보가 필요하다. 
