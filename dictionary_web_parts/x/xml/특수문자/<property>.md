```
전자정부표준프레임워크(및 스프링 기반 XML 설정)에서 <property> 태그는 스프링 빈(Bean)의 프로퍼티(멤버 변수나 Setter 메서드)에
특정 값이나 다른 빈을 주입(Dependency Injection, DI)하기 위해 사용하는 요소입니다. [1] (https://www.egovframe.go.kr/wiki/
doku.php?id=egovframework:rte3:fdl:property_source)

주요 의미와 역할

1. 설정값 주입 (Value Injection)
	• 클래스의 필드(멤버 변수)나 Setter 메서드에 직접 문자열, 숫자 등의 고정된 값이나 외부 프로퍼티 파일(properties)의 키 값을 주입할 때 사용합니다.
	• 예: context-properties.xml 등에서 페이징 크기(pageUnit, pageSize), 파일 경로, 데이터베이스 접속 정보 등을 정의하고 빈에 전달합니다.
[1] (https://egovframe.go.kr/docs/5.0/common-component/user-support/online-help/glossary/), [2] (https://egovframe.go.kr/docs/5.0/common-component/user-support/information-provided/map-management/)
2. 외부 프로퍼티 연결 (PropertySource / PlaceHolder 연동)
	• ${...} 형태의 플레이스홀더(Placeholder)를 사용하여 외부 properties 파일에 정의된 키(Key) 값에 대응하는 실제 값을 불러와 빈 설정에
바인딩할 때 쓰입니다.
	• 예: #{...} 또는 ${globals.username} 같은 방식으로 시스템 환경변수나 공통 설정 파일의 값을 참조합니다. [1] (https://www.egov
frame.go.kr/wiki/doku.php?id=egovframework:rte3:fdl:property_source)
3. 시스템 유연성 확보
	• 소스코드 내부(java 파일)에 하드코딩하지 않고, XML 설정 파일이나 외부 프로퍼티 파일에서 변경 가능한 설정값을 관리할 수 있게 해
주어 애플리케이션의 유지보수성과 유연성을 높입니다. [1] (https://www.egovframe.go.kr/wiki/doku.php?id=egovframework:rte3:fdl:property_service)

사용 예시 (XML 구조)

xml
<bean id="propertyService" class="org.egovframe.rte.fdl.property.impl.EgovPropertyServiceImpl">
    <property name="fileExts">
        <value>jpg,gif,png,jpeg</value>
    </property>
    <property name="pageUnit" value="10"/>
    <property name="pageSize" value="10"/>
</bean>
코드를 사용할 때는 주의가 필요합니다.
• 위 예시에서 name="pageUnit"은 자바 클래스의 setPageUnit(...) Setter 메서드를 호출하여 값(10)을 주입한다는 의미입니다.
전자정부표준프레임워크의 공식 가이드는 표준프레임워크 개발가이드 - 프로퍼티 또는 eGovFrame 위키 Property Source 문서에서 더 자세히
확인할 수 있습니다. [1] (https://egovframe.go.kr/docs/5.0/common-component/elementary-technology/system/property/), [2] (https://www.egovframe.go.kr/wiki/doku.php?id=egovframework:rte3:fdl:property_source)
만약 특정 XML 파일(context-*.xml)에서 사용된 <property> 태그의 전체 설정 코드나 특정 프로퍼티 키값의 사용법을 확인하고 싶다면
코드를 보여주세요. 함께 분석해 드리겠습니다.

```

Setter 기반의 DI는 argument가 없는 생성자를 통해 bean 객체가 생성된 후,<br>
setter 메소드를 호출하여 종속성을 주입한다.<br>
<property> element를 사용한다.

```xml
<bean id="exampleBean" class="examples.ExampleBean">
   <!-- setter injection using the nested <ref/> element -->
   <property name="beanOne"><ref bean="anotherExampleBean"/></property>
 
   <!-- setter injection using the neater 'ref' attribute -->
   <property name="beanTwo" ref="yetAnotherBean"/>
   <property name="integerProperty" value="1"/>
</bean>
 
<bean id="anotherExampleBean" class="examples.AnotherBean"/>
<bean id="yetAnotherBean" class="examples.YetAnotherBean"/>
```

```java
public class ExampleBean {
   private AnotherBean beanOne;
   private YetAnotherBean beanTwo;
   private int i;
 
   public void setBeanOne(AnotherBean beanOne) {
       this.beanOne = beanOne;
   }
 
   public void setBeanTwo(YetAnotherBean beanTwo) {
       this.beanTwo = beanTwo;
   }
 
   public void setIntegerProperty(int i) {
       this.i = i;
   }    
}
```

# &lt;value&gt; 대체
```xml
<bean id="myDataSource" class="org.apache.commons.dbcp.BasicDataSource" destroy-method="close">
   <!-- results in a setDriverClassName(String) call -->
   <property name="driverClassName" value="com.mysql.jdbc.Driver"/>
   <property name="url" value="jdbc:mysql://localhost:3306/mydb"/>
   <property name="username" value="root"/>
   <property name="password" value="masterkaoli"/>
</bean>
```

# 복합 형식의 property 이름도 사용 가능하다.
```xml
<bean id="foo" class="foo.Bar">
   <property name="fred.bob.sammy" value="123" />
</bean>

```
