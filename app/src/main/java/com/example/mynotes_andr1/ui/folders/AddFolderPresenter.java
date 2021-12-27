package com.example.mynotes_andr1.ui.folders;

import android.os.Bundle;

import com.example.mynotes_andr1.domain.CallBack;
import com.example.mynotes_andr1.domain.NoteFolder;
import com.example.mynotes_andr1.domain.NotesRepository;

import java.util.UUID;

public class AddFolderPresenter {

    public static final String KEY = "AddFolderBottomSheetDialogFragment_ADDFOLDER";
    public static final String ARG_FOLDER = "ARG_FOLDER";

    private AddFolderView view;
    private NotesRepository repository;

    public AddFolderPresenter(AddFolderView view, NotesRepository repository) {
        this.view = view;
        this.repository = repository;
    }

    public void onActionPressed(String folderName) {
        view.showProgress();

        repository.addFolder(folderName, new CallBack<NoteFolder>() {
            @Override
            public void onSuccess(NoteFolder result) {
                view.hideProgress();
                Bundle bundle = new Bundle();
                bundle.putParcelable(ARG_FOLDER, result);
                view.actionCompleted(KEY, bundle);
            }

            @Override
            public void onError(Throwable error) {
                view.hideProgress();
            }
        });
    }
}
