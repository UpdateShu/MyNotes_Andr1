package com.example.mynotes_andr1.ui.folders;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.mynotes_andr1.R;
import com.example.mynotes_andr1.domain.FirestoreNotesRepository;
import com.example.mynotes_andr1.domain.InMemoryNotesRepository;
import com.example.mynotes_andr1.domain.Note;
import com.example.mynotes_andr1.ui.editor.EditNoteFragment;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class AddFolderBottomSheetDialogFragment extends BottomSheetDialogFragment implements AddFolderView {

    public static final String TAG = "AddFolderBottomSheetDialogFragment";

    private Button btnSave;
    private ProgressBar progressBar;
    private EditText editFolderName;
    private AddFolderPresenter presenter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_add_folder_bottom_sheet, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        progressBar = view.findViewById(R.id.progress);
        btnSave = view.findViewById(R.id.btn_save);

        editFolderName = view.findViewById(R.id.folder_name);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (editFolderName.getText() == null || editFolderName.getText().toString().equals(""))
                    Toast.makeText(requireActivity(), getResources().getString(R.string.input_folder_message), Toast.LENGTH_SHORT).show();
                else {
                    presenter.onActionPressed(editFolderName.getText().toString());
                }
            }
        });
        presenter = new AddFolderPresenter(this, FirestoreNotesRepository.INSTANCE);
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
    public void setFolderName(String folderName) {
        editFolderName.setText(folderName);
    }

    @Override
    public void actionCompleted(String key, Bundle bundle) {
        getParentFragmentManager()
                .setFragmentResult(key, bundle);

        dismiss();
    }
}
