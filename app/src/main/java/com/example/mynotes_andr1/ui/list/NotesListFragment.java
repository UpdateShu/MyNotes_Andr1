package com.example.mynotes_andr1.ui.list;

import android.content.DialogInterface;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

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
import com.example.mynotes_andr1.ui.details.NoteDetailsFragment;
import com.example.mynotes_andr1.ui.folders.NoteFoldersListFragment;
import com.example.mynotes_andr1.ui.navdrawer.BaseAlertDialogFragment;
import com.example.mynotes_andr1.ui.navdrawer.NavDrawerHost;

import java.util.List;

public class NotesListFragment extends NotesBaseListFragment implements NotesListView {

    public static final String ARG_NOTE = "ARG_NOTE";
    public static final String TAG = "NotesListFragment";
    public static final String KEY_RESULT = "NotesListFragment_RESULT";

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
        getParentFragmentManager().setFragmentResultListener(BaseAlertDialogFragment.KEY_RESULT, requireActivity(),
                new FragmentResultListener() {
                    @Override
                    public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle result) {
                        switch (result.getInt(BaseAlertDialogFragment.ARG_BUTTON)) {
                            case DialogInterface.BUTTON_POSITIVE:
                                Toast.makeText(requireActivity(), "Удалено!", Toast.LENGTH_SHORT).show();
                                break;
                            case DialogInterface.BUTTON_NEGATIVE:
                                break;
                        }
                    }
                });
    }

    @Override
    public void showFolderNotes(NoteFolder folder) {
        List<Note> notes = folder.getNotes();
        for (Note note: notes) {
            View itemView = createView(note);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Bundle data = new Bundle();
                    data.putParcelable(ARG_NOTE, note);
                    getParentFragmentManager().setFragmentResult(KEY_RESULT, data);
                    //Toast.makeText(requireContext(), note.getName(), Toast.LENGTH_LONG).show();
                    presenter.setSelectedNote(note);
                }
            });
            notesContainer.addView(itemView);
        }
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
        BaseAlertDialogFragment.newInstance("Подтверждение удаления", "Удалить заметку " + note.getName() +"?", "Удалить")
                .show(getParentFragmentManager(), BaseAlertDialogFragment.TAG);
    }
}
