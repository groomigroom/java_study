# 기본 Web 설정(Initial web configuration)
* request, session, application, websocket scope을 사용하기 위해서는 추가적인 설정이 필요하다.
* 추가 설정은 사용할 Servlet 환경에 따라 달라진다.
* 만약 Spring Web MVC 안에서 bean에 접근할 경우, 즉 Spring DispatcherServlet 또는 DispatcherPortlet에서 처리되는 요청인 경우,
* 별도의 추가 설정은 필요없다.( DispatcherServlet과 DispatcherPortlet은 이미 모든 관련있는 상태를 제공한다.)
* 만약 Servlet 2.4+ web Container를 사용하고, JSF나 Struts 등과 같이 Spring의 DispatcherServlet의 외부에서 요청을 처리하는 경우,
* 다음 jakarta.servlet.ServletRequestListener를 ‘web.xml’ 파일에 추가해야 한다.
```xml
<web-app>
   ...
   <listener>
       <listener-class>org.springframework.web.context.request.RequestContextListener</listener-class>
   </listener>
   ...
</web-app>
```
