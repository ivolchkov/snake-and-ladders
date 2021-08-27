package com.demo.service.mapper.impl;

import com.demo.dto.GameStatus;
import com.demo.dto.TokenDTO;
import com.demo.entity.TokenEntity;
import com.demo.service.mapper.Mapper;
import org.springframework.stereotype.Component;

@Component
public class TokenMapper implements Mapper {
    @Override
    public TokenEntity mapDtoToEntity(TokenDTO dto) {
        return dto == null ? null : new TokenEntity(
                dto.getId(),
                dto.getCurrentPosition()
        );
    }

    @Override
    public TokenDTO mapEntityToDto(TokenEntity entity, GameStatus gameStatus) {
        return entity == null ? null : new TokenDTO(
                entity.getId(),
                entity.getCurrentPosition(),
                gameStatus
        );
    }
}
