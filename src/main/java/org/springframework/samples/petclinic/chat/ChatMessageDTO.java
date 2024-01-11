package org.springframework.samples.petclinic.chat;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChatMessageDTO {

    Integer id;
    String content;
    String source_user;
    Integer game_id;

    public ChatMessageDTO(){}

    public ChatMessageDTO(ChatMessage cm){
        this.id = cm.getId();
        this.content = cm.getContent();
        this.source_user = cm.getSource_user();
        this.game_id = cm.getGame().getId();
    }
    
}
