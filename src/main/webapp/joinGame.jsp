<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="java.util.LinkedList" %>
<%@ page import="exceptions.IDNotFoundException" %>
<%@ page import="exceptions.GameInWrongStateException" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@ include file="common/gameServer.jsp"%>

<%
    String userKey = "loggedInUser";

    int playerID = -1;
    if(session.getAttribute(userKey) != null) playerID = (Integer) session.getAttribute(userKey);

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
            try {
                String gameID = request.getParameter("gameID");
                gameServer.joinExistingGame(playerID, Integer.parseInt(gameID));
                RequestDispatcher rd = request.getRequestDispatcher("/game");
                rd.forward(request, response);
            } catch (IDNotFoundException e) {
                %>
                <script>alert(<%=e.getMessage()%>)</script>
                <%
            } catch (GameInWrongStateException e) {
                %>
                <script>alert("Spiel wurde bereits gestartet! ")</script>
                <%
            }
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
<form method="POST" action="<c:url value="/joingame"/>">
    <label>Spiel-ID
        <input id="gameID" type="text" name="gameID" placeholder="Spiel-ID">
        <br>
        <button id="join" type="submit">Spiel beitreten</button>
    </label>
</form>


</body>
</html>
