package com.ydp.etl.middleware.engine.repository;

import com.google.common.base.Preconditions;
import com.ydp.etl.middleware.engine.ClusterInfo;
import com.ydp.etl.middleware.engine.Executor;
import com.ydp.etl.middleware.engine.RunningProperties;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.dom.DOMElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Paths;

public class RepositoryExecutor extends Executor {

    private String repositoryUrl;
    private Logger log= LoggerFactory.getLogger(RepositoryExecutor.class);

    private ClusterInfo clusterInfo;

    public RepositoryExecutor(ClusterInfo clusterInfo, String repositoryUrl) {
        super(clusterInfo);
        this.clusterInfo=clusterInfo;
        this.repositoryUrl=repositoryUrl;
    }

    public void writeClusterInfoToRepo() throws IOException {
        Preconditions.checkNotNull(repositoryUrl);
        String fileSuffix = "cluster.kcs";
        Document clusterInfo = DocumentHelper.createDocument();
        clusterInfo.add(clusterInfoGen());
        String filePath=Paths.get(repositoryUrl).resolve(fileSuffix).toAbsolutePath().toString();
        try(BufferedWriter fileWriter= new BufferedWriter(new FileWriter(filePath,false))){
            clusterInfo.write(fileWriter);
        } catch (IOException e) {
            log.error("write cluster info xml error");
            throw e;
        }
    }

    public void writeFile(String xmlFileBody, String fileName) throws IOException {
        Preconditions.checkNotNull(repositoryUrl);
        File file=Paths.get(repositoryUrl).resolve(fileName).toFile();
        if(file.isDirectory())
            throw new IOException("file  is a directory");
        if(!file.getParentFile().exists())
            file.mkdirs();
        try(BufferedWriter bufferedWriter=new BufferedWriter(new FileWriter(file,false))){
            bufferedWriter.write(xmlFileBody);
            log.info("write file:{} success",fileName);
        }
    }

    public void removeFile(String fileName){
        File file=Paths.get(repositoryUrl).resolve(fileName).toFile();
        if(file.exists())
            file.delete();
    }

    private Element clusterInfoGen(){
        Element root= new DOMElement("clusterschema");
        root.addElement("name")
                .addText(clusterInfo.getName());
        root.addElement("base_port")
                .addText(clusterInfo.getBasePort());
        root.addElement("sockets_buffer_size")
                .addText(clusterInfo.getSocketsBufferSize());
        root.addElement("sockets_flush_interval")
                .addText(clusterInfo.getSocketsFlushInterval());
        root.addElement("sockets_compressed")
                .addText(clusterInfo.isSocketsCompressed()?"Y":"N");
        root.addElement("dynamic")
                .addText(clusterInfo.isDynamic()?"Y":"N");
        root.addElement("slaveservers")
                .addElement("name")
                .addText(clusterInfo.getSlaveServersName());
        return root;
    }
}
