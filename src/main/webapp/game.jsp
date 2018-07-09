<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="java.util.LinkedList" %>
<%@ page import="data.Game" %>
<%@ page import="data.Player" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@ include file="common/gameServer.jsp"%>

<%
    String userKey = "loggedInUser";

    int playerID = -1;
    if(session.getAttribute(userKey) != null) playerID = (Integer) session.getAttribute(userKey);

    LinkedList<String> words = new LinkedList();

    //Handle HTTP GET
    if(request.getMethod().equals("GET")) {
        response.setContentType("text/html;charset=UTF-8");

        // Forward immediately if user is already logged in
        if (!gameServer.checkPlayerLoginState(session.getId(), playerID)) {
            RequestDispatcher rd = request.getRequestDispatcher("/login");
            rd.forward(request, response);
        } else {
            Player player = gameServer.findPlayerByID(playerID);
            Game game = gameServer.checkPlayerInGameState(playerID);
            words = game.getPlayerGameBoard(player).getBuzzwords();
        }
    }
    if(request.getMethod().equals("POST")) {
        response.setContentType("text/html;charset=UTF-8");

        // Forward immediately if user is already logged in
        if (!gameServer.checkPlayerLoginState(session.getId(), playerID)) {
            RequestDispatcher rd = request.getRequestDispatcher("/login");
            rd.forward(request, response);
        } else if (request.getParameter("category") != null || request.getParameter("gameID") != null) {
            Player player = gameServer.findPlayerByID(playerID);
            Game game = gameServer.checkPlayerInGameState(playerID);
            words = game.getPlayerGameBoard(player).getBuzzwords();
        }
    }
%>

<html>
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta charset="UTF-8"/>
    <title>Buzzword Bingo</title>
    <link rel=stylesheet type="text/css" href="css/stylesheet.css">
    <%@include file="common/imports.jsp"%>
    <script src="js/game.js" type="text/javascript"></script>

</head>

<body>

<script>connect()</script>

<header>
    <h1 class="h1">Buzzword Bingo</h1>
</header>

<table class="container">
    <tr>
        <td class="left">
            <button id="startGameButton" class="gameButton" onclick="startGame();">Spiel starten</button>
            <form method="get" action="<c:url value="/mainmenu"/>">
                <input class="mainMenuButton" type="submit" value="Zum Hauptmenü" name="newGame">
            </form>
        </td>
        <td class="middleGame">
            <%if(words.size() == 24) {%>
            <table>
                <tr>
                    <td class="word 00 cell"><%=words.get(0)%></td>
                    <td class="word 01 cell"><%=words.get(1)%></td>
                    <td class="word 02 cell"><%=words.get(2)%></td>
                    <td class="word 03 cell"><%=words.get(3)%></td>
                    <td class="word 04 cell"><%=words.get(4)%></td>

                </tr>
                <tr>
                    <td class="word 10 cell"><%=words.get(5)%></td>
                    <td class="word 11 cell"><%=words.get(6)%></td>
                    <td class="word 12 cell"><%=words.get(7)%></td>
                    <td class="word 13 cell"><%=words.get(8)%></td>
                    <td class="word 14 cell"><%=words.get(9)%></td>

                </tr>
                <tr>
                    <td class="word 20 cell"><%=words.get(10)%></td>
                    <td class="word 21 cell"><%=words.get(11)%></td>
                    <td class="spacer cell"></td>
                    <td class="word 23 cell"><%=words.get(12)%></td>
                    <td class="word 24 cell"><%=words.get(13)%></td>

                </tr>
                <tr>
                    <td class="word 30 cell"><%=words.get(14)%></td>
                    <td class="word 31 cell"><%=words.get(15)%></td>
                    <td class="word 32 cell"><%=words.get(16)%></td>
                    <td class="word 33 cell"><%=words.get(17)%></td>
                    <td class="word 34 cell"><%=words.get(18)%></td>

                </tr>
                <tr>
                    <td class="word 40 cell"><%=words.get(19)%></td>
                    <td class="word 41 cell"><%=words.get(20)%></td>
                    <td class="word 42 cell"><%=words.get(21)%></td>
                    <td class="word 43 cell"><%=words.get(22)%></td>
                    <td class="word 44 cell"><%=words.get(23)%></td>

                </tr>
            </table>
            <% } else { %>
            <p>Keine Wörter geladen!</p>
            <% } %>
        </td>
        <td class="right">
            <div id="info"></div>
        </td>
    </tr>
</table>
</body>
</html>
