package com.ydp.etl.scheduler.dao;

import com.ydp.etl.scheduler.model.ScheduleJob;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author IonCannon
 * @date 2018/4/29
 * @decription : content
 */

@Mapper
public interface JobDao {
    ScheduleJob select(@Param("id") long id);

    Integer update(ScheduleJob scheduleJob);

    Integer insert(ScheduleJob scheduleJob);

    Integer delete(Long productId);

    List<ScheduleJob> getAllJob();

    List<ScheduleJob> getAllEnableJob();
}
