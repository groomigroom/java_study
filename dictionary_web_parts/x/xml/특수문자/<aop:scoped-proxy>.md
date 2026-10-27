# 한정적 bean에 대한 종속성(Scoped beans as dependencies)
* HTTP request 또는 Session에 한정적인(scoped) bean을 정의하는 것은 꽤
* 괜찮은 기능이지만 Spring IoC Container가 제공하는 핵심 기능은 객체를 생성하는 것
*  뿐만 아니라 엮어준다는 것이다.
*  만약 HTTP request에 한정적인(scoped) bean을 다른 bean에 주입하기를 원한다면,
*  한정적(scoped) bean 대신에 AOP Proxy를 주입해야 한다.

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
   xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
   xmlns:aop="http://www.springframework.org/schema/aop"
   xsi:schemaLocation="http://www.springframework.org/schema/beans
       http://www.springframework.org/schema/beans/spring-beans.xsd
       http://www.springframework.org/schema/aop
       http://www.springframework.org/schema/aop/spring-aop.xsd">
 
   <!-- a HTTP Session-scoped bean exposed as a proxy -->
   <bean id="userPreferences" class="com.foo.UserPreferences" scope="session">
       <!-- this next element effects the proxying of the surrounding bean -->
       <aop:scoped-proxy/>
   </bean>
 
   <!-- a singleton-scoped bean injected with a proxy to the above bean -->
   <bean id="userService" class="com.foo.SimpleUserService">
       <!-- a reference to the proxied 'userPreferences' bean -->
       <property name="userPreferences" ref="userPreferences"/>
   </bean>
</beans>

```
* Proxy를 생성하기 위해서 <aop:scoped-proxy/> element를 scoped bean 정의에
* 추가해야 한다(CGLIB 라이브러리도 classpath에 추가해야 한다).
