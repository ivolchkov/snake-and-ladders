package com.demo.service.impl;

import com.demo.dto.GameStatus;
import com.demo.dto.TokenDTO;
import com.demo.entity.TokenEntity;
import com.demo.exception.TokenNotFoundException;
import com.demo.repository.TokenRepository;
import com.demo.service.TokenService;
import com.demo.service.mapper.Mapper;
import org.junit.After;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.both;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.lessThan;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {DefaultTokenService.class})
public class DefaultTokenServiceUnitTest {
    private static final int ID = 1;

    @Rule
    public final ExpectedException exception = ExpectedException.none();

    @MockBean
    private TokenRepository repository;

    @MockBean
    private Mapper mapper;

    @Autowired
    private TokenService tokenService;

    @After
    public void resetMock() {
        reset(repository, mapper);
    }

    @Test
    public void shouldCreateToken() {
        when(repository.save(any(TokenEntity.class))).thenReturn(tokenEntity);
        when(mapper.mapDtoToEntity(any(TokenDTO.class))).thenReturn(tokenEntity);
        when(mapper.mapEntityToDto(any(TokenEntity.class), any(GameStatus.class))).thenReturn(tokenDTO);

        TokenDTO actual = tokenService.create(tokenDTO);

        verify(repository).save(any(TokenEntity.class));
        verify(mapper).mapDtoToEntity(any(TokenDTO.class));
        verify(mapper).mapEntityToDto(any(TokenEntity.class), any(GameStatus.class));

        assertThat(actual, is(tokenDTO));
    }

    @Test
    public void shouldThrowIllegalArgumentExceptionWhenTokenIsNull() {
        exception.expect(IllegalArgumentException.class);
        exception.expectMessage("Token is invalid. Please check your input parameters.");

        tokenService.create(null);
    }

    @Test
    public void shouldGenerateRollNumber() {
        int rollNumber = tokenService.generateRollNumber();

        assertThat(rollNumber, is(both(greaterThan(ID)).and(lessThan(6))));
    }

    @Test
    public void shouldUpdateTokenWhenStatusIsInProgress() {
        when(repository.findById(anyInt())).thenReturn(Optional.of(tokenEntity));
        when(repository.save(any(TokenEntity.class))).thenReturn(tokenEntity);
        when(mapper.mapEntityToDto(any(TokenEntity.class), any(GameStatus.class))).thenReturn(tokenDTO);

        TokenDTO actual = tokenService.update(ID, 6);

        verify(repository).findById(anyInt());
        verify(repository).save(any(TokenEntity.class));
        verify(mapper).mapEntityToDto(any(TokenEntity.class), GameStatus.IN_PROGRESS);

        assertThat(actual.getCurrentPosition(), is(7));
        assertThat(actual.getGameStatus(), is(GameStatus.IN_PROGRESS));
    }

    @Test
    public void shouldUpdateTokenWhenStatusIsWin() {
        TokenEntity entity = new TokenEntity(ID, 99);
        when(repository.findById(anyInt())).thenReturn(Optional.of(entity));
        when(repository.save(any(TokenEntity.class))).thenReturn(entity);
        when(mapper.mapEntityToDto(any(TokenEntity.class), any(GameStatus.class))).thenReturn(tokenDTO);

        TokenDTO actual = tokenService.update(ID, ID);

        verify(repository).findById(anyInt());
        verify(repository).save(any(TokenEntity.class));
        verify(mapper).mapEntityToDto(any(TokenEntity.class), GameStatus.WIN);

        assertThat(actual.getCurrentPosition(), is(100));
        assertThat(actual.getGameStatus(), is(GameStatus.WIN));
    }

    @Test
    public void shouldUpdateTokenWhenStatusIsLose() {
        TokenEntity entity = new TokenEntity(ID, 97);
        when(repository.findById(anyInt())).thenReturn(Optional.of(entity));
        when(repository.save(any(TokenEntity.class))).thenReturn(entity);
        when(mapper.mapEntityToDto(any(TokenEntity.class), any(GameStatus.class))).thenReturn(tokenDTO);

        TokenDTO actual = tokenService.update(ID, 4);

        verify(repository).findById(anyInt());
        verify(repository).save(any(TokenEntity.class));
        verify(mapper).mapEntityToDto(any(TokenEntity.class), GameStatus.LOSE);

        assertThat(actual.getCurrentPosition(), is(97));
        assertThat(actual.getGameStatus(), is(GameStatus.LOSE));
    }

    @Test
    public void shouldThrowIllegalArgumentExceptionWhenIdIsNull() {
        exception.expect(IllegalArgumentException.class);
        exception.expectMessage("Id is invalid.");

        tokenService.update(null, ID);
    }

    @Test
    public void shouldThrowIllegalArgumentExceptionWhenCurrentPositionIsNull() {
        exception.expect(IllegalArgumentException.class);
        exception.expectMessage("Current position is invalid.");

        tokenService.update(ID, null);
    }

    @Test
    public void shouldThrowIllegalArgumentExceptionWhenCurrentPositionIsLessThanZero() {
        exception.expect(IllegalArgumentException.class);
        exception.expectMessage("Current position is invalid.");

        tokenService.update(ID, -5);
    }

    @Test
    public void shouldThrowIllegalArgumentExceptionWhenCurrentPositionIsGreaterThanSix() {
        exception.expect(IllegalArgumentException.class);
        exception.expectMessage("Current position is invalid.");

        tokenService.update(ID, 7);
    }

    @Test
    public void shouldThrowTokenIsNotFoundExceptionWhenIdIsWrong() {
        exception.expect(TokenNotFoundException.class);
        exception.expectMessage("Token is not found by this id");

        when(repository.findById(ID)).thenReturn(Optional.empty());

        tokenService.update(ID, 4);
    }

    @Test
    public void shouldDeleteTokenById() {
        tokenService.deleteById(ID);

        verify(repository).deleteById(ID);
    }

    @Test
    public void shouldThrowIllegalArgumentExceptionWhenIdIsNullWhileDeletion() {
        exception.expect(IllegalArgumentException.class);
        exception.expectMessage("Id is invalid.");

        tokenService.deleteById(null);
    }

    private final TokenEntity tokenEntity = new TokenEntity(ID, 1);

    private final TokenDTO tokenDTO = new TokenDTO(ID, 1, GameStatus.IN_PROGRESS);
}