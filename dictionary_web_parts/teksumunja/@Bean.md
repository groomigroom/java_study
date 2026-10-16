import org.springframework.context.annotation.Bean;

@Bean은 스프링에 의해 생성 또는 관리되는 객체를 의미.
컨트롤러, 서비스, 리포지터리 등도 모두 빈에 해당됨.
@Bean 애너테이션을 통해 자바 코드 내에서 별도로 빈을 정의하고 등록하는거도 가능함.

--------------------------------------------------------------------------------------------------
스프링 부트(Spring Boot)의 @Bean 어노테이션 뒤에 붙는 속성들은 해당 객체(Bean)를 스프링 컨테이너에 등록할 때 사용할 고유 이름과, 앱이 종료될 때 안전하게 객체를 소멸시키는 방식을 명시하는 설정입니다.
질문하신 @Bean(name = "dataSource-mysql", destroyMethod = "close")의 구체적인 의미는 다음과 같습니다.

1. name = "dataSource-mysql" 의 의미

• 역할: 스프링 컨테이너 안에서 이 객체를 찾을 수 있는 고유한 이름(ID)을 강제로 지정합니다.
• 상세 설명: 기본적으로 @Bean을 붙이면 메서드 이름이 빈의 이름이 됩니다. 하지만 이 속성을 사용하면 메서드 이름과 상관없이 "dataSource-mysql"이라는 이름으로 빈이 등록됩니다.
• 주요 용도: 프로젝트 내에 데이터베이스 연결 객체(DataSource)가 여러 개(MySQL용, Oracle용 등) 존재할 때, 이 이름표를 보고 이전 질문에서 다룬 @Qualifier("dataSource-mysql")을 사용해 원하는 데이터베이스 객체를 정확히 찾아 주입(DI)받을 수 있게 됩니다.

2. destroyMethod = "close" 의 의미

• 역할: 스프링 부트 애플리케이션이 종료될 때(서버가 꺼질 때), 이 객체가 가지고 있는 자원을 안전하게 반납하도록 특정 메서드를 자동으로 호출해 줍니다.
• 상세 설명: 데이터베이스 커넥션 풀(예: HikariCP, Commons DBCP 등)은 실행 중에 많은 네트워크 연결(Connection)을 맺고 있습니다. 서버가 꺼질 때 이 연결들을 제대로 닫아주지 않으면 커넥션 누수(Connection Leak)나 데이터베이스 서버에 부하가 남는 문제가 생길 수 있습니다.
• 종합하면: 스프링 부트가 종료되는 시점에 이 DataSource 객체 내부의 close() 메서드를 자동으로 실행시켜 데이터베이스와의 연결을 안전하고 깔끔하게 끊어주라는 특수 명령어입니다.

💻 코드로 보는 이해

java
@Configuration
public class DbConfig {

    @Bean(name = "dataSource-mysql", destroyMethod = "close")
    public DataSource mySqlDataSource() {
        // 원래는 메서드명인 'mySqlDataSource'가 빈의 이름이 되지만,
        // name 속성 때문에 'dataSource-mysql'이라는 이름으로 스프링에 등록됩니다.
        
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
        dataSource.setUrl("jdbc:mysql://localhost:3306/mydb");
        dataSource.setUsername("root");
        dataSource.setPassword("password");
        
        return dataSource;
    }
}
코드를 사용할 때는 주의가 필요합니다.
이렇게 등록된 빈은 나중에 서비스나 리포지토리에서 사용할 때 다음과 같이 명확하게 꽂아서 쓸 수 있습니다.
java
@Autowired
@Qualifier("dataSource-mysql") // 👈 name으로 지정한 이름을 찾아옵니다.
private DataSource dataSource;
코드를 사용할 때는 주의가 필요합니다.

💡 한 줄 요약

"이 메서드가 생성하는 객체는 스프링 공간에 'dataSource-mysql'이라는 이름으로 보관하고(name), 나중에 프로그램이 종료될 때는 이 객체 안의 close() 메서드를 자동으로 실행해서 데이터베이스 연결을 안전하게 닫아라(destroyMethod)"라는 뜻입니다.
참고로 스프링 부트 환경에서 주로 사용하는 HikariCP(기본 커넥션 풀)나 Tomcat DataSource 등은 기본적으로 close나 shutdown 메서드를 자동으로 감지하여 종료 시 실행해 주기 때문에 생략해도 무방한 경우가 많습니다.
현재 분석 중이신 코드가 MySQL과 Oracle 등 멀티 데이터소스(Multi DataSource)를 다중으로 연동하는 설정 클래스인가요? 만약 그렇다면 주 데이터베이스를 지정하는 @Primary나 복수 개의 데이터소스를 다루는 모범 설정 코드가 필요하신지 말씀해 주세요!
