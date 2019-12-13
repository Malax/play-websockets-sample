document.addEventListener('DOMContentLoaded', function (event) {
    var dateSocket = new WebSocket(window.websocketUrl);
    dateSocket.onmessage = onMessageHandler;
});

function onMessageHandler(event) {
    document.getElementById("ping").innerHTML = "Last ping: " + event.data;
}
