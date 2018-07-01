package com.example.denis.journalapp.ViewModel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.example.denis.journalapp.Data.AppDatabase;
import com.example.denis.journalapp.Data.JournalEntry;

import java.util.List;

/**
 * Created by denis on 30/06/18.
 */

public class MainViewModel extends AndroidViewModel {
    private LiveData<List<JournalEntry>> journals;

    public MainViewModel(@NonNull Application application) {
        super(application);
        AppDatabase appDatabase = AppDatabase.getsInstance(this.getApplication());
        journals = appDatabase.journalDao().loadAllJournals();
    }

    public LiveData<List<JournalEntry>> getJournals(){return journals;}

}
