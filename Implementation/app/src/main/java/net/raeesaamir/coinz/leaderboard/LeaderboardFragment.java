package net.raeesaamir.coinz.leaderboard;

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

public class LeaderboardFragment extends Fragment {

    private static final List<Integer> LOREM_IPSUM = Arrays.asList(1,2,3,4,5,6,7,8,9,10);
    private View view;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.view = view;
        populateScores();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.leaderboard_fragment, container, false);
    }

    private void populateScores() {
        ListView scores = view.findViewById(R.id.scoresFragment);
        ArrayAdapter<Integer> integerArrayAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, LOREM_IPSUM);
        scores.setAdapter(integerArrayAdapter);
    }
}
