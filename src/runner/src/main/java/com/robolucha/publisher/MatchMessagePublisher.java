package com.robolucha.publisher;

import com.robolucha.game.vo.MessageVO;
import com.robolucha.runner.MatchRunner;
import com.robolucha.runner.MatchRunnerListener;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import org.apache.log4j.Logger;

public class MatchMessagePublisher implements Consumer<MessageVO>, MatchRunnerListener {

    private static Logger logger = Logger.getLogger(MatchMessagePublisher.class);

    private RemoteQueue remoteQueue;
    private MatchRunner matchRunner;
    private Disposable disposable;

    public MatchMessagePublisher(RemoteQueue remoteQueue) {
        this.remoteQueue = remoteQueue;
    }

    protected void subscribe(MatchRunner matchRunner, ErrorHandler errorHandler) {
        this.matchRunner = matchRunner;
        this.disposable = matchRunner.getOnMessage().subscribe(this, new ErrorHandler());
    }

    public void subscribe(MatchRunner matchRunner) {
        subscribe(matchRunner, new ErrorHandler());
    }

    @Override
    public void accept(MessageVO messageVO) throws Exception {
        remoteQueue.publish(messageVO);
    }

    @Override
    public void dispose() {
        this.disposable.dispose();
    }

    @Override
    public boolean isDisposed() {
        return this.disposable.isDisposed();
    }

    protected class ErrorHandler implements Consumer<Throwable> {
        @Override
        public void accept(Throwable error) {
            logger.error("Error from onMessage", error);
        }
    }

}
