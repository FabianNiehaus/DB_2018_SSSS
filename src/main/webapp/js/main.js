// Variables
var conn;
var classHighlight = 'highlight';

//Methods

function connect() {
    conn = new WebSocket("ws://localhost:8080/BuzzwordBingo/actions");

    conn.onmessage = function (msg) {

        cValue = document.getElementById("info").value;

        document.getElementById("info").value = cValue + msg.data + "\n";

    }

    document.getElementById("connect").setAttribute("disabled", "disabled");
    document.getElementById("disconnect").removeAttribute("disabled");
}

function disconnect() {
    conn.close();
    document.getElementById("info").value = "";
    document.getElementById("disconnect").setAttribute("disabled", "disabled");
    document.getElementById("connect").removeAttribute("disabled");
}

function sendMessage() {
    conn.send(document.getElementById("msg").value);
}

// Sends the presed tiles as a String to the Server
function sendAction(action) {
    conn.send(action)
}

window.addEventListener("DOMContentLoaded", function () {

    var $hwords = $('.word').click(function (e) {
        e.preventDefault();

        $(this).addClass(classHighlight);

    });

    let words = document.querySelectorAll(".word");

    Array.from(words, function (word) {

        word.addEventListener("click", function () {
            //alert(this.classList[1]);
            sendAction(this.classList[1]);
        });
    });
});