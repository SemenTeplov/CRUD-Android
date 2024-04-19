package com.main.crud;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

public class AddNoteActivity extends AppCompatActivity {
    private EditText editViewAddNote;

    private RadioGroup radioGroupPriority;
    private RadioButton radioButtonLow;
    private RadioButton radioButtonMedium;
    private RadioButton radioButtonHigh;
    private Button ButtonSave;
    private AddNoteViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);
        viewModel = new ViewModelProvider(this).get(AddNoteViewModel.class);
        viewModel.getShouldCloseScreen().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean shouldClose) {
                if (shouldClose) {
                    finish();
                }
            }
        });
        initViews();
        radioButtonHigh.setChecked(true);

        ButtonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveNote();
            }
        });
    }

    private void initViews() {
        editViewAddNote = findViewById(R.id.editViewAddNote);

        radioGroupPriority = findViewById(R.id.radioGroupPriority);
        radioButtonLow = findViewById(R.id.radioButtonLow);
        radioButtonMedium = findViewById(R.id.radioButtonMedium);
        radioButtonHigh = findViewById(R.id.radioButtonHigh);

        ButtonSave = findViewById(R.id.ButtonSave);
    }

    private void saveNote() {
        String text = editViewAddNote.getText().toString().trim();

        int priority = getPriority();

        Note note = new Note(text, priority);
        viewModel.saveNote(note);
    }

    private int getPriority() {
        int priority;

        if (radioButtonLow.isChecked()) {
            priority = 0;
        }
        else if (radioButtonMedium.isChecked()) {
            priority = 1;
        }
        else {
            priority = 2;
        }

        return priority;
    }

    public static Intent newIntent(Context context) {
        return  new Intent(context, AddNoteActivity.class);
    }
}