// Variables
let gameSocket;
let infoSocket;

let gameStarted;
let gameEnded;

let clickEventListener;

//Methods

function connect() {
    gameSocket = new WebSocket("ws://localhost:8080/BuzzwordBingo/game");
    infoSocket = new WebSocket("ws://localhost:8080/BuzzwordBingo/info");

    gameSocket.onmessage = function (msg) {

        let element = ".word." + msg.data;
        $(element).css('background-color', 'green');

    };

    infoSocket.onmessage = function (msg) {
        if(msg.data === "Spiel gestartet!"){
            gameStarted = true;
            checkGameState();
        }if(msg.data === "Das Spiel ist vorbei! Gewonnen haben: "){
            gameEnded = true;
            checkGameState();
        }

        const infoBox = $("#info");
        infoBox.val(infoBox.val() + "\n" + msg.data);
    };

}

function disconnect() {
    gameSocket.close();
    infoSocket.close();
}

function checkGameState(){
    if(gameStarted && !gameEnded){
        clickEventListener = window.addEventListener("DOMContentLoaded", function () {

            let words = document.querySelectorAll(".word");

            Array.from(words, function (word) {

                word.addEventListener("click", function () {
                    sendAction(this.classList[1]);
                });
            });
        });
    } else {
        window.removeEventListener("DOMContentLoaded", clickEventListener);
    }
}

function startGame(){
    infoSocket.send("gameStarted");
}


