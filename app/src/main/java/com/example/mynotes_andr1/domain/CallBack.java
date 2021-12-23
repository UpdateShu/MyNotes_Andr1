package com.example.mynotes_andr1.domain;

public interface CallBack<T> {

    void onSuccess(T result);
    void onError(Throwable error);
}
