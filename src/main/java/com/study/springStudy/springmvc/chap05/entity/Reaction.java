package com.study.springStudy.springmvc.chap05.entity;


import lombok.*;

import java.time.LocalDateTime;

@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Builder
public class Reaction {

    private String reactionId;
    private long boardNo;
    private String account;
    private ReactionType reactionType;
    private LocalDateTime reactionDate;
}
