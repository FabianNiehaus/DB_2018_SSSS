<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="java.util.LinkedList" %>
<%@ page import="java.util.Collections" %>
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
        } else {
            Player player = gameServer.findPlayerByID(playerID);
            Game game = gameServer.checkPlayerInGameState(playerID);
            words = game.getPlayerGameBoard(player).getBuzzwords();
        }
    }
%>

<html>
<!-- See this live on http://jsfiddle.net/FloydPink/KHLtw/ -->
<head>
    <meta charset="UTF-8"/>
    <title>Buzzword Bingo</title>
    <link rel=stylesheet type="text/css" href="css/stylesheet.css">
    <script src="https://code.jquery.com/jquery-3.3.1.min.js"
            integrity="sha256-FgpCb/KJQlLNfOu91ta32o/NMZxltwRo8QtmkMRdAu8="
            crossorigin="anonymous"></script>
    <script src="js/game.js" type="text/javascript"></script>
</head>

<body>

<script>connect()</script>


<header>
    <h1 class="h1">Buzzword Bingo</h1>
</header>

<div class="container">

    <div class="playfield">

        <table class="table-grid">
            <tr>
                <table class="left">
                    <tr>
                        <td>

                            <%--<button type="button" onclick="connect()" style="color: green" id="connect">Verbindung
                                aufbauen
                            </button>
                            </br>
                            <button type="button" onclick="disconnect()" style="color: red" id="disconnect" disabled>
                                Verbindung trennen
                            </button>
                            </br>--%>
                            <button type="button" onclick="startGame()" style="color: black" id="start">Spiel starten</button>
                            </br>
                            <%--<button type="button" onclick="" style="color: black" id="quit" disabled>Spiel verlassen
                            </button>--%>

                        </td>
                    </tr>
                </table>
            </tr>
            <tr>
                <td>
                <table class="middle" style="width:50%">

                    <tr>
                        <td class="word 11"><%=words.get(0)%></td>
                        <td class="word 12"><%=words.get(1)%></td>
                        <td class="word 13"><%=words.get(2)%></td>
                        <td class="word 14"><%=words.get(3)%></td>
                        <td class="word 15"><%=words.get(4)%></td>

                    </tr>
                    <tr>
                        <td class="word 21"><%=words.get(5)%></td>
                        <td class="word 22"><%=words.get(6)%></td>
                        <td class="word 23"><%=words.get(7)%></td>
                        <td class="word 24"><%=words.get(8)%></td>
                        <td class="word 25"><%=words.get(9)%></td>

                    </tr>
                    <tr>
                        <td class="word 31"><%=words.get(10)%></td>
                        <td class="word 32"><%=words.get(11)%></td>
                        <td class="spacer"></td>
                        <td class="word 34"><%=words.get(12)%></td>
                        <td class="word 35"><%=words.get(13)%></td>

                    </tr>
                    <tr>
                        <td class="word 41"><%=words.get(14)%></td>
                        <td class="word 42"><%=words.get(15)%></td>
                        <td class="word 43"><%=words.get(16)%></td>
                        <td class="word 44"><%=words.get(17)%></td>
                        <td class="word 45"><%=words.get(18)%></td>

                    </tr>
                    <tr>
                        <td class="word 51"><%=words.get(19)%></td>
                        <td class="word 52"><%=words.get(20)%></td>
                        <td class="word 53"><%=words.get(21)%></td>
                        <td class="word 54"><%=words.get(22)%></td>
                        <td class="word 55"><%=words.get(23)%></td>

                    </tr>
                </table>
                </td>
            </tr>

            <tr>
                <table class="right">
                    <tr>
                        <textarea name="info" id="info" cols="50" rows="25" disabled></textarea>
                    </tr>
                </table>
            </tr>

        </table>
    </div>
</div>
</body>
</html>
