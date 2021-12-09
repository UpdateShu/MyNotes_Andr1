package com.example.mynotes_andr1.domain;

import java.util.List;

public class NoteFolder {

    public String getName() {
        return name;
    }

    private String name;

    private List<Note> notes;

    public NoteFolder(String name) {
        this.name = name;
    }

}
