/*var conn;

function connect() {
    conn = new WebSocket("ws://localhost:8080/BuzzwordBingo/actions");
    conn.onmessage = function(msg) {
        cValue = document.getElementById("out").value;
        document.getElementById("out").value = cValue + msg.data + "\n";
    }
    document.getElementById("connect").setAttribute("disabled", "disabled");
    document.getElementById("disconnect").removeAttribute("disabled");
}

function disconnect() {
    conn.close();
    document.getElementById("out").value = "";
    document.getElementById("disconnect").setAttribute("disabled", "disabled");
    document.getElementById("connect").removeAttribute("disabled");
}

function sendMessage() {
    conn.send(document.getElementById("msg").value);
}

function sendAction (action) {
    conn.send(action)
}*/

window.addEventListener("DOMContentLoaded", function() {
    'use strict';
    let tables = document.querySelectorAll(".table");

    Array.from(tables, function(table) {
        box.addEventListener("click", function() {
            alert(this.classList[1]);
            //sendAction(this.classList[1].toString());
        });
    });
});