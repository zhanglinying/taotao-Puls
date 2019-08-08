<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2019-08-08
  Time: 11:18
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String callback=request.getParameter("callback");
    if(callback!=null){
        out.print(callback+"({\"abc\":123})");
    }else {
        out.print("{\"abc\":123}");
    }
%>
