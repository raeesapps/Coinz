package net.raeesaamir.coinz.menu;

import android.content.Context;
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
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import net.raeesaamir.coinz.R;
import net.raeesaamir.coinz.authentication.LoginController;

import java.util.Arrays;
import java.util.List;

public class MenuFragment extends Fragment {

    private static final List<MenuItem> MENU_ITEMS = Arrays.asList(MenuItem.values());
    private static final String WELCOME_STRING = "Welcome \n";

    private Context context;

    private View view;

    private FirebaseAuth mAuth;
    private FirebaseUser mUser;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.menu_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.view = view;
        this.mAuth = FirebaseAuth.getInstance();
        this.mUser = mAuth.getCurrentUser();

        populateListView();
        populateUserInformation();
    }

    private void populateUserInformation() {
        Preconditions.checkNotNull(mUser);

        TextView welcomeMessage = view.findViewById(R.id.welcomeMessage);
        welcomeMessage.setText(String.format("%s%s!", WELCOME_STRING, mUser.getDisplayName()));

        Button signOutButton = view.findViewById(R.id.signOut);
        signOutButton.setOnClickListener((View view) -> {
            mAuth.signOut();
            Intent login = new Intent(getContext(), LoginController.class);
            startActivity(login);
        });
    }

    /**
     * Populates the list UI with the menu options
     */
    private void populateListView() {
        ListView menu = view.findViewById(R.id.listView);

        List<String> items = Lists.newArrayList();
        MENU_ITEMS.forEach(x -> items.add(x.nameOfItem()));

        ArrayAdapter<String> adapter = new ArrayAdapter<>(context, android.R.layout.simple_list_item_checked, items);
        menu.setAdapter(adapter);

        List<Class<?>> controllers = Lists.newArrayList();
        MENU_ITEMS.forEach(x -> controllers.add(x.segueTo()));

        menu.setOnItemClickListener((AdapterView<?> adapterView, View view, int position, long l) -> {
            Class<?> controller = controllers.get(position);
            Intent activity = new Intent(getContext(), controller);
            startActivity(activity);
        });
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }
}
