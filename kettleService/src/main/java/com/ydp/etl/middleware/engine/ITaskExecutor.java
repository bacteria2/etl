package com.ydp.etl.middleware.engine;

public interface ITaskExecutor {
    <T> T doCall();

}
