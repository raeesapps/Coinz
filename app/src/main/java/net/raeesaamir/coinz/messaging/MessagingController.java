package net.raeesaamir.coinz.messaging;

import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;

import net.raeesaamir.coinz.R;
import net.raeesaamir.coinz.game.GameFragment;
import net.raeesaamir.coinz.menu.MenuFragment;

import java.util.Objects;

/**
 * The controller class for the messaging section. This handles navigation to other fragments you can reach from the messaging section.
 *
 * @author raeesaamir
 */
public class MessagingController extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.messaging_view);
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
                case R.id.messaging_nav_users:
                    selectedFragment = new UserListFragment();
                    break;
                case R.id.messaging_nav_home:
                    selectedFragment = new MenuFragment();
                    break;
                case R.id.messaging_nav_game:
                    selectedFragment = new GameFragment();
                    break;
            }

            Log.d("[MessagingController]", "FRAGMENT STATE " + Boolean.toString(selectedFragment == null));

            Log.d("[MessagingController", "FRAGMENT STATE CONTAINER " + Boolean.toString(getSupportFragmentManager().findFragmentById(R.id.fragment_container) == null));

            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, Objects.requireNonNull(selectedFragment)).commit();

            return true;
        });

        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new MessagingFragment()).commit();
    }
}
