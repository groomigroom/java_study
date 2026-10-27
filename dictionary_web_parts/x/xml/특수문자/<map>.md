# Java Collection 타입인 Map을 표현
```xml
<map>
      <entry>
          <key>
              <value>an entry</value>
          </key>
          <value>just some string</value>
      </entry>
      <entry>
          <key>
              <value>a ref</value>
          </key>
          <ref bean="myDataSource" />
      </entry>
</map>
```
* map의 key와 value는 아래 중에 하나
* bean | ref | idref | list | set | map | props | value | null
