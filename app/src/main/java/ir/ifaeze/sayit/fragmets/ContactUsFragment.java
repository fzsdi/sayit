package ir.ifaeze.sayit.fragmets;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageButton;

import ir.ifaeze.sayit.R;
import ir.ifaeze.sayit.ReportBugActivity;

public class ContactUsFragment extends Fragment {
    ImageButton btnReportBug;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_contact_us, container, false);
        InputMethodManager inputMethodManager = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
        initiews(view);
        return view;
    }

    View.OnClickListener reportBug = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent i = new Intent(getActivity(), ReportBugActivity.class);
            startActivity(i);
        }
    };

    private void initiews(View view) {
        btnReportBug = view.findViewById(R.id.imageButton);
        btnReportBug.setOnClickListener(reportBug);
    }
}
