package com.example.meuprojetobackend;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class fcxMainController {

    private final TournamentService tournamentService = new TournamentService();
    private final PlayerService playerService = new PlayerService();

    public fcxMainController() {
        //Inicializando com dois campeonatos locais
        tournamentService.saveTournament(new Tournament(1, "Campeonato Agosto", "2024-08-01"));
        tournamentService.saveTournament(new Tournament(2, "Campeonato Agosto Q2", "2024-08-10"));
    }

    @GetMapping("/tournaments")
    public ResponseEntity<List<Tournament>> getTournaments() {
        List<Tournament> tournaments = tournamentService.getAllTournaments();
        return new ResponseEntity<>(tournaments, HttpStatus.OK);
    }

    @GetMapping("/tournaments/{id}")
    public ResponseEntity<Tournament> getTournamentById(@PathVariable int id) {
        Optional<Tournament> tournament = tournamentService.getTournamentById(id);
        if (tournament.isPresent()) {
            return new ResponseEntity<>(tournament.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/tournaments")
    public ResponseEntity<String> registerTournament(@RequestBody Tournament tournament) {
        tournamentService.saveTournament(tournament);
        return new ResponseEntity<>("Tournament registered: " + tournament.getName(), HttpStatus.CREATED);
    }

    @PutMapping("/tournaments/{id}")
    public ResponseEntity<String> updateTournament(@PathVariable int id, @RequestBody Tournament updatedTournament) {
        Optional<Tournament> tournamentOptional = tournamentService.getTournamentById(id);
        if (tournamentOptional.isPresent()) {
            Tournament tournament = tournamentOptional.get();
            tournament.setName(updatedTournament.getName());
            tournament.setDate(updatedTournament.getDate());
            return new ResponseEntity<>("Tournament updated: " + tournament.getName(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Tournament not found", HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/tournaments/{id}")
    public ResponseEntity<String> deleteTournament(@PathVariable int id) {
        boolean isDeleted = tournamentService.deleteTournament(id);
        if (isDeleted) {
            return new ResponseEntity<>("Tournament deleted", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Tournament not found", HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/players")
    public ResponseEntity<List<Player>> getPlayers() {
        return new ResponseEntity<>(playerService.getAllPlayers(), HttpStatus.OK);
    }

    @GetMapping("/players/{id}")
    public ResponseEntity<Player> getPlayerById(@PathVariable int id) {
        Optional<Player> player = playerService.getPlayerById(id);
        return player.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                     .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping("/players")
    public ResponseEntity<String> registerPlayer(@RequestBody Player player) {
        playerService.savePlayer(player);
        return new ResponseEntity<>("Player registered: " + player.getName(), HttpStatus.CREATED);
    }

    @PutMapping("/players/{id}")
    public ResponseEntity<String> updatePlayer(@PathVariable int id, @RequestBody Player updatedPlayer) {
        Optional<Player> playerOptional = playerService.getPlayerById(id);
        if (playerOptional.isPresent()) {
            Player player = playerOptional.get();
            player.setName(updatedPlayer.getName());
            player.setAge(updatedPlayer.getAge());
            return new ResponseEntity<>("Player updated: " + player.getName(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Player not found", HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/players/{id}")
    public ResponseEntity<String> deletePlayer(@PathVariable int id) {
        boolean isDeleted = playerService.deletePlayer(id);
        if (isDeleted) {
            return new ResponseEntity<>("Player deleted", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Player not found", HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/tournament/results")
    public ResponseEntity<String> submitResults(@RequestBody String results) {
        return new ResponseEntity<>("Results submitted: " + results, HttpStatus.OK);
    }
    @PostMapping("/tournament/export")
    public ResponseEntity<String> submitResults(@RequestBody String results) {
        return new ResponseEntity<>("Results exported: " + results, HttpStatus.OK);
    }

    static class TournamentService {
        private List<Tournament> tournaments = new ArrayList<>();

        public List<Tournament> getAllTournaments() {
            return tournaments;
        }

        public void saveTournament(Tournament tournament) {
            tournaments.add(tournament);
        }

        public Optional<Tournament> getTournamentById(int id) {
            return tournaments.stream().filter(t -> t.getId() == id).findFirst();
        }

        public boolean deleteTournament(int id) {
            return tournaments.removeIf(t -> t.getId() == id);
        }
    }

    static class PlayerService {
        private List<Player> players = new ArrayList<>();

        public List<Player> getAllPlayers() {
            return players;
        }

        public void savePlayer(Player player) {
            players.add(player);
        }

        public Optional<Player> getPlayerById(int id) {
            return players.stream().filter(p -> p.getId() == id).findFirst();
        }

        public boolean deletePlayer(int id) {
            return players.removeIf(p -> p.getId() == id);
        }
    }

    static class Tournament {
        private int id;
        private String name;
        private String date;

        public Tournament(int id, String name, String date) {
            this.id = id;
            this.name = name;
            this.date = date;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }
    }

    static class Player {
        private int id;
        private String name;
        private int age;

        public Player(int id, String name, int age) {
            this.id = id;
            this.name = name;
            this.age = age;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }
    }
}
