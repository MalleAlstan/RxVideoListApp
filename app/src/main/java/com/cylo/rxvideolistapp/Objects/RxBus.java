package com.cylo.rxvideolistapp.Objects;

import android.support.annotation.NonNull;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.subjects.BehaviorSubject;

public final class RxBus {

    // create data passing bus
    private static final BehaviorSubject<Object> behaviorSubject = BehaviorSubject.create();

    public static Disposable subscribe(@NonNull Consumer<Object> action) {
        return behaviorSubject.subscribe(action);
    }

    // method of sending data
    public static void publish(@NonNull Object message) {
        behaviorSubject.onNext(message);
    }
}
