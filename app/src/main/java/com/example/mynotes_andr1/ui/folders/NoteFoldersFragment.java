package com.example.mynotes_andr1.ui.folders;

import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuInflater;
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
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.fragment.app.FragmentResultListener;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.mynotes_andr1.R;
import com.example.mynotes_andr1.domain.FirestoreNotesRepository;
import com.example.mynotes_andr1.domain.Note;
import com.example.mynotes_andr1.domain.NoteFolder;
import com.example.mynotes_andr1.ui.adapters.AdapterItem;
import com.example.mynotes_andr1.ui.adapters.NoteFolderAdapterItem;
import com.example.mynotes_andr1.ui.adapters.NoteFoldersAdapter;
import com.example.mynotes_andr1.ui.adapters.NotesAdapter;
import com.example.mynotes_andr1.ui.navdrawer.BaseAlertDialogFragment;
import com.example.mynotes_andr1.ui.navdrawer.BaseNavFeatureFragment;
import com.example.mynotes_andr1.ui.notes.NoteDialogFragment;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;

public class NoteFoldersFragment extends BaseNavFeatureFragment implements NoteFoldersListView {

    public static final String ARG_FOLDER = "ARG_FOLDER";
    public static final String TAG = "NoteFoldersListFragment";
    public static final String KEY_RESULT = "NoteFoldersListFragment_RESULT";

    private NoteFoldersAdapter adapter;
    private NoteFoldersPresenter presenter;

    private CoordinatorLayout root;
    private RecyclerView noteFoldersContainer;
    private SwipeRefreshLayout swipeRefreshLayout;

    private View empty;
    private Toolbar toolbar;

    private NoteFolder selectedFolder = null;

    @Override
    public int getToolbarId() {
        return R.id.toolbar;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        presenter = new NoteFoldersPresenter(this, new FirestoreNotesRepository());
        adapter = new NoteFoldersAdapter(this);
        adapter.setOnClick(new NoteFoldersAdapter.OnClick() {
            @Override
            public void onClick(NoteFolder folder) {
                selectedFolder = folder;
            }

            @Override
            public void onLongClick(NoteFolder folder) {

            }
        });
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_folders_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        root = view.findViewById(R.id.folders_coordinator);

        noteFoldersContainer = view.findViewById(R.id.folders_container);
        noteFoldersContainer.setLayoutManager(new LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false));
        noteFoldersContainer.setAdapter(adapter);

        swipeRefreshLayout = view.findViewById(R.id.swipe_to_refresh);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                presenter.refresh();
            }
        });
        empty = view.findViewById(R.id.empty);

        view.findViewById(R.id.fab).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddFolderBottomSheetDialogFragment fragment = new AddFolderBottomSheetDialogFragment();
                fragment.show(getParentFragmentManager(), AddFolderBottomSheetDialogFragment.TAG);
            }
        });
        getParentFragmentManager().setFragmentResultListener(AddFolderPresenter.KEY, getViewLifecycleOwner(), new FragmentResultListener() {
            @Override
            public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle result) {
                NoteFolder folder = result.getParcelable(AddFolderPresenter.ARG_FOLDER);
                presenter.onNoteFolderAdded(folder);
            }
        });
        getParentFragmentManager().setFragmentResultListener(NoteFolderDialogFragment.newInstance().getKeyResult(), requireActivity(),
                new FragmentResultListener() {
                    @Override
                    public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle result) {
                        switch (result.getInt(NoteFolderDialogFragment.ARG_BUTTON)) {
                            case DialogInterface.BUTTON_POSITIVE:
                                presenter.removeNoteFolder(selectedFolder);
                                break;
                            case DialogInterface.BUTTON_NEGATIVE:
                                break;
                        }
                    }
                });
        presenter.refresh();
    }

    @Override
    public void showNoteFolders(List<NoteFolder> folders) {

        List<AdapterItem> folderItems = presenter.getNoteFoldersItems(folders);
        adapter.setData(folderItems);
        adapter.notifyDataSetChanged();
        if (folderItems.isEmpty()) {
            showEmpty();
        }
        else {
            hideEmpty();
        }
    }

    @Override
    public void onNoteFolderAdded(NoteFolderAdapterItem adapterItem) {
        int index = adapter.addItem((AdapterItem)adapterItem);

        adapter.notifyItemInserted(index - 1);
        noteFoldersContainer.smoothScrollToPosition(index - 1);
    }

    @Override
    public void onNoteFolderRemoved(NoteFolder selectedFolder) {
        int index = adapter.removeItem(selectedFolder.getId());
        adapter.notifyItemRemoved(index);
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
    public void showEmpty() {
        empty.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideEmpty() {
        empty.setVisibility(View.GONE);
    }

    @Override
    public void onCreateContextMenu(@NonNull ContextMenu menu, @NonNull View v,
                                    @Nullable ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);

        MenuInflater menuInflater = requireActivity().getMenuInflater();
        menuInflater.inflate(R.menu.context_menu_folders, menu);
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {

        if (item.getItemId() == R.id.action_open) {
            Bundle data = new Bundle();
            data.putParcelable(ARG_FOLDER, selectedFolder);
            getParentFragmentManager().setFragmentResult(KEY_RESULT, data);
            return true;
        }
        if (item.getItemId() == R.id.action_delete) {
            if (selectedFolder != null) {
                //проверка на список заметок
                NoteFolderDialogFragment dialog = NoteFolderDialogFragment.newInstance(getResources(), selectedFolder.getName());
                dialog.show(getParentFragmentManager(), dialog.getDialogTag());
            }
            return true;
        }
        return super.onContextItemSelected(item);
    }

    @Override
    @RequiresApi(api = Build.VERSION_CODES.N)
    public void showError(String error) {
        Snackbar.make(root, error, Snackbar.LENGTH_INDEFINITE)
                .setAction(R.string.retry, new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        presenter.refresh();
                    }
                })
                .show();
    }
}
