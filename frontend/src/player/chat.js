import React, { useState, useEffect } from 'react';
import Stomp from 'stompjs';

const Chat = () => {
  const [stompClient, setStompClient] = useState(null);
  const [messages, setMessages] = useState([]);
  const [username, setUsername] = useState('');
  const [message, setMessage] = useState('');

  useEffect(() => {
    const connectToWebSocket = () => {
      const socket = new WebSocket('ws://localhost:8080/ws');
      const stomp = Stomp.over(socket);

      stomp.connect({}, frame => {
        setStompClient(stomp);
      });

      return () => {
        if (stompClient) {
          stompClient.disconnect();
        }
      };
    };

    connectToWebSocket();
  }, []);

  const handleSetUsername = () => {
    console.log('handleSetUsername');
    if (stompClient && stompClient.connected) {
      const chatMessage = {
        sender: username,
        type: 'JOIN',
      };
      stompClient.send('/app/chat.addUser', {}, JSON.stringify(chatMessage));
    }
  };
  
  const handleSendMessage = () => {
    console.log('handleSendMessage');
    if (stompClient && stompClient.connected) {
      const chatMessage = {
        content: message,
        sender: username,
        type: 'CHAT',
      };
      stompClient.send('/app/chat.sendMessage', {}, JSON.stringify(chatMessage));
      setMessage('');
    }
  };
  
  useEffect(() => {
    console.log('useEffect subscription');
    if (stompClient) {
      stompClient.subscribe('/topic/public', message => {
        const receivedMessage = JSON.parse(message.body);
        console.log('Received message:', receivedMessage);
        setMessages(prevMessages => [...prevMessages, receivedMessage]);
      });
    }
  }, [stompClient]);
  

  return (
    <div>
      <div>
        <input
          type="text"
          placeholder="Enter your username"
          onChange={(e) => setUsername(e.target.value)}
        />
        <button onClick={handleSetUsername}>Set Username</button>
      </div>
      <div>
        <div>
          {messages.map((msg, index) => (
            <div key={index}>
              {msg.sender}: {msg.content}
            </div>
          ))}
        </div>
        <div>
          <input
            type="text"
            placeholder="Type your message..."
            value={message}
            onChange={(e) => setMessage(e.target.value)}
          />
          <button onClick={handleSendMessage}>Send</button>
        </div>
      </div>
    </div>
  );
};

export default Chat;
