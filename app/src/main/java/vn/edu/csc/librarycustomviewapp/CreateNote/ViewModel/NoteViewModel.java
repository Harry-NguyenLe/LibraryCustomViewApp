package vn.edu.csc.librarycustomviewapp.CreateNote.ViewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import vn.edu.csc.librarycustomviewapp.CreateNote.Model.Note;
import vn.edu.csc.librarycustomviewapp.CreateNote.Repository.NoteRepository;

public class NoteViewModel extends ViewModel {
    private NoteRepository repository;
    private LiveData<List<Note>> allNotes;

    public LiveData<List<Note>> getAllNotes() {
        if (allNotes == null) {
            allNotes = repository.getAllNotes();
        }
        return allNotes;
    }

    public void insertNote(Note note) {
        repository.insertNote(note);
    }

    public void updateNote(Note note) {
        repository.updateNote(note);
    }

    public void deleteNote(Note note) {
        repository.deleteNote(note);
    }

    public void deleteAllNotes() {
        repository.deleteAllNotes();
    }


    public void setNoteRepository(NoteRepository noteRepository) {
        this.repository = noteRepository;
    }
}
