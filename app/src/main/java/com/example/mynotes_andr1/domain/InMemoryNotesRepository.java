package com.example.mynotes_andr1.domain;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class InMemoryNotesRepository implements NotesRepository {
    @Override
    public List<Note> getAllNotes() {

        ArrayList<Note> result = new ArrayList<>();
        result.add(new Note("Заметка 1", "У фрагмента есть свой жизненный цикл, связанный с жизненным циклом активити, в которой он создается и отображается", new Date()));
        result.add(new Note("Заметка 2", "У фрагмента нет доступа к Контексту приложения, но есть доступ к Активити, в которой он отображается (а значит и к Контексту)", new Date()));
        result.add(new Note("Заметка 3", "Фрагментами управляет специальный класс FragmentManager. Именно через него идут все транзакции", new Date()));
        result.add(new Note("Заметка 4", "Фрагменты создаются, пересоздаются, добавляются в стек и т. п. через транзакции", new Date()));
        result.add(new Note("Заметка 5", "Фрагменты пересоздаются так же, как и активити", new Date()));
        result.add(new Note("Заметка 6", "Фрагменты создаются Менеджером фрагментов асинхронно. Это значит, что мы не знаем точно, когда именно фрагмент появится и может быть такое (хоть и редко), когда фрагмент пересоздается раньше активити, или уничтожается позже. Если в это время вы обратитесь к Контексту во фрагменте, вы получите NPE", new Date()));
        result.add(new Note("Заметка 7", "Одни и те же фрагменты можно переиспользовать в разных частях приложения — это всего лишь экран в рамках какой-либо активити", new Date()));
        result.add(new Note("Заметка 8", "Фрагменты не нужно прописывать в Манифесте, но нужно прописывать для них контейнер в xml", new Date()));
        result.add(new Note("Заметка 9", "В коллбэке onCreateView() вы создаете фрагмент из xml", new Date()));
        result.add(new Note("Заметка 10", "В коллбэке onViewCreated() вы инициализируете нужные вам элементы", new Date()));

        return result;
    }

    @Override
    public void addNote(Note note) {

    }

    @Override
    public void deleteNote(Note note) {

    }
}
