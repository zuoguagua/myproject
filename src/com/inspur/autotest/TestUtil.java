package com.inspur.autotest;

import java.util.Date;

import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.inspur.ics.client.ClientFactory;
import com.inspur.ics.client.TaskService;
import com.inspur.ics.common.Constant;
import com.inspur.ics.common.Types.TaskState;
import com.inspur.ics.pojo.TaskInfo;

/**
 * 测试工具类
 * 
 * @author Jss
 */
public class TestUtil {
	/**
	 * TOKENLIFE.
	 */
	private static final long TOKENLIFE = 2 * 3600 * 1000;
	/**
	 * TaskService.
	 */
	private static TaskService taskService = null;
	/**
	 * TaskService.
	 */
	private static ClientFactory factory = null;
	/**
	 * url.
	 */
	private static String url = null;
	/**
	 * username.
	 */
	private static String username = null;
	/**
	 * password.
	 */
	private static String password = null;
	/**
	 * userLocale.
	 */
	private static String userLocale = null;
	/**
	 * date.
	 */
	private static Date date = null;

	/**
	 * @param url
	 *            url.
	 * @param username
	 *            username.
	 * @param password
	 *            password.
	 * @param userLocale
	 *            userLocale.
	 */
	@Parameters({ "url", "username", "password", "userLocale" })
	@Test(groups = { "init" })
	public static void init(String url, String username, String password, String userLocale) {
		System.out.println("初始化ClientFactory");
		TestUtil.url = url;
		TestUtil.username = username;
		TestUtil.password = password;
		TestUtil.userLocale = userLocale;
		TestUtil.date = new Date();
		factory = new ClientFactory(url, username, password, userLocale);
		taskService = factory.getTaskService();
	}

	/**
	 * 等待任务执行完成.
	 * @param taskid
	 *            任务ID.
	 * @return TaskInfo.
	 */
	public static TaskInfo waitFor(String taskid) {
		TaskInfo taskInfo = null;
		while (true) {
			try {
				java.lang.Thread.sleep(2000);
			} catch (Exception e) {
				System.out.println("sleep 中断异常");
			}
			taskInfo = taskService.getTaskInfo(taskid);
			if (taskInfo.getState().equals(TaskState.ERROR)
					|| taskInfo.getState().equals(TaskState.FINISHED)) {
				return taskInfo;
			}
		}
	}

	/**
	 * 获取ClienFactory.
	 * @return factory.
	 */
	public static ClientFactory getClientFactory() {
		Date datenow = new Date();
		if ((datenow.getTime() - date.getTime()) >= (TOKENLIFE - 60 * 1000)) {
			date = new Date();
			factory = new ClientFactory(url, username, password, userLocale);
		}
		return factory;
	}
}
