package com.example.mynotes_andr1.ui.list;

import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentResultListener;

import com.example.mynotes_andr1.R;
import com.example.mynotes_andr1.domain.InMemoryNotesRepository;
import com.example.mynotes_andr1.domain.Note;
import com.example.mynotes_andr1.domain.NoteFolder;
import com.example.mynotes_andr1.ui.navdrawer.BaseAlertDialogFragment;
import com.example.mynotes_andr1.ui.navdrawer.NavDrawerHost;

public class NotesEditFragment extends NoteListFragment implements NotesListView {

    public static final String ARG_NOTE = "ARG_NOTE";
    public static final String TAG = "NotesEditFragment";
    public static final String KEY_RESULT = "NotesEditFragment_RESULT";

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        presenter = new NotesPresenter(this, new InMemoryNotesRepository());
        adapter = new NotesAdapter();
        adapter.setOnClick(new NotesAdapter.OnClick() {
            @Override
            public void onClick(Note note) {
                Bundle data = new Bundle();
                data.putParcelable(ARG_NOTE, note);
                getParentFragmentManager().setFragmentResult(KEY_RESULT, data);
                //Toast.makeText(requireContext(), note.getName(), Toast.LENGTH_LONG).show();
                presenter.setSelectedNote(note);
            }

            @Override
            public void onLongClick(Note note) {

            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        NoteFolder folder = presenter.getCurrentFolder();
        if (folder != null) {
            presenter.showFolder(folder);
            Toolbar toolbar = view.findViewById(R.id.toolbar);
            toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem item) {
                    switch (item.getItemId()) {
                        case R.id.action_add:
                            presenter.addNote();
                            return true;
                        case R.id.action_delete:
                            presenter.deleteNote();
                            return true;
                    }
                    return false;
                }
            });
            toolbar.setTitle(folder.getName());
        }
        /*getParentFragmentManager().setFragmentResultListener(NoteListDialogFragment.NOTES_KEY_RESULT, requireActivity(),
                new FragmentResultListener() {
                    @Override
                    public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle result) {
                        switch (result.getInt(NoteListDialogFragment.ARG_BUTTON)) {
                            case DialogInterface.BUTTON_POSITIVE:
                                Toast.makeText(requireActivity(), "Удалено!", Toast.LENGTH_SHORT).show();
                                break;
                            case DialogInterface.BUTTON_NEGATIVE:
                                break;
                        }
                    }
                });*/
    }

    @Override
    public void showFolderNotes(NoteFolder folder) {

        adapter.setData(folder.getNotes());
        adapter.notifyDataSetChanged();
    }

    @Override
    public void addNote() {
        if (this.requireActivity() instanceof NavDrawerHost) {
            NavDrawerHost host = (NavDrawerHost)this.requireActivity();
            host.showNote(null);
        }
    }

    @Override
    public void deleteNote(Note note) {
        //NoteListDialogFragment.newRemovingDialog(note)
         //       .show(getParentFragmentManager(), NoteListDialogFragment.NOTES_TAG);
    }
}
