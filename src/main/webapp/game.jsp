<html>
<!-- See this live on http://jsfiddle.net/FloydPink/KHLtw/ -->
<head>
    <meta charset="UTF-8"/>
    <title>Buzzword Bingo</title>
    <link rel=stylesheet type="text/css" href="css/stylesheet.css">
    <script type="text/javascript" src="http://code.jquery.com/jquery-1.7.js"></script>
    <script src="js/main.js" type="text/javascript"></script>
</head>

<body>

<header>
    <h1 class="h1">Buzzword Bingo</h1>
</header>

<div class="container">

    <br/>
    <textarea cols="50" rows="10" disabled id="out"></textarea>

    <br/>
    <div class="playfield">

        <table class="table-grid">
            <tr>
                <table class="left">
                    <tr>
                        <td>

                            <button type="submit" onclick="connect()" style="color: green" id="connect">Verbindung
                                aufbauen
                            </button>
                            </br>
                            <button type="submit" onclick="disconnect()" style="color: red" id="disconnect" disabled>
                                Verbindung trennen
                            </button>
                            </br>
                            <button type="submit" onclick="" style="color: black" id="start">Spiel starten</button>
                            </br>
                            <button type="submit" onclick="" style="color: black" id="quit" disabled>Spiel verlassen
                            </button>

                        </td>
                    </tr>
                </table>
            </tr>
            <tr>
                <table class="middle" style="width:50%">

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
