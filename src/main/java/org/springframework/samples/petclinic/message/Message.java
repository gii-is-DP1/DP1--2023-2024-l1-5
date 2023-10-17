package org.springframework.samples.petclinic.message;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.springframework.samples.petclinic.model.BaseEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@Entity
@Table(name = "messages")
public class Message extends BaseEntity{

    @Column(name = "content")
    @NotNull
    private String content;

    @Column(name = "source_user")
    @NotNull
    private String source_user;

    @Column(name = "message_date")
    @CreationTimestamp
    private LocalDateTime message_date;
}
