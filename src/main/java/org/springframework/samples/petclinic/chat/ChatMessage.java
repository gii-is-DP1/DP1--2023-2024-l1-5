package org.springframework.samples.petclinic.chat;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.samples.petclinic.game.Game;
import org.springframework.samples.petclinic.model.BaseEntity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@Entity
@Table(name = "chat_messages")
public class ChatMessage extends BaseEntity{

    @Column(name = "content")
    @NotNull
    private String content;

    @Column(name = "source_user")
    @NotNull
    private String source_user;

    @Column(name = "message_date")
    @CreationTimestamp
    private LocalDateTime message_date;

    @JoinColumn(name = "game_id", referencedColumnName = "id")
    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Game game;
}
