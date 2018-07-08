<%@ page import="java.util.LinkedList" %>
<%@ page import="data.BuzzwordCategory" %>
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

    <link rel="stylesheet" href="css/StartHintergrund.css">


    <script>
        /*When the user clicks on the button,
        toggle between hiding and showing the dropdown content */
        function toggleDropdown() {
            document.getElementById("myDropdown").classList.toggle("show");
        }
    </script>

</head>

<body>

<header>
    <h1 class="h1">Buzzword Bingo</h1>
</header>
<form action="/createGame.jsp"></form>
<select name="category">
    <%
        for (String categoryName : buzzwordCategories){
    %>
    <option value="<%=categoryName%>"><%=categoryName%></option>
    <%
        }
    %>
</select>
<input type="submit" value="Kategorie auswählen">
</form>

</body>
</html>