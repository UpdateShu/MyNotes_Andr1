package com.example.mynotes_andr1.ui.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mynotes_andr1.R;
import com.example.mynotes_andr1.domain.NoteFolder;

import java.util.ArrayList;
import java.util.List;

public class NoteFoldersAdapter extends ListAdapter {

    private Fragment fragment;

    public NoteFoldersAdapter(Fragment fragment) {
        super(fragment);
    }

    class NoteFolderViewHolder extends RecyclerView.ViewHolder {

        private TextView folderName;

        public NoteFolderViewHolder(@NonNull View itemView) {
            super(itemView);

            CardView card = itemView.findViewById(R.id.f_card);
            fragment.registerForContextMenu(card);
            folderName = itemView.findViewById(R.id.folder_name);
            card.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    card.showContextMenu();
                    AdapterItem item = data.get(getAdapterPosition());
                    if (item instanceof NoteFolderAdapterItem) {
                        if (getOnClick() != null) {
                            getOnClick().onLongClick(((NoteFolderAdapterItem)item).getFolder());
                        }
                    }
                }
            });
            card.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    card.showContextMenu();
                    AdapterItem item = data.get(getAdapterPosition());
                    if (item instanceof NoteFolderAdapterItem) {
                        if (getOnClick() != null) {
                            getOnClick().onLongClick(((NoteFolderAdapterItem)item).getFolder());
                        }
                    }
                    return false;
                }
            });
        }

        public TextView getFolderName() {
            return folderName;
        }
    }

    public
    interface OnClick {
        void onClick(NoteFolder folder);

        void onLongClick(NoteFolder folder);
    }

    private NoteFoldersAdapter.OnClick onClick;

    public NoteFoldersAdapter.OnClick getOnClick() {
        return onClick;
    }

    public void setOnClick(NoteFoldersAdapter.OnClick onClick) {
        this.onClick = onClick;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_folder, parent, false);

        return new NoteFolderViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        NoteFolderViewHolder noteViewHolder = (NoteFolderViewHolder)holder;
        NoteFolderAdapterItem noteItem = (NoteFolderAdapterItem)data.get(position);
        noteViewHolder.getFolderName().setText(noteItem.getName());
    }

    @Override
    public int getItemCount() {
        return data.size();
    }
}
