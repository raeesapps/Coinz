package net.raeesaamir.coinz;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.Arrays;
import java.util.List;

public class LeaderboardController extends AppCompatActivity {

    private static final List<Integer> LOREM_IPSUM = Arrays.asList(1,2,3,4,5,6,7,8,9,10);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.leaderboard_view);
        populateScores();
    }

    private void populateScores() {
        ListView scores = findViewById(R.id.scores);
        ArrayAdapter<Integer> integerArrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, LOREM_IPSUM);
        scores.setAdapter(integerArrayAdapter);
    }
}
