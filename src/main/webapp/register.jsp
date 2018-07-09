<%@ page import="exceptions.InvalidCharacterException" %>
<%@ page import="exceptions.NameAlreadyExistsException" %>
<%@ page import="data.Player" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@include file="common/gameServer.jsp"%>

<%

    String userKey = "loggedInUser";

    int playerID = -1;
    if(session.getAttribute(userKey) != null) playerID = (Integer) session.getAttribute(userKey);

    //Handle HTTP GET
    if(request.getMethod().equals("GET")) {
        response.setContentType("text/html;charset=UTF-8");

        // Forward immediately if user is already logged in
        if (gameServer.checkPlayerLoginState(session.getId(), playerID)) {
            RequestDispatcher rd = request.getRequestDispatcher("/mainmenu");
            rd.forward(request, response);
        }
    }
    // Handle HTTP Post
    else if(request.getMethod().equals("POST")){
        response.setContentType("text/html;charset=UTF-8");

        // Forward immediately if user is already logged in
        if(gameServer.checkPlayerLoginState(session.getId(), playerID)){
            RequestDispatcher rd = request.getRequestDispatcher("/mainmenu");
            rd.forward(request,response);
        }
        // Try to create new user
        else {
            String username = request.getParameter("newUsername");
            String password = request.getParameter("newPassword");

            try {
                Player player = gameServer.registerNewPlayer(username, password);
                %>
                    <script>alert("Nutzer + <%=player.getLoginname()%> + erfolgreich erstellt!")</script>
                <%

                response.sendRedirect("/login");
            } catch (InvalidCharacterException | NameAlreadyExistsException e ) {

%>

<div id="createFailed"> <%=e.getMessage()%> </div>

<%
            }

        }
    }
%>


<html>
<head>
    <title>Registrieren</title>
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
            <form method="post" action="<c:url value="/register"/>">
                <label for="usernameInput">Nutzername</label>
                <input type="text" name="newUsername" id="usernameInput">
                <label for="passwordInput">Passwort</label>
                <input type="password" name="newPassword" id="passwordInput">
            </form>
        </td>
    </tr>
</table>
</body>
</html>