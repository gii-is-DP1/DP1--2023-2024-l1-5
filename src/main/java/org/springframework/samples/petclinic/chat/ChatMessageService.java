package org.springframework.samples.petclinic.chat;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;

@Service
public class ChatMessageService {

    ChatMessageRepository repo;

    @Autowired
    public ChatMessageService(ChatMessageRepository repo){
        this.repo=repo;
    }

    @Transactional(readOnly = true)
    public List<ChatMessage> getChatMessages(){
        return repo.findAll();
    }

    @Transactional(readOnly = true)
    public List<ChatMessage> getChatMessagesByGameId(Integer id){
        return repo.findChatMessagesByGameId(id);
    }

    @Transactional
    public ChatMessage saveChatMessage(ChatMessage newChatMessage) {
        return repo.save(newChatMessage);
    }

}
