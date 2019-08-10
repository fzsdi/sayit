package ir.ifaeze.sayit;

import android.content.ActivityNotFoundException;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import static ir.ifaeze.sayit.utilities.ConstantStrings.CANNOT;
import static ir.ifaeze.sayit.utilities.ConstantStrings.COPIED_SUCCESSFULLY;
import static ir.ifaeze.sayit.utilities.ConstantStrings.EMAIL;
import static ir.ifaeze.sayit.utilities.ConstantStrings.EMAIL_ADDRESS;
import static ir.ifaeze.sayit.utilities.ConstantStrings.NOTHING_TO_CLEAR;
import static ir.ifaeze.sayit.utilities.ConstantStrings.OK;
import static ir.ifaeze.sayit.utilities.ConstantStrings.SEND_EMAIL;

public class ReportBugActivity extends AppCompatActivity {
    TextView txtEmailAddress;
    Button btnSendEmail;
    EditText edtTextSub, edtTextMsg;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_bug);
        initViews();
        initActionBar();
    }

    private void initActionBar() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(SEND_EMAIL);
    }

    View.OnClickListener copyToClipboard = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            ClipboardManager clipboardManager = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
            ClipData clipData = ClipData.newPlainText(EMAIL_ADDRESS, EMAIL);
            clipboardManager.setPrimaryClip(clipData);
            Toast.makeText(getApplicationContext(), COPIED_SUCCESSFULLY, Toast.LENGTH_SHORT).show();
        }
    };

    View.OnClickListener sendEmail = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String subject = edtTextSub.getText().toString();
            String message = edtTextMsg.getText().toString();
            String msgTrim = edtTextMsg.getText().toString().trim();
            if (msgTrim.length() != 0) {
                String mailto = "mailto:carmenapps@pm.me" +
                        "?cc=" + "" +
                        "&subject=" + Uri.encode(subject) +
                        "&body=" + Uri.encode(message);

                Intent emailIntent = new Intent(Intent.ACTION_SENDTO);
                emailIntent.setData(Uri.parse(mailto));

                try {
                    startActivity(emailIntent);
                } catch (ActivityNotFoundException e) {
                    // TODO: Handle case where no email app is available
                }
            } else {
                Toast.makeText(getApplicationContext(), CANNOT, Toast.LENGTH_SHORT).show();
            }
        }
    };


    private void initViews() {
        txtEmailAddress = findViewById(R.id.txt_email_address);
        txtEmailAddress.setOnClickListener(copyToClipboard);
        btnSendEmail = findViewById(R.id.btn_send_email);
        btnSendEmail.setOnClickListener(sendEmail);
        edtTextSub = findViewById(R.id.edt_txt_sub);
        edtTextMsg = findViewById(R.id.edt_txt_msg);
        edtTextSub.requestFocus();
    }
}
