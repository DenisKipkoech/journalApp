package com.example.denis.journalapp.ViewModel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.example.denis.journalapp.Data.AppDatabase;
import com.example.denis.journalapp.Data.JournalEntry;

/**
 * Created by denis on 30/06/18.
 */

public class AddJournalViewModel extends ViewModel {

    private LiveData<JournalEntry> journal;

    public AddJournalViewModel(AppDatabase appDatabase, int id){
        journal = appDatabase.journalDao().loadJournalById(id);
    }

    public LiveData<JournalEntry> getJournal(){return journal;}
}
