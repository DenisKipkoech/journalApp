package com.example.denis.journalapp;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.denis.journalapp.Adapter.JournalAdapter;
import com.example.denis.journalapp.Data.AppDatabase;
import com.example.denis.journalapp.Data.JournalEntry;
import com.example.denis.journalapp.ViewModel.AppExecutors;
import com.example.denis.journalapp.ViewModel.MainViewModel;

import java.util.List;

public class MainActivity extends AppCompatActivity implements JournalAdapter.ItemClickListener{
    private static final String TAG = MainActivity.class.getSimpleName();

    private RecyclerView recyclerView;
    private AppDatabase database;
    private JournalAdapter journalAdapter;
    private TextView no_journal_tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        recyclerView = findViewById(R.id.journal_recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        no_journal_tv = findViewById(R.id.tv_nothing);
        no_journal_tv.setVisibility(View.INVISIBLE);

        journalAdapter = new JournalAdapter(this, this);
        recyclerView.setAdapter(journalAdapter);


        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(final RecyclerView.ViewHolder viewHolder, int direction) {
                AppExecutors.getInstance().diskIO().execute(new Runnable() {
                    @Override
                    public void run() {
                        int position = viewHolder.getAdapterPosition();
                        List<JournalEntry> journals = journalAdapter.getJournalEntries();
                        database.journalDao().deleteJournal(journals.get(position));
                    }
                });
            }
        }).attachToRecyclerView(recyclerView);


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent  intent = new Intent(MainActivity.this,
                        AddJournalActivity.class);
                startActivity(intent);
            }
        });

        database = AppDatabase.getsInstance(getApplicationContext());
        setUpViewModel();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void setUpViewModel(){
        MainViewModel viewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        viewModel.getJournals().observe(this, new Observer<List<JournalEntry>>() {
            @Override
            public void onChanged(@Nullable List<JournalEntry> journalEntries) {
                journalAdapter.setJournals(journalEntries);
                if (journalEntries != null && journalEntries.isEmpty()) {
                    no_journal_tv.setVisibility(View.VISIBLE);
                }else{
                    no_journal_tv.setVisibility(View.INVISIBLE);
                }
            }
        });
    }

    @Override
    public void onItemClickListener(int itemId) {
        Intent intent = new Intent(MainActivity.this, AddJournalActivity.class);
        intent.putExtra(AddJournalActivity.EXTRA_ID, itemId);
        startActivity(intent);
    }
}
