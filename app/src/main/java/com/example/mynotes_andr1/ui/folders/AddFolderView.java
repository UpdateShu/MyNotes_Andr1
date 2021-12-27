package com.example.mynotes_andr1.ui.folders;

import android.os.Bundle;

public interface AddFolderView {
    void showProgress();

    void hideProgress();

    void setFolderName(String folderName);

    void actionCompleted(String key, Bundle bundle);
}
