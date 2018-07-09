// Variables
let gameSocket;
let infoSocket;

let gameStarted;
let gameEnded;

let clickEventListener;

let state = true;

//Methods

function connect() {
    gameSocket = new WebSocket("ws://localhost:8080/BuzzwordBingo/game");
    infoSocket = new WebSocket("ws://localhost:8080/BuzzwordBingo/info");

    gameSocket.onmessage = function (msg) {

        let element = ".word." + msg.data;
        $(element).css('background-color', 'green');

    };

    infoSocket.onmessage = function (msg) {
        const infoBox = $("#info");

        if(msg.data === "Das Spiel wurde gestartet!"){
            gameStarted = true;
            checkGameState();
            setStartButtonState(false);
        } else if (msg.data === "Das Spiel ist vorbei! Gewonnen haben: ") {
            gameEnded = true;
            checkGameState();
        }
        infoBox.html("<p>" + msg.data + "</p>" + infoBox.html());
    };

}

function disconnect() {
    gameSocket.close();
    infoSocket.close();
}

function setStartButtonState(state){

    let startGameButton = $("#startGameButton");

    if(state) startGameButton.prop("disabled",false);
    if(!state)  startGameButton.prop("disabled",true);

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


