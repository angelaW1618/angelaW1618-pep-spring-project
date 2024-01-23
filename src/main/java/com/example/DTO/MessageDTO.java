package com.example.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;

public class MessageDTO {

    @JsonProperty("message_text")
    private String messageText; 

    public String getMessageText() {
        return messageText;
    }

    public void setMessageText(String messageText) {
        this.messageText = messageText;
    }
}
