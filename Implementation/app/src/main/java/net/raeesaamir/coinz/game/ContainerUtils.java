package net.raeesaamir.coinz.game;

import com.google.common.base.Preconditions;
import java.util.List;

public class ContainerUtils {

    public static void addCoin(List<String> coins, String coin) {
        Preconditions.checkNotNull(coin);

        coins.add(coin);
    }

    public static void removeCoin(List<String> coins, String coin) {
        Preconditions.checkNotNull(coin);
        Preconditions.checkArgument(coins.size() > 0);
        Preconditions.checkArgument(coins.contains(coin));

        coins.remove(coin);
    }

}
