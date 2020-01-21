package com.ocs.sequre;

import org.junit.Before;
import org.junit.Test;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.Scheduler;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.functions.Function3;
import io.reactivex.observers.TestObserver;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.PublishSubject;

public class Validate {
    private Observable phone = PublishSubject.just(false, false, false, false, false, false, true, true);
    private Observable email = PublishSubject.just(false, false, true, false, false, false, true, false);
    private Observable api = Single.just(true).toObservable();

//    private PublishSubject<Boolean> phone = PublishSubject.create();
//    private PublishSubject<Boolean> email = PublishSubject.create();
//    private PublishSubject<Boolean> api = PublishSubject.create();

    private TestObserver<Boolean> observer = new TestObserver<>();

//    @Before
//    public void initData() {
//        email.onNext(false);
//        phone.onNext(false);
//
//        email.onNext(false);
//        phone.onNext(false);
//
//        email.onNext(false);
//        phone.onNext(true);
//
//        email.onNext(false);
//        phone.onNext(false);
//
//        email.onNext(false);
//        phone.onNext(false);
//
//        email.onNext(false);
//        phone.onNext(false);
//
//        email.onNext(false);
//        phone.onNext(false);
//
//        email.onNext(true);
//        phone.onNext(true);
//    }

    @Test
    public void test() {
        Observable observable = Observable.combineLatest(
                objects -> {

                    return (Boolean) objects[0] && (Boolean) objects[1] && (Boolean) objects[2];
                }
                , Flowable.bufferSize()
                , email, phone, api
        ).subscribeOn(Schedulers.trampoline())
                .observeOn(Schedulers.trampoline())
                .doOnNext(o -> System.out.println("o = " + o));
        observable.subscribe(observer);

        observer.awaitTerminalEvent();
        observer.assertSubscribed();
        observer.assertNoErrors();
        observer.assertComplete();
        
//        observer.assertValues(false, false, false, false, false, false, true);

        phone.retry();
    }
}