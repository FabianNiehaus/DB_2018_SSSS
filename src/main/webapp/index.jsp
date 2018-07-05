<!DOCTYPE html>
<html>

<head>
    <title>GEDOPLAN - WebSocketDemo</title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <script src="main.js" type="text/javascript"></script>
</head>

<body>
<h1>WebSocketDemo</h1>
<button type="submit" onclick="connect()" style="color: green" id="connect">verbinden</button>
<button type="submit" onclick="disconnect()" style="color: red" id="disconnect" disabled>trennen</button>
<hr/>
<input type="text" placeholder="nachricht eingeben" id="msg">
<button type="submit" onclick="sendMessage()">senden</button>
<br/>
<textarea cols="50" rows="10" disabled id="out"></textarea>
</body>

</html>