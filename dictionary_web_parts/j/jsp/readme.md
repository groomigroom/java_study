<%
    int num = 0;
    String num = request.getParameter("n");
%>

<% if(num % 2 != 0) { %>
홀수입니다.
<% } else%>
짝수입니다.
<% } %>
