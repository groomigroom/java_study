XML 설정 정보를 여러 개의 파일로 나뉘어 구성될 수 있다.<br>
이 경우, 전체 설정 정보를 읽기 위해서 하나의 설정 파일에서 다른 파일을 import할 수 있다. <br>
Import 하는 방법으로 <import> tag를 사용한다.

```xml
<beans>
   <import resource="services.xml"/>
   <import resource="resources/messageSource.xml"/>
   <import resource="/resources/themeSource.xml"/>
 
   <bean id="bean1" class="..."/>
   <bean id="bean2" class="..."/>
</beans>
<import>
```
tag의 resource attribute는 import할 XML 설정 파일의 위치를 나타낸다.
