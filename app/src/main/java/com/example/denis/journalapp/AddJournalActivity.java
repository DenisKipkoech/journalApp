package com.example.denis.journalapp;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import com.example.denis.journalapp.Database.AppDatabase;
import com.example.denis.journalapp.Database.JournalEntry;
import com.example.denis.journalapp.ViewModel.AddJournalViewModel;
import com.example.denis.journalapp.ViewModel.AddJournalViewModelFactory;
import com.example.denis.journalapp.ViewModel.AppExecutors;

import java.util.Date;

public class AddJournalActivity extends AppCompatActivity {
    public static final String EXTRA_ID = "extraJournalId";
    public static final String INSTANCE_ID = "InstanceId";

    private static final int DEFAULT_JOURNAL_ID=-1;
    private int journalId = DEFAULT_JOURNAL_ID;

    EditText journalEditText;

    private AppDatabase dB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_journal);

        journalEditText = findViewById(R.id.journal_editText);
        dB = AppDatabase.getsInstance(getApplicationContext());

        if (savedInstanceState != null && savedInstanceState.containsKey(INSTANCE_ID)){
            journalId = savedInstanceState.getInt(INSTANCE_ID);
        }

        Intent intent = getIntent();
        if (intent != null && intent.hasExtra(EXTRA_ID)){
            if (journalId == DEFAULT_JOURNAL_ID) {

                journalId = intent.getIntExtra(EXTRA_ID, DEFAULT_JOURNAL_ID);

                AddJournalViewModelFactory factory = new AddJournalViewModelFactory(dB, journalId);

                final AddJournalViewModel viewModel = ViewModelProviders.of(this, factory)
                        .get(AddJournalViewModel.class);

                viewModel.getJournal().observe(this, new Observer<JournalEntry>() {
                    @Override
                    public void onChanged(@Nullable JournalEntry journalEntry) {
                        viewModel.getJournal().removeObserver(this);
                        populateUi(journalEntry);
                    }
                });
            }
        }

    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        outState.putInt(INSTANCE_ID, journalId);
        super.onSaveInstanceState(outState, outPersistentState);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_add, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_add){
            onJournalSaved();
        }
        return super.onOptionsItemSelected(item);
    }

    private void populateUi(JournalEntry journalEntry){
        if (journalEntry == null){return;}
        journalEditText.setText(journalEntry.getDescription());
    }

    public void onJournalSaved(){
        String description = journalEditText.getText().toString();
        Date date = new Date();

        final JournalEntry journalEntry = new JournalEntry(description, date);
        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                if (journalId == DEFAULT_JOURNAL_ID){
                    dB.journalDao().insertJournal(journalEntry);
                }else{
                    journalEntry.setId(journalId);
                    dB.journalDao().updateJournal(journalEntry);
                }

            }
        });
    }
}
