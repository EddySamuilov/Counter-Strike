package CounterStriker.repositories;

import CounterStriker.common.ExceptionMessages;
import CounterStriker.models.players.Player;

import java.util.ArrayList;
import java.util.Collection;

public class PlayerRepository implements Repository<Player> {
    private Collection<Player> models;

    public PlayerRepository() {
        this.models = new ArrayList<>();
    }

    @Override
    public Collection<Player> getModels() {
        return this.models;
    }

    @Override
    public void add(Player model) {
        if (model == null){
            throw new NullPointerException(ExceptionMessages.INVALID_PLAYER_REPOSITORY);
        }
        this.models.add(model);
    }

    @Override
    public boolean remove(Player model) {
        return this.models.removeIf(p -> p.getUsername().equals(model.getUsername()));
    }

    @Override
    public Player findByName(String name) {
        return this.models.stream()
                .filter(g -> g.getUsername().equals(name))
                .findFirst()
                .orElse(null);
    }
}
