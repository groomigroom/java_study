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
