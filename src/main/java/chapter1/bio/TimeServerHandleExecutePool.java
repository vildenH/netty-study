package chapter1.bio;

import java.util.concurrent.*;

public class TimeServerHandleExecutePool {
    private ExecutorService executorService;

    public TimeServerHandleExecutePool(int maxPoolSize, int queueSize) {
        executorService = new ThreadPoolExecutor(Runtime.getRuntime().availableProcessors(),
                maxPoolSize, 120L, TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(queueSize));
    }

    public void execute(Runnable task) {
        executorService.execute(task);
    }
}
