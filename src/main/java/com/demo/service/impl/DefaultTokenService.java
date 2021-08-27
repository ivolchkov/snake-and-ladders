package com.demo.service.impl;

import com.demo.dto.TokenDTO;
import com.demo.repository.TokenRepository;
import com.demo.service.TokenService;
import com.demo.service.mapper.Mapper;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class DefaultTokenService implements TokenService {
    private final TokenRepository repository;
    private final Mapper tokenMapper;

    @Override
    public TokenDTO create(TokenDTO token) {
        return null;
    }

    @Override
    public int generateRollNumber() {
        return 0;
    }

    @Override
    public TokenDTO update(Integer id, Integer diceRollNumber) {
        return null;
    }

    @Override
    public void deleteById(Integer id) {

    }
}
