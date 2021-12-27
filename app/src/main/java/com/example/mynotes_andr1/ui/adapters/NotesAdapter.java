package com.example.mynotes_andr1.ui.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mynotes_andr1.R;
import com.example.mynotes_andr1.domain.Note;
import com.example.mynotes_andr1.ui.adapters.AdapterItem;
import com.example.mynotes_andr1.ui.adapters.ListAdapter;
import com.example.mynotes_andr1.ui.adapters.NoteAdapterItem;

public class NotesAdapter extends ListAdapter {

    public NotesAdapter(Fragment fragment) {
        super(fragment);
    }

    class NoteViewHolder extends RecyclerView.ViewHolder {

        private TextView noteName;

        private TextView noteDescription;

        private TextView noteCreated;

        public NoteViewHolder(@NonNull View itemView) {
            super(itemView);

            CardView card = itemView.findViewById(R.id.note_card);
            fragment.registerForContextMenu(card);
            noteName = itemView.findViewById(R.id.note_name);
            noteDescription = itemView.findViewById(R.id.note_description);
            noteCreated = itemView.findViewById(R.id.note_created);

            card.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    card.showContextMenu();
                    AdapterItem item = data.get(getAdapterPosition());
                    if (item instanceof NoteAdapterItem) {
                        if (getOnClick() != null) {                            //getOnClick().onLongClick(((NoteAdapterItem)item).getNote());
                            getOnClick().onClick(((NoteAdapterItem)item).getNote());
                        }
                    }
                    /*AdapterItem item = data.get(getAdapterPosition());
                    if (item instanceof NoteAdapterItem) {
                        if (getOnClick() != null) {
                            getOnClick().onClick(((NoteAdapterItem)item).getNote());
                        }
                    }*/
                }
            });
            card.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    card.showContextMenu();
                    AdapterItem item = data.get(getAdapterPosition());
                    if (item instanceof NoteAdapterItem) {
                        if (getOnClick() != null) {
                            getOnClick().onLongClick(((NoteAdapterItem)item).getNote());
                        }
                    }
                    return false;
                }
            });
        }

        public TextView getNoteCreated() {
            return noteCreated;
        }

        public TextView getNoteName() {
            return noteName;
        }

        public TextView getNoteDescription() {
            return noteDescription;
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
        noteViewHolder.getNoteDescription().setText(noteItem.getMessage());
        noteViewHolder.getNoteCreated().setText(noteItem.getTime());
    }

    @Override
    public int getItemCount() {
        return data.size();
    }
}
