package net.raeesaamir.coinz.messaging;

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
import net.raeesaamir.coinz.authentication.simple.SimpleUserManager;

import java.util.List;

public class UserListFragment extends Fragment {

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
        populateUserList();
    }

    private void populateUserList() {
        ListView usernamesList = view.findViewById(R.id.users);

        List<String> usernames = Lists.newArrayList();
        SimpleUserManager.USERS.forEach(x -> usernames.add(x.name()));

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, usernames);
        usernamesList.setAdapter(arrayAdapter);
        usernamesList.setOnItemClickListener((AdapterView<?> adapterView, View view, int position, long l) -> {
            String username = usernames.get(position);
            Intent intent = new Intent(getContext(), MessagingController.class);
            intent.putExtra("username", username);
            startActivity(intent);
        });
    }
}
