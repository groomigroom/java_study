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
