package com.demo.service;

import com.demo.dto.TokenDTO;

public interface TokenService {
    TokenDTO create(TokenDTO token);
    int generateRollNumber();
    TokenDTO update(Integer id, Integer diceRollNumber);
    void deleteById(Integer id);
}
