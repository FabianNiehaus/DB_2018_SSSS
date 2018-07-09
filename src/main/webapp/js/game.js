// Variables
let gameSocket;
let infoSocket;

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
        infoBox.val(infoBox.val() + "\n" + msg.data);
    };

}

function disconnect() {
    gameSocket.close();
    infoSocket.close();
}


// Sends the presed tiles as a String to the Server
function sendAction(action) {
    gameSocket.send(action)
}



