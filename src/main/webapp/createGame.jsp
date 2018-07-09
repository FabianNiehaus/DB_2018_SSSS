<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="java.util.LinkedList" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@ include file="common/gameServer.jsp"%>

<%
    String userKey = "loggedInUser";

    int playerID = -1;
    if(session.getAttribute(userKey) != null) playerID = (Integer) session.getAttribute(userKey);

    LinkedList<String> buzzwordCategories = gameServer.getBuzzwordCategoryNames();

    //Handle HTTP GET
    if(request.getMethod().equals("GET")) {
        response.setContentType("text/html;charset=UTF-8");

        // Forward immediately if user is already logged in
        if (!gameServer.checkPlayerLoginState(session.getId(), playerID)) {
            RequestDispatcher rd = request.getRequestDispatcher("/login");
            rd.forward(request, response);
        }
    } else if (request.getMethod().equals("POST")){
        if (!gameServer.checkPlayerLoginState(session.getId(), playerID)) {
            RequestDispatcher rd = request.getRequestDispatcher("/login");
            rd.forward(request, response);
        } else {
            String categoryName = request.getParameter("category");
            gameServer.createNewGame(playerID, categoryName);
            RequestDispatcher rd = request.getRequestDispatcher("/game");
            rd.forward(request, response);
        }
    }
%>

<!doctype html>
<html>
<head>
    <meta charset="UTF-8" />
    <title>Buzzword Bingo</title>

    <%@ include file="common/imports.jsp"%>

    <link rel=stylesheet type="text/css" href="css/stylesheet.css">


</head>

<body>

<script>connect()</script>

<header>
    <h1 class="h1">Buzzword Bingo</h1>
</header>

<table class="container">
    <tr>
        <td class="middle">

            <form method="POST" action="<c:url value="/creategame"/>">
                <label>
                    <select name="category">
                        <%
                            for (String categoryName : buzzwordCategories){
                        %>
                        <option value="<%=categoryName%>"><%=categoryName%></option>
                        <%
                            }
                        %>
                    </select>
                </label>
                <input class="catButton" type="submit" value="Kategorie auswählen">
            </form>
        </td>
    </tr>
</table>
</body>
</html>
