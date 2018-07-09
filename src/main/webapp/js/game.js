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
    let words = document.querySelectorAll(".word");

    if(gameStarted && !gameEnded){

        Array.from(words, function (word) {

            clickEventListener = word.addEventListener("click", function () {
                gameSocket.send(this.classList[1]);
            });
        });

    } else {
        Array.from(words, function (word) {

           word.removeEventListener("click", function () {
                gameSocket.send(this.classList[1]);
            });
        });
    }
}

function startGame(){
    infoSocket.send("gameStarted");
}


