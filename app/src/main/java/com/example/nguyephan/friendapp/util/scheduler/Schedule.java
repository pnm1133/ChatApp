package com.example.nguyephan.friendapp.util.scheduler;

import io.reactivex.Scheduler;

/**
 * Created by nguye phan on 5/9/2018.
 */

public interface Schedule {

    Scheduler io();

    Scheduler compound();

    Scheduler main();
}
