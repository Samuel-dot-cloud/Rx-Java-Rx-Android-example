package com.studiofive.rx_java_rx_android_example;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.TextView;

import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.ObservableEmitter;
import io.reactivex.rxjava3.core.ObservableOnSubscribe;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.core.Scheduler;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.functions.Function;
import io.reactivex.rxjava3.functions.Predicate;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    private TextView text;

    private CompositeDisposable disposable = new CompositeDisposable();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        text = findViewById(R.id.text);

// Create an Observable
        Observable<Task> taskObservable = Observable
                .fromIterable(DataSource.createTasksList())
                .subscribeOn(Schedulers.io());

        taskObservable
                .buffer(2) // Apply the Buffer() operator
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<Task>>() { // Subscribe and view the emitted results
                    @Override
                    public void onSubscribe(Disposable d) {
                    }
                    @Override
                    public void onNext(List<Task> tasks) {
                        Log.d(TAG, "onNext: bundle results: -------------------");
                        for(Task task: tasks){
                            Log.d(TAG, "onNext: " + task.getDescription());
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                    }
                    @Override
                    public void onComplete() {
                    }
                });


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        disposable.clear();
    }
}