전자정부프레임워크(Spring 기반 설정 파일, 예: context-datasource.xml)에서 <jdbc:embedded-database>는 메모리(In-Memory) 기반의 내장형 데이터베이스를 자동으로 생성하고 초기화하는 스프링 설정 태그를 의미합니다. [1] (https://www.egovframe.go.kr/wiki/doku.php?id=egovframework:rte3:psl:h2), [2] (https://m.blog.naver.com/PostView.nhn?isHttpsRedirect=true&blogId=ramisiel&logNo=221165261185&categoryNo=25&proxyReferer=)

주요 의미 및 특징

• 내장형 DB 생성: 별도의 외장 데이터베이스(Oracle, MySQL, MariaDB 등)를 설치하거나 실행하지 않아도, 애플리케이션이 구동될 때 메모리 상에 가벼운 DB(예: HSQL, H2 등)를 자동으로 띄워 사용합니다. [1] (https://m.blog.naver.com/PostView.nhn?isHttpsRedirect=true&blogId=ramisiel&logNo=221165261185&categoryNo=25&proxyReferer=)
• 초기화 스크립트 실행: <jdbc:script> 태그와 함께 쓰이며, DB가 생성될 때 미리 작성해 둔 SQL 스크립트(sampledb.sql 등)를 자동으로 실행해 테이블을 만들고 샘플 데이터를 채워넣습니다. [1] (https://m.blog.naver.com/PostView.nhn?isHttpsRedirect=true&blogId=ramisiel&logNo=221165261185&categoryNo=25&proxyReferer=)
• 개발 및 테스트용: 실제 운영(Production) 환경에서는 사용하지 않으며, 전자정부프레임워크를 처음 설치하거나 예제(Sample) 프로젝트를 구동해 볼 때 빠르고 간편하게 테스트하기 위한 용도로 쓰입니다. [1] (https://m.blog.naver.com/PostView.nhn?isHttpsRedirect=true&blogId=ramisiel&logNo=221165261185&categoryNo=25&proxyReferer=)

설정 예시

xml
<jdbc:embedded-database id="dataSource" type="HSQL">
    <jdbc:script location="classpath:/db/sampledb.sql"/>
</jdbc:embedded-database>
코드를 사용할 때는 주의가 필요합니다.
• id="dataSource": 스프링 빈(Bean)으로 등록되는 데이터소스의 이름입니다.
• type="HSQL": 사용할 내장형 데이터베이스의 종류를 지정합니다 (HSQL, H2 등).
• <jdbc:script>: DB가 생성될 때 실행할 초기 SQL 파일의 경로를 지정합니다. [1] (https://egovframe.go.kr/cmm/file/readDownloadFile.do?fileId=FILE_000000000014211&fileSn=0), [2] (https://m.blog.naver.com/PostView.nhn?isHttpsRedirect=true&blogId=ramisiel&logNo=221165261185&categoryNo=25&proxyReferer=)
실제 운영 환경으로 전환할 때는 이 태그를 제거하고, 오라클이나 MySQL 등 실제 운영용 DB의 접속 정보(URL, 계정, 패키지 등)를 담은 DataSource 설정으로 교체해야 합니다. 혹시 현재 프로젝트를 운영용 DB로 연결하는 방법이 필요하신가요?
