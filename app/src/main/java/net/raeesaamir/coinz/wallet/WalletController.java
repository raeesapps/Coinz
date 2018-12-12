package net.raeesaamir.coinz.wallet;

import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import net.raeesaamir.coinz.R;
import net.raeesaamir.coinz.menu.MenuFragment;
import net.raeesaamir.coinz.messaging.UserListFragment;

import java.util.Objects;

/**
 * The controller class for the wallet section. This handles navigation to other fragments you can reach from the wallet section.
 *
 * @author raeesaamir
 */
public class WalletController extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.wallet_view);
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
                case R.id.wallet_nav_home:
                    selectedFragment = new MenuFragment();
                    break;
                case R.id.wallet_nav_user_list:
                    selectedFragment = new UserListFragment();
                    break;
                case R.id.wallet_nav_wallet:
                    selectedFragment = new WalletFragment();
                    break;
            }

            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, Objects.requireNonNull(selectedFragment)).commit();

            return true;
        });
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new WalletFragment()).commit();
    }
}
