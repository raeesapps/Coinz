package net.raeesaamir.coinz.menu;

import net.raeesaamir.coinz.about.AboutController;
import net.raeesaamir.coinz.game.GameController;
import net.raeesaamir.coinz.leaderboard.LeaderboardController;
import net.raeesaamir.coinz.messaging.MessagingController;
import net.raeesaamir.coinz.wallet.WalletController;

public enum MenuItem {

    PLAY(GameController.class),
    WALLET(WalletController.class),
    MESSAGING(MessagingController.class),
    LEADERBOARD(LeaderboardController.class),
    ABOUT(AboutController.class);

    private Class<?> segueTo;

    MenuItem(Class<?> segueTo) {
        this.segueTo = segueTo;
    }

    public Class<?> segueTo() {
        return segueTo;
    }

    public String nameOfItem() {
        return name().toLowerCase();
    }
}
