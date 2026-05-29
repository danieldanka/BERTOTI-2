package org.example;

import org.example.Game;
import org.example.GameRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/games")
public class GameController {

    private final GameRepository repository;

    public GameController(GameRepository repository) {
        this.repository = repository;
    }

    // GET todos
    @GetMapping
    public List<Game> getGames() {
        return repository.findAll();
    }

    // GET por ID
    @GetMapping("/{id}")
    public Game getGameById(@PathVariable Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Jogo não encontrado"));
    }

    // POST criar
    @PostMapping
    public Game createGame(@RequestBody Game game) {
        return repository.save(game);
    }

    // PUT atualizar
    @PutMapping("/{id}")
    public Game updateGame(@PathVariable Long id, @RequestBody Game newGame) {
        return repository.findById(id)
                .map(game -> {
                    game.setName(newGame.getName());
                    game.setGenre(newGame.getGenre());
                    game.setPlatform(newGame.getPlatform());
                    game.setReleaseYear(newGame.getReleaseYear());
                    return repository.save(game);
                })
                .orElseThrow(() -> new RuntimeException("Jogo não encontrado"));
    }

    // DELETE remover
    @DeleteMapping("/{id}")
    public void deleteGame(@PathVariable Long id) {
        repository.deleteById(id);
    }
}