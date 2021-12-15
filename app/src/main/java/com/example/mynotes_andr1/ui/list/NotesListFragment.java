package com.example.mynotes_andr1.ui.list;

import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentResultListener;

import com.example.mynotes_andr1.R;
import com.example.mynotes_andr1.domain.InMemoryNotesRepository;
import com.example.mynotes_andr1.domain.Note;
import com.example.mynotes_andr1.domain.NoteFolder;
import com.example.mynotes_andr1.ui.details.NoteDetailsFragment;
import com.example.mynotes_andr1.ui.folders.NoteFoldersListFragment;

import java.util.List;

public class NotesListFragment extends Fragment implements NotesListView {

    public static final String ARG_FOLDER = "ARG_FOLDER";
    public static final String ARG_NOTE = "ARG_NOTE";
    public static final String TAG = "NotesListFragment";
    public static final String KEY_RESULT = "NotesListFragment_RESULT";

    private LinearLayout notesContainer;

    private NotesListPresenter presenter;

    public static NotesListFragment newInstance(NoteFolder folder) {
        NotesListFragment fragment = new NotesListFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARG_FOLDER, folder);
        fragment.setArguments(args);
        return fragment;
    }

    public NotesListFragment() {
        super(R.layout.fragment_notes_list);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        presenter = new NotesListPresenter(this, new InMemoryNotesRepository());
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_notes_list, container, false);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        notesContainer = view.findViewById(R.id.notes_container);

        if (getArguments() != null && getArguments().containsKey(ARG_FOLDER)) {
            showFolderNotes(requireArguments().getParcelable(ARG_FOLDER));
        }
        else {
            presenter.refresh();
        }
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            getParentFragmentManager()
                    .setFragmentResultListener(KEY_RESULT, getViewLifecycleOwner(), new FragmentResultListener() {
                        @Override
                        public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle result) {
                            //NoteFolder folder = result.getParcelable(NoteFoldersListFragment.ARG_FOLDER);
                            //showFolderNotes(folder);
                        }
                    });
        }
    }

    @Override
    public void showFolderNotes(NoteFolder folder) {

        notesContainer.removeAllViews();
        List<Note> notes = folder.getNotes();
        for (Note note: notes) {
            View itemView = LayoutInflater.from(requireContext()).inflate(R.layout.item_note, notesContainer, false);
            itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Bundle data = new Bundle();
                        data.putParcelable(ARG_NOTE, note);
                        getParentFragmentManager().setFragmentResult(KEY_RESULT, data);
                        //Toast.makeText(requireContext(), note.getName(), Toast.LENGTH_LONG).show();
                    }
                });
            TextView noteCreated = itemView.findViewById(R.id.note_created);
            noteCreated.setText(note.getFormattedDateOfCreated());

            TextView noteName = itemView.findViewById(R.id.note_name);
            noteName.setText(note.getName());
            
            TextView noteText = itemView.findViewById(R.id.note_description);
            noteText.setText(note.getDescription());

            notesContainer.addView(itemView);
        }
    }
}
