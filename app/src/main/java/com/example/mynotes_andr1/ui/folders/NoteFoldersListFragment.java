package com.example.mynotes_andr1.ui.folders;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.mynotes_andr1.NotesActivity;
import com.example.mynotes_andr1.R;
import com.example.mynotes_andr1.domain.InMemoryNotesRepository;
import com.example.mynotes_andr1.domain.NoteFolder;

import java.util.List;

public class NoteFoldersListFragment extends Fragment implements NoteFoldersListView {

    public static final String ARG_FOLDER = "ARG_FOLDER";
    public static final String TAG = "NoteFoldersListFragment";
    public static final String KEY_RESULT = "NoteFoldersListFragment_RESULT";

    private LinearLayout foldersContainer;

    private NoteFoldersListPresenter presenter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        presenter = new NoteFoldersListPresenter(this, new InMemoryNotesRepository());
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_folders_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        foldersContainer = view.findViewById(R.id.folders_container);

        Button folderSelect = view.findViewById(R.id.confirm_select);
        folderSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((NotesActivity)getActivity()).confirmFolderSelection();
            }
        });

        presenter.refresh();
    }

    @Override
    public void showNoteFolders(List<NoteFolder> folders) {
        for (NoteFolder folder: folders) {
            View itemView = LayoutInflater.from(requireContext()).inflate(R.layout.item_folder, foldersContainer, false);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Bundle data = new Bundle();
                    data.putParcelable(ARG_FOLDER, folder);
                    getParentFragmentManager().setFragmentResult(KEY_RESULT, data);
                    //Toast.makeText(requireContext(), note.getName(), Toast.LENGTH_LONG).show();
                }
            });
            TextView folderName = itemView.findViewById(R.id.folder_name);
            folderName.setText(folder.getName());

            foldersContainer.addView(itemView);
        }

        View addFolderView = LayoutInflater.from(requireContext()).inflate(R.layout.add_folder, foldersContainer, false);
        foldersContainer.addView(addFolderView);
    }
}
