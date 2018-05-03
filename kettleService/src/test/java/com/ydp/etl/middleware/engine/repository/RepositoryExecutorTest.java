package com.ydp.etl.middleware.engine.repository;

import com.ydp.etl.middleware.engine.ClusterInfo;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class RepositoryExecutorTest {
    private static RepositoryExecutor executor;

    @BeforeAll
    static  void   init(){
        executor= new RepositoryExecutor(
                new ClusterInfo("192.168.40.235","8080","cluster","cluster"),
                "D://repoTest//");
    }

    @Test
    void writeTransToRepo() throws IOException {

    }

    @Test
    void writeJobToRepo() throws IOException {

    }

    @Test
    void writeClusterInfoToRepo() throws IOException {
        executor.writeClusterInfoToRepo();
    }
}