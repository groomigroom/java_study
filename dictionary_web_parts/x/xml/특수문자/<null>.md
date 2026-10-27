* null 값을 사용하기 위해서 <null/> element를 사용한다.
* Spring는 argument가 없을 경우 빈 문자열(””)로 인식한다.

```xml
<bean class="ExampleBean">
   <property name="email"><null/></property>
</bean>
```
