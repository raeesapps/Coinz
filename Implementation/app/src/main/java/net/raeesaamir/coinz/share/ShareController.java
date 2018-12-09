package net.raeesaamir.coinz.share;

import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import net.raeesaamir.coinz.R;
import net.raeesaamir.coinz.game.GameFragment;
import net.raeesaamir.coinz.leaderboard.LeaderboardFragment;
import net.raeesaamir.coinz.menu.MenuFragment;

import java.util.Objects;

/**
 * The controller class for the share section. This handles navigation to other fragments you can reach from the share section.
 *
 * @author raeesaamir
 */
public class ShareController extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.share_view);
        configureBottomNavigationBar();
    }

    /**
     * This method the bottom navigation bar which fragment to go to upon clicking the item.
     */
    private void configureBottomNavigationBar() {
        BottomNavigationView bottomNavigationView = findViewById(R.id.navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener((MenuItem item) -> {
            Fragment selectedFragment = null;

            switch (item.getItemId()) {
                case R.id.share_nav_game:
                    selectedFragment = new GameFragment();
                    break;
                case R.id.share_nav_home:
                    selectedFragment = new MenuFragment();
                    break;
                case R.id.share_nav_leaderboard:
                    selectedFragment = new LeaderboardFragment();
                    break;
            }

            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, Objects.requireNonNull(selectedFragment)).commit();

            return true;
        });
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new ShareFragment()).commit();
    }
}
