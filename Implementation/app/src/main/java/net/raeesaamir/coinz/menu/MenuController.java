package net.raeesaamir.coinz.menu;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;


import com.google.common.collect.Lists;

import net.raeesaamir.coinz.R;

import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

/**
 * MVC controller for the Menu.
 * @author raeesaamir
 */
public class MenuController extends AppCompatActivity {

    private static final List<MenuItem> MENU_ITEMS = Arrays.asList(MenuItem.values());

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

        List<String> items = Lists.newArrayList();
        MENU_ITEMS.stream().forEachOrdered(x -> items.add(x.nameOfItem()));

        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_checked, items);
        menu.setAdapter(adapter);

        List<Class<?>> controllers = Lists.newArrayList();
        MENU_ITEMS.stream().forEachOrdered(x -> controllers.add(x.segueTo()));


        menu.setOnItemClickListener((AdapterView<?> adapterView, View view, int position, long l) -> {
            Class<?> controller = controllers.get(position);
            Intent activity = new Intent(getApplicationContext(), controller);
            startActivity(activity);
        });
    }
}
