package com.example.mynotes_andr1.ui.details;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toolbar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentResultListener;

import com.example.mynotes_andr1.R;
import com.example.mynotes_andr1.domain.Note;
import com.example.mynotes_andr1.domain.NoteFolder;
import com.example.mynotes_andr1.ui.list.NotesListFragment;
import com.example.mynotes_andr1.ui.list.NotesListView;
import com.example.mynotes_andr1.ui.navdrawer.BaseNavFeatureFragment;

public class NoteDetailsFragment extends BaseNavFeatureFragment implements NoteDetailsView {

    public static final String ARG_NOTE = "ARG_NOTE";
    public static final String KEY_RESULT = "NoteDetailsFragment_KEY_RESULT";
    public static final String TAG = "NoteDetailsFragment";

    private TextView noteCreated;
    private TextView noteName;
    private TextView noteDescription;
    private Toolbar toolbar;

    private NoteDetailsPresenter presenter;
    private Note note;

    public static NoteDetailsFragment newInstance(Note note) {
        NoteDetailsFragment fragment = new NoteDetailsFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARG_NOTE, note);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getToolbarId() {
        return R.id.toolbar;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        presenter = new NoteDetailsPresenter(this);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_note_details, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        noteCreated = view.findViewById(R.id.note_created);
        noteName = view.findViewById(R.id.note_name);
        noteDescription = view.findViewById(R.id.note_description);
        toolbar = view.findViewById(getToolbarId());//вылетает null

        /*if (getArguments() != null && getArguments().containsKey(ARG_NOTE)) {
            presenter.showNote(requireArguments().getParcelable(ARG_NOTE));
        }
        getParentFragmentManager()
                .setFragmentResultListener(KEY_RESULT, getViewLifecycleOwner(), new FragmentResultListener() {
                    @Override
                    public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle result) {
                        presenter.showNote(result.getParcelable(NotesListFragment.ARG_NOTE));
                    }
                });*/
    }

    @Override
    public void showNote(Note note) {
        if (note != null) {
            toolbar.setTitle(note.getName());
        }
        noteCreated.setText(note.getFormattedDateOfCreated());
        noteName.setText(note.getName());
        noteDescription.setText(note.getDescription());
    }
}
