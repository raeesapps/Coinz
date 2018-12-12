package net.raeesaamir.coinz.menu;

import net.raeesaamir.coinz.about.AboutController;
import net.raeesaamir.coinz.game.GameController;
import net.raeesaamir.coinz.leaderboard.LeaderboardController;
import net.raeesaamir.coinz.messaging.UserListController;
import net.raeesaamir.coinz.share.ShareController;
import net.raeesaamir.coinz.wallet.WalletController;

/**
 * Represents an item in the game's menu.
 *
 * @author raeesaamir
 */
public enum MenuItem {

    /**
     * The play game section.
     */
    PLAY(GameController.class),

    /**
     * The wallet section.
     */
    WALLET(WalletController.class),

    /**
     * The messaging section.
     */
    MESSAGING(UserListController.class),

    /**
     * The leaderboard section.
     */
    LEADERBOARD(LeaderboardController.class),

    /**
     * The about section.
     */
    ABOUT(AboutController.class),

    /**
     * The share section.
     */
    SHARE(ShareController.class);

    /**
     * The class that you segue to when clicking the menu item, aka the class that controls the MenuItem.
     */
    private final Class<?> segueTo;

    /**
     * Creates a new MenuItem.
     *
     * @param segueTo - The class that controls the menu item.
     */
    MenuItem(Class<?> segueTo) {
        this.segueTo = segueTo;
    }

    /**
     * Gets the class that controls the menu item.
     *
     * @return The class object.
     */
    public Class<?> segueTo() {
        return segueTo;
    }

    /**
     * Formats the name of the menu item.
     *
     * @return A formatted name.
     */
    public String nameOfItem() {
        String name = name();
        name = name.substring(0, 1).toUpperCase() + name.substring(1).toLowerCase();
        return name;
    }
}
