package com.example.mynotes_andr1.ui.notes;

import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.fragment.app.FragmentResultListener;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.mynotes_andr1.R;
import com.example.mynotes_andr1.domain.FirestoreNotesRepository;
import com.example.mynotes_andr1.domain.InMemoryNotesRepository;
import com.example.mynotes_andr1.domain.Note;
import com.example.mynotes_andr1.domain.NoteFolder;
import com.example.mynotes_andr1.ui.editor.AddNotePresenter;
import com.example.mynotes_andr1.ui.editor.UpdateNotePresenter;
import com.example.mynotes_andr1.ui.adapters.AdapterItem;
import com.example.mynotes_andr1.ui.adapters.NoteAdapterItem;
import com.example.mynotes_andr1.ui.adapters.NotesAdapter;
import com.example.mynotes_andr1.ui.navdrawer.BaseAlertDialogFragment;
import com.example.mynotes_andr1.ui.navdrawer.BaseNavFeatureFragment;
import com.example.mynotes_andr1.ui.navdrawer.NavDrawerHost;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;

public class NotesFragment extends BaseNavFeatureFragment implements NotesListView {

    public static final String ARG_FOLDER = "ARG_FOLDER";
    public static final String ARG_NOTE = "ARG_NOTE";
    public static final String TAG = "NotesEditFragment";
    public static final String KEY_RESULT = "NotesEditFragment_RESULT";

    private NotesPresenter presenter;
    private NotesAdapter adapter;

    private CoordinatorLayout root;
    private RecyclerView notesContainer;
    private SwipeRefreshLayout swipeRefreshLayout;

    private View empty;
    private Toolbar toolbar;

    private NoteFolder folder = null;

    @Override
    public int getToolbarId() {
        return R.id.toolbar;
    }

    public static NotesFragment newInstance(NoteFolder folder) {
        NotesFragment fragment = new NotesFragment();

        Bundle args = new Bundle();
        args.putParcelable(ARG_FOLDER, folder);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        presenter = new NotesPresenter(requireContext(), requireActivity(), this, new FirestoreNotesRepository());
        adapter = new NotesAdapter(this);
        adapter.setOnClick(new NotesAdapter.OnClick() {
            @Override
            public void onClick(Note note) {
                //Bundle data = new Bundle();
                //data.putParcelable(ARG_NOTE, note);
                //getParentFragmentManager().setFragmentResult(KEY_RESULT, data);
                presenter.setSelectedNote(note);
            }

            @Override
            public void onLongClick(Note note) {

            }
        });
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_notes_list, container, false);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        root = view.findViewById(R.id.notes_coordinator);

        notesContainer = view.findViewById(R.id.notes_container);
        notesContainer.setLayoutManager(new LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false));
        notesContainer.setAdapter(adapter);

        toolbar = view.findViewById(getToolbarId());
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.action_add_note:
                        //presenter.addNote();
                        return true;
                    case R.id.action_delete_note:
                        //presenter.deleteNote();
                        return true;
                }
                return false;
            }
        });
        swipeRefreshLayout = view.findViewById(R.id.swipe_to_refresh);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                presenter.loadFolder(folder);
            }
        });
        empty = view.findViewById(R.id.empty);
        if (getArguments() != null && getArguments().containsKey(ARG_FOLDER)) {
            folder = requireArguments().getParcelable(ARG_FOLDER);
        }
        presenter.showFolder(folder);

        view.findViewById(R.id.fab).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.addNote();
            }
        });

        setDetailsVisible(true);
        getParentFragmentManager().setFragmentResultListener(NoteDialogFragment.newInstance().getKeyResult(), requireActivity(),
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
        getParentFragmentManager()
                .setFragmentResultListener(AddNotePresenter.KEY, getViewLifecycleOwner(), new FragmentResultListener() {
                    @Override
                    public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle result) {
                        Note note = result.getParcelable(AddNotePresenter.ARG_NOTE);

                        presenter.onNoteAdded(note);

                    }
                });

        getParentFragmentManager()
                .setFragmentResultListener(UpdateNotePresenter.KEY, getViewLifecycleOwner(), new FragmentResultListener() {
                    @Override
                    public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle result) {
                        Note note = result.getParcelable(UpdateNotePresenter.ARG_NOTE);

                        presenter.onNoteUpdate(note);
                    }
                });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        setDetailsVisible(false);
    }

    void setDetailsVisible(boolean visible) {
        if (this.requireActivity() instanceof NavDrawerHost) {
            NavDrawerHost host = (NavDrawerHost)this.requireActivity();
            host.setDetailsVisible(visible);
        }
    }

    @Override
    public void showFolderNotes(NoteFolder folder) {

        if (folder != null) {
            toolbar.setTitle(folder.getName());
            List<AdapterItem> noteItems = presenter.getNoteItems(folder);
            adapter.setData(noteItems);
            adapter.notifyDataSetChanged();
            if (noteItems.isEmpty()) {
                showEmpty();
            }
            else {
                hideEmpty();
            }
        }
    }

    @Override
    public void onNoteAdded(NoteAdapterItem adapterItem) {
        int index = adapter.addItem(adapterItem);

        adapter.notifyItemInserted(index - 1);
        notesContainer.smoothScrollToPosition(index - 1);
    }

    @Override
    public void onNoteRemoved(Note selectedNote) {
        int index = adapter.removeItem(selectedNote.getId());
        adapter.notifyItemRemoved(index);
    }

    @Override
    public void onNoteUpdated(NoteAdapterItem adapterItem) {
        int index = adapter.updateItem(adapterItem);
        adapter.notifyItemChanged(index);
    }

    @Override
    public void showProgress() {
        swipeRefreshLayout.setRefreshing(true);
    }

    @Override
    public void hideProgress() {
        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void onCreateContextMenu(@NonNull ContextMenu menu, @NonNull View v, @Nullable ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);

        MenuInflater menuInflater = requireActivity().getMenuInflater();
        menuInflater.inflate(R.menu.context_menu_notes, menu);
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {

        Note note = presenter.getSelectedNote();
        if (item.getItemId() == R.id.action_delete) {
            String message = getString(R.string.delete_note) + "'" + note.getName() + "'?";
            NoteDialogFragment dialog = NoteDialogFragment.newInstance(getResources(), note.getName());
            dialog.show(getParentFragmentManager(), dialog.getDialogTag());
            return true;
        }
        if (item.getItemId() == R.id.action_update) {
            Bundle data = new Bundle();
            data.putParcelable(ARG_NOTE, note);
            getParentFragmentManager().setFragmentResult(KEY_RESULT, data);
            //Toast.makeText(requireContext(), note.getName(), Toast.LENGTH_LONG).show();
            presenter.setSelectedNote(note);
            return true;
        }

        return super.onContextItemSelected(item);
    }

    @Override
    public void showEmpty() {
        empty.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideEmpty() {
        empty.setVisibility(View.GONE);
    }

    @Override
    @RequiresApi(api = Build.VERSION_CODES.N)
    public void showError(String error) {
        Snackbar.make(root, error, Snackbar.LENGTH_INDEFINITE)
                .setAction(R.string.retry, new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        presenter.showFolder(folder);
                    }
                })
                .show();
    }
}
