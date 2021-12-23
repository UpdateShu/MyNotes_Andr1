package com.example.mynotes_andr1.ui.list.adapter;

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

public class NotesAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

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

                    AdapterItem item = data.get(getAdapterPosition());
                    if (item instanceof NoteAdapterItem) {
                        if (getOnClick() != null) {
                            getOnClick().onClick(((NoteAdapterItem)item).getNote());
                        }
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

    public
    interface OnClick {
        void onClick(Note note);

        void onLongClick(Note note);
    }

    private OnClick onClick;

    public OnClick getOnClick() {
        return onClick;
    }

    public void setOnClick(OnClick onClick) {
        this.onClick = onClick;
    }

    private List<AdapterItem> data = new ArrayList<>();

    public void setData(Collection<AdapterItem> notes) {
        data.clear();
        data.addAll(notes);
    }

    public int addItem(NoteAdapterItem note) {
        data.add(note);
        return data.size();
    }

    public int removeItem(Note selectedNote) {
        int index = 0;
        for (int i = 0; i < data.size(); i++) {
            if (data.get(i) instanceof NoteAdapterItem && ((NoteAdapterItem) data.get(i)).getNote().getId().equals(selectedNote.getId())) {
                index = i;

                break;
            }
        }

        data.remove(index);
        return index;
    }

    public int updateItem(NoteAdapterItem adapterItem) {
        int index = 0;
        for (int i = 0; i < data.size(); i++) {
            if (data.get(i) instanceof NoteAdapterItem && ((NoteAdapterItem) data.get(i)).getNote().getId().equals(adapterItem.getNote().getId())) {
                index = i;

                break;
            }
        }

        data.set(index, adapterItem);
        return index;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_note, parent, false);

        return new NoteViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        NoteViewHolder noteViewHolder = (NoteViewHolder) holder;
        NoteAdapterItem noteItem = (NoteAdapterItem) data.get(position);

        noteViewHolder.getNoteName().setText(noteItem.getTitle());
        noteViewHolder.getNoteText().setText(noteItem.getMessage());
        noteViewHolder.getNoteCreated().setText(noteItem.getTime());
    }

    @Override
    public int getItemCount() {
        return data.size();
    }
}
