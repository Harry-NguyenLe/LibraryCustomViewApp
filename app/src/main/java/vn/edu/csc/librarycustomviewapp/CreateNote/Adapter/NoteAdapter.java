package vn.edu.csc.librarycustomviewapp.CreateNote.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import vn.edu.csc.librarycustomviewapp.CreateNote.Model.Note;
import vn.edu.csc.librarycustomviewapp.Helper.OnClickListenner;
import vn.edu.csc.librarycustomviewapp.R;

public class NoteAdapter extends ListAdapter<Note, NoteAdapter.NoteVH> {

    OnClickListenner listenner;

    public NoteAdapter() {
        super(DIFF_CALLBACK);

    }

    private static final DiffUtil.ItemCallback<Note> DIFF_CALLBACK = new DiffUtil.ItemCallback<Note>() {
        @Override
        public boolean areItemsTheSame(@NonNull Note oldItem, @NonNull Note newItem) {
            return oldItem.getNote_id() == newItem.getNote_id();
        }

        @Override
        public boolean areContentsTheSame(@NonNull Note oldItem, @NonNull Note newItem) {
            return oldItem.equals(newItem);
        }
    };

    public void setOnItemClcikListenner(OnClickListenner listenner) {
        this.listenner = listenner;
    }

    @NonNull
    @Override
    public NoteVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View convertView = layoutInflater.inflate(R.layout.item_note_row, parent, false);
        return new NoteVH(convertView);
    }

    @Override
    public void onBindViewHolder(@NonNull NoteVH holder, int position) {
        Note currentNote = getItem(position);
        holder.tv_note_tittle.setText(currentNote.getNote_title());
        holder.tv_note_description.setText(currentNote.getNote_description());
        holder.tv_note_priority.setText(String.valueOf(currentNote.getNote_priority()));

        holder.itemView.setOnClickListener((View v) -> {
            if (listenner != null) {
                listenner.onItemClick(getItem(position));
            }
        });
    }

    public Note getNoteAt(int position) {
        return getItem(position);
    }

    public class NoteVH extends RecyclerView.ViewHolder {
        TextView tv_note_tittle, tv_note_description, tv_note_priority;

        public NoteVH(@NonNull View itemView) {
            super(itemView);
            tv_note_tittle = itemView.findViewById(R.id.tv_note_tittle);
            tv_note_description = itemView.findViewById(R.id.tv_note_description);
            tv_note_priority = itemView.findViewById(R.id.tv_note_priority);
        }
    }

}
