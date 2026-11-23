package com.example.menuinawigacja;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    private EditText etName;
    private View sectionForm, sectionSettings;
    private TextView tvSettings;
    private CheckBox cbNotifications, cbSound, cbDark, cbSave;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);          // pasek staje się paskiem aplikacji

        etName = findViewById(R.id.etName);
        sectionForm = findViewById(R.id.sectionForm);
        sectionSettings = findViewById(R.id.sectionSettings);
        tvSettings = findViewById(R.id.tvSettings);
        cbDark = findViewById(R.id.cbDark);
        cbNotifications = findViewById(R.id.cbNotifications);
        cbSave = findViewById(R.id.cbSave);
        cbSound  = findViewById(R.id.cbSound);

        cbSound.setOnClickListener(v -> changeSettings());
        cbDark.setOnClickListener(v -> changeSettings());
        cbSave.setOnClickListener(v -> changeSettings());
        cbNotifications.setOnClickListener(v -> changeSettings());


        tvSettings.setText(getString(R.string.tv_settings, ""));
        setUpBottomNavigation();

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private void changeSettings(){
        StringBuilder settings = new StringBuilder();
        CheckBox[] checkBoxes = {cbNotifications,cbSave, cbDark, cbSound};
        for(CheckBox i : checkBoxes){
            if(i.isChecked()){
                settings.append(i.getText().toString()).append(", ");
            }
        tvSettings.setText(getString(R.string.tv_settings, settings));

        }
    }



    private void setUpBottomNavigation() {
        BottomNavigationView bottomNav = findViewById(R.id.bottomNav);
        bottomNav.setOnItemSelectedListener(item -> {
            int id = item.getItemId();
            if (id == R.id.nav_form) {
                showSection(true);
                return true;
            } else if (id == R.id.nav_settings) {
                showSection(false);
                return true;
            }
            return false;
        });
    }

    private void showSection(boolean form) {
        sectionForm.setVisibility(form ? View.VISIBLE : View.GONE);
        sectionSettings.setVisibility(form ? View.GONE : View.VISIBLE);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_share) {
            share();
            return true;
        } else if (id == R.id.action_clear) {
            clearForm();
            return true;
        } else if (id == R.id.action_about) {
            startActivity(new Intent(this, AboutActivity.class));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    private void clearForm() {
        etName.setText("");
        Toast.makeText(this, R.string.cleared, Toast.LENGTH_SHORT).show();
    }

    private void share() {
        String text = etName.getText().toString().trim();
        if (text.isEmpty()) {
            Toast.makeText(this, R.string.nothing_to_share, Toast.LENGTH_SHORT).show();
            return;
        }
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_TEXT, text);

        try {
            startActivity(Intent.createChooser(intent, getString(R.string.share_via)));
        } catch (ActivityNotFoundException e) {
            Toast.makeText(this, R.string.error_no_app, Toast.LENGTH_SHORT).show();
        }
    }


    }