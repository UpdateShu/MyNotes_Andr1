package com.example.mynotes_andr1.ui.list;

import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentResultListener;

import com.example.mynotes_andr1.R;
import com.example.mynotes_andr1.domain.InMemoryNotesRepository;
import com.example.mynotes_andr1.domain.Note;
import com.example.mynotes_andr1.domain.NoteFolder;
import com.example.mynotes_andr1.ui.folders.NoteFoldersListFragment;

import java.util.List;

public class NotesDetailsListFragment extends NotesBaseListFragment implements NotesListView {

    public static final String ARG_FOLDER = "ARG_FOLDER";
    public static final String TAG = "NotesDetailsListFragment";
    public static final String KEY_RESULT = "NotesDetailsListFragment_RESULT";

    public static NotesDetailsListFragment newInstance(NoteFolder folder) {
        NotesDetailsListFragment fragment = new NotesDetailsListFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARG_FOLDER, folder);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        presenter = new NotesListPresenter(this, new InMemoryNotesRepository());
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        notesContainer = view.findViewById(R.id.notes_container);
        Toolbar toolbar = view.findViewById(getToolbarId());

        if (getArguments() != null && getArguments().containsKey(ARG_FOLDER)) {
            NoteFolder folder = requireArguments().getParcelable(ARG_FOLDER);
            presenter.showFolder(folder);
            toolbar.setTitle(folder.getName());
        }
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            getParentFragmentManager()
                    .setFragmentResultListener(KEY_RESULT, getViewLifecycleOwner(), new FragmentResultListener() {
                        @Override
                        public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle result) {
                            NoteFolder folder = result.getParcelable(NoteFoldersListFragment.ARG_FOLDER);
                            presenter.showFolder(folder);
                        }
                    });
        }
    }

    @Override
    public void showFolderNotes(NoteFolder folder) {

        notesContainer.removeAllViews();
        List<Note> notes = folder.getNotes();
        for (Note note: notes) {
            View itemView = createView(note);
            notesContainer.addView(itemView);
        }
    }

    @Override
    public void addNote() {

    }

    @Override
    public void deleteNote(Note note) {

    }
}
