package com.example.mynotes_andr1.domain;

import android.os.Looper;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.logging.LogRecord;
import android.os.Handler;

public class InMemoryNotesRepository implements NotesRepository {

    public static final NotesRepository INSTANCE = new InMemoryNotesRepository();

    private Executor executor = Executors.newSingleThreadExecutor();

    private Handler handler = new  Handler(Looper.getMainLooper());

    private final ArrayList<NoteFolder> folders = new ArrayList<>();
    public ArrayList<NoteFolder> getFolders() {
        return folders;
    }

    public InMemoryNotesRepository() {
        folders.add(new NoteFolder("1", "Раздел 1"));
        folders.add(new NoteFolder("2", "Раздел 2"));
        folders.add(new NoteFolder("3", "Раздел 3"));
    }

    @Override
    public void loadAllNoteFolders(CallBack<List<NoteFolder>> callBack) {

        executor.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1000L);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        callBack.onSuccess(folders);
                    }
                });
            }
        });
    }

    @Override
    public void loadNoteFolder(NoteFolder folder, CallBack<Void> callBack) {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        loadNoteFolder(folder);
                        callBack.onSuccess(null);
                    }
                });
            }
        });
    }

    void loadNoteFolder(NoteFolder folder) {

        if (folder == null || folder.getNotes() != null)
            return;

        ArrayList<Note> noteList = new ArrayList<>();
        switch (folder.getId()) {
            case "1":
                noteList.add(new Note("1", "Заметка 1", "", "У фрагмента есть свой жизненный цикл, связанный с жизненным циклом активити, в которой он создается и отображается", new Date()));
                noteList.add(new Note("2", "Заметка 2", "", "У фрагмента нет доступа к Контексту приложения, но есть доступ к Активити, в которой он отображается (а значит и к Контексту)", new Date()));
                break;

            case "2":
                noteList.add(new Note("3", "Заметка 3", "", "Фрагментами управляет специальный класс FragmentManager. Именно через него идут все транзакции", new Date()));
                noteList.add(new Note("4", "Заметка 4", "", "Фрагменты создаются, пересоздаются, добавляются в стек и т. п. через транзакции", new Date()));
                noteList.add(new Note("5", "Заметка 5", "", "Фрагменты пересоздаются так же, как и активити", new Date()));
                noteList.add(new Note("6", "Заметка 6", "", "Фрагменты создаются, пересоздаются, добавляются в стек и т. п. через транзакции", new Date()));
                noteList.add(new Note("7", "Заметка 7", "", "Фрагменты пересоздаются так же, как и активити", new Date()));
                noteList.add(new Note("8", "Заметка 8", "", "Фрагменты создаются Менеджером фрагментов асинхронно. Это значит, что мы не знаем точно, когда именно фрагмент появится и может быть такое (хоть и редко), когда фрагмент пересоздается раньше активити, или уничтожается позже. Если в это время вы обратитесь к Контексту во фрагменте, вы получите NPE", new Date()));
                noteList.add(new Note("9", "Заметка 9", "", "Одни и те же фрагменты можно переиспользовать в разных частях приложения — это всего лишь экран в рамках какой-либо активити", new Date()));
                break;

            case "3":
                noteList.add(new Note("10", "Заметка 10", "", "Фрагменты не нужно прописывать в Манифесте, но нужно прописывать для них контейнер в xml", new Date()));
                noteList.add(new Note("11", "Заметка 11", "", "В коллбэке onCreateView() вы создаете фрагмент из xml", new Date()));
                noteList.add(new Note("12", "Заметка 12", "", "В коллбэке onViewCreated() вы инициализируете нужные вам элементы", new Date()));
                break;
        }
        folder.setNotes(noteList);
    }

    @Override
    public void addFolder(String name, CallBack<NoteFolder> callBack) {
        NoteFolder folder = new NoteFolder("", name);
        folders.add(folder);
        callBack.onSuccess(folder);
    }

    @Override
    public void deleteFolder(NoteFolder folder, CallBack<Void> callback) {

    }

    @Override
    public void addNote(NoteFolder folder, String name, String link, String description, Date date, CallBack<Note> callBack) {
        Note note = new Note("", name, "", description, date);
        callBack.onSuccess(note);
    }

    @Override
    public void updateNote(Note note, NoteFolder folder, String name, String link, String description, Date date, CallBack<Note> callBack) {

    }

    @Override
    public void deleteNote(Note note, CallBack<Void> callback) {

    }
}
