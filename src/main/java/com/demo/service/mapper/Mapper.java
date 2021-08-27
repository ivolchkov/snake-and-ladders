package com.demo.service.mapper;

import com.demo.dto.GameStatus;
import com.demo.dto.TokenDTO;
import com.demo.entity.TokenEntity;

public interface Mapper {
    TokenEntity mapDtoToEntity(TokenDTO dto);

    TokenDTO mapEntityToDto(TokenEntity entity, GameStatus gameStatus);
}
