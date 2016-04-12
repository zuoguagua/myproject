package com.inspur.ics.client.impl;

import java.util.ArrayList;
import java.util.List;

import org.jboss.resteasy.client.ProxyFactory;

import com.inspur.ics.client.VMTemplateService;
import com.inspur.ics.client.rest.VcTemplateRestService;
import com.inspur.ics.client.rest.VmTemplateRestService;
import com.inspur.ics.client.support.ExecutorFactory;
import com.inspur.ics.client.support.RemoteException;
import com.inspur.ics.common.Types.TaskTargetType;
import com.inspur.ics.common.util.FormatUtil;
import com.inspur.ics.core.task.TaskIntermediateResult;
import com.inspur.ics.framework.result.Result;
import com.inspur.ics.pojo.NfsDataStore;
import com.inspur.ics.pojo.VM;
import com.inspur.ics.pojo.VNic;
import com.inspur.ics.pojo.VirtualDisk;

/**
 * @author zuolanhai
 * 
 */
public class VmTemplateServiceImpl implements VMTemplateService {

	/**
	 * vm template service. 完成虚拟机模板接口实际工作类
	 */
	private VmTemplateRestService vmTemplateRestService;

	/**
	 * User Authentication. 用户合法性验证信息
	 */
	private String token;

	/**
	 * @param url
	 *            Service ip
	 * @param token
	 *            User Authentication
	 */
	public VmTemplateServiceImpl(String url, String token) {
		this.token = token;
		vmTemplateRestService = ProxyFactory.create(VmTemplateRestService.class,
                url,
                ExecutorFactory.getDefaultClientExecutor());
	}

	@Override
	public TaskIntermediateResult createVMTemplateByVM(String vmUUID,
			String templateName, String tmplDesp) {
		String stringXml = vmTemplateRestService.createTemplateByVm(token, vmUUID,
				templateName, tmplDesp);
		Result result = (Result) FormatUtil.fromXML(stringXml, new Class[] {
				Result.class, TaskIntermediateResult.class });
		if (result.getError() != null) {
			throw new RemoteException(result.getError().getMessage());
		} else {
			return ((TaskIntermediateResult) ((List) result.getData()).get(0));
		}
	}

	@Override
	public TaskIntermediateResult createVMTemplateByTemplate(String tmplUUID,
			String templateName, String tmplDesp) {
		String stringXml = vmTemplateRestService.createByTemplate(token, tmplUUID,
				templateName, tmplDesp);
		Result result = (Result) FormatUtil.fromXML(stringXml, new Class[] {
				Result.class, TaskIntermediateResult.class });
		if (result.getError() != null) {
			throw new RemoteException(result.getError().getMessage());
		} else {
			return ((TaskIntermediateResult) ((List) result.getData()).get(0));
		}
	}

	@Override
	public List<VM> getVmTemplateByCluster(String clusterUUID) {
		String stringXml = vmTemplateRestService.getVmTemplateByCluster(token,
				clusterUUID);
		Result result = (Result) FormatUtil.fromXML(stringXml, new Class[] {
				Result.class, VM.class, NfsDataStore.class});
		if (result.getError() != null) {
			throw new RemoteException(result.getError().getMessage());
		} else if (result.getData() == null) {
			return new ArrayList<VM>();
		} else {
			return (List<VM>) result.getData();
		}
	}

	@Override
	public List<VM> getVMTemplateList(TaskTargetType targetType, String targetUUID) {
		String stringXml = vmTemplateRestService.getVMTemplateList(token, targetType.toString(), targetUUID);
		Result result = (Result) FormatUtil.fromXML(stringXml, new Class[] {
				Result.class, VM.class });
		if (result.getError() != null) {
			throw new RemoteException(result.getError().getMessage());
		} else if (result.getData() == null) {
			return new ArrayList<VM>();
		} else {
			return (List<VM>) result.getData();
		}
	}
	@Override
	public List<VM> getVmTemplateByName(String templateName) {
		String stringXml = vmTemplateRestService.getVmTemplateByName(token,
				templateName);
		// System.out.println("strintxml : \n" + stringXml);
		Result result = (Result) FormatUtil.fromXML(stringXml, new Class[] {
				Result.class, VM.class});
		if (result.getError() != null) {
			throw new RemoteException(result.getError().getMessage());
		} else if (result.getData() == null) {
			return new ArrayList<VM>();
		} else {
			return (List<VM>) result.getData();
		}
	}

	@Override
	public TaskIntermediateResult modifyVmTemplate(VM vm) {
		for (VirtualDisk vd : vm.getConfig().getDisks()) {
	        vd.setVm(null);
		}
		for (VNic nic : vm.getConfig().getNics()) {
			nic.setVm(null);
	    }
		String stringXml = vmTemplateRestService.modifyVmTemplate(token, FormatUtil.toJson(vm));
		Result result = (Result) FormatUtil.fromXML(stringXml, new Class[] {
				Result.class, TaskIntermediateResult.class });
		if (result.getError() != null) {
			throw new RemoteException(result.getError().getMessage());
		} else {
			return ((TaskIntermediateResult) ((List) result.getData()).get(0));
		}
	}

	@Override
	public TaskIntermediateResult deleteVmTemplate(String tmplUUID) {
		String stringXml = vmTemplateRestService.deleteVmTemplate(token, tmplUUID);
		Result result = (Result) FormatUtil.fromXML(stringXml, new Class[] {
				Result.class, TaskIntermediateResult.class });
		// System.out.println("Delete vm template :" + stringXml);
		if (result.getError() != null) {
			// System.out.println("delete VmTemplate Error:" +
			// result.getError().getMessage());
			throw new RemoteException(result.getError().getMessage());
		} else {
			return ((TaskIntermediateResult) ((List) result.getData()).get(0));
		}
	}

	@Override
	public TaskIntermediateResult importVMTemplate(VM template, String filePath) {
		String stringXml = vmTemplateRestService.importVMTemplate(token, FormatUtil.toJson(template), filePath);
		Result result = (Result) FormatUtil.fromXML(stringXml, new Class[] {
				Result.class, TaskIntermediateResult.class });
		// System.out.println("Importe vm template :" + stringXml);
		if (result.getError() != null) {
			// System.out.println("import VMTemplate Error:" +
			// result.getError().getMessage());
			throw new RemoteException(result.getError().getMessage());
		} else {
			return ((TaskIntermediateResult) ((List) result.getData()).get(0));
		}
	}

	@Override
	public TaskIntermediateResult exportVMTemplate(String templateUUID, String filePath) {
		String stringXml = vmTemplateRestService.exportVMTemplate(token, templateUUID, filePath);
		Result result = (Result) FormatUtil.fromXML(stringXml, new Class[] {
				Result.class, TaskIntermediateResult.class });
		// System.out.println("Importe vm template :" + stringXml);
		if (result.getError() != null) {
			// System.out.println("export VMTemplate Error:" +
			// result.getError().getMessage());
			throw new RemoteException(result.getError().getMessage());
		} else {
			return ((TaskIntermediateResult) ((List) result.getData()).get(0));
		}
	}

	@Override
	public VM getVMTemplateInfo(String tmplUUID) {
		String stringXml = vmTemplateRestService.getVmTemplateInfo(token, tmplUUID);
		Result result = (Result) FormatUtil.fromXML(stringXml, new Class[] {
				Result.class, VM.class });
		if (result.getError() != null) {
			throw new RemoteException(result.getError().getMessage());
		} else {
			return ((VM) ((List) result.getData()).get(0));
		}
	}
}
