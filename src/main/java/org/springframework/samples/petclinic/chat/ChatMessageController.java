package org.springframework.samples.petclinic.chat;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.samples.petclinic.exceptions.BadRequestException;
import org.springframework.samples.petclinic.exceptions.ResourceNotFoundException;
import org.springframework.samples.petclinic.game.Game;
import org.springframework.samples.petclinic.game.GameService;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/chatMessages")
@Tag(name = "Chat", description = "The Chat API")
@SecurityRequirement(name = "bearerAuth")
public class ChatMessageController {
    
    private final ChatMessageService chatMessageService;
    private final GameService gameService;

    public ChatMessageController(ChatMessageService chatMessageService, GameService gameService) {
        this.chatMessageService = chatMessageService;
        this.gameService = gameService;
    }

    @GetMapping
    public ResponseEntity<List<ChatMessageDTO>> findAll() {
        List<ChatMessage> chatMessages = chatMessageService.getChatMessages();
        List<ChatMessageDTO> chatMessagesDTOs = chatMessages.stream().map(ChatMessageDTO::new).collect(Collectors.toList());
        return new ResponseEntity<>(chatMessagesDTOs, HttpStatus.OK);
    }

    @GetMapping("/{gameId}")
    public ResponseEntity<List<ChatMessageDTO>> findChatMessagesByGameId(@PathVariable("gameId") Integer id){
        List<ChatMessage> chatMessages=chatMessageService.getChatMessagesByGameId(id);
        if(chatMessages==null)
            throw new ResourceNotFoundException("ChatMessages with gameId " + id + " not found!");
        List<ChatMessageDTO> chatMessagesDTOs = chatMessages.stream().map(ChatMessageDTO::new).collect(Collectors.toList());
        return new ResponseEntity<List<ChatMessageDTO>>(chatMessagesDTOs, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<ChatMessageDTO> createChatMessage(@RequestBody @Valid ChatMessageDTO newChatMessageDTO, BindingResult br) {
        if (br.hasErrors()) {
            throw new BadRequestException(br.getAllErrors());
        }

        ChatMessage newChatMessage = convertToEntity(newChatMessageDTO);
        ChatMessage savedChatMessage = chatMessageService.saveChatMessage(newChatMessage);

        ChatMessageDTO savedChatMessageDTO = new ChatMessageDTO(savedChatMessage);

        return new ResponseEntity<>(savedChatMessageDTO, HttpStatus.CREATED);
    }

    private ChatMessage convertToEntity(ChatMessageDTO chatMessageDTO) {
        ChatMessage chatMessage = new ChatMessage();
        chatMessage.setContent(chatMessageDTO.getContent());
        chatMessage.setSource_user(chatMessageDTO.getSource_user());

        Game game = gameService.getGameById(chatMessageDTO.getGame_id()).get();
        chatMessage.setGame(game);

        return chatMessage;
    }

}
