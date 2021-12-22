package com.example.mynotes_andr1.ui.list;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mynotes_andr1.R;
import com.example.mynotes_andr1.domain.Note;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.NoteViewHolder> {

    class NoteViewHolder extends RecyclerView.ViewHolder {

        private TextView noteCreated;

        private TextView noteName;

        private TextView noteText;

        public NoteViewHolder(@NonNull View itemView) {
            super(itemView);

            noteCreated = itemView.findViewById(R.id.note_created);
            noteName = itemView.findViewById(R.id.note_name);
            noteText = itemView.findViewById(R.id.note_description);

            itemView.findViewById(R.id.card).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Note note = data.get(getAdapterPosition());
                    if (getOnClick() != null) {
                        getOnClick().onClick(note);
                    }
                }
            });
        }

        public TextView getNoteCreated() {
            return noteCreated;
        }

        public TextView getNoteName() {
            return noteName;
        }

        public TextView getNoteText() {
            return noteText;
        }
    }

    interface OnClick {
        void onClick(Note note);

        void onLongClick(Note note);
    }

    private OnClick onClick;

    private List<Note> data = new ArrayList<>();

    public void setData(Collection<Note> notes) {
        data.clear();
        data.addAll(notes);
    }

    @NonNull
    @Override
    public NoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_note, parent, false);

        return new NoteViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull NoteViewHolder holder, int position) {

        Note note = data.get(position);
        holder.getNoteName().setText(note.getName());
        holder.getNoteText().setText(note.getDescription());
        holder.getNoteCreated().setText(note.getFormattedDateOfCreated());
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public OnClick getOnClick() {
        return onClick;
    }

    public void setOnClick(OnClick onClick) {
        this.onClick = onClick;
    }
}
