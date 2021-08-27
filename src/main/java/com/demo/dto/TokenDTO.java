package com.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class TokenDTO {
    private Integer id;
    private Integer currentPosition;
    private GameStatus gameStatus;

    public TokenDTO(Integer currentPosition) {
        this.currentPosition = currentPosition;
    }
}
