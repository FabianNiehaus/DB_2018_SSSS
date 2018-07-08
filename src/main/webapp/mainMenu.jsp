<%@ page import="data.Game" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@ include file="common/gameServer.jsp"%>
<%

    String userKey = "loggedInUser";

    int playerID = -1;
    if(session.getAttribute(userKey) != null) playerID = (Integer) session.getAttribute(userKey);

    Game userInGame = null;

    //Handle HTTP GET
    if(request.getMethod().equals("GET")) {
        response.setContentType("text/html;charset=UTF-8");

        // Forward immediately if user is already logged in
        if (!gameServer.checkPlayerLoginState(session.getId(), playerID)) {
            RequestDispatcher rd = request.getRequestDispatcher("/login");
            rd.forward(request, response);
        } else {
            userInGame = gameServer.checkPlayerInGameState(playerID);
        }
    }
%>

<!doctype html>
<html>
<head>
    <meta charset="UTF-8" />
    <title>Buzzword Bingo</title>

    <%@ include file="common/imports.jsp"%>

    <link rel="stylesheet" href="css/StartHintergrund.css">

</head>
<body>

<header>
    <h1 class="h1">Buzzword Bingo</h1>
</header>

<%
    if(userInGame != null){
        %>
            <form action="<c:url value="/game"/>">
                <input class="mainMenuButton" type="submit" value="Zum laufenden Spiel" />
            </form><br>
        <%
    } else {
        %>
            <form action="<c:url value="/creategame"/>">
                <input class="mainMenuButton" type="submit" value="Neues Spiel erstellen" />
            </form><br>
            <form action="<c:url value="/joingame"/>">
                <input class="mainMenuButton" type="submit" value="Spiel beitreten" />
            </form><br>
        <%
    }

    if(gameServer.checkPlayerAdminState(playerID)){
        %>
            <form action="<c:url value="/managecategories"/>">
                <input class="mainMenuButton" type="submit" value="Buzzwords bearbeiten" />
            </form><br>
        <%
    }
%>

</body>
</html>
