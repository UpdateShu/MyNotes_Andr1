package com.example.mynotes_andr1.ui.details;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentResultListener;

import com.example.mynotes_andr1.R;
import com.example.mynotes_andr1.domain.Note;
import com.example.mynotes_andr1.ui.list.NotesListFragment;

public class NoteDetailsFragment extends Fragment {

    public static final String ARG_NOTE = "ARG_NOTE";
    public static final String KEY_RESULT = "NoteDetailsFragment_KEY_RESULT";

    private TextView noteCreated;
    private TextView noteName;
    private TextView noteDescription;

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

        noteCreated = view.findViewById(R.id.note_created);
        noteName = view.findViewById(R.id.note_name);
        noteDescription = view.findViewById(R.id.note_description);

        if (getArguments() != null && getArguments().containsKey(ARG_NOTE)) {
            displayDetails(requireArguments().getParcelable(ARG_NOTE));
        }

        getParentFragmentManager()
                .setFragmentResultListener(KEY_RESULT, getViewLifecycleOwner(), new FragmentResultListener() {
                    @Override
                    public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle result) {
                        Note note = result.getParcelable(NotesListFragment.ARG_NOTE);
                        displayDetails(note);
                    }
                });
    }

    void displayDetails(Note note) {
        noteCreated.setText(note.getFormattedDateOfCreated());
        noteName.setText(note.getName());
        noteDescription.setText(note.getDescription());
    }
}
