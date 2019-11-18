package vn.edu.csc.librarycustomviewapp.CreateNote.View;

import android.content.Intent;
import android.os.Bundle;
import android.transition.Slide;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import vn.edu.csc.librarycustomviewapp.Base.BaseActivity;
import vn.edu.csc.librarycustomviewapp.CreateNote.Adapter.NoteAdapter;
import vn.edu.csc.librarycustomviewapp.CreateNote.Model.Note;
import vn.edu.csc.librarycustomviewapp.CreateNote.Repository.NoteRepository;
import vn.edu.csc.librarycustomviewapp.CreateNote.ViewModel.NoteViewModel;
import vn.edu.csc.librarycustomviewapp.R;
import vn.edu.csc.librarycustomviewapp.Utilities.Constant;

public class ActivitySecond extends BaseActivity {
    private NoteViewModel noteViewModel;
    private NoteAdapter note_adapter;

    @BindView(R.id.rv_notes_list)
    RecyclerView rv_notes_list;
    @BindView(R.id.layout_button_add)
    RelativeLayout layout_button_add;
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    private final String TOOLBAR_TITTLE = "Note";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_ACTIVITY_TRANSITIONS);
        getWindow().setExitTransition(new Slide().setDuration(1000));
        setContentView(R.layout.activity_second);
        ButterKnife.bind(this);

        //Set toolbar
        toolbar.setTitle(TOOLBAR_TITTLE);
        toolbar.setOverflowIcon(getResources().getDrawable(R.drawable.ic_hamburger, getTheme()));
        toolbar.setTitleTextColor(getResources().getColor(R.color.ef_white, getTheme()));
        setSupportActionBar(toolbar);


        rv_notes_list.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        rv_notes_list.setHasFixedSize(true);
        note_adapter = new NoteAdapter();
        rv_notes_list.setAdapter(note_adapter);

        noteViewModel = ViewModelProviders.of(ActivitySecond.this).get(NoteViewModel.class);
        noteViewModel.setNoteRepository(new NoteRepository(getApplication()));
        noteViewModel.getAllNotes().observe(this, allNotes -> {
            note_adapter.submitList(allNotes);
        });

        //Perform swipe to delete object in recycler view
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                noteViewModel.deleteNote(note_adapter.getNoteAt(viewHolder.getAdapterPosition()));
                Toast.makeText(ActivitySecond.this, "Note deleted", Toast.LENGTH_SHORT).show();
            }
        }).attachToRecyclerView(rv_notes_list);

        note_adapter.setOnItemClcikListenner((Note note) -> {
            Bundle bundle = new Bundle();
            bundle.putString(Constant.EXTRA_TITTLE, note.getNote_title());
            bundle.putString(Constant.EXTRA_DESCRIPTION, note.getNote_description());
            bundle.putInt(Constant.EXTRA_PRIORITY, note.getNote_priority());
            bundle.putInt(Constant.EXTRA_ID, note.getNote_id());
            moveToActivityLeftToRightBundleWithResult(NoteActivity.class, bundle);
        });
    }

    @OnClick({R.id.layout_button_add})
    public void handleClick(View view) {
        switch (view.getId()) {
            case R.id.layout_button_add:
                moveToNextActivityWithResult(NoteActivity.class);
                break;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.option_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.mnu_delete_all_notes:
                noteViewModel.deleteAllNotes();
                break;
        }
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Constant.ADD_NOTE_REQUEST_CODE && resultCode == RESULT_OK) {
            if (data != null) {
                Bundle bundle = data.getExtras();
                if (bundle != null) {
                    String tittle = bundle.getString(Constant.EXTRA_TITTLE);
                    String description = bundle.getString(Constant.EXTRA_DESCRIPTION);
                    int priority = bundle.getInt(Constant.EXTRA_PRIORITY);
                    noteViewModel.insertNote(new Note(tittle, description, priority));
                } else {
                    Toast.makeText(this, "Note not save", Toast.LENGTH_SHORT).show();
                }
            }
        }

        if (requestCode == Constant.EDIT_NOTE_REQUEST_CODE && resultCode == RESULT_OK) {
            if (data != null) {
                int id = data.getIntExtra(Constant.EXTRA_ID, -1);
                Bundle bundle = data.getExtras();
                if (id == -1){
                    Toast.makeText(this, "Note can not be updated", Toast.LENGTH_SHORT).show();
                }
                else if (bundle != null){
                    String tittle = bundle.getString(Constant.EXTRA_TITTLE);
                    String description = bundle.getString(Constant.EXTRA_DESCRIPTION);
                    int priority = bundle.getInt(Constant.EXTRA_PRIORITY);
                    Note note = new Note(tittle, description, priority);
                    note.setNote_id(id);
                    noteViewModel.updateNote(note);
                    Toast.makeText(this, "Note updated", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }
}
