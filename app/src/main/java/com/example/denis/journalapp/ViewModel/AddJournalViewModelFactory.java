package com.example.denis.journalapp.ViewModel;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;

import com.example.denis.journalapp.Database.AppDatabase;

/**
 * Created by denis on 30/06/18.
 */

public class AddJournalViewModelFactory extends ViewModelProvider.NewInstanceFactory{
    private final AppDatabase db;
    private final int journalId;

    public AddJournalViewModelFactory(AppDatabase db, int journalId) {
        this.db = db;
        this.journalId = journalId;
    }

    public <T extends ViewModel> T create(Class<T> modelClass){
        return (T) new AddJournalViewModel(db, journalId);
    }
}
