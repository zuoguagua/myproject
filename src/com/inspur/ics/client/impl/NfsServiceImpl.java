package com.inspur.ics.client.impl;

import java.util.ArrayList;
import java.util.List;

import org.jboss.resteasy.client.ProxyFactory;

import com.inspur.ics.client.NfsService;
import com.inspur.ics.client.rest.NfsRestService;
import com.inspur.ics.client.support.ExecutorFactory;
import com.inspur.ics.common.util.FormatUtil;
import com.inspur.ics.core.task.TaskIntermediateResult;
import com.inspur.ics.framework.result.Result;
import com.inspur.ics.pojo.ISOFile;

/**
 * @author liuyi 2015/03/18
 */
public class NfsServiceImpl implements NfsService {

	/**
	 * 用户认证token.
	 */
	private String token = "";
	/**
	 * Nfs Service 对象.
	 */
	private NfsRestService client;

	/**
	 * 构造函数,发送http请求，赋值token.
	 * @param url
	 *            请求URL
	 * @param token
	 *            认证token
	 */
	public NfsServiceImpl(String url, String token) {
		client = ProxyFactory.create(NfsRestService.class, url,
		        ExecutorFactory.getDefaultClientExecutor());
		this.token = token;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ISOFile> getAllIsoFiles() {
		// TODO Auto-generated method stub
		String resultStr = client.getIsoFiles(token);
		Result result = (Result) FormatUtil.fromXML(resultStr, new Class[] {
				Result.class, ISOFile.class }); // 解包结果inputStream流，获取result对象
		if (result.getError() != null) { // 出现错误，抛出异常
			throw new RuntimeException(result.getError().getMessage());
		} else if (result.getData() == null) { // 无数据返回空列表
			return new ArrayList<ISOFile>();
		} else { // 存在则返回ISO列表
			return (List<ISOFile>) result.getData();
		}
	}

	@Override
	public void mountNfsStorage(String hostIP) {
		// TODO Auto-generated method stub
		String resultStr = client.mount(token, hostIP);
		Result result = (Result) FormatUtil.fromXML(resultStr, new Class[] {
				Result.class, TaskIntermediateResult.class }); // 解包结果inputStream流，获取result对象
		if (result.getError() != null) { // 出现错误，抛出异常
			throw new RuntimeException(result.getError().getMessage());
		}
	}

	@Override
	public void umountNfsStorage(String hostIP) {
		// TODO Auto-generated method stub
		String resultStr = client.umount(token, hostIP);
		Result result = (Result) FormatUtil.fromXML(resultStr, new Class[] {
				Result.class, TaskIntermediateResult.class }); // 解包结果inputStream流，获取result对象
		if (result.getError() != null) { // 出现错误，抛出异常
			throw new RuntimeException(result.getError().getMessage());
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean isNfsStorageMounted(String hostIP) {
		// TODO Auto-generated method stub
		String resultStr = client.isMounted(token, hostIP); // 调用service的方法
		Result result = (Result) FormatUtil.fromXML(resultStr, new Class[] {
				Result.class, Boolean.class }); // 解包结果inputStream流，获取result对象
		if (result.getError() != null) { // 抛出异常
			throw new RuntimeException(result.getError().getMessage());
		} else { // 返回结果
			return ((List<Boolean>) result.getData()).get(0);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public String getNfsExportPath() {
		// TODO Auto-generated method stub
		String resultStr = client.getNfsExportPath(token); // 调用service的方法
		Result result = (Result) FormatUtil.fromXML(resultStr, new Class[] {
				Result.class, String.class }); // 解包结果inputStream流，获取result对象
		if (result.getError() != null) { // 抛出异常
			throw new RuntimeException(result.getError().getMessage());
		} else if (result.getData() == null) {
			return "";
		} else { // 返回结果
			return ((List<String>) result.getData()).get(0);
		}
	}

	@Override
	public void modifyNfsExportPath(String nfsExportPath) {
		// TODO Auto-generated method stub
		String resultStr = client.modifyNfsExportPath(token, nfsExportPath);
		Result result = (Result) FormatUtil.fromXML(resultStr, new Class[] {
				Result.class, TaskIntermediateResult.class }); // 解包结果inputStream流，获取result对象
		if (result.getError() != null) { // 出现错误，抛出异常
			throw new RuntimeException(result.getError().getMessage());
		}
	}

	@Override
	public String openIsoDir() {
		// TODO Auto-generated method stub
		String resultStr = client.openIsoDir(token); // 调用service的方法
		return resultStr;
	
	}

	/**
     * getters & setters.
     * @return client
     */
	public NfsRestService getClient() {
		return client;
	}
	/**
     * getters & setters.
     */
	public void setClient(NfsRestService client) {
		this.client = client;
	}

}
