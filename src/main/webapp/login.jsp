<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <script
            src="https://code.jquery.com/jquery-3.3.1.min.js"
            integrity="sha256-FgpCb/KJQlLNfOu91ta32o/NMZxltwRo8QtmkMRdAu8="
            crossorigin="anonymous"></script>
    <title>Login - BuzzwordBingo</title>
    <meta charset="utf-8">
    <%--<link rel="stylesheet" href="css/StartHintergrund.css">--%>
    <link rel="stylesheet" href="fancybox/source/jquery.fancybox.css?v=2.1.6" type="text/css" media="screen" />
    <script type="text/javascript" src="fancybox/source/jquery.fancybox.pack.js?v=2.1.6"></script>
    <script type="text/javascript">
        $(document).ready(function() {
            $("#single_1").fancybox({
                helpers: {
                    title : {
                        type : 'float'
                    }
                }
            });
        });
    </script>

</head>
<body>

<header>
    <h1>Buzzword Bingo</h1>
</header>


<main>
    <script>
        function checkInput(){
            var input = document.getElementById("Abfrage1").value,
                richtigerInput = ["Spieler", "spieler"];

            if(richtigerInput.indexOf(input) > -1){
                var txt = document.createTextNode ("Du bist jetzt angemeldet!"),
                    btn = document.createElement("button"),
                    btn_txt = document.createTextNode("Weiter"),
                    img = document.createElement("img");


                btn.addEventListener("click", function () {
                    window.location = "Anmelden.html";
                });
                btn.className = "Choice";
                btn.appendChild(btn_txt);

                var myNode = document.getElementById("main");
                while (myNode.firstChild) {
                    myNode.removeChild(myNode.firstChild);
                }

                document.getElementById("main").appendChild(txt);
                document.getElementById("main").appendChild(btn);
                document.getElementById("main").appendChild(img);
            } else {
                document.getElementById("beschreibung").innerHTML = "";
            }

        }
    </script>
    <center>
        <div id="main">

            <p id="beschreibung"> <p> Usename: <br>

        </p>

            <input id="Abfrage"  type= "usename">

        </div>
        <script>
            function checkInput(){
                var input = document.getElementById("Abfrage2").value,
                    richtigerInput = ["SSSS", "ssss"];

                if(richtigerInput.indexOf(input) > -1){
                    var txt = document.createTextNode ("Du bist jetzt angemeldet!"),
                        btn = document.createElement("button"),
                        btn_txt = document.createTextNode("Weiter"),
                        img = document.createElement("img");


                    btn.addEventListener("click", function () {
                        window.location = "SpielAuswählen.html";
                    });
                    btn.className = "Choice";
                    btn.appendChild(btn_txt);

                    var myNode = document.getElementById("main");
                    while (myNode.firstChild) {
                        myNode.removeChild(myNode.firstChild);
                    }

                    document.getElementById("main").appendChild(txt);
                    document.getElementById("main").appendChild(btn);
                    document.getElementById("main").appendChild(img);
                } else {
                    document.getElementById("beschreibung").innerHTML = "Bitte gebe etwas anderes ein!";
                }

            }
        </script>

        <div id="main">

            <p id="beschreibung"> <p> Passwort <br>

        </p>

            <input id="Abfrage"  type= "name"> <br>

            <br>

            <button type="button" onclick="checkInput()">Anmelden</button>

        </div>

        <br>
        <button type="button" ><a href="Registieren02.html" class="choice">Registieren</a></button>

    </center>
</main>
</body>
</html>

