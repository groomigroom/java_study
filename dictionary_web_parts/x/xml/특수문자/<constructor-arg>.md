```java
package examples;

public class ExampleBean {
   // No. of years to the calculate the Ultimate Answer
   private int years;

   // The Answer to Life, the Universe, and Everything
   private String ultimateAnswer;

   public ExampleBean(int years, String ultimateAnswer) {
       this.years = years;
       this.ultimateAnswer = ultimateAnswer;
   }
}
```

Constructor Argument Type Matching
위와 같은 경우, ’type’ attribute를 통해서 각 argument의 타입을 지정할 수 있다.

```xml
<bean id="exampleBean" class="examples.ExampleBean">
   <constructor-arg type="int" value="7500000"/>
   <constructor-arg type="java.lang.String" value="42"/>
</bean>
```

# index

```xml
<!-- 위와 같은 경우 ‘index’ attribute를 통해서 각 argument의 위치를 지정할 수 있다. -->

<bean id="exampleBean" class="examples.ExampleBean">
   <constructor-arg index="0" value="7500000"/>
   <constructor-arg index="1" value="42"/>
</bean>
```
