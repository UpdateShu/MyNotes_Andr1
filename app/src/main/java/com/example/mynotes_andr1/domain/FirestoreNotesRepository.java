package com.example.mynotes_andr1.domain;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FirestoreNotesRepository implements NotesRepository {

    public static final NotesRepository INSTANCE = new FirestoreNotesRepository();

    private static final String KEY_FOLDER_NAME = "name";
    private static final String KEY_NOTE_FOLDER_ID = "folder_ID";
    private static final String KEY_NOTE_NAME = "name";
    private static final String KEY_NOTE_LINK = "link";
    private static final String KEY_NOTE_DESCRIPTION = "description";
    private static final String KEY_NOTE_CREATED = "created";

    private static final String FOLDERS = "folders";
    private static final String NOTES = "notes";

    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    public void loadAllNoteFolders(CallBack<List<NoteFolder>> callBack) {

        db.collection(FOLDERS)
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        List<DocumentSnapshot> documents = queryDocumentSnapshots.getDocuments();
                        ArrayList<NoteFolder> result = new ArrayList<>();
                        for (DocumentSnapshot snapshot: documents) {
                            String id = snapshot.getId();
                            String name = snapshot.getString(KEY_FOLDER_NAME);
                            result.add(new NoteFolder(id, name));
                        }
                        callBack.onSuccess(result);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        callBack.onError(e);
                    }
                });

    }

    @Override
    public void addFolder(String name, CallBack<NoteFolder> callBack) {
        Map<String, Object> data = new HashMap<>();

        data.put(KEY_FOLDER_NAME, name);

        db.collection(FOLDERS)
                .add(data)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        String id = documentReference.getId();

                        callBack.onSuccess(new NoteFolder(id, name));
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        callBack.onError(e);
                    }
                });
    }

    @Override
    public void deleteFolder(NoteFolder folder, CallBack<Void> callBack) {
        db.collection(FOLDERS)
                .document(folder.getId())
                .delete()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        callBack.onSuccess(unused);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        callBack.onError(e);
                    }
                });
    }

    @Override
    public void loadNoteFolder(NoteFolder folder, CallBack<Void> callBack) {

        db.collection(NOTES)
                .whereEqualTo(KEY_NOTE_FOLDER_ID, folder.getId())
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        List<DocumentSnapshot> documents = queryDocumentSnapshots.getDocuments();

                        ArrayList<Note> result = new ArrayList<>();

                        for (DocumentSnapshot snapshot: documents) {
                            String id = snapshot.getId();
                            String name = snapshot.getString(KEY_NOTE_NAME);
                            String description = snapshot.getString(KEY_NOTE_DESCRIPTION);
                            String link = snapshot.getString(KEY_NOTE_LINK);
                            Date created = snapshot.getDate(KEY_NOTE_CREATED);
                            result.add(new Note(id, name, link, description, created));
                        }
                        folder.setNotes(result);
                        callBack.onSuccess(null);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        callBack.onError(e);
                    }
                });
    }

    @Override
    public void addNote(NoteFolder folder, String name, String link, String description, Date date, CallBack<Note> callBack) {
        Map<String, Object> data = new HashMap<>();

        Date createdAt = new Date();
        data.put(KEY_NOTE_FOLDER_ID, folder.getId());
        data.put(KEY_NOTE_NAME, name);
        data.put(KEY_NOTE_LINK, link);
        data.put(KEY_NOTE_DESCRIPTION, description);
        data.put(KEY_NOTE_CREATED, createdAt);

        db.collection(NOTES)
                .add(data)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        String id = documentReference.getId();
                        callBack.onSuccess(new Note(id, name, link, description, createdAt));
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        callBack.onError(e);
                    }
                });
    }

    @Override
    public void updateNote(Note note, NoteFolder folder, String name, String link, String description, Date created, CallBack<Note> callBack) {
        Map<String, Object> data = new HashMap<>();

        data.put(KEY_NOTE_FOLDER_ID, folder.getId());
        data.put(KEY_NOTE_NAME, name);
        data.put(KEY_NOTE_LINK, link);
        data.put(KEY_NOTE_DESCRIPTION, description);
        data.put(KEY_NOTE_CREATED, note.getCreated());

        db.collection(NOTES)
                .document(note.getId())
                .set(data)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        note.setName(name);
                        note.setLink(link);
                        note.setDescription(description);
                        note.setCreated(created);
                        callBack.onSuccess(note);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        callBack.onError(e);
                    }
                });
    }

    @Override
    public void deleteNote(Note note, CallBack<Void> callback) {
        db.collection(NOTES)
                .document(note.getId())
                .delete()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        callback.onSuccess(unused);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        callback.onError(e);
                    }
                });
    }
}
