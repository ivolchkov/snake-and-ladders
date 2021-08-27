package com.demo.service.impl;

import com.demo.dto.GameStatus;
import com.demo.dto.TokenDTO;
import com.demo.entity.TokenEntity;
import com.demo.exception.TokenNotFoundException;
import com.demo.repository.TokenRepository;
import com.demo.service.TokenService;
import com.demo.service.mapper.Mapper;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
@Log4j
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class DefaultTokenService implements TokenService {
    private static final int MIN_BOUND = 1;
    private static final int MAX_BOUND = 5;
    private static final String ID_ERROR_MESSAGE = "Id is invalid.";
    private static final String DICE_ROLL_NUMBER_ERROR_MESSAGE = "Dice roll number is invalid.";
    private static final String TOKEN_IS_NOT_FOUND_ERROR_MESSAGE = "Token is not found by this id";

    private final TokenRepository repository;
    private final Mapper tokenMapper;

    @Override
    public TokenDTO create(TokenDTO token) {
        validateNotNull(token, "Token is invalid. Please check your input parameters.");
        return tokenMapper.mapEntityToDto(saveToken(token), GameStatus.IN_PROGRESS);
    }

    private TokenEntity saveToken(TokenDTO token) {
        return repository.save(tokenMapper.mapDtoToEntity(token));
    }

    @Override
    public int generateRollNumber() {
        Random random = new Random();
        return random.nextInt(MAX_BOUND) + MIN_BOUND;
    }

    @Override
    public TokenDTO update(Integer id, Integer diceRollNumber) {
        validateNotNull(id, ID_ERROR_MESSAGE);
        validateNotNull(diceRollNumber, DICE_ROLL_NUMBER_ERROR_MESSAGE);
        validateDiceRollRange(diceRollNumber);

        TokenEntity entity = repository.findById(id).orElseThrow(() -> {
            log.warn(TOKEN_IS_NOT_FOUND_ERROR_MESSAGE);
            return new TokenNotFoundException(TOKEN_IS_NOT_FOUND_ERROR_MESSAGE);
        });
        int newPosition = entity.getCurrentPosition() + diceRollNumber;

        return updateTokenState(entity, newPosition);
    }

    private TokenDTO updateTokenState(TokenEntity entity, int newPosition) {
        GameStatus gameStatus;

        if (newPosition == 100) {
            gameStatus = GameStatus.WIN;
            entity.setCurrentPosition(newPosition);
        } else if (newPosition > 100) {
            gameStatus = GameStatus.LOSE;
        } else {
            gameStatus = GameStatus.IN_PROGRESS;
            entity.setCurrentPosition(newPosition);
        }

        return tokenMapper.mapEntityToDto(repository.save(entity), gameStatus);
    }

    private void validateDiceRollRange(Integer diceRollNumber) {
        if (diceRollNumber < 1 || diceRollNumber > 6) {
            log.warn("Dice roll number is out of range. It should be greater than zero and less than seven. Current value: " + diceRollNumber);
            throw new IllegalArgumentException(DICE_ROLL_NUMBER_ERROR_MESSAGE);
        }
    }

    @Override
    public void deleteById(Integer id) {
        validateNotNull(id, ID_ERROR_MESSAGE);

        repository.deleteById(id);
    }

    private void validateNotNull(Object object, String errorMessage) {
        if (object == null) {
            log.warn("Exception occurs during validation of input parameters." + errorMessage);
            throw new IllegalArgumentException(errorMessage);
        }
    }
}
