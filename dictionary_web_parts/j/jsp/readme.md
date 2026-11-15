<%
    int num = 0;
    request.getParameter("n");
%>

<% if(num % 2 != 0) { %>
홀수입니다.
<% } else%>
짝수입니다.
<% } %>
