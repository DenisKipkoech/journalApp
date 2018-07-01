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
    private String description;
    @ColumnInfo(name = "entry_date")
    private Date dateOfEntry;

    @Ignore
    public JournalEntry(int id, String description, Date dateOfEntry) {
        this.id = id;
        this.description = description;
        this.dateOfEntry = dateOfEntry;
    }

    public JournalEntry(String description, Date dateOfEntry) {
        this.description = description;
        this.dateOfEntry = dateOfEntry;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getDateOfEntry() {
        return dateOfEntry;
    }

    public void setDateOfEntry(Date dateOfEntry) {
        this.dateOfEntry = dateOfEntry;
    }
}
