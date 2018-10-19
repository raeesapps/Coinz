package net.raeesaamir.coinz.menu;

import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.common.collect.Lists;

import net.raeesaamir.coinz.R;

import java.util.List;

/**
 * MVC controller for the Menu.
 * @author raeesaamir
 */
public class MenuController extends AppCompatActivity {

    /**
     * The different options in the menu
     */
    private static final List<String> MENU_OPTIONS = Lists.newArrayList("Play", "Wallet", "Messaging", "Leaderboard", "About");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_view);
        populateListView();
    }

    /**
     * Populates the list UI with the menu options
     */
    private void populateListView() {
        ListView menu = findViewById(R.id.listView);
        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_checked, MENU_OPTIONS);
        menu.setAdapter(adapter);
    }
}
