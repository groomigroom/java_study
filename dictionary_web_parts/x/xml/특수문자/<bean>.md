# 각각의 <bean> tag는 Spring IoC Container가 생성하고, 관리할 객체의 정의를 나타낸다.
------

# Bean 객체화(Instantiation beans)

모든 bean 정의는 객체화를 위해 실제 Java Class가 필요하다.
XML 설정에서는 ‘class’ attribute를 통해 Java Class를 설정한다. 대부분의 경우 Container는 bean를 객체화하기 위해서 Java의 ’new’ 연산자를 사용한다.
또는 특수한 경우, static 메소드를 사용할 수도 있다. 본 문서는 생성자를 이용한 객체화만을 설명한다.
생성자를 이용한 객체화는 가장 일반적인 방식으로, 다음과 같이 사용한다.

```xml
<bean id="exampleBean" class="example.ExampleBean"/>
 
<bean name="anotherExample" class="examples.ExampleBeanTwo"/>
```

```java
package x.y;
 
public class Foo {
   public Foo(Bar bar, Baz baz) {
       // ...
   }
}
```

```xml
<beans>
   <bean name="foo" class="x.y.Foo">
       <constructor-arg>
           <bean class="x.y.Bar"/>
       </constructor-arg>
       <constructor-arg>
           <bean class="x.y.Baz"/>
       </constructor-arg>
   </bean>
</beans>
```
# Inner beans
* <property/> 또는 <constructor-arg/> element 안에 있는 <bean/> element를 inner bean이라고 한다.
* Inner bean은 id나 name을 정의할 필요가 없다.
* 정의한다 해도 Container에서 무시하기 때문에 정의하지 않는 것이 좋다.

```xml
<bean id="outer" class="...">
   <!-- instead of using a reference to a target bean, simply define the target bean inline -->
   <property name="target">
       <bean class="com.example.Person"> <!-- this is the inner bean -->
           <property name="name" value="Fiona Apple"/>
           <property name="age" value="25"/>
       </bean>
   </property>
</bean>
```
* Inner bean의 ‘scope’ flag와 ‘id’, ’name’은 무시된다. 
* Inner bean의 scope은 항상 prototype이다.
* 따라서 inner bean을 다른 bean에 주입하는 것은 불가능한다.

# p-namespace
* ‘’<property/>’’ element 대신 “p-namespace”를 사용하여 XML 설정을 작성할 수 있다.
* 아래 classic bean과 p-namespace bean은 동일한 Bean 설정이다.
```xml
<beans xmlns="http://www.springframework.org/schema/beans"
   xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
   xmlns:p="http://www.springframework.org/schema/p"
   xsi:schemaLocation="http://www.springframework.org/schema/beans
       http://www.springframework.org/schema/beans/spring-beans.xsd">
 
   <bean name="classic" class="com.example.ExampleBean">
       <property name="email" value="foo@bar.com/>
   </bean>
 
   <bean name="p-namespace"
       class="com.example.ExampleBean"
       p:email="foo@bar.com"/>
</beans>
```
# ref
* bean 객체의 참조를 삽입하는 예제이다.
* Attribute 이름 끝에 ‘-ref’를 붙이면 참조로 인식한다.
```xml
<beans xmlns="http://www.springframework.org/schema/beans"
   xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
   xmlns:p="http://www.springframework.org/schema/p"
   xsi:schemaLocation="http://www.springframework.org/schema/beans
       http://www.springframework.org/schema/beans/spring-beans.xsd">
 
   <bean name="john-classic" class="com.example.Person">
       <property name="name" value="John Doe"/>
       <property name="spouse" ref="jane"/>
   </bean>
 
   <bean name="john-modern" 
       class="com.example.Person"
       p:name="John Doe"
       p:spouse-ref="jane"/>
 
   <bean name="jane" class="com.example.Person">
       <property name="name" value="Jane Doe"/>
   </bean>
</beans>
```
# depends-on
* bean들간의 종속성은 ‘’<ref/>’’ element에 의해 표현된다.
* 하지만 드물게 이런 종속성이 직접 나타나지 않는 경우도 있다(예를 들면, database driver 등록처럼 static 메소드에 의해 초기화되어야 하는 경우 등).
* 이런 경우 ‘depends-on’ attribute를 사용하여 명시적으로 종속성을 표현할 수 있다.
```xml
<bean id="beanOne" class="ExampleBean" depends-on="manager"/>
 
<bean id="manager" class="ManagerBean" />
```
* 만약 다수의 bean에 대한 종속성을 표현하고 하는 경우에는,
* ‘depends-on’ attribute의 값으로 bean 이름을 나열하면 된다.
* bean 이름의 구분자로는 콤마(’,’), 공백문자(’ ‘), 세미콜론(’;’) 등을 사용할 수 있다.
```xml
<bean id="beanOne" class="ExampleBean" depends-on="manager,accountDao">
   <property name="manager" ref="manager" />
</bean>
 
<bean id="manager" class="ManagerBean" />
<bean id="accountDao" class="x.y.jdbc.JdbcAccountDao"/>
```
# 늦은 객체화(Lazily-instantiated beans)
* ApplicationContext는 시작시에 모든 singleton bean을 선객체화(pre-instantiate)한다.
* 선객체화(pre-instantiate)는 초기화 과정에서 모든 singleton baen을 생성하고 설정한다는 것을 의미한다.
* 일반적으로 선객체화가 좋은 방식인데, 왜냐하면 잘못된 설정이 있는 경우, 즉시 발견할 수 있기 때문이다.

* 어쨌거나, 이런 방식을 원하지 않을 경우도 있다.
* 만약 ApplicationContext에 의해 선 객체화 되는 singleton bean을 원하지 않을 경우,
* 선택적으로 bean 정의에 늦은 객체화(lazy-initailized)를 설정할 수 있다.
* 늦은 객체화(lazy-initailized)로 설정된 bean은 시작 시에 생성되는 것이 아니라, 처음으로 필요로 했을 때 생성된다.

* XML 설정에서는 ‘’<bean/>’’ element의 ’lazy-init’ attribute를 사용한다.
```xml
<bean id="lazy" class="com.foo.ExpensiveToCreateBean" lazy-init="true"/>
 
<bean name="not.lazy" class="com.foo.AnotherBean"/>
```
* 늦은 객체화에 대해서 이해하고 있어야 하는 것은,
* 만약 늦은 객체화로 설정된 bean에 대해서 그렇지 않은 singleton bean이 종속성을 가지고 있다면,
* ApplicationContext는 시작 시에 singleton bean이 종속하고 있는 모든 bean을 생성한다는 것이다.
* 즉, 명시적으로 늦은 객체화로 선언한 bean이라도 시작 시에 생성될 수 있다.

* 그리고, <beans/> element의 ‘default-lazy-init’ attribute를 사용하여 Container 레벨에서의 늦은 객체화를 설정할 수 있다.
```xml
<beans default-lazy-init="true">
   <!-- no beans will be pre-instantiated... -->
</beans>
```
# 자동엮기(Autowiring collaborators)
* Spring Container는 서로 관계된 bean들을 자동으로 엮어(autowire)줄 수 있다.
* 자동엮기(autowiring)는 각각의 bean 단위로 설정된다.
* 자동엮기(autowiring) 기능을 사용하면 property나 생성자 argument를 지정할 필요가 없어지므로, 타이핑일 줄일 수 있다.
* 자동엮기(autowiring)에는 5가지 모드가 있으며, XML 기반 설정에서는 <bean/> element의 ‘autowire’ attribute를 사용하여 설정할 수 있다.
```
no
자동엮기를 사용하지 않는다. Bean에 대한 참조는 ref element를 사용하여 지정해야만 한다. 이 모드가 기본(default)이다.

byName
Property 이름으로 자동엮기를 수행한다. Property의 이름과 같은 이름을 가진 bean을 찾아서 엮어준다.

byType
Property 타입으로 자동엮기를 수행한다.
Property의 타입과 같은 타입을 가진 bean을 찾아서 엮어준다.
만약 같은 타입을 가진 bean이 Container에 둘 이상 존재할 경우 exception이 발생한다.
만약 같은 타입을 가진 bean이 존재하지 않는 경우, 아무 일도 발생하지 않는다; 즉, property에는 설정되지 않는다.

constructor
byType과 유사하지만, 생성자 argument에만 적용된다.
만약 같은 타입의 bean이 존재하지 않거나 둘 이상 존재할 경우, exception이 발생한다.

autodetect
Bean class의 성질에 따라 constructor와 byType 모드 중 하나를 선택한다.
만약 default 생성자가 존재하면, byType 모드가 적용된다
```
# Bean을 자동엮기 대상에서 제외하는 방법(Excluding a bean from being available from autowiring
* <bean/> element의 ‘autowire-candidate’ attribute 값을 ‘false’로 설정함으로써,
* 대상 bean이 다른 bean에 의해 자동엮임을 당하는 것을 방지할 수 있다.
# 종속성 검사(Checking for dependencies)
* Spring IoC Container는 bean의 미해결 종속성의 존재를 검사할 수 있다.
* 이 기능은 bean의 모든 property가 지정되었는지는 확인하고 싶을 때 유용하다.
* 종속성 검자(Dependency checking) 기능은 자동엮기(autowiring) 기능과 마찬가지로 각각의 bean마다 설정할 수 있다.
* 종속성 검사에는 4가지 모드가 있으며, XML 기반 설정에서는 <bean/> element의 ‘dependency-check’ attribute를 사용하여 설정할 수 있다.
```
none
종속성 검사를 하지 않는다. 기본(default) 모드이다.

simple
Primitive 타입과 collection에 대해서 종속성 검사를 수행한다.

object
관련된 객체에 대해서만 종속성 검사를 수행한다.

all
Primitive 타입과 collection, 관련된 객체에 대해서 종속성 검사를 수행한다.
```
# scope
* Bean 정의를 통해 객체에 다양한 종속성 및 설정값을 주입할 수 있을 뿐 아니라,
* 객체의 범위(Scope)를 정의할 수 있다.
* Spring 프레임워크는 6개의 Scope를 지원하며,
* 이 중 4개의 Scope는 Web-aware ApplicationContext를 사용하는 경우에만 사용할 수 있다.
* 또한, 사용자 정의 범위를 생성할 수도 있다.
```
singleton
하나의 Bean 정의에 대해서 Spring IoC Container 내에 단 하나의 객체만 존재한다.

prototype
하나의 Bean 정의에 대해서 다수의 객체가 존재할 수 있다.

request
하나의 Bean 정의에 대해서 하나의 HTTP request 생명주기 안에 단 하나의 객체만 존재한다.
즉, 각각의 HTTP Request는 자신만의 객체를 가진다.
web-aware Spring ApplicationContext 안에서만 유효하다.

session
하나의 Bean 정의에 대해서 하나의 HTTP Session 생명주기 안에 단 하나의 객체만 존재한다.
web-aware Spring ApplicationContext 안에서만 유효하다.

application
하나의 Bean 정의에 대해서 하나의 ServletContext 생명주기 안에 단 하나의 객체만 존재한다.
web-aware Spring ApplicationContext 안에서만 유효하다.

websocket
하나의 Bean 정의에 대해서 하나의 Websocket 생명주기 안에 단 하나의 객체만 존재한다.
web-aware Spring ApplicationContext 안에서만 유효하다.
```
