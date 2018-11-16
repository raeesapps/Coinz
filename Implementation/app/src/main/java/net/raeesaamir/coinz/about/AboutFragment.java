package net.raeesaamir.coinz.about;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import net.raeesaamir.coinz.R;

public class AboutFragment extends Fragment {

    private static final String ABOUT_TEXT = "Designed and Developed by Raees Aamir @ www.raeesaamir.net.\n\n" +
            "Coursework specification and GeoJSON data provided by Stephen Gilmore. \n\n" +
            "Coinz is an assessed piece of coursework for the BSc (Hons) Computer Science's Informatics Large Practical(INFR09051) class.";

    private View view;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.view = view;
        populateAboutText();
    }

    private void populateAboutText() {
        TextView aboutTextView = view.findViewById(R.id.aboutTextFragment);
        aboutTextView.setText(ABOUT_TEXT);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.about_fragment, container, false);
    }
}
