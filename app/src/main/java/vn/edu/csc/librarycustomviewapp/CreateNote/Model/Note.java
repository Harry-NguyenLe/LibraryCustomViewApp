package vn.edu.csc.librarycustomviewapp.CreateNote.Model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Objects;


@Entity(tableName = "note_table")
public class Note {
    @PrimaryKey(autoGenerate = true)
    private int note_id;
    @ColumnInfo(name = "title_column")
    private String note_title;
    @ColumnInfo(name = "description_column")
    private String note_description;
    @ColumnInfo(name = "priority_column")
    private int note_priority;

    public Note() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Note)) return false;
        Note note = (Note) o;
        return getNote_priority() == note.getNote_priority() &&
                getNote_title().equals(note.getNote_title()) &&
                getNote_description().equals(note.getNote_description());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getNote_title(), getNote_description(), getNote_priority());
    }

    public Note(String note_title, String note_description, int note_priority) {
        this.note_title = note_title;
        this.note_description = note_description;
        this.note_priority = note_priority;
    }

    public int getNote_id() {
        return note_id;
    }

    public void setNote_id(int note_id) {
        this.note_id = note_id;
    }

    public String getNote_title() {
        return note_title;
    }

    public void setNote_title(String note_title) {
        this.note_title = note_title;
    }

    public String getNote_description() {
        return note_description;
    }

    public void setNote_description(String note_description) {
        this.note_description = note_description;
    }

    public int getNote_priority() {
        return note_priority;
    }

    public void setNote_priority(int note_priority) {
        this.note_priority = note_priority;
    }
}
