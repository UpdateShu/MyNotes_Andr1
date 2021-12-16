package com.example.mynotes_andr1.ui.list;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.view.menu.MenuView;
import androidx.fragment.app.Fragment;

import com.example.mynotes_andr1.R;
import com.example.mynotes_andr1.domain.InMemoryNotesRepository;
import com.example.mynotes_andr1.domain.Note;
import com.example.mynotes_andr1.domain.NoteFolder;
import com.example.mynotes_andr1.ui.navdrawer.BaseNavFeatureFragment;

import java.util.List;

public class NotesBaseListFragment extends BaseNavFeatureFragment {

    protected LinearLayout notesContainer;

    protected NotesListPresenter presenter;

    @Override
    public int getToolbarId() {
        return R.id.toolbar;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_notes_list, container, false);
    }

    protected View createView(Note note) {
        View itemView = LayoutInflater.from(requireContext()).inflate(R.layout.item_note, notesContainer, false);

        TextView noteCreated = itemView.findViewById(R.id.note_created);
        noteCreated.setText(note.getFormattedDateOfCreated());

        TextView noteName = itemView.findViewById(R.id.note_name);
        noteName.setText(note.getName());

        TextView noteText = itemView.findViewById(R.id.note_description);
        noteText.setText(note.getDescription());
        return itemView;
    }
}
