# Bean 별명(Aliasing beans)

* <alias> tag를 사용하여 이미 정의된 bean에게 추가적인 이름을 부여할 수 있다.

```xml
<alias name="fromName" alias="toName"/>
```

name attribute는 대상이 되는 bean의 이름이고,<br>
alias attribute는 부여할 새로운 이름이다.
