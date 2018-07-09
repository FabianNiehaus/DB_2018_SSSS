<%@ page import="exceptions.InvalidCharacterException" %>
<%@ page import="exceptions.NameAlreadyExistsException" %>
<%@ page import="data.Player" %>
<%@ page import="java.security.MessageDigest" %>
<%@ page import="java.security.NoSuchAlgorithmException" %>
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
            String password = generateHash(request.getParameter("newUsername")
                    + request.getParameter("newPassword"));



            try {
                Player player = gameServer.registerNewPlayer(username, password);
                %>
                    <script>alert("Nutzer + <%=player.getLoginname()%> + erfolgreich erstellt!")</script>
                <%

                response.sendRedirect("/BuzzwordBingo/login");
            } catch (InvalidCharacterException  e ) {

%>

<div id="createFailed"> <%=e.getMessage()%> </div>

<%
            }
            catch ( NameAlreadyExistsException e ) {

%>

<div id="createFailed"> <%=e.getMessage()%> </div>

<%
            }

        }
    }
%>


<%!
    private static String generateHash(String input) {
        StringBuilder hash = new StringBuilder();

        try {
            MessageDigest sha = MessageDigest.getInstance("SHA-256");
            byte[] hashedBytes = sha.digest(input.getBytes());
            char[] digits = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
                    'a', 'b', 'c', 'd', 'e', 'f' };
            for (int idx = 0; idx < hashedBytes.length; ++idx) {
                byte b = hashedBytes[idx];
                hash.append(digits[(b & 0xf0) >> 4]);
                hash.append(digits[b & 0x0f]);
            }
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        return hash.toString();
    }

%>

<html>
<head>
    <title>Registrieren</title>
    <link rel=stylesheet type="text/css" href="css/stylesheet.css">

</head>


<body>

<header>
    <h1 class="h1">Buzzword Bingo</h1>
</header>

<table class="container">
    <tr>
        <td class="middle">
            <form id="registerForm" method="post" action="<c:url value="/register"/>">
                <label for="usernameInput">Nutzername</label>
                <input type="text" name="newUsername" id="usernameInput">
                <label for="passwordInput">Passwort</label>
                <input type="password" name="newPassword" id="passwordInput">
                <input class="registerButton" type="submit" value="Registrieren">
            </form>
        </td>
    </tr>
</table>
</body>
</html>