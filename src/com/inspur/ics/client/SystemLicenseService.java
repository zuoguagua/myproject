package com.inspur.ics.client;

import java.util.List;

import com.inspur.ics.core.task.TaskIntermediateResult;
import com.inspur.ics.pojo.License;

/**
 * 系统许可证书配置.
 * @author ychau
 */
public interface SystemLicenseService {

    /**
     * 获取许可证相关信息.
     * @return 许可证书信息
     */
	List<License> getLicense();

//    /**
//     * 导入或更新许可证书.
//     * @param licence 许可证书信息
//     * @return 返回任务信息
//     */
//    @SuppressWarnings("rawtypes")
//    TaskIntermediateResult modifyLicence(License licence);
    /**
     * 添加新的许可证信息.
     * @param licence
     * @return
     */
    @SuppressWarnings("rawtypes")
    TaskIntermediateResult addLicense(License licence);
    /**
     * 移除许可证信息.
     * @param licence
     * @return
     */
    @SuppressWarnings("rawtypes")
    TaskIntermediateResult removeLicense(License licence);
}
