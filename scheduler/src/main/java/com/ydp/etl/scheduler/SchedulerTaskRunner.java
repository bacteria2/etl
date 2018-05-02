package com.ydp.etl.scheduler;


import org.quartz.DisallowConcurrentExecution;
import org.springframework.beans.factory.config.YamlMapFactoryBean;
import org.springframework.beans.factory.config.YamlPropertiesFactoryBean;
import org.springframework.boot.Banner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.quartz.SchedulerFactoryBeanCustomizer;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.io.ClassPathResource;

import javax.sql.DataSource;

@SpringBootApplication
@DisallowConcurrentExecution

public class SchedulerTaskRunner {

    @Bean
    public PropertySourcesPlaceholderConfigurer factoryBean() {
        PropertySourcesPlaceholderConfigurer configurer = new PropertySourcesPlaceholderConfigurer();
        YamlPropertiesFactoryBean yaml = new YamlPropertiesFactoryBean();
        yaml.setResources(new ClassPathResource("quartz.yaml"));
        configurer.setProperties(yaml.getObject());
        return configurer;
    }

    @Bean
    public SchedulerFactoryBeanCustomizer customizer() {
        return schedulerFactoryBean -> {
            schedulerFactoryBean.setStartupDelay(20);
            schedulerFactoryBean.setOverwriteExistingJobs(true);

        };
    }

    @Bean
    @ConfigurationProperties(prefix = "datasource")
    public DataSource dataSource() {
        return DataSourceBuilder.create().build();
    }

    public static void main(String[] args) {
        new SpringApplicationBuilder()
                .sources(SchedulerTaskRunner.class)
                .bannerMode(Banner.Mode.OFF)
                .build()
                .run(args);
    }
}
