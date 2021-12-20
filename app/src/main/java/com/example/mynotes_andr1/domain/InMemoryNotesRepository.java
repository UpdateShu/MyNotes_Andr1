package com.example.mynotes_andr1.domain;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class InMemoryNotesRepository implements NotesRepository {

    @Override
    public List<NoteFolder> getAllFolders() {

        ArrayList<NoteFolder> folders = new ArrayList<>();
        folders.add(new NoteFolder(1, "Раздел 1"));
        folders.add(new NoteFolder(2, "Раздел 2"));
        folders.add(new NoteFolder(3, "Раздел 3"));
        return folders;
    }

    @Override
    public void loadNoteFolder(NoteFolder folder) {

        if (folder == null || folder.getNotes() != null)
            return;

        ArrayList<Note> noteList = new ArrayList<>();
        switch (folder.getId()) {
            case 1:
                noteList.add(new Note("Заметка 1", "У фрагмента есть свой жизненный цикл, связанный с жизненным циклом активити, в которой он создается и отображается", new Date()));
                noteList.add(new Note("Заметка 2", "У фрагмента нет доступа к Контексту приложения, но есть доступ к Активити, в которой он отображается (а значит и к Контексту)", new Date()));
                noteList.add(new Note("Заметка 3", "Фрагментами управляет специальный класс FragmentManager. Именно через него идут все транзакции", new Date()));
                noteList.add(new Note("Заметка 4", "Фрагменты создаются, пересоздаются, добавляются в стек и т. п. через транзакции", new Date()));
                noteList.add(new Note("Заметка 5", "Фрагменты пересоздаются так же, как и активити", new Date()));
                noteList.add(new Note("Заметка 6", "Фрагменты создаются, пересоздаются, добавляются в стек и т. п. через транзакции", new Date()));
                noteList.add(new Note("Заметка 7", "Фрагменты пересоздаются так же, как и активити", new Date()));
                break;

            case 2:
                noteList.add(new Note("Заметка 8", "Фрагменты создаются Менеджером фрагментов асинхронно. Это значит, что мы не знаем точно, когда именно фрагмент появится и может быть такое (хоть и редко), когда фрагмент пересоздается раньше активити, или уничтожается позже. Если в это время вы обратитесь к Контексту во фрагменте, вы получите NPE", new Date()));
                noteList.add(new Note("Заметка 9", "Одни и те же фрагменты можно переиспользовать в разных частях приложения — это всего лишь экран в рамках какой-либо активити", new Date()));
                break;

            case 3:
                noteList.add(new Note("Заметка 10", "Фрагменты не нужно прописывать в Манифесте, но нужно прописывать для них контейнер в xml", new Date()));
                noteList.add(new Note("Заметка 11", "В коллбэке onCreateView() вы создаете фрагмент из xml", new Date()));
                noteList.add(new Note("Заметка 12", "В коллбэке onViewCreated() вы инициализируете нужные вам элементы", new Date()));
                break;
        }
        folder.setNotes(noteList);
    }

    @Override
    public void addFolder(NoteFolder folder) {

    }

    @Override
    public void deleteFolder(NoteFolder folder) {

    }

    @Override
    public void addNote(Note note) {

    }

    @Override
    public void deleteNote(Note note) {

    }
}
