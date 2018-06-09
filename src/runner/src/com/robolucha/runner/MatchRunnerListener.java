package com.robolucha.runner;

import io.reactivex.disposables.Disposable;

public interface MatchRunnerListener extends Disposable {
    void subscribe(MatchRunner matchRunner);
}
