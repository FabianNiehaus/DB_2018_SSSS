/*var gameSocket;

function connect() {
    gameSocket = new WebSocket("ws://localhost:8080/BuzzwordBingo/actions");
    gameSocket.onmessage = function(msg) {
        cValue = document.getElementById("out").value;
        document.getElementById("out").value = cValue + msg.data + "\n";
    }
    document.getElementById("connect").setAttribute("disabled", "disabled");
    document.getElementById("disconnect").removeAttribute("disabled");
}

function disconnect() {
    gameSocket.close();
    document.getElementById("out").value = "";
    document.getElementById("disconnect").setAttribute("disabled", "disabled");
    document.getElementById("connect").removeAttribute("disabled");
}

function sendMessage() {
    gameSocket.send(document.getElementById("msg").value);
}

function sendAction (action) {
    gameSocket.send(action)
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