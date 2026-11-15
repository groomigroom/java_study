<%
    int num = 0;
    String num_ = request.getParameter("n");
    if (num_ != null && num )
%>

<% if(num % 2 != 0) { %>
홀수입니다.
<% } else%>
짝수입니다.
<% } %>
