package com.mlsdev.mlsdevstore.data.model.user;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.mlsdev.mlsdevstore.data.local.database.Table;

@Entity(tableName = Table.PERSONAL_INFO)
public class PersonalInfo {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private String contactEmail;
    private String contactFirstName;
    private String contactLastName;

    public PersonalInfo() {
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setContactEmail(String contactEmail) {
        this.contactEmail = contactEmail;
    }

    public void setContactFirstName(String contactFirstName) {
        this.contactFirstName = contactFirstName;
    }

    public void setContactLastName(String contactLastName) {
        this.contactLastName = contactLastName;
    }

    public int getId() {
        return id;
    }

    public String getContactEmail() {
        return contactEmail;
    }

    public String getContactFirstName() {
        return contactFirstName;
    }

    public String getContactLastName() {
        return contactLastName;
    }
}
