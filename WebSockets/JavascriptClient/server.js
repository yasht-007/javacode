const WebSocket = require('ws')

const ws = new WebSocket('ws://localhost:8080/');

ws.on('open', function() {
  console.log('WebSocket connection opened');
  ws.send("Hello from Yash");

  ws.send("1 + 1 = 2");



});

ws.on('message', function(data) {
  console.log('Received message from server: ' + data);
});

ws.on('close', function() {
  console.log('WebSocket connection closed');
});

ws.on('error', function(error) {
  console.error('WebSocket error: ' + error);
});


