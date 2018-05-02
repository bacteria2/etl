package com.ydp.etl.scheduler;

import com.ydp.etl.scheduler.model.ScheduleJob;
import com.ydp.etl.scheduler.service.JobService;
import com.ydp.etl.scheduler.utils.SchedulerHelper;
import com.ydp.etl.scheduler.utils.ServiceException;
import org.quartz.CronTrigger;
import org.quartz.Scheduler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author IonCannon
 * @date 2018/4/29
 * @decription : content
 */
@Component
public class ApplicationListener  implements CommandLineRunner {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private JobService jobService;

    @Autowired
    private Scheduler scheduler;

    @Override
    public void run(String... args) throws Exception {

        // 应用启动之后执行所有可执行的的任务
        List<ScheduleJob> scheduleJobList = jobService.getAllEnableJob();
        for (ScheduleJob scheduleJob : scheduleJobList) {
            try {
                CronTrigger cronTrigger = SchedulerHelper.getCronTrigger(scheduler, scheduleJob);
                if (cronTrigger == null) {
                    SchedulerHelper.createScheduleJob(scheduler, scheduleJob);
                } else {
                    SchedulerHelper.updateScheduleJob(scheduler, scheduleJob);
                }
                logger.info("Startup {}-{} success", scheduleJob.getJobGroup(), scheduleJob.getJobName());

            } catch (ServiceException e) {
                e.printStackTrace();
            }
        }
    }
}
