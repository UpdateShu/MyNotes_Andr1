package com.example.mynotes_andr1.ui.list;

import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentResultListener;

import com.example.mynotes_andr1.R;
import com.example.mynotes_andr1.domain.InMemoryNotesRepository;
import com.example.mynotes_andr1.domain.Note;
import com.example.mynotes_andr1.domain.NoteFolder;
import com.example.mynotes_andr1.ui.folders.NoteFoldersFragment;

public class NotesInfoFragment extends NoteListFragment implements NotesListView {

    public static final String ARG_FOLDER = "ARG_FOLDER";
    public static final String TAG = "NotesDetailsFragment";
    public static final String KEY_RESULT = "NotesDetailsFragment_RESULT";

    public static NotesInfoFragment newInstance(NoteFolder folder) {
        NotesInfoFragment fragment = new NotesInfoFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARG_FOLDER, folder);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        presenter = new NotesPresenter(this, new InMemoryNotesRepository());
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
                            NoteFolder folder = result.getParcelable(NoteFoldersFragment.ARG_FOLDER);
                            presenter.showFolder(folder);
                        }
                    });
        }
    }

    @Override
    public void showFolderNotes(NoteFolder folder) {

        adapter.setData(folder.getNotes());
        adapter.notifyDataSetChanged();
    }

    @Override
    public void addNote() {

    }

    @Override
    public void deleteNote(Note note) {

    }
}
