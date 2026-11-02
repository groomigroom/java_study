<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%
String cnt_ = request.getParameter("cnt");

int cnt = 100;
if (cnt_ != null && !cnt.equals(""))
    cnt = Integer.parseInt(cnt_);
%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Insert title here</title>
    </head>
    <body>
        <% for(int i = 0; i<cnt; i++) { %>
            안녕 김구름 멍멍<br>
        <% } %>
        
    </body>
</html>
<!--teul.jsp?cnt=3 이런식으로 요청하면 cnt가 3이 됨-->
