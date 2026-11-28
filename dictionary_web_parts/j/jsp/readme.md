https://www.youtube.com/watch?v=VPvs6SGQ9yY&list=PLq8wAnVUcTFVOtENMsujSgtv2TOsMy8zd&index=48
12분 37초부터
 보기

<%
    int num = 0;
    String num_ = request.getParameter("n");
    if (num_ != null && !num_.equals(""))
        num = Integer.parseInt(num_);
%>

<% if(num % 2 != 0) { %>
홀수입니다.
<% } else%>
짝수입니다.
<% } %>
