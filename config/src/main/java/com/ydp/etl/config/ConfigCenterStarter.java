package com.ydp.etl.config;

import org.springframework.boot.Banner;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.config.server.EnableConfigServer;

@SpringBootApplication
@EnableConfigServer
public class ConfigCenterStarter {
    public static void main(String[] args) {
        new SpringApplicationBuilder()
                .sources(ConfigCenterStarter.class)
                .bannerMode(Banner.Mode.OFF)
                .web(WebApplicationType.SERVLET)
                .logStartupInfo(false)
                .build()
                .run(args);
    }
}
