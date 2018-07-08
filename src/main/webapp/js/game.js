// Variables
var conn;
var classHighlight = 'highlight';

var gameStarted = false;

//Methods

function connect() {
    conn = new WebSocket("ws://localhost:8080/BuzzwordBingo/actions");

    conn.onmessage = function (msg) {

        let element = ".word." + msg.data;
        $(element).css('background-color', 'green');

    }

/*    document.getElementById("connect").setAttribute("disabled", "disabled");
    document.getElementById("disconnect").removeAttribute("disabled");*/
}

function disconnect() {
    conn.close();
    /*document.getElementById("info").value = "";
    document.getElementById("disconnect").setAttribute("disabled", "disabled");
    document.getElementById("connect").removeAttribute("disabled");*/
}

function startGame() {

    if (!gameStarted) {
        gameStarted = true;
        $('button[name="startGame"]').prop('disabled', true);
        $('#middle').getChildren().prop("disabled", false);
        alert("Das Spiel wurde gestartet.");
    }
}


// Sends the presed tiles as a String to the Server
function sendAction(action) {
    conn.send(action)
}

window.addEventListener("DOMContentLoaded", function () {

    /*var $hwords = $('.word').click(function (e) {
        e.preventDefault();

        $(this).addClass(classHighlight);

    });*/

    let words = document.querySelectorAll(".word");

    Array.from(words, function (word) {

        word.addEventListener("click", function () {
            sendAction(this.classList[1]);
        });
    });
});




