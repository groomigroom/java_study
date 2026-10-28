```
<jee:jndi-lookup> 태그는 전자정부 표준프레임워크(스프링 기반) 설정 XML
파일에서 JNDI(Java Naming and Directory Interface)를 통해 외부 서버에 등록된
자원(리소스)을 찾아 스프링 빈(Bean)으로 등록할 때 사용하는 태그입니다.
[1] (https://www.egovframe.go.kr/wiki/doku.php?id=egovframework:rte2:itl:naming_service)

주요 의미와 역할

• 외부 자원 참조: 주로 톰캣(Tomcat), 웨이브로직(WebLogic), Jeus 등의 WAS 서버에 미리 설정해 둔 데이터소스(DataSource, DB 커넥션 풀)나 메일 서버 등을 코드 수정 없이 XML 설정만으로 가져와 주입(DI)할 때 씁니다. [1] (https://offbyone.tistory.com/252), [2] (https://translate.google.com/translate?u=https://www.quora.com/What-is-the-use-of-JNDI-in-WebLogic&hl=ko&sl=en&tl=ko&client=sge), [3] (https://velog.io/@kkuldangi3/Spring-%ED%86%A0%EB%B9%84%EC%9D%98-%EC%8A%A4%ED%94%84%EB%A7%81-Vol.2-2%EC%9E%A5-%EB%8D%B0%EC%9D%B4%ED%84%B0-%EC%95%A1%EC%84%B8%EC%8A%A4-%EA%B8%B0%EC%88%A0)
• 보안 및 환경 분리: 데이터베이스 접속 정보(URL, ID, PW 등)를 프로젝트 소스코드나 설정 파일에 직접 쓰지 않고, WAS 서버에 안전하게 감춰서 관리할 수 있습니다.
• 사용 예시 개념:xml
<jee:jndi-lookup id="dataSource" jndi-name="jdbc/myDataSource" />
코드를 사용할 때는 주의가 필요합니다.

	• id: 스프링 컨테이너 내에서 사용할 빈(Bean) 이름
	• jndi-name: WAS 서버에 등록된 실제 JNDI 리소스의 이름 [1] (https://www.egovframe.go.kr/wiki/doku.php?id=egovframework:rte2:itl:naming_service)
자세한 내용은 표준프레임워크 포털 eGovFrame에서 확인할 수 있습니다. [1] (https://www.egovframe.go.kr/wiki/doku.php?id=egovframework:rte2:itl:naming_service)
혹시 현재 설정하려는 JNDI 자원(예: 데이터소스 등)이 무엇인지 알려주시면, XML 전체 설정 작성법이나 주의사항을 더 자세히 안내해 드릴 수 있습니다.
```
```xml
<beans profile="production">
    <jee:jndi-lookup id="dataSource" jndi-name="java:comp/env/jdbc/datasource"/>
</beans>

```
