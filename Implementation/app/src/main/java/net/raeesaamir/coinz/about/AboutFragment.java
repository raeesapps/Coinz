package net.raeesaamir.coinz.about;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.VisibleForTesting;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import net.raeesaamir.coinz.R;

/**
 * The fragment that holds the meta information about the Coinz app.
 *
 * @author raeesaamir
 */
public class AboutFragment extends Fragment {

    /**
     * The meta information about the app such as why it was built and what it was built for.
     */
    @VisibleForTesting
    static final String ABOUT_TEXT = "Designed and Developed by Raees Aamir @ www.raeesaamir.net.\n\n" +
            "Coursework specification and GeoJSON data provided by Stephen Gilmore. \n\n" +
            "Coinz is an assessed piece of coursework for the BSc (Hons) Computer Science's Informatics Large Practical(INFR09051) class.";

    /**
     * The view returned from onViewCreated.
     */
    private View view;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.view = view;
        populateAboutText();
    }

    /**
     * Puts the meta information into a text view.
     */
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
