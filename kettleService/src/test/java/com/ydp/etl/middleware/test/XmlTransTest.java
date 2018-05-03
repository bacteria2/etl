package com.ydp.etl.middleware.test;

import com.ydp.etl.middleware.engine.server.ServerInfo;
import org.dom4j.*;
import org.junit.jupiter.api.Test;

import java.util.List;

public class XmlTransTest {
    String xmlBody = "<serverstatus><statusdesc>Online</statusdesc><memory_free>229093440</memory_free><memory_total>285736960</memory_total><cpu_cores>4</cpu_cores><cpu_process_time>7534848300</cpu_process_time><uptime>68818403</uptime><thread_count>45</thread_count><load_avg>-1.0</load_avg><os_name>Windows7</os_name><os_version>6.1</os_version><os_arch>amd64</os_arch><transstatuslist><transstatus><transname>Rowgeneratortest</transname><id>56c93d4e-96c1-4fae-92d9-d864b0779845</id><status_desc>Waiting</status_desc><error_desc/><paused>N</paused><stepstatuslist></stepstatuslist><first_log_line_nr>0</first_log_line_nr><last_log_line_nr>0</last_log_line_nr><logging_string><![CDATA[]]></logging_string></transstatus><transstatus><transname>dummy-trans</transname><id>c56961b2-c848-49b8-abde-76c8015e29b0</id><status_desc>Stopped</status_desc><error_desc/><paused>N</paused><stepstatuslist></stepstatuslist><first_log_line_nr>0</first_log_line_nr><last_log_line_nr>0</last_log_line_nr><logging_string><![CDATA[]]></logging_string></transstatus></transstatuslist><jobstatuslist><jobstatus><jobname>dummy_job</jobname><id>abd61143-8174-4f27-9037-6b22fbd3e229</id><status_desc>Stopped</status_desc><error_desc/><logging_string><![CDATA[]]></logging_string><first_log_line_nr>0</first_log_line_nr><last_log_line_nr>0</last_log_line_nr></jobstatus></jobstatuslist></serverstatus>";


    @Test
    public void test() throws DocumentException {
        Document document = DocumentHelper.parseText(xmlBody);
        ServerInfo serverInfo = new ServerInfo();

        ;
      //  List<Node> nodeList = document.selectNodes("/serverstatus/serverstatus");
        List<Element> elements = document.getRootElement().elements("jobstatuslist");


        //  document.selectSingleNode()
//        nodeList.forEach(node -> {
//            System.out.println(node.selectSingleNode("jobstatus/jobname").getText());
//            System.out.println(node.selectSingleNode("jobstatus/id").getText());
//            System.out.println(node.selectSingleNode("jobstatus/status_desc").getText());
//            System.out.println(node.selectSingleNode("jobstatus/error_desc").getText());
//
//        });
        elements.forEach(element -> {
            System.out.println(element.element("jobstatus").element("jobname").getText());
            System.out.println(element.element("jobstatus").element("id").getText());
            System.out.println(element.element("jobstatus").element("status_desc").getText());
            System.out.println(element.element("jobstatus").element("error_desc").getText());
        });
        // System.out.println(serverInfo.getStatusdesc());
    }

}
