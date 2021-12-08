package com.example.mynotes_andr1.ui.list;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.example.mynotes_andr1.R;
import com.example.mynotes_andr1.domain.InMemoryNotesRepository;
import com.example.mynotes_andr1.domain.Note;

import java.util.List;

public class NotesListFragment extends Fragment implements NotesListView {

    public static final String ARG_NOTE = "ARG_NOTE";
    public static final String KEY_RESULT = "NotesListFragment_RESULT";

    private LinearLayout notesContainer;

    private NotesListPresenter presenter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        presenter = new NotesListPresenter(this, new InMemoryNotesRepository());
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_notes, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        notesContainer = view.findViewById(R.id.notes_container);

        presenter.refresh();
    }

    @Override
    public void showNotes(List<Note> notes) {

        for (Note note: notes) {
            View itemView = LayoutInflater.from(requireContext()).inflate(R.layout.item_note, notesContainer, false);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Bundle data = new Bundle();
                    data.putParcelable(ARG_NOTE, note);
                    getParentFragmentManager().setFragmentResult(KEY_RESULT, data);
                    //Toast.makeText(requireContext(), note.getName(), Toast.LENGTH_LONG).show();
                }
            });
            TextView noteCreated = itemView.findViewById(R.id.note_created);
            noteCreated.setText(note.getFormattedDateOfCreated());

            TextView noteName = itemView.findViewById(R.id.note_name);
            noteName.setText(note.getName());
            
            TextView noteText = itemView.findViewById(R.id.note_description);
            noteText.setText(note.getDescription());

            notesContainer.addView(itemView);
        }
    }
}
