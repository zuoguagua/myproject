package com.inspur.ics.client.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jboss.resteasy.client.ProxyFactory;

import com.inspur.ics.client.VClusterService;
import com.inspur.ics.client.rest.VClusterRestService;
import com.inspur.ics.client.support.ExecutorFactory;
import com.inspur.ics.client.support.RemoteException;
import com.inspur.ics.common.util.FormatUtil;
import com.inspur.ics.core.task.TaskIntermediateResult;
import com.inspur.ics.framework.result.Result;
import com.inspur.ics.pojo.VCluster;
import com.inspur.ics.pojo.VClusterTemplate;
import com.inspur.ics.pojo.VM;
import com.inspur.ics.pojo.VMConfigInfo;

/**
 * VClusterService接口实现类.
 * @author zuolanhai
 *
 */
public class VClusterServiceImpl implements VClusterService {
    /**
     * token.
     */
    private String token;
    /**
     * VClusterRestService.
     */
    private VClusterRestService vClusterRestService;
    /**
     * @param url 虚拟集群Rest服务路径.
     * @param token 用户令牌
     */
    public VClusterServiceImpl(String url, String token) {
        vClusterRestService = ProxyFactory.create(VClusterRestService.class,
                url,
                ExecutorFactory.getDefaultClientExecutor());
        this.token = token;
    }
    @Override
    public TaskIntermediateResult createVCluster(VCluster vcluster) {
        if (!checkStringRegx(vcluster.getName(), "^[0-9a-zA-Z_\u4e00-\u9fa5_-]{1,70}$")) {
            throw new RemoteException(
                    "VCluster name illegal,must be composed of A-Z,a-z,0-9,-,_,"
                    + "or Chinese characters or its length must be less than 70");
        }
        String resultStr = vClusterRestService.createVCluster(token, FormatUtil.toJson(vcluster));
        Result result = (Result) FormatUtil.fromXML(resultStr, new Class[] {
            Result.class, TaskIntermediateResult.class });
        if (result.getError() != null) {
            throw new RemoteException(result.getError().getMessage());
        } else {
            return (TaskIntermediateResult) ((List) result.getData()).get(0);
        }
    }

    @Override
    public TaskIntermediateResult createVClusterByTemplate(String vclusterName,
            String templateUUID) {
        if (!checkStringRegx(vclusterName, "^[0-9a-zA-Z_\u4e00-\u9fa5_-]{1,70}$")) {
            throw new RemoteException(
                    "VCluster name illegal,must be composed of A-Z,a-z,0-9,-,_,"
                    + "or Chinese characters or its length must be less than 70");
        }
        String resultStr = vClusterRestService.createVClusterByTemplate(token, vclusterName, templateUUID);
        Result result = (Result) FormatUtil.fromXML(resultStr, new Class[] {
                Result.class, TaskIntermediateResult.class });
        if (result.getError() != null) {
            throw new RemoteException(result.getError().getMessage());
        } else {
            return (TaskIntermediateResult) ((List) result.getData()).get(0);
        }
    }

    @Override
    public TaskIntermediateResult createVMByIsoInVCluster(String vclusterUUID,
            VMConfigInfo config) {
        String configjson = FormatUtil.toJson(config);
        String resultStr = vClusterRestService.createVMByIsoInVCluster(vclusterUUID, configjson, token);
        Result result = (Result) FormatUtil.fromXML(resultStr, new Class[] {
                Result.class, TaskIntermediateResult.class });
        if (result.getError() != null) {
            throw new RemoteException(result.getError().getMessage());
        } else if (result.getData() != null) {
            return ((List<TaskIntermediateResult>) result.getData()).get(0);
        }
        return new TaskIntermediateResult<String>("0");
    }

//    @Override
//    public TaskIntermediateResult createVMByTemplateInVCluster(String vclusterUUID,
//            String templateUUID, String vmName) {
//        String resultStr = client.createVMByTemplateInVCluster(vclusterUUID, templateUUID, vmName, token);
//        Result result = (Result) FormatUtil.fromXML(resultStr, new Class[] {
//                 Result.class, TaskIntermediateResult.class });
//        if (result.getError() != null) {
//            throw new RemoteException(result.getError().getMessage());
//        } else if (result.getData() != null) {
//            return ((List<TaskIntermediateResult>) result.getData()).get(0);
//        }
//        return new TaskIntermediateResult<String>("0");
//    }

//    @Override
//    public TaskIntermediateResult copyVMInVCluster(String vclusterUUID,
//            String beCopiedVMUUID, String newVMName) {
//        String resultStr = client.copyVMInVCluster(vclusterUUID, beCopiedVMUUID, newVMName, token);
//        Result result = (Result) FormatUtil.fromXML(resultStr, new Class[] {
//                Result.class, TaskIntermediateResult.class });
//       if (result.getError() != null) {
//           throw new RemoteException(result.getError().getMessage());
//       } else if (result.getData() != null) {
//           return ((List<TaskIntermediateResult>) result.getData()).get(0);
//       }
//       return new TaskIntermediateResult<String>("0");
//    }
//
//    @Override
//    public TaskIntermediateResult batchCreateVMByTemplateInVCluster(
//            String vclusterUUID, String templateUUID, String prefixName, int num) {
//        String resultStr = client.batchCreateVMByTemplateInVCluster(
//    vclusterUUID, templateUUID, prefixName, num, token);
//        Result result = (Result) FormatUtil.fromXML(resultStr, new Class[] {
//                Result.class, TaskIntermediateResult.class });
//       if (result.getError() != null) {
//           throw new RemoteException(result.getError().getMessage());
//       } else if (result.getData() != null) {
//           return ((List<TaskIntermediateResult>) result.getData()).get(0);
//       }
//       return new TaskIntermediateResult<String>("0");
//    }

    @Override
    public List<VM> getVmInVCluster(String vclusterUUID) {
        String restring = vClusterRestService.getVmInVCluster(token, vclusterUUID);
        Result result = (Result) FormatUtil.fromXML(restring, new Class[] {Result.class, VM.class});
        if (result.getError() != null) {
            throw new RemoteException(result.getError().getMessage());
        } else if (result.getData() == null) {
            return new ArrayList<VM>();
        } else {
            return (List<VM>) result.getData();
        }
    }

    @Override
    public List<VCluster> getVClusterList() {
        String restring = vClusterRestService.getVClusterList(token);
        Result result = (Result) FormatUtil.fromXML(restring, new Class[] {Result.class, VCluster.class});
        if (result.getError() != null) {
            throw new RemoteException(result.getError().getMessage());
        } else if (result.getData() == null) {
            return new ArrayList<VCluster>();
        } else {
            return (List<VCluster>) result.getData();
        }
    }

    @Override
    public TaskIntermediateResult deleteVMInVCluster(String vclusterUUID,
            String vmUUID) {
        String resultStr = vClusterRestService.deleteVMInVCluster(vclusterUUID, vmUUID, token);
        Result result = (Result) FormatUtil.fromXML(resultStr, new Class[] {
                Result.class, TaskIntermediateResult.class });
       if (result.getError() != null) {
           throw new RemoteException(result.getError().getMessage());
       } else if (result.getData() != null) {
           return ((List<TaskIntermediateResult>) result.getData()).get(0);
       }
       return new TaskIntermediateResult<String>("0");
    }

//    @Override
//    public List<VCluster> getVClustersInCluster(String clusterUUID) {
//        String restring = vClusterRestService.getv;
//        Result result = (Result) FormatUtil.fromXML(restring, new Class[] {Result.class, VCluster.class});
//        if (result.getError() != null) {
//            throw new RemoteException(result.getError().getMessage());
//        } else if (result.getData() == null) {
//            return new ArrayList<VCluster>();
//        } else {
//            return (List<VCluster>) result.getData();
//        }
//    }

    @Override
    public VCluster getVClusterInfo(String vclusterUUID) {
        String restring = vClusterRestService.getVClusterInfo(token, vclusterUUID);
        Result result = (Result) FormatUtil.fromXML(restring, new Class[] {Result.class, VCluster.class});
        if (result.getError() != null) {
            throw new RemoteException(result.getError().getMessage());
        } else if (result.getData() == null) {
       	    return null;
        } else {
            return (VCluster) ((List) result.getData()).get(0);
        }
    }
    @Override
    public VCluster getVClusterInfoByName(String vclusterName) {
         String restring = vClusterRestService.getVClusterInfoByName(token, vclusterName);
         Result result = (Result) FormatUtil.fromXML(restring, new Class[] {Result.class, VCluster.class});
         if (result.getError() != null) {
             throw new RemoteException(result.getError().getMessage());
         } else if (result.getData() == null) {
        	 return null;
         } else {
             return (VCluster) ((List) result.getData()).get(0);
         }
    }

    @Override
    public TaskIntermediateResult copyVCluster(String vclusterUUID,
            String vclusterName) {
        if (!checkStringRegx(vclusterName, "^[0-9a-zA-Z_\u4e00-\u9fa5_-]{1,70}$")) {
            throw new IllegalArgumentException(
                    "VCluster name illegal,must be composed of A-Z,a-z,0-9,-,_,"
                    + "or Chinese characters or its length must be less than 70");
        }
        String resultStr = vClusterRestService.copyVCluster(token, vclusterUUID, vclusterName);
        Result result = (Result) FormatUtil.fromXML(resultStr, new Class[] {
                Result.class, TaskIntermediateResult.class });
       if (result.getError() != null) {
           throw new RemoteException(result.getError().getMessage());
       } else if (result.getData() == null) {
      	   return null;
       } else {
           return (TaskIntermediateResult) ((List) result.getData()).get(0);
       }
    }

    @Override
    public TaskIntermediateResult deleteVCluster(String vclusterUUID) {
        String resultStr = vClusterRestService.deleteVCluster(token, vclusterUUID);
        Result result = (Result) FormatUtil.fromXML(resultStr, new Class[] {
                Result.class, TaskIntermediateResult.class });
       if (result.getError() != null) {
           throw new RemoteException(result.getError().getMessage());
       } else if (result.getData() == null) {
      	   return null;
       } else {
           return (TaskIntermediateResult) ((List) result.getData()).get(0);
       }
    }

    @Override
    public List<VM> getVMsCanBeMovedIn() {
        String resultStr = vClusterRestService.getVMsCanBeMovedIn(token);
        Result result = (Result) FormatUtil.fromXML(resultStr, new Class[] {Result.class, VM.class});
        if (result.getError() != null) {
            throw new RemoteException(result.getError().getMessage());
        } else if (result.getData() == null) {
            return new ArrayList<VM>();
        } else {
            return (List<VM>) result.getData();
        }
    }

    @Override
    public TaskIntermediateResult moveVMInVCluster(String vclusterUUID,
           List<String> vms) {
    	if (vms == null) {
    		throw new RemoteException("要移除的虚拟机数目为空"); 
    	}
        String resultStr = vClusterRestService.moveVMInVCluster(token, vclusterUUID, (String[])vms.toArray(new String[vms.size()]));
        Result result = (Result) FormatUtil.fromXML(resultStr, new Class[] {
                Result.class, TaskIntermediateResult.class });
       if (result.getError() != null) {
           throw new RemoteException(result.getError().getMessage());
       } else if (result.getData() == null) {
      	   return null;
       } else {
           return (TaskIntermediateResult) ((List) result.getData()).get(0);
       }
    }

    @Override
    public TaskIntermediateResult moveVMOutVCluster(String vclusterUUID,
            String vmUUID) {
        String resultStr = vClusterRestService.moveVMOutVCluster(token, vclusterUUID, vmUUID);
        Result result = (Result) FormatUtil.fromXML(resultStr, new Class[] {
                Result.class, TaskIntermediateResult.class });
       if (result.getError() != null) {
           throw new RemoteException(result.getError().getMessage());
       } else if (result.getData() == null) {
      	   return null;
       } else {
           return (TaskIntermediateResult) ((List) result.getData()).get(0);
       }
     }

    @Override
    public TaskIntermediateResult powerOnVCluster(String vclusterUUID) {
        String resultStr = vClusterRestService.powerOnVCluster(token, vclusterUUID);
        Result result = (Result) FormatUtil.fromXML(resultStr, new Class[] {
                Result.class, TaskIntermediateResult.class });
       if (result.getError() != null) {
           throw new RemoteException(result.getError().getMessage());
       } else if (result.getData() == null) {
      	   return null;
       } else {
           return (TaskIntermediateResult) ((List) result.getData()).get(0);
       }
    }

    @Override
    public TaskIntermediateResult powerOffVCluster(String vclusterUUID) {
        String resultStr = vClusterRestService.powerOffVCluster(token, vclusterUUID);
        Result result = (Result) FormatUtil.fromXML(resultStr, new Class[] {
                Result.class, TaskIntermediateResult.class });
       if (result.getError() != null) {
           throw new RemoteException(result.getError().getMessage());
       } else if (result.getData() == null) {
      	   return null;
       } else {
           return (TaskIntermediateResult) ((List) result.getData()).get(0);
       }
    }

    @Override
    public TaskIntermediateResult restartVCluster(String vclusterUUID) {
        String resultStr = vClusterRestService.restartVCluster(token, vclusterUUID);
        Result result = (Result) FormatUtil.fromXML(resultStr, new Class[] {
                Result.class, TaskIntermediateResult.class });
       if (result.getError() != null) {
           throw new RemoteException(result.getError().getMessage());
       } else if (result.getData() == null) {
      	   return null;
       } else {
           return (TaskIntermediateResult) ((List) result.getData()).get(0);
       }
    }

    @Override
    public TaskIntermediateResult shutdownVCluster(String vclusterUUID) {
        String resultStr = vClusterRestService.shutdownVCluster(token, vclusterUUID);
        Result result = (Result) FormatUtil.fromXML(resultStr, new Class[] {
                Result.class, TaskIntermediateResult.class });
       if (result.getError() != null) {
           throw new RemoteException(result.getError().getMessage());
       } else if (result.getData() == null) {
      	   return null;
       } else {
           return (TaskIntermediateResult) ((List) result.getData()).get(0);
       }
    }
    @Override
    public TaskIntermediateResult modifyVCluster(VCluster vcluster) {
        if (!checkStringRegx(vcluster.getName(), "^[0-9a-zA-Z_\u4e00-\u9fa5_-]{1,70}$")) {
             throw new IllegalArgumentException(
                     "VCluster name illegal,must be composed of A-Z,a-z,0-9,-,_,or Chinese characters or its length must be less than 70");
         }
        String vclusterJson = FormatUtil.toJson(vcluster);
        String resultStr = vClusterRestService.modifyVCluster(token, vclusterJson);
        Result result = (Result) FormatUtil.fromXML(resultStr, new Class[] {
                Result.class, TaskIntermediateResult.class });
       if (result.getError() != null) {
           throw new RemoteException(result.getError().getMessage());
       } else if (result.getData() == null) {
      	   return null;
       } else {
           return (TaskIntermediateResult) ((List) result.getData()).get(0);
       }
    }
    /**
     * 检查输入的数据是否符合正则表达式regx中定义的规则.
     * @param checkString
     *            要检查的数据
     * @param regx
     *             字符正则表达式
     * @return boolean 符合正则表达式regx中定义的规则，返回true； 否则返回false
     */
    public static boolean checkStringRegx(String checkString, String regx) {
       if (checkString != null) {
           checkString = checkString.trim();
           Pattern p = Pattern.compile(regx, Pattern.CASE_INSENSITIVE);
           Matcher m = p.matcher(checkString);
           return m.find();
       } else {
            return false;
       }
    }


}
