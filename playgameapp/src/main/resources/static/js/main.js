// Try to set up WebSocket connection with the handshake at "http://localhost:8080/stomp"
let sock = new SockJS("http://localhost:8080/stomp");

// Create a new StompClient object with the WebSocket endpoint
let client = Stomp.over(sock);
var i = 10;
// Start the STOMP communications, provide a callback for when the CONNECT frame arrives.
client.connect({}, frame => {
    // Subscribe to "/topic/messages". Whenever a message arrives add the text in a list-item element in the unordered list.
    client.subscribe("/topic/messages", payload => {
        let input = JSON.parse(payload.body);
        addMessage(input.from, input.text);
    });

});

// Take the value in the ‘message-input’ text field and send it to the server with empty headers.
function sendMessage() {

    let input = document.getElementById("comment");
    let message = input.value;
    input.value = "";
    let name = document.getElementById("name");
    client.send('/app/chat', {}, JSON.stringify({text: message, from: name.value}));

}

function getAllMessageHeight() {
    let allMessages = document.getElementsByClassName("message");
    let result = 0;
    for (let i = 0; i < allMessages.length; ++i) {
        result += allMessages[i].offsetHeight;
    }
    return result;
}

function addMessage(name, text) {
        let message_list = document.getElementById('message_list');

    message_list.innerHTML = message_list.innerHTML +
            `<div id="message${++i}" 
class="bg-primary text-light col-md-9 message" style="visibility:hidden">
                    <strong class="text-warning">From ${name}:</strong> 
                    &nbsp ${text}        
                 </div>`;

                 
    while (getAllMessageHeight() >= 270) {
        let allMessages = document.getElementsByClassName("message");
        document.getElementById(allMessages[0].id).remove();
    }
    
    document.getElementById("message"+i).style.visibility = "visible";
}

document.getElementById("comment")
        .addEventListener("keypress", function (event) {

            if (event.key === 'Enter') {
                document.getElementById("button").click();
            }
        });


function getMessages() {
    fetch('/api/message').then(
            function (response) {
                response.json().then(data => {
                    for (let i = data.length - 1; i >= 0; --i) {
                        addMessage(data[i].sender, data[i].message);
                    }
                });
            }
    );
}