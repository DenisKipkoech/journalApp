package com.example.denis.journalapp.Database;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

/**
 * Created by denis on 28/06/18.
 */
@Dao
public interface JournalDao {
    @Query("SELECT * FROM journal")
    LiveData<List<JournalEntry>> loadAllJournals();

    @Insert
    void insertJournal(JournalEntry journalEntry);

    @Delete
    void deleteJournal(JournalEntry journalEntry);

    @Query("SELECT * FROM journal WHERE id=:id")
    LiveData<JournalEntry> loadJournalById(int id);
}
