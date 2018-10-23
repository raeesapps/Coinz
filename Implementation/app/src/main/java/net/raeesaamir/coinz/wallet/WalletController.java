package net.raeesaamir.coinz.wallet;

import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

import net.raeesaamir.coinz.R;
import net.raeesaamir.coinz.about.AboutFragment;
import net.raeesaamir.coinz.game.GameFragment;
import net.raeesaamir.coinz.menu.MenuFragment;
import net.raeesaamir.coinz.messaging.MessagingFragment;

public class WalletController extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.wallet_view);
        configureBottomNavigationBar();
    }

    private void configureBottomNavigationBar() {
        BottomNavigationView bottomNavigationView = findViewById(R.id.navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener((MenuItem item) -> {
            Fragment selectedFragment = null;

            switch(item.getItemId()) {
                case R.id.wallet_nav_home:
                    selectedFragment = new MenuFragment();
                    break;
                case R.id.wallet_nav_messaging:
                    selectedFragment = new MessagingFragment();
                    break;
                case R.id.wallet_nav_wallet:
                    selectedFragment = new WalletFragment();
                    break;
            }

            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, selectedFragment).commit();

            return true;
        });
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new WalletFragment()).commit();
    }
}
