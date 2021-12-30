package com.example.mynotes_andr1.ui.editor;

import static android.content.Context.CLIPBOARD_SERVICE;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.Calendar;
import java.util.Date;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentResultListener;

import com.example.mynotes_andr1.R;
import com.example.mynotes_andr1.domain.FirestoreNotesRepository;
import com.example.mynotes_andr1.domain.Note;
import com.example.mynotes_andr1.domain.NoteFolder;
import com.example.mynotes_andr1.ui.notes.NoteDialogFragment;
import com.example.mynotes_andr1.ui.notes.NotesFragment;
import com.example.mynotes_andr1.ui.navdrawer.BaseNavFeatureFragment;

public class EditNoteFragment extends BaseNavFeatureFragment implements EditNoteView {

    public static final String ARG_NOTE = "ARG_NOTE";
    public static final String ARG_FOLDER = "ARG_FOLDER";
    public static final String KEY_RESULT = "NoteDetailsFragment_KEY_RESULT";
    public static final String TAG = "NoteDetailsFragment";

    private Button btnSave;
    private Button btnLink;
    private ProgressBar progressBar;

    private LinearLayout editor;
    private EditText noteName;
    private EditText noteLink;
    private EditText noteDescription;
    private DatePicker noteCreated;

    private EditNotePresenter presenter;

    public static EditNoteFragment newInstance(Note note, NoteFolder folder) {
        EditNoteFragment fragment = new EditNoteFragment();

        Bundle args = new Bundle();
        args.putParcelable(ARG_NOTE, note);
        args.putParcelable(ARG_FOLDER, folder);
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
        return inflater.inflate(R.layout.fragment_edit_note, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        progressBar = view.findViewById(R.id.progress);
        btnSave = view.findViewById(R.id.btn_save);
        btnLink = view.findViewById(R.id.btn_link);

        noteName = view.findViewById(R.id.note_name);
        noteLink = view.findViewById(R.id.note_link);
        noteDescription = view.findViewById(R.id.note_description);
        noteCreated = view.findViewById(R.id.note_created);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Date created = getDateFromDatePicker();
                presenter.onActionPressed(noteName.getText().toString(),
                        noteLink.getText().toString(), noteDescription.getText().toString(), created);
            }
        });
        btnLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(noteLink.getText().toString()));
                    startActivity(browserIntent);
                }
                catch (Exception ex) {
                    Toast.makeText(requireContext(), ex.getMessage(), Toast.LENGTH_LONG).show();
                }
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
        NoteFolder folder = null;
        if (getArguments() != null && getArguments().containsKey(ARG_FOLDER)) {
            folder = requireArguments().getParcelable(ARG_FOLDER);
        }
        showNote(note, folder);
        getParentFragmentManager()
                .setFragmentResultListener(KEY_RESULT, getViewLifecycleOwner(), new FragmentResultListener() {
                    @Override
                    public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle result) {
                        showNote(result.getParcelable(NotesFragment.ARG_NOTE), result.getParcelable(NotesFragment.ARG_FOLDER));
                    }
                });
    }

    void showNote(Note note, NoteFolder folder) {
        if (note != null) {
            presenter = new UpdateNotePresenter(note, folder,this, FirestoreNotesRepository.INSTANCE);
        }
        else {
            presenter = new AddNotePresenter(folder, this, FirestoreNotesRepository.INSTANCE);
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
    public void setLink(String link) {
        noteLink.setText(link);
    }

    @Override
    public void setDescription(String description) {
        noteDescription.setText(description);
    }

    @Override
    public void setCreated(Date created) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(created);

        noteCreated.init(calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH),
                null);
    }

    // Получение даты из DatePicker
    private Date getDateFromDatePicker() {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, noteCreated.getYear());
        cal.set(Calendar.MONTH, noteCreated.getMonth());
        cal.set(Calendar.DAY_OF_MONTH, noteCreated.getDayOfMonth());
        return cal.getTime();
    }

    @Override
    public void actionCompleted(String key, Bundle bundle) {

        getParentFragmentManager()
                .setFragmentResult(key, bundle);

        getParentFragmentManager().popBackStack();
    }
}
