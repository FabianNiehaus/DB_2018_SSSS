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
                        <td class="word 11">A</td>
                        <td class="word 12">A</td>
                        <td class="word 13">A</td>
                        <td class="word 14">A</td>
                        <td class="word 15">A</td>

                    </tr>
                    <tr>
                        <td class="word 21">B</td>
                        <td class="word 22">B</td>
                        <td class="word 23">B</td>
                        <td class="word 24">B</td>
                        <td class="word 25">B</td>

                    </tr>
                    <tr>
                        <td class="word 31">C</td>
                        <td class="word 32">C</td>
                        <td class="spacer"></td>
                        <td class="word 34">C</td>
                        <td class="word 35">C</td>

                    </tr>
                    <tr>
                        <td class="word 41">D</td>
                        <td class="word 42">D</td>
                        <td class="word 43">D</td>
                        <td class="word 44">D</td>
                        <td class="word 45">D</td>

                    </tr>
                    <tr>
                        <td class="word 51">E</td>
                        <td class="word 52">E</td>
                        <td class="word 53">E</td>
                        <td class="word 54">E</td>
                        <td class="word 55">E</td>

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
