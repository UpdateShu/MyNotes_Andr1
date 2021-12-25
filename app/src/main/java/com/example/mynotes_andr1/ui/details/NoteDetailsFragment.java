package com.example.mynotes_andr1.ui.details;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentResultListener;

import com.example.mynotes_andr1.R;
import com.example.mynotes_andr1.domain.InMemoryNotesRepository;
import com.example.mynotes_andr1.domain.Note;
import com.example.mynotes_andr1.ui.list.NotesFragment;
import com.example.mynotes_andr1.ui.navdrawer.BaseNavFeatureFragment;

import java.util.Date;

public class NoteDetailsFragment extends BaseNavFeatureFragment implements NoteDetailsView {

    public static final String ARG_NOTE = "ARG_NOTE";
    public static final String KEY_RESULT = "NoteDetailsFragment_KEY_RESULT";
    public static final String TAG = "NoteDetailsFragment";

    private Button btnSave;
    private ProgressBar progressBar;

    private EditText noteName;
    private EditText noteDescription;
    private EditText noteCreated;

    private NotePresenter presenter;

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

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_note_details, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        progressBar = view.findViewById(R.id.progress);
        btnSave = view.findViewById(R.id.btn_save);

        noteName = view.findViewById(R.id.note_name);
        noteDescription = view.findViewById(R.id.note_description);
        noteCreated = view.findViewById(R.id.note_created);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.onActionPressed(noteName.getText().toString(), noteDescription.getText().toString(), new Date());
            }
        });

//        DatePicker picker = view.findViewById(R.id.picker);
//
//        picker.setOnDateChangedListener(new DatePicker.OnDateChangedListener() {
//            @Override
//            public void onDateChanged(DatePicker datePicker, int i, int i1, int i2) {
//
//            }
//        });

        Note note = null;
        if (getArguments() != null && getArguments().containsKey(ARG_NOTE)) {
            note = requireArguments().getParcelable(ARG_NOTE);
        }
        showNote(note);
        getParentFragmentManager()
                .setFragmentResultListener(KEY_RESULT, getViewLifecycleOwner(), new FragmentResultListener() {
                    @Override
                    public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle result) {
                        showNote(result.getParcelable(NotesFragment.ARG_NOTE));
                    }
                });

    }

    void showNote(Note note) {
        if (note != null) {
            presenter = new UpdateNotePresenter(note,this, InMemoryNotesRepository.INSTANCE);
        }
        else {
            presenter = new AddNotePresenter(this, InMemoryNotesRepository.INSTANCE);
        }
    }

    @Override
    public void showProgress() {
        btnSave.setVisibility(View.GONE);
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        btnSave.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void setActionButtonText(int title) {
        btnSave.setText(title);
    }

    @Override
    public void setTitle(int title) {
        toolbar.setTitle(title);
    }

    @Override
    public void setName(String name) {
        noteName.setText(name);
    }

    @Override
    public void setDescription(String description) {
        noteDescription.setText(description);
    }

    @Override
    public void setCreated(Date created) {
        noteCreated.setText("new Date()");
    }

    @Override
    public void actionCompleted(String key, Bundle bundle) {

        getParentFragmentManager()
                .setFragmentResult(key, bundle);

        getParentFragmentManager().popBackStack();
    }
}
