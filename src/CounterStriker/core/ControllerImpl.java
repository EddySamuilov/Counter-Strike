package CounterStriker.core;

import CounterStriker.common.ExceptionMessages;
import CounterStriker.common.OutputMessages;
import CounterStriker.models.field.Field;
import CounterStriker.models.field.FieldImpl;
import CounterStriker.models.guns.Gun;
import CounterStriker.models.guns.Pistol;
import CounterStriker.models.guns.Rifle;
import CounterStriker.models.players.CounterTerrorist;
import CounterStriker.models.players.Player;
import CounterStriker.models.players.Terrorist;
import CounterStriker.repositories.GunRepository;
import CounterStriker.repositories.PlayerRepository;
import CounterStriker.repositories.Repository;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ControllerImpl implements Controller {
    private Repository<Gun> guns;
    private Repository<Player> players;
    private Field field;

    public ControllerImpl() {
        this.players = new PlayerRepository();
        this.guns = new GunRepository();
        this.field = new FieldImpl();
    }

    @Override
    public String addGun(String type, String name, int bulletsCount) {
        Gun gun;

        switch (type){
            case "Pistol":
                gun = new Pistol(name, bulletsCount);
                break;
            case "Rifle":
                gun = new Rifle(name, bulletsCount);
                break;
            default:
                throw new IllegalArgumentException(ExceptionMessages.INVALID_GUN_TYPE);
        }

        this.guns.add(gun);

        return String.format(OutputMessages.SUCCESSFULLY_ADDED_GUN, name);
    }

    @Override
    public String addPlayer(String type, String username, int health, int armor, String gunName) {
        Player player;
        Gun gun = guns.findByName(gunName);

        if (gun == null) {
            throw new NullPointerException(ExceptionMessages.GUN_CANNOT_BE_FOUND);
        }

        switch (type){
            case "Terrorist":
                player = new Terrorist(username, health, armor, gun);
                break;
            case "CounterTerrorist":
                player = new CounterTerrorist(username, health, armor, gun);
                break;
            default:
                throw new IllegalArgumentException(ExceptionMessages.INVALID_PLAYER_TYPE);
        }

        this.players.add(player);

        return String.format(OutputMessages.SUCCESSFULLY_ADDED_PLAYER, username);
    }

    @Override
    public String startGame() {
        List<Player> suitablePlayers = this.players.getModels().stream()
                .filter(Player::isAlive)
                .collect(Collectors.toList());

        return this.field.start(suitablePlayers);
    }

    @Override
    public String report() {
        List<Player> sorted = this.players.getModels().stream()
                .sorted((p1, p2) -> p1.getUsername().compareTo(p2.getUsername()))
                .sorted((p1, p2) -> Integer.compare(p2.getHealth(), p1.getHealth()))
                .sorted((p1, p2) -> p1.getClass().getSimpleName().compareTo(p2.getClass().getSimpleName()))
                .collect(Collectors.toList());

        StringBuilder sb = new StringBuilder();

        for (Player player : sorted) {
            sb.append(player.toString());
        }
        
        return sb.toString().trim();
    }
}
