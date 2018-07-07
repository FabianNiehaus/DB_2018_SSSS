<%@ page import="domain.BuzzwordServer" %>
<html>
<!-- See this live on http://jsfiddle.net/FloydPink/KHLtw/ -->
<head>
    <meta charset="UTF-8" />
    <title>Buzzword Bingo</title>
    <link rel=stylesheet type="text/css" href="css/stylesheet.css">
    <script type="text/javascript" src="http://code.jquery.com/jquery-1.7.js"></script>
    <script  src="js/main.js" type="text/javascript"></script>

    <server><%= BuzzwordServer.getInstance().description() %></server>

</head>
<body>
<header>
    <h1 class="h1">Buzzword Bingo</h1>
</header>

    <div class="container">
        <div class="bigNumberDisplay">
            <span>0</span>
        </div>
        <div>
            <input id="btnGenerate" type="button" value="Generate"/>
        </div>

        <br/>

        <button type="submit" onclick="connect()" style="color: green" id="connect">verbinden</button>
        <button type="submit" onclick="disconnect()" style="color: red" id="disconnect" disabled>trennen</button>

        <hr/>

        <br/>
        <textarea cols="50" rows="10" disabled id="out"></textarea>

        <br/>

        <div class="numbersTable">
            <table>
                <tr>
                    <td class="table 11">A</td>
                    <td class="table 12">A</td>
                    <td class="table 13">A</td>
                    <td class="table 14">A</td>
                    <td class="table 15">A</td>

                </tr>
                <tr>
                    <td class="table 21">B</td>
                    <td class="table 22">B</td>
                    <td class="table 23">B</td>
                    <td class="table 24">B</td>
                    <td class="table 25">B</td>

                </tr>
                <tr>
                    <td class="table 31">c</td>
                    <td class="table 32">c</td>
                    <td class="table 33">c</td>
                    <td class="table 34">c</td>
                    <td class="table 35">c</td>

                </tr>
                <tr>
                    <td class="table 41">D</td>
                    <td class="table 42">D</td>
                    <td class="table 43">D</td>
                    <td class="table 44">D</td>
                    <td class="table 45">D</td>

                </tr>
                <tr>
                    <td class="table 51">E</td>
                    <td class="table 52">E</td>
                    <td class="table 53">E</td>
                    <td class="table 54">E</td>
                    <td class="table 55">E</td>

                </tr>

            </table>

        </div>
        <table style="float:left;width:25%;" border="1">
            <tr>
                <td>links</td>
            </tr>
        </table>

        <table style="float:right;width:25%;" border="1">
            <tr>
                <td>rechts</td>
            </tr>
        </table>
    </div>
</body>
</html>
