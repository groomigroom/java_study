# 명확한 값(Straight values(primitives, Strings, etc.))
* 사람이 인식 가능한 문자열 형태를 <value> tag를 사용하여 표현한다. 
* String을 argument나 property의 type에 맞춰 변환해준다.

```xml
<bean id="myDataSource" class="org.apache.commons.dbcp.BasicDataSource" destroy-method="close">
   <!-- results in a setDriverClassName(String) call -->
   <property name="driverClassName">
       <value>com.mysql.jdbc.Driver</value>
   </property>
   <property name="url">
       <value>jdbc:mysql://localhost:3306/mydb</value>
   </property>
   <property name="username">
       <value>root</value>
   </property>
   <property name="password">
       <value>masterkaoli</value>
   </property>
</bean>
```
