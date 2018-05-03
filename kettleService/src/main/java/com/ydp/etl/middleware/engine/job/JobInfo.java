package com.ydp.etl.middleware.engine.job;

import com.ydp.etl.middleware.engine.AbstractKettleTask;
import org.pentaho.di.cluster.SlaveServer;
import org.pentaho.di.repository.filerep.KettleFileRepository;

public class JobInfo extends AbstractKettleTask {


    private String name;
    private String id;

    private Enum status;

    public enum JobStatus {
        WAITTING,
        RUNNING,
        FAILD,
        PAUSED,
        FINISHED;
    }

    public JobInfo() {
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Enum getStatus() {
        return status;
    }

    public void setStatus(Enum status) {
        this.status = status;
    }
}
