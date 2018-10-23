package net.raeesaamir.coinz.wallet;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import net.raeesaamir.coinz.R;

import java.util.Arrays;
import java.util.List;

public class WalletFragment extends Fragment {

    private static final List<Integer> LOREM_IPSUM = Arrays.asList(1,2,3,4,5,6,7,8,9,10);
    private View view;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.wallet_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.view = view;
        populateWallet();
    }

    private void populateWallet() {
        ListView wallet = view.findViewById(R.id.wallet_items);
        ArrayAdapter<Integer> integerArrayAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, LOREM_IPSUM);
        wallet.setAdapter(integerArrayAdapter);
    }
}
