package com.pczech.taskmanager.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data()
@AllArgsConstructor()
@NoArgsConstructor()
@Builder()
public class Message {
    private String content;
    private String author;
}
