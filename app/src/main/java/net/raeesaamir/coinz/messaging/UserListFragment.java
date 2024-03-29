package net.raeesaamir.coinz.messaging;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.android.gms.tasks.Task;
import com.google.common.collect.Lists;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import net.raeesaamir.coinz.CoinzApplication;
import net.raeesaamir.coinz.R;
import net.raeesaamir.coinz.menu.MenuFragment;

import java.util.List;
import java.util.Objects;

/**
 * Renders the list of registered players you can message.
 *
 * @author raeesaamir
 */
public class UserListFragment extends Fragment {

    /**
     * The view returned from onCreateView.
     */
    private View view;

    /**
     * The context of the fragment.
     */
    private Context context;

    /**
     * The parent activity.
     */
    private FragmentActivity activity;

    /**
     * The authenticated user.
     */
    private FirebaseUser mUser;

    /**
     * The loading dialog.
     */
    private ProgressDialog dialog;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.user_list_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.view = view;
        dialog = ProgressDialog.show(context, "",
                "Loading. Please wait...", true);
        dialog.show();

        if (!CoinzApplication.isInternetConnectionAvailable(context)) {
            dialog.dismiss();
            activity.getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new MenuFragment()).commit();

            AlertDialog internetConnectionHangedDialog = new AlertDialog.Builder(activity).setTitle("Game").setMessage("Your internet connection has hanged. Try again later when it's backup and running!").setPositiveButton("Close", (x, y) -> {
            }).create();
            internetConnectionHangedDialog.show();
        } else {
            FirebaseAuth mAuth = FirebaseAuth.getInstance();
            this.mUser = mAuth.getCurrentUser();
            populateUserList();

        }
    }

    /**
     * Gets all the registered users from the database and puts them into the list view.
     */
    private void populateUserList() {
        ListView usernamesList = view.findViewById(R.id.usersListView);

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference users = db.collection("Users");

        users.get().addOnCompleteListener((@NonNull Task<QuerySnapshot> task) -> {
            if (task.isSuccessful()) {

                List<String> usernames = Lists.newArrayList();
                for (DocumentSnapshot documentSnapshot : Objects.requireNonNull(task.getResult())) {

                    if (!documentSnapshot.contains("uid") || !documentSnapshot.contains("email") || !documentSnapshot.contains("displayName")) {
                        continue;
                    }

                    String uid = (String) documentSnapshot.get("uid");

                    if (Objects.requireNonNull(uid).equals(mUser.getUid())) {
                        continue;
                    }

                    String displayName = (String) documentSnapshot.get("displayName");
                    usernames.add(displayName);
                }


                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(context, android.R.layout.simple_list_item_1, usernames);
                usernamesList.setAdapter(arrayAdapter);
                usernamesList.setOnItemClickListener((AdapterView<?> adapterView, View view, int position, long l) -> {
                    String username = usernames.get(position);
                    Intent intent = new Intent(context, MessagingController.class);
                    intent.putExtra("username", username);
                    startActivity(intent);
                });
            }

            dialog.dismiss();
        });
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;

        if (context instanceof FragmentActivity) {
            this.activity = (FragmentActivity) context;
        }
    }
}
