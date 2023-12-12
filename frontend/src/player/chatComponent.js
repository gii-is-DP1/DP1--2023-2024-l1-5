import React, { useState, useEffect } from 'react';
import io from 'socket.io-client';

const ChatComponent = () => {
    const [messages, setMessages] = useState([]);
    const [inputMessage, setInputMessage] = useState('');
    const [socket, setSocket] = useState(null);

    useEffect(() => {
        const newSocket = io('http://localhost:8080/ws'); // Cambia la URL por tu servidor WebSocket

        // Eventos para manejar los mensajes entrantes
        newSocket.on('chat message', (msg) => {
            setMessages([...messages, { text: msg, sender: 'other' }]);
        });

        setSocket(newSocket);

        return () => {
            newSocket.disconnect();
        };
    }, [messages]);

    const handleKeyDown = (event) => {
        if (event.key === 'Enter' && inputMessage.trim() !== '') {
            event.preventDefault();

            // Enviar el mensaje al servidor
            socket.emit('chat message', inputMessage);

            const newMessage = {
                text: inputMessage,
                sender: 'user',
            };

            setMessages([...messages, newMessage]);
            setInputMessage('');
        }
    };

    const handleInputChange = (event) => {
        setInputMessage(event.target.value);
    };

    return (
        <div className="chat-container">
            <div className="messages">
                {messages.map((message, index) => (
                    <div
                        key={index}
                        className={message.sender === 'user' ? 'user-message' : 'other-user-message'}
                    >
                        {message.text}
                    </div>
                ))}
            </div>
            <div className="input-container">
                <input
                    type="text"
                    value={inputMessage}
                    onChange={handleInputChange}
                    onKeyDown={handleKeyDown}
                    className="chat-input"
                    placeholder="Type a message..."
                />
            </div>
        </div>
    );
};

export default ChatComponent;
