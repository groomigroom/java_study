# ref element는 Container 안에 있는 다른 bean을 참조한다. 참조할 객체를 지정하는 방식에는 3가지가 있다.
# bean attribute
* 가장 일반적인 형태로 같은 Container 또는 부모 Container에 포함된 bean 객체를 참조한다. 
* ‘bean’ attribute는 대상 bean의 ‘id’ 또는 여러 ’name’들 중. 하나와 같아야 한다.

```xml
<ref bean="someBean"/>
```
# local attribute
* 같은 XML 설정 파일 내의 bean 객체를 참조한다.
* ’local’ attribute는 반드시 대상 bean의 ‘id’와 같아야 한다.
* 만약 대상 bean이 같은 XML 파일에 존재한다면. local을 사용하는 것이 좋다.
```xml
<ref local="someBean"/>
```
