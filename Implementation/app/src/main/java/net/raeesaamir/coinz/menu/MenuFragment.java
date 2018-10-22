package net.raeesaamir.coinz.menu;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.common.collect.Lists;

import net.raeesaamir.coinz.R;

import java.util.Arrays;
import java.util.List;

public class MenuFragment extends Fragment {

    private static final List<MenuItem> MENU_ITEMS = Arrays.asList(MenuItem.values());
    private View view;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.menu_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.view = view;
        populateListView();
    }

    /**
     * Populates the list UI with the menu options
     */
    private void populateListView() {
        ListView menu = view.findViewById(R.id.listView);

        List<String> items = Lists.newArrayList();
        MENU_ITEMS.stream().forEachOrdered(x -> items.add(x.nameOfItem()));

        ArrayAdapter adapter = new ArrayAdapter(getContext(), android.R.layout.simple_list_item_checked, items);
        menu.setAdapter(adapter);

        List<Class<?>> controllers = Lists.newArrayList();
        MENU_ITEMS.stream().forEachOrdered(x -> controllers.add(x.segueTo()));

        menu.setOnItemClickListener((AdapterView<?> adapterView, View view, int position, long l) -> {
            Class<?> controller = controllers.get(position);
            Intent activity = new Intent(getContext(), controller);
            startActivity(activity);
        });
    }
}
