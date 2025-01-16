package com.katri.batch;


import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;

/***************************************************
 * <ul>
 * <li>업무 그룹명 : 배치 Config</li>
 * <li>서브 업무명 : 배치 Config</li>
 * <li>설     명 : 배치를 위한 config</li>
 * <li>작  성  자 : ASH</li>
 * <li>작  성  일 : 2022. 09. 28.</li>
 * </ul>
 * <pre>
 * ======================================
 * 변경자/변경일 :
 * 변경사유/내역 :
 * ======================================
 * </pre>
 ***************************************************/
@Configuration
public class SchedulerConfig implements SchedulingConfigurer {

	private final int POOL_SIZE = 10;

	@Override
	public void configureTasks(ScheduledTaskRegistrar scheduledTaskRegistrar) {

		ThreadPoolTaskScheduler threadPoolTaskScheduler = new ThreadPoolTaskScheduler();
		threadPoolTaskScheduler.setPoolSize(POOL_SIZE);
		threadPoolTaskScheduler.setThreadNamePrefix("my-scheduled-task-pool-");
		threadPoolTaskScheduler.initialize();
		scheduledTaskRegistrar.setTaskScheduler(threadPoolTaskScheduler);

	}

}
