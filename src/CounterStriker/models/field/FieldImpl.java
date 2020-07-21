package CounterStriker.models.field;

import CounterStriker.common.OutputMessages;
import CounterStriker.models.players.Player;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class FieldImpl implements Field {
    @Override
    public String start(Collection<Player> players) {
        List<Player> terrorist = players.stream()
                .filter(p -> p.getClass().getSimpleName().equals("Terrorist"))
                .collect(Collectors.toList());
        List<Player> counterTerrorist = players.stream()
                .filter(p -> p.getClass().getSimpleName().equals("CounterTerrorist"))
                .collect(Collectors.toList());


        while (true) {

            for (Player terror : terrorist) {
                for (Player counterT : counterTerrorist) {
                    if (counterT.isAlive() && terror.isAlive()) {
                        int damage = terror.getGun().fire();
                        counterT.takeDamage(damage);
                        List<Player> collect1 = counterTerrorist.stream().filter(Player::isAlive).collect(Collectors.toList());
                        if (collect1.isEmpty()) {
                            return OutputMessages.TERRORIST_WINS;
                        }
                    }

                }
            }

            for (Player counterT : counterTerrorist) {
                for (Player terror : terrorist) {
                    if (terror.isAlive() && counterT.isAlive()) {
                        int damage = counterT.getGun().fire();
                        terror.takeDamage(damage);

                        List<Player> collect = terrorist.stream().filter(Player::isAlive).collect(Collectors.toList());
                        if (collect.isEmpty()) {
                            return OutputMessages.COUNTER_TERRORIST_WINS;
                        }


                    }
                }
            }

        }
    }
}
