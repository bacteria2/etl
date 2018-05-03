package com.ydp.etl.middleware;

import com.google.common.collect.Lists;
import com.ydp.etl.middleware.engine.ClusterInfo;
import com.ydp.etl.middleware.engine.RunningProperties;
import com.ydp.etl.middleware.engine.job.JobExecutor;
import com.ydp.etl.middleware.engine.repository.RepositoryExecutor;
import com.ydp.etl.middleware.engine.server.ServerInfoExecutor;
import com.ydp.etl.middleware.engine.trans.TransformationExecutor;
import org.apache.catalina.Cluster;
import org.apache.http.Header;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicHeader;
import org.pentaho.di.trans.steps.transexecutor.TransExecutor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.boot.Banner;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConfigurationPropertiesBinding;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

import java.util.Base64;
import java.util.LinkedList;
import java.util.List;


@SpringBootApplication
@EnableEurekaClient
@EnableConfigurationProperties
public class KettleServiceStarter {

    @Bean
    @ConfigurationProperties(prefix = "kettle.cluster")
    public ClusterInfo clusterInfo(){
        return new ClusterInfo();
    }

    @Value("${kettle.repository-path}")
    private String repositoryPath;

    public static void main(String[] args) {

        new SpringApplicationBuilder()
                .sources(KettleServiceStarter.class)
                .bannerMode(Banner.Mode.OFF)
                .web(WebApplicationType.SERVLET)
                .logStartupInfo(false)
                .build()
                .run(args);
    }

    @Bean
    public RunningProperties runningProperties(){
        RunningProperties properties= new RunningProperties();
        properties.setRepositoryPath(repositoryPath);
        return properties;
    }

    @Bean
    public ServerInfoExecutor serverInfoExecutor(ClusterInfo clusterInfo){
        return new ServerInfoExecutor(clusterInfo);
    }

   @Bean
    public RepositoryExecutor repositoryExecutor(ClusterInfo clusterInfo,RunningProperties properties){
       return new RepositoryExecutor(clusterInfo,properties.getRepositoryPath());
   }

    @Bean
    public TransformationExecutor transExecutor(ClusterInfo clusterInfo){
        return new TransformationExecutor(clusterInfo);
    }

    @Bean
    public JobExecutor jobExecutor(ClusterInfo clusterInfo,RunningProperties properties){
        return new JobExecutor(clusterInfo,properties);
    }

}
