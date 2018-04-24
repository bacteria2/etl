package com.ydp.etl.task;

import org.springframework.boot.Banner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.task.configuration.EnableTask;

@SpringBootApplication
@EnableTask
public class SchedulerTaskRunner {
    public static void main(String[] args) {
        new SpringApplicationBuilder()
                .sources(SchedulerTaskRunner.class)
                .bannerMode(Banner.Mode.OFF)
                .build()
                .run(args);

    }
}
