package com.ydp.etl.middleware.engine;

import java.util.function.Supplier;

public class KettleTaskBuilder<T extends AbstractKettleTask> {

    private T task;

    public KettleTaskBuilder(Supplier<T> consumer) {
        this.task = consumer.get();
    }

    /**
     * 填充文本
     * */
    public KettleTaskBuilder content(String body) {
        return this;
    }

    /**
     * 运行配置
     * */
    public KettleTaskBuilder configuration(RunningProperties properties){
        return this;
    }

    /**
     * 注册任务，获得ID
     * */
    public KettleTaskBuilder registry(String job) {
        return this;
    }

    public T build() {
        return null;
    }


}
