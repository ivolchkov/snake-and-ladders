package com.demo.controller;

import com.demo.dto.TokenDTO;
import com.demo.service.TokenService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/tokens")
@CrossOrigin
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class TokenController {
    private static final int INITIAL_TOKEN_POSITION = 1;

    private final TokenService tokenService;

    @GetMapping("/rollADie")
    public ResponseEntity<Integer> rollADie() {
        return ResponseEntity.ok(tokenService.generateRollNumber());
    }

    @PostMapping("/startGame")
    public ResponseEntity<TokenDTO> startGame() {
        return ResponseEntity.ok(tokenService.create(new TokenDTO(INITIAL_TOKEN_POSITION)));
    }

    @PutMapping("/{id}/{diceRollNumber}")
    public ResponseEntity<TokenDTO> moveToken(@PathVariable Integer id, @PathVariable Integer diceRollNumber) {
        return ResponseEntity.ok(tokenService.update(id, diceRollNumber));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> endGame(@PathVariable Integer id) {
        tokenService.deleteById(id);

        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }

}
