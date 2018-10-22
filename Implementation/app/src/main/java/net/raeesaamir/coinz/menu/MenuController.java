package net.raeesaamir.coinz.menu;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import net.raeesaamir.coinz.R;

/**
 * MVC controller for the Menu.
 * @author raeesaamir
 */
public class MenuController extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_view);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new MenuFragment()).commit();
    }
}
