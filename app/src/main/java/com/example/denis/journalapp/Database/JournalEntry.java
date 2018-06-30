package com.example.denis.journalapp.Database;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

import java.util.Date;

/**
 * Created by denis on 28/06/18.
 */
@Entity(tableName = "journal")
public class JournalEntry {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String thoughts;
    @ColumnInfo(name = "entry_date")
    private Date dateOfEntry;

    @Ignore
    public JournalEntry(int id, String thoughts, Date dateOfEntry) {
        this.id = id;
        this.thoughts = thoughts;
        this.dateOfEntry = dateOfEntry;
    }

    public JournalEntry(String thoughts, Date dateOfEntry) {
        this.thoughts = thoughts;
        this.dateOfEntry = dateOfEntry;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getThoughts() {
        return thoughts;
    }

    public void setThoughts(String thoughts) {
        this.thoughts = thoughts;
    }

    public Date getDateOfEntry() {
        return dateOfEntry;
    }

    public void setDateOfEntry(Date dateOfEntry) {
        this.dateOfEntry = dateOfEntry;
    }
}
