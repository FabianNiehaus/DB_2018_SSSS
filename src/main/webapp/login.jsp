<%@ page import="static com.sun.javafx.font.FontResource.SALT" %>
<%@ page import="java.security.NoSuchAlgorithmException" %>
<%@ page import="java.security.MessageDigest" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java"%>

<%@ include file="common/gameServer.jsp"%>

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
        // Try to log in the user
        else {
            String username = request.getParameter("username");
            String password = generateHash(request.getParameter("username")
                    + request.getParameter("password"));


            playerID = gameServer.playerLogin(username, password, session);

            if(playerID > -1) {
                session.setAttribute(userKey, playerID);
                request.changeSessionId();
                RequestDispatcher rd = request.getRequestDispatcher("/mainmenu");
                rd.forward(request,response);
            } else {
                %>
                <script>alert("Nutzername oder Passwort falsch!")</script>
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
    <%@ include file="common/imports.jsp"%>
    <meta charset="utf-8">
    <link rel=stylesheet type="text/css" href="css/stylesheet.css">
    <script type="text/javascript" src="fancybox/source/jquery.fancybox.pack.js?v=2.1.6"></script>
</head>

<body>

<script>connect()</script>

<header>
    <h1 class="h1">Buzzword Bingo</h1>
</header>

<table class="container">
    <tr>
        <td class="middle">
            <form id="loginForm" method="post" action="<c:url value="/login"/>">

                <label for="username"><b>Username</b></label>
                <input id="username" type="text" name="username" placeholder="Username"><br>

                <label for="password"><b>Password</b></label>
                <input id="password" type="password" name="password"><br>

                <button id="login" type="submit">Login</button>

                <form id="registerRedirectForm" method="get" action="<c:url value="/register"/>">
                    <input class="registerButton" type="submit" value="Registrieren" />
                </form>
            </form>


        </td>
    </tr>
</table>
</body>
</html>
