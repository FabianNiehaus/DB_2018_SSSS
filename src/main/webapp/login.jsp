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
            String password = request.getParameter("password");

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

<html>
<head>
    <%@ include file="common/imports.jsp"%>
    <meta charset="utf-8">
    <%--<link rel="stylesheet" href="css/StartHintergrund.css">--%>
    <link rel="stylesheet" href="fancybox/source/jquery.fancybox.css?v=2.1.6" type="text/css" media="screen"/>
    <script type="text/javascript" src="fancybox/source/jquery.fancybox.pack.js?v=2.1.6"></script>
</head>
<body>

<h1>Buzzword Bingo</h1>

<form id="loginForm" method="post" action="<c:url value="/login"/>">

    <label>Nutzername
        <input id="username" type="text" name="username" placeholder="Username">
    </label>

    <br>

    <label>Passwort
        <input id="password" type="password" name="password">
    </label>

    <br>

    <button id="login" type="submit">Login</button>

</form>

<form id="registerRedirectForm" method="get" action="<c:url value="/register"/>">
        <input class="registerButton" type="submit" value="Registrieren" />
</form>




</body>
</html>

