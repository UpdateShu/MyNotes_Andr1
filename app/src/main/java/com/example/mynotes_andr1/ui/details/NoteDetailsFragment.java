package com.example.mynotes_andr1.ui.details;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.mynotes_andr1.R;
import com.example.mynotes_andr1.domain.Note;

public class NoteDetailsFragment extends Fragment {

    public static final String ARG_NOTE = "ARG_NOTE";

    public static NoteDetailsFragment newInstance(Note note) {
        NoteDetailsFragment fragment = new NoteDetailsFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARG_NOTE, note);
        fragment.setArguments(args);
        return fragment;
    }

    public NoteDetailsFragment() {
        super(R.layout.fragment_note_details);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Note note = requireArguments().getParcelable(ARG_NOTE);

        TextView noteCreated = view.findViewById(R.id.note_created);
        noteCreated.setText(note.getFormattedDateOfCreated());

        TextView noteName = view.findViewById(R.id.note_name);
        noteName.setText(note.getName());

        TextView noteDescription = view.findViewById(R.id.note_description);
        noteDescription.setText(note.getDescription());
    }
}
