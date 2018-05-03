package com.ydp.etl.scheduler.utils;

import com.google.common.base.Preconditions;
import com.ydp.etl.scheduler.model.ScheduleJob;
import org.quartz.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import java.util.Map;


/**
 * @author IonCannon
 * @date 2018/4/29
 * @decription : content
 */
public class SchedulerHelper {
    private final static Logger logger = LoggerFactory.getLogger(SchedulerHelper.class);

    /**
     * 获取 Trigger Key
     *
     * @param scheduleJob
     * @return
     */
    public static TriggerKey getTriggerKey(ScheduleJob scheduleJob) {
        return TriggerKey.triggerKey(scheduleJob.getTriggerName(), scheduleJob.getTriggerGroup());
    }

    /**
     * 获取 Job Key
     *
     * @param scheduleJob
     * @return
     */
    public static JobKey getJobKey(ScheduleJob scheduleJob) {
        return JobKey.jobKey(scheduleJob.getJobName(), scheduleJob.getJobGroup());
    }

    /**
     * 获取 Cron Trigger
     *
     * @param scheduler
     * @param scheduleJob
     * @return
     * @throws ServiceException
     */
    public static CronTrigger getCronTrigger(Scheduler scheduler, ScheduleJob scheduleJob) throws ServiceException {
        try {
            return (CronTrigger) scheduler.getTrigger(getTriggerKey(scheduleJob));
        } catch (SchedulerException e) {
            throw new ServiceException("Get Cron trigger failed", e);
        }
    }

    /**
     * 创建任务
     *
     * @param scheduler
     * @param scheduleJob
     * @throws ServiceException
     */
    public static void createScheduleJob(Scheduler scheduler, ScheduleJob scheduleJob) throws ServiceException {
        try {
            // 要执行的 Job 的类
            Class<? extends Job> jobClass = (Class<? extends Job>) Class.forName(scheduleJob.getClassName()).newInstance().getClass();

            JobDetail jobDetail = JobBuilder.newJob(jobClass)
                    .setJobData(parametersMap(scheduleJob.getParameters()))
                    .withIdentity(scheduleJob.getJobName(), scheduleJob.getJobGroup())
                    .withDescription(scheduleJob.getDescription())
                    .build();

            CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(scheduleJob.getCronExpression())
                    .withMisfireHandlingInstructionDoNothing();

            CronTrigger cronTrigger = TriggerBuilder.newTrigger()
                    .withIdentity(scheduleJob.getTriggerName(), scheduleJob.getTriggerGroup())
                    .withDescription(scheduleJob.getDescription())
                    .withSchedule(scheduleBuilder)
                    .startNow()
                    .build();

            scheduler.scheduleJob(jobDetail, cronTrigger);

            logger.info("Create schedule model {}-{} success", scheduleJob.getJobGroup(), scheduleJob.getJobName());

            if (scheduleJob.isPause()) {
                pauseJob(scheduler, scheduleJob);
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("Execute schedule model failed");
            throw new ServiceException("Execute schedule model failed", e);
        }
    }


    /**
     * 更新任务
     *
     * @param scheduler
     * @param scheduleJob
     * @throws ServiceException
     */
    public static void updateScheduleJob(Scheduler scheduler, ScheduleJob scheduleJob) throws ServiceException {
        try {

            TriggerKey triggerKey = getTriggerKey(scheduleJob);
            ;

            CronScheduleBuilder cronScheduleBuilder = CronScheduleBuilder.cronSchedule(scheduleJob.getCronExpression())

                    .withMisfireHandlingInstructionDoNothing();

            CronTrigger cronTrigger = getCronTrigger(scheduler, scheduleJob);

            cronTrigger = cronTrigger.getTriggerBuilder()
                    .usingJobData(parametersMap(scheduleJob.getParameters()))
                    .withIdentity(triggerKey)
                    .withDescription(scheduleJob.getDescription())
                    .withSchedule(cronScheduleBuilder).build();

            scheduler.rescheduleJob(triggerKey, cronTrigger);

            logger.info("Update schedule model {}-{} success", scheduleJob.getJobGroup(), scheduleJob.getJobName());

            if (scheduleJob.isPause()) {
                pauseJob(scheduler, scheduleJob);
            }
        } catch (SchedulerException e) {
            e.printStackTrace();
            logger.error("Update schedule model failed");
            throw new ServiceException("Update schedule model failed", e);
        }
    }

    /**
     * 执行任务
     *
     * @param scheduler
     * @param scheduleJob
     * @throws ServiceException
     */
    public static void run(Scheduler scheduler, ScheduleJob scheduleJob) throws ServiceException {
        try {
            scheduler.triggerJob(getJobKey(scheduleJob));
            logger.info("Run schedule model {}-{} success", scheduleJob.getJobGroup(), scheduleJob.getJobName());
        } catch (SchedulerException e) {
            e.printStackTrace();
            logger.error("Run schedule model failed");
            throw new ServiceException("Run schedule model failed", e);
        }
    }

    /**
     * 暂停任务
     *
     * @param scheduler
     * @param scheduleJob
     */
    public static void pauseJob(Scheduler scheduler, ScheduleJob scheduleJob) throws ServiceException {
        try {
            scheduler.pauseJob(getJobKey(scheduleJob));
            logger.info("Pause schedule model {}-{} success", scheduleJob.getJobGroup(), scheduleJob.getJobName());
        } catch (SchedulerException e) {
            e.printStackTrace();
            logger.error("Pause schedule model failed");
            throw new ServiceException("Pause model failed", e);
        }
    }

    /**
     * 继续执行任务
     *
     * @param scheduler
     * @param scheduleJob
     * @throws ServiceException
     */
    public static void resumeJob(Scheduler scheduler, ScheduleJob scheduleJob) throws ServiceException {
        try {
            scheduler.resumeJob(getJobKey(scheduleJob));
            logger.info("Resume schedule model {}-{} success", scheduleJob.getJobGroup(), scheduleJob.getJobName());
        } catch (SchedulerException e) {
            e.printStackTrace();
            logger.error("Resume schedule model failed");
            throw new ServiceException("Resume model failed", e);
        }
    }

    /**
     * 删除任务
     *
     * @param scheduler
     * @param scheduleJob
     * @throws ServiceException
     */
    public static void deleteJob(Scheduler scheduler, ScheduleJob scheduleJob) throws ServiceException {
        try {
            scheduler.deleteJob(getJobKey(scheduleJob));
            logger.info("Delete schedule model {}-{} success", scheduleJob.getJobGroup(), scheduleJob.getJobName());
        } catch (SchedulerException e) {
            e.printStackTrace();
            logger.error("Delete schedule model failed");
            throw new ServiceException("Delete model failed", e);
        }
    }

    private static JobDataMap parametersMap(String parameters){
        JobDataMap jobDataMap    =new JobDataMap();
        if(StringUtils.isEmpty(parameters)){
            logger.debug("parameter string is null,return empty dataMap");
            return jobDataMap;
        }



        //替换换行符后trim
        String rawParamString=parameters.replaceAll("\n","").trim();
        String[] keyValuePairs=rawParamString.split("&");

        for(String pair : keyValuePairs){
            if(pair.length()>0&&pair.contains("=")){
                String[] keyValue=pair.split("=");
                if(keyValue.length!=2){
                    continue;
                }
                String key=keyValue[0];
               if(!StringUtils.isEmpty(key))
                   jobDataMap.put(key.trim(),keyValue[1]);
            }
        }
        return jobDataMap;
    }
}
