package ir.ifaeze.sayit.fragmets;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;

import ir.ifaeze.sayit.MainActivity;
import ir.ifaeze.sayit.R;

import static ir.ifaeze.sayit.utilities.ConstantStrings.ARE_YOU_SURE;
import static ir.ifaeze.sayit.utilities.ConstantStrings.NO;
import static ir.ifaeze.sayit.utilities.ConstantStrings.NOTHING_TO_CLEAR;
import static ir.ifaeze.sayit.utilities.ConstantStrings.NOTHING_TO_READ;
import static ir.ifaeze.sayit.utilities.ConstantStrings.OK;
import static ir.ifaeze.sayit.utilities.ConstantStrings.YES;

public class SayItFragment extends Fragment {
    EditText edtTxtToSpeech;
    Button btnRead, btnClear;
    SeekBar skBarSpeed, skBarPitch;
    TextView txtReset, txtReset0;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_say_it, container, false);
        MainActivity mainActivity = (MainActivity) getActivity();
        initViews(view);
        mainActivity.initTextToSpeech(btnRead);
        return view;
    }

    View.OnClickListener readText = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String txt = edtTxtToSpeech.getText().toString();
            String txtTrim = txt.trim();
            if (txtTrim.length() != 0) {
                MainActivity mainActivity = (MainActivity) getActivity();
                mainActivity.readText(txt, skBarPitch, skBarSpeed);
                InputMethodManager inputMethodManager = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                inputMethodManager.hideSoftInputFromWindow(getView().getWindowToken(), 0);
            } else {
                AlertDialog.Builder alertBuilder = new AlertDialog.Builder(getContext());
                alertBuilder.setMessage(NOTHING_TO_READ)
                        .setCancelable(false)
                        .setNegativeButton(OK, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                InputMethodManager inputMethodManager = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                                inputMethodManager.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
                                edtTxtToSpeech.requestFocus();
                                dialog.cancel();
                            }
                        });
                alertBuilder.show();
            }
        }
    };

    SeekBar.OnSeekBarChangeListener handleResetText0 = new SeekBar.OnSeekBarChangeListener() {
        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            if (progress != 50) {
                txtReset0.setTextColor(getResources().getColor(R.color.colorLink));
                txtReset0.setEnabled(true);
            } else {
                txtReset0.setTextColor(getResources().getColor(R.color.colorDisable));
                txtReset0.setEnabled(false);
            }
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {

        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {

        }
    };

    SeekBar.OnSeekBarChangeListener handleResetText = new SeekBar.OnSeekBarChangeListener() {
        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            if (progress != 50) {
                txtReset.setTextColor(getResources().getColor(R.color.colorLink));
                txtReset.setEnabled(true);
            } else {
                txtReset.setTextColor(getResources().getColor(R.color.colorDisable));
                txtReset.setEnabled(false);
            }
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {

        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {

        }
    };

    private void resetSeekBar(SeekBar seekBar) {
        seekBar.setProgress(50);
    }

    View.OnClickListener resetSpeed = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            resetSeekBar(skBarSpeed);
        }
    };

    View.OnClickListener resetPitch = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            resetSeekBar(skBarPitch);
        }
    };

    View.OnClickListener clearText = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String text = edtTxtToSpeech.getText().toString().trim();
            if (text.length() != 0) {
                AlertDialog.Builder alertBuilder = new AlertDialog.Builder(getContext());
                alertBuilder.setMessage(ARE_YOU_SURE)
                        .setCancelable(false)
                        .setPositiveButton(YES, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                edtTxtToSpeech.getText().clear();
                            }
                        })
                        .setNegativeButton(NO, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });
                alertBuilder.show();
            } else {
                AlertDialog.Builder alertBuilder = new AlertDialog.Builder(getContext());
                alertBuilder.setMessage(NOTHING_TO_CLEAR)
                        .setCancelable(false)
                        .setNegativeButton(OK, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });
                alertBuilder.show();
            }
        }
    };

    private void initViews(View view) {
        edtTxtToSpeech = view.findViewById(R.id.edt_txt_speech);

        btnRead = view.findViewById(R.id.btn_read);
        btnClear = view.findViewById(R.id.btn_clear);

        skBarSpeed = view.findViewById(R.id.sk_bar_speed);
        skBarPitch = view.findViewById(R.id.sk_bar_pitch);

        skBarSpeed.setProgress(50);
        skBarPitch.setProgress(50);

        txtReset = view.findViewById(R.id.txt_reset);
        txtReset0 = view.findViewById(R.id.txt_reset0);

        btnRead.setOnClickListener(readText);
        btnClear.setOnClickListener(clearText);

        txtReset.setOnClickListener(resetSpeed);
        txtReset0.setOnClickListener(resetPitch);

        skBarSpeed.setOnSeekBarChangeListener(handleResetText);
        skBarPitch.setOnSeekBarChangeListener(handleResetText0);
    }
}
