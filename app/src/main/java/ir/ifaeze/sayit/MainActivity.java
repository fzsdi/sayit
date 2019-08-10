package ir.ifaeze.sayit;

import android.speech.tts.TextToSpeech;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.Toast;

import java.util.Locale;

import ir.ifaeze.sayit.fragmets.ContactUsFragment;
import ir.ifaeze.sayit.fragmets.SayItFragment;

import static ir.ifaeze.sayit.utilities.ConstantStrings.*;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {
    BottomNavigationView navView;
    TextToSpeech txtToSpeech;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();

        loadFragment(new SayItFragment());
    }

    private void initViews() {
        navView = findViewById(R.id.nav_bar);
        navView.setOnNavigationItemSelectedListener(this);
    }

    public void initTextToSpeech(final Button btnRead) {
        txtToSpeech = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status == TextToSpeech.SUCCESS) {
                    int result = txtToSpeech.setLanguage(Locale.ENGLISH);
                    if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                        Toast.makeText(getApplicationContext(), LANGUAGE_NOT_SUPPORTED, Toast.LENGTH_SHORT).show();
                    } else {
                        btnRead.setEnabled(true);
                    }
                } else {
                    Toast.makeText(getApplicationContext(), CONNECTION_FAILED, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void readText(String text, SeekBar skBarPitch, SeekBar skBarSpeed) {
        float pitch = (float) skBarPitch.getProgress() / 50;
        if (pitch < 0.1) pitch = 0.1f;
        float speed = (float) skBarSpeed.getProgress() / 50;
        if (speed < 0.1) speed = 0.1f;

        txtToSpeech.setPitch(pitch);
        txtToSpeech.setSpeechRate(speed);

        txtToSpeech.speak(text, txtToSpeech.QUEUE_FLUSH, null);
    }

    @Override
    protected void onDestroy() {
        if (txtToSpeech != null) {
            txtToSpeech.stop();
            txtToSpeech.shutdown();
        }
        super.onDestroy();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        Fragment fragment = null;
        switch (menuItem.getItemId()) {
            case R.id.contact_us:
                fragment = new ContactUsFragment();
                break;
            case R.id.say_it:
                fragment = new SayItFragment();
                break;
        }
        return loadFragment(fragment);
    }

    private boolean loadFragment(Fragment fragment) {
        if (fragment != null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.container, fragment)
                    .commit();
            return true;
        }
        return false;
    }
}
