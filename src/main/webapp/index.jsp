<%@ page import="domain.BuzzwordServer" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    BuzzwordServer.getInstance();

    RequestDispatcher rd = request.getRequestDispatcher("/login");
    rd.forward(request, response);
%>
