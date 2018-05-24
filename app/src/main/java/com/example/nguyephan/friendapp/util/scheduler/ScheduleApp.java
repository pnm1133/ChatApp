package com.example.nguyephan.friendapp.util.scheduler;

import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by nguye phan on 5/9/2018.
 */

public class ScheduleApp  implements Schedule{


    @Override
    public Scheduler io() {
        return Schedulers.io();
    }

    @Override
    public Scheduler compound() {
        return Schedulers.computation();
    }

    @Override
    public Scheduler main() {
        return AndroidSchedulers.mainThread();
    }
}
