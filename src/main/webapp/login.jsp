<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="domain.BuzzwordServer" %>
<%@ page import="java.util.Enumeration" %>
<%@ page import="java.util.LinkedList" %>
<%@ page contentType="text/html;charset=UTF-8" language="java"%>

<%  BuzzwordServer gameServer = (BuzzwordServer) BuzzwordServer.getInstance();

    String userKey = "loggedInUser";

    int playerID = -1;
    if(session.getAttribute(userKey) != null) playerID = (Integer) session.getAttribute(userKey);

     String test = request.getParameterNames().toString();

    //Handle HTTP GET
    if(request.getMethod().equals("GET")) {
        response.setContentType("text/html;charset=UTF-8");

        // Forward immediately if user is already logged in
        if (gameServer.checkPlayerLoginState(session.getId(), playerID)) {
            RequestDispatcher rd = request.getRequestDispatcher("/game");
            rd.forward(request, response);
        }
    }
    // Handle HTTP Post
    else if(request.getMethod().equals("POST")){
        response.setContentType("text/html;charset=UTF-8");

        // Forward immediately if user is already logged in
        if(gameServer.checkPlayerLoginState(session.getId(), playerID)){
            RequestDispatcher rd = request.getRequestDispatcher("/game");
            rd.forward(request,response);
        }
        // Try to log in the user
        else {
            String username = request.getParameter("username");
            String password = request.getParameter("password");

            playerID = gameServer.playerLogin(username, password, session);

            if(playerID > -1) {
                request.changeSessionId();
                RequestDispatcher rd = request.getRequestDispatcher("/game");
                rd.forward(request,response);
                session.setAttribute(userKey, playerID);
            } else {
                %>

                    <div id="loginFailed"> Nutzername oder Passwort falsch! </div>

                <%
            }

        }

    }
%>

<html>
<head>
    <script
            src="https://code.jquery.com/jquery-3.3.1.min.js"
            integrity="sha256-FgpCb/KJQlLNfOu91ta32o/NMZxltwRo8QtmkMRdAu8="
            crossorigin="anonymous"></script>
    <title>Login - BuzzwordBingo</title>
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

    <script>
        function checkUsername(username){
            const expr = /[!"§$%&/()=?`*'_:;,.#+´²³{[\]}~@€\\]/;

            return username.length > 0 && !expr.test(username);
        }

        function checkInput() {
            let username = $('#username').val();

            if(checkUsername(username)){
                $('#loginForm').submit();
            } else {
                alert("Username beinhaltet ungültige Zeichen!")
            }

        }
    </script>

    <%--<button type="button"><a href="Registieren02.html" class="choice">Registieren</a></button>--%>


</body>
</html>

