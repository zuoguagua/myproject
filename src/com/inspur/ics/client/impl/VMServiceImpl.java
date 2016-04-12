package com.inspur.ics.client.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.jboss.resteasy.client.ProxyFactory;

import com.inspur.ics.client.VMService;
import com.inspur.ics.client.rest.VMRestService;
import com.inspur.ics.client.support.ExecutorFactory;
import com.inspur.ics.client.support.RemoteException;
import com.inspur.ics.common.Types;
import com.inspur.ics.common.Types.TaskTargetType;
import com.inspur.ics.common.util.FormatUtil;
import com.inspur.ics.core.task.TaskIntermediateResult;
import com.inspur.ics.framework.result.Result;
import com.inspur.ics.pojo.CpuInfo;
import com.inspur.ics.pojo.Host;
import com.inspur.ics.pojo.NfsDataStore;
import com.inspur.ics.pojo.StandardPortGroup;
import com.inspur.ics.pojo.VM;
import com.inspur.ics.pojo.VMBackup;
import com.inspur.ics.pojo.VMConfigInfo;
import com.inspur.ics.pojo.VMSnapshot;
import com.inspur.ics.pojo.VNic;
import com.inspur.ics.pojo.VNicQos;
import com.inspur.ics.pojo.VirtualCdrom;
import com.inspur.ics.pojo.VirtualDisk;
import com.inspur.ics.pojo.VirtualDiskQos;
import com.inspur.ics.pojo.monitor.VmMonitorInfo;

/**
 * Implementation of VMService interface.
 */
@SuppressWarnings({"rawtypes", "unchecked" })
public class VMServiceImpl implements VMService {
    /**
     * vm rest service.
     */
    private VMRestService restService;
    /**
     * user token.
     */
    private String token;

    /**
     * Constructor.
     * @param url
     *            request path
     * @param token
     *            user token
     */
    public VMServiceImpl(String url, String token) {
        restService = (VMRestService) ProxyFactory.create(VMRestService.class,
                url,
                ExecutorFactory.getDefaultClientExecutor());
//        restService = ProxyFactory.create(VMRestService.class, url);
//        ProxyFactory.
        this.token = token;
    }

    @Override
    public List<VM> getVMInCluster(String clusterUUID) {
        String resultStr = restService.getVMsInCluster(token, clusterUUID);
        Result result = (Result) FormatUtil.fromXML(resultStr, new Class[] {
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
    public List<VM> getAll() {
        String resultStr = restService.getAllVMs(token);
        Result result = (Result) FormatUtil.fromXML(resultStr, new Class[] {
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
    public List<VM> getVMList(TaskTargetType targetType, String targetUuid) {
        String resultStr = restService.getAllCustomVMs(token, targetType.toString(), targetUuid);
        Result result = (Result) FormatUtil.fromXML(resultStr, new Class[] {
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
    public TaskIntermediateResult createVMByIso(VM vm) {
        for (VirtualDisk vd : vm.getConfig().getDisks()) {
            if (vd.getQos() == null) {
                VirtualDiskQos vdQoS = new VirtualDiskQos();
                vdQoS.setEnabled(false);
                vd.setQos(vdQoS);
                }
            vd.setVm(null);
        }
        for (VNic nic : vm.getConfig().getNics()) {
            if (nic.getQos() == null) {
                VNicQos nicQoS = new VNicQos();
                nicQoS.setEnable(false);
                nic.setQos(nicQoS);
            }
            nic.setVm(null);
            if (nic.getPortGroup() instanceof StandardPortGroup) {
                ((StandardPortGroup) nic.getPortGroup()).setIscsiIface(null);
            }
       }
        String resultStr = restService.createVMByIso(token, FormatUtil.toJson(vm));
        Result result = (Result) FormatUtil.fromXML(resultStr, new Class[] {
                Result.class, TaskIntermediateResult.class});
        if (result.getError() != null) {
            throw new RemoteException(result.getError().getMessage());
        } else {
            // return (TaskIntermediateResult) result.getData();
            return (TaskIntermediateResult) ((List) result.getData()).get(0);
        }
    }

    @Override
    public TaskIntermediateResult powerOnVM(String vmUUID) {
        String resultStr = restService.powerOnVM(token, vmUUID);
        Result result = (Result) FormatUtil.fromXML(resultStr, new Class[] {
                Result.class, TaskIntermediateResult.class});
        if (result.getError() != null) {
            throw new RemoteException(result.getError().getMessage());
        } else {
            return (TaskIntermediateResult) ((List) result.getData()).get(0);
        }
    }

    @Override
    public TaskIntermediateResult powerOffVM(String vmUUID) {
        String resultStr = restService.powerOffVM(token, vmUUID);
        Result result = (Result) FormatUtil.fromXML(resultStr, new Class[] {
                Result.class, TaskIntermediateResult.class});
        if (result.getError() != null) {
            throw new RemoteException(result.getError().getMessage());
        } else {
            return (TaskIntermediateResult) ((List) result.getData()).get(0);
        }
    }

    @Override
    public TaskIntermediateResult restartVM(String vmUUID) {
        String resultStr = restService.restartVM(token, vmUUID);
        Result result = (Result) FormatUtil.fromXML(resultStr, new Class[] {
                Result.class, TaskIntermediateResult.class});
        if (result.getError() != null) {
            throw new RemoteException(result.getError().getMessage());
        } else {
            return (TaskIntermediateResult) ((List) result.getData()).get(0);
        }
    }

    @Override
    public TaskIntermediateResult pauseVM(String vmUUID) {
        String resultStr = restService.pauseVM(token, vmUUID);
        Result result = (Result) FormatUtil.fromXML(resultStr, new Class[] {
                Result.class, TaskIntermediateResult.class});
        if (result.getError() != null) {
            throw new RemoteException(result.getError().getMessage());
        } else {
            return (TaskIntermediateResult) ((List) result.getData()).get(0);
        }
    }

    @Override
    public TaskIntermediateResult resumeVM(String vmUUID) {
        String resultStr = restService.resumeVM(token, vmUUID);
        Result result = (Result) FormatUtil.fromXML(resultStr, new Class[] {
                Result.class, TaskIntermediateResult.class});
        if (result.getError() != null) {
            throw new RemoteException(result.getError().getMessage());
        } else {
            return (TaskIntermediateResult) ((List) result.getData()).get(0);
        }
    }

    @Override
    public TaskIntermediateResult deleteVM(String vmUUID) {
        String resultStr = restService.deleteVM(token, vmUUID);
        Result result = (Result) FormatUtil.fromXML(resultStr, new Class[] {
                Result.class, TaskIntermediateResult.class});
        if (result.getError() != null) {
            throw new RemoteException(result.getError().getMessage());
        } else {
            return (TaskIntermediateResult) ((List) result.getData()).get(0);
        }
    }

    @Override
    public TaskIntermediateResult modifyVM(VM vm) {
        for (VirtualDisk vd : vm.getConfig().getDisks()) {
            vd.setVm(null);
        }
        for (VNic nic : vm.getConfig().getNics()) {
            nic.setVm(null);
            if(nic.getPortGroup() instanceof StandardPortGroup){
            	StandardPortGroup pg = ((StandardPortGroup) nic.getPortGroup());
            	pg.setIscsiIface(null);
            }
        }
        vm.getConfig().setName(vm.getName());
        String resultStr = restService.modifyVM(token,
                FormatUtil.toJson(vm));
        Result result = (Result) FormatUtil.fromXML(resultStr, new Class[] {
                Result.class, TaskIntermediateResult.class});
        if (result.getError() != null) {
            throw new RemoteException(result.getError().getMessage());
        } else {
            return (TaskIntermediateResult) ((List) result.getData()).get(0);
        }
    }

    @Override
    public TaskIntermediateResult copyVM(String vmUUID,
            String vmName) {
        String resultStr = restService.copyVM(token, vmUUID, vmName);
        Result result = (Result) FormatUtil.fromXML(resultStr, new Class[] {
                Result.class, TaskIntermediateResult.class});
        if (result.getError() != null) {
            throw new RemoteException(result.getError().getMessage());
        } else {
            return (TaskIntermediateResult) ((List) result.getData()).get(0);
        }
    }

    @Override
    public TaskIntermediateResult createVMByTemplate(String templateUUID,
            String vmName,
            String hostUuid,
            List<VirtualDisk> diskList) {
        List<String> vdJsons = new ArrayList<String>();
        for (VirtualDisk vd : diskList) {
            vd.setVm(null);
            vdJsons.add(FormatUtil.toJson(vd));
        }
        String resultStr = restService.createVMByTemplate(token, templateUUID,
                vmName, hostUuid, vdJsons.toArray(new String[0]));
        Result result = (Result) FormatUtil.fromXML(resultStr, new Class[] {
                Result.class, TaskIntermediateResult.class});
        if (result.getError() != null) {
            throw new RemoteException(result.getError().getMessage());
        } else {
            return (TaskIntermediateResult) ((List) result.getData()).get(0);
        }
    }

    @Override
    public TaskIntermediateResult batchCreateVMByTemplate(String templateUUID,
            String vmName, int num) {
        String resultStr = restService.batchCreateVMByTemplate(token,
                templateUUID, vmName, num);
        Result result = (Result) FormatUtil.fromXML(resultStr, new Class[] {
                Result.class, TaskIntermediateResult.class});
        if (result.getError() != null) {
            throw new RemoteException(result.getError().getMessage());
        } else {
            return (TaskIntermediateResult) ((List) result.getData()).get(0);
        }
    }

    @Override
    public List<Host> getAvailableHostsToMigrateVM(String vmUUID) {
        String resultStr = restService.getHostsMovedIn(token, vmUUID);
        Result result = (Result) FormatUtil.fromXML(resultStr, new Class[] {
                Result.class, Host.class});
        if (result.getError() != null) {
            throw new RemoteException(result.getError().getMessage());
        } else if (result.getData() == null) {
            return new ArrayList<Host>();
        } else {
            return (List<Host>) result.getData();
        }
    }

    @Override
    public TaskIntermediateResult migrateVM(String vmUUID,
           String hostUuid) {
        String resultStr = restService.migrateVM(token, vmUUID, hostUuid);
        Result result = (Result) FormatUtil.fromXML(resultStr, new Class[] {
                Result.class, TaskIntermediateResult.class});
        if (result.getError() != null) {
            throw new RemoteException(result.getError().getMessage());
        } else {
            return (TaskIntermediateResult) ((List) result.getData()).get(0);
        }
    }

    @Override
    public List<Host> getAvailableHostsToRelocateVM(String vmUUID) {
        String resultStr = restService.getAvailableHostsToRelocateVM(token, vmUUID);
        Result result = (Result) FormatUtil.fromXML(resultStr, new Class[] {
                Result.class, TaskIntermediateResult.class});
        if (result.getError() != null) {
            throw new RemoteException(result.getError().getMessage());
        } else if (result.getData() == null) {
            return new ArrayList<Host>();
        } else {
            return (List<Host>) result.getData();
        }
    }

    @Override
    public TaskIntermediateResult relocate(String vmUUID, String hostUUID, List<VirtualDisk> diskList) {
        List<String> vdJsons = new ArrayList<String>();
        for (VirtualDisk vd : diskList) {
            vd.setVm(null);
            vdJsons.add(FormatUtil.toJson(vd));
        }
        String resultStr = restService.relocate(token, vmUUID, hostUUID, vdJsons.toArray(new String[0]));
        Result result = (Result) FormatUtil.fromXML(resultStr, new Class[] {
                Result.class, TaskIntermediateResult.class, List.class});
        if (result.getError() != null) {
            throw new RemoteException(result.getError().getMessage());
        } else {
            return (TaskIntermediateResult) ((List) result.getData()).get(0);
        }
    }
    @Override
    public VM getVMInfo(String vmUUID) {
        String resultStr = restService.getVMInfo(token, vmUUID);
        Result result = (Result) FormatUtil.fromXML(resultStr, new Class[] {
                Result.class, VM.class, NfsDataStore.class});
        if (result.getError() != null) {
            throw new RemoteException(result.getError().getMessage());
        } else if (result.getData() == null) {
            return null;
        } else {
            return (VM) ((List) result.getData()).get(0);
        }
    }

    @Override
    public VM getVMInfoByName(String vmName) {
        String resultStr = restService.getVMInfoByName(token, vmName);
        Result result = (Result) FormatUtil.fromXML(resultStr, new Class[] {
                Result.class, VM.class});
        if (result.getError() != null) {
            throw new RemoteException(result.getError().getMessage());
        } else if (result.getData() == null) {
            return null;
        } else {
            return (VM) ((List) result.getData()).get(0);
        }
    }

    @Override
    public VmMonitorInfo getVMRuntimeInfo(String vmUUID) {
        String resultStr = restService.getVMRuntimInfo(token, vmUUID);
        Result result = (Result) FormatUtil.fromXML(resultStr, new Class[] {
                Result.class, VmMonitorInfo.class});
        if (result.getError() != null) {
            throw new RemoteException(result.getError().getMessage());
        } else if (result.getData() == null) {
            return null;
        } else {
            return (VmMonitorInfo) ((List) result.getData()).get(0);
        }
    }

    @Override
    public String openVncConsole(String vmUUID) {
        String resultStr = restService.openConsole(token, vmUUID);
        return resultStr;
    }

    @Override
    public TaskIntermediateResult attachCdrom(String vmUUID,
            VirtualCdrom cdrom) {
        String resultStr = restService.attachCdrom(token, vmUUID, FormatUtil.toJson(cdrom));
        Result result = (Result) FormatUtil.fromXML(resultStr, new Class[] {
                Result.class, TaskIntermediateResult.class});
        if (result.getError() != null) {
            throw new RemoteException(result.getError().getMessage());
        } else {
            return (TaskIntermediateResult) ((List) result.getData()).get(0);
        }
    }

    @Override
    public TaskIntermediateResult detachCdrom(String vmUUID) {
        String resultStr = restService.detachCdrom(token, vmUUID);
        Result result = (Result) FormatUtil.fromXML(resultStr, new Class[] {
                Result.class, TaskIntermediateResult.class});
        if (result.getError() != null) {
            throw new RemoteException(result.getError().getMessage());
        } else {
            return (TaskIntermediateResult) ((List) result.getData()).get(0);
        }
    }

    @Override
    public TaskIntermediateResult hotAddDisk(String vmUUID,
            VirtualDisk disk) {
        String resultStr = restService.hotAddDisk(token, vmUUID,
                FormatUtil.toJson(disk));
        Result result = (Result) FormatUtil.fromXML(resultStr, new Class[] {
                Result.class, TaskIntermediateResult.class});
        if (result.getError() != null) {
            throw new RemoteException(result.getError().getMessage());
        } else {
            return (TaskIntermediateResult) ((List) result.getData()).get(0);
        }
    }

    @Override
    public TaskIntermediateResult configDirectNetVNicForVM(VMConfigInfo config) {
        String resultStr = restService.configDirectNet(token,
                FormatUtil.toJson(config));
        Result result = (Result) FormatUtil.fromXML(resultStr, new Class[] {
                Result.class, TaskIntermediateResult.class});
        if (result.getError() != null) {
            throw new RemoteException(result.getError().getMessage());
        } else {
            return (TaskIntermediateResult) ((List) result.getData()).get(0);
        }
    }

    @Override
    public TaskIntermediateResult attachUsb(String vmUUID,
            String usbDevice) {
        String resultStr = restService.attachUsb(token, vmUUID, usbDevice,
                Types.UsbDeviceType.LOCAL);
        Result result = (Result) FormatUtil.fromXML(resultStr, new Class[] {
                Result.class, TaskIntermediateResult.class});
        if (result.getError() != null) {
            throw new RemoteException(result.getError().getMessage());
        } else {
            return (TaskIntermediateResult) ((List) result.getData()).get(0);
        }
    }

    @Override
    public TaskIntermediateResult detachUsb(String vmUUID) {
        String resultStr = restService.detachUsb(token, vmUUID);
        Result result = (Result) FormatUtil.fromXML(resultStr, new Class[] {
                Result.class, TaskIntermediateResult.class});
        if (result.getError() != null) {
            throw new RemoteException(result.getError().getMessage());
        } else {
            return (TaskIntermediateResult) ((List) result.getData()).get(0);
        }
    }

    @Override
    public TaskIntermediateResult configHA(String[] vmUUIDs,
            boolean openHA) {
        String resultStr = restService.configHA(token, vmUUIDs, openHA);
        Result result = (Result) FormatUtil.fromXML(resultStr, new Class[] {
                Result.class, TaskIntermediateResult.class});
        if (result.getError() != null) {
            throw new RemoteException(result.getError().getMessage());
        } else {
            return (TaskIntermediateResult) ((List) result.getData()).get(0);
        }
    }

    @Override
    public TaskIntermediateResult configMigratable(String[] vmUUIDs, boolean migratable) {
        String resultStr = restService.configMigratable(token, vmUUIDs, migratable);
        Result result = (Result) FormatUtil.fromXML(resultStr, new Class[] {
                Result.class, TaskIntermediateResult.class});
        if (result.getError() != null) {
            throw new RemoteException(result.getError().getMessage());
        } else {
            return (TaskIntermediateResult) ((List) result.getData()).get(0);
        }
    }

    @Override
    public TaskIntermediateResult createSnapshot(String vmUUID,
            VMSnapshot snapshot) {
        String resultStr = restService.createSnapshot(token, vmUUID,
                FormatUtil.toJson(snapshot));
        Result result = (Result) FormatUtil.fromXML(resultStr, new Class[] {
                Result.class, TaskIntermediateResult.class});
        if (result.getError() != null) {
            throw new RemoteException(result.getError().getMessage());
        } else {
            return (TaskIntermediateResult) ((List) result.getData()).get(0);
        }
    }

    @Override
    public TaskIntermediateResult revertToSnapshot(String vmUUID,
            String snapshotUUID) {
        String resultStr = restService.revertToSnapshot(token, vmUUID,
                snapshotUUID);
        Result result = (Result) FormatUtil.fromXML(resultStr, new Class[] {
                Result.class, TaskIntermediateResult.class});
        if (result.getError() != null) {
            throw new RemoteException(result.getError().getMessage());
        } else {
            return (TaskIntermediateResult) ((List) result.getData()).get(0);
        }
    }

    @Override
    public TaskIntermediateResult deleteSnapshot(String vmUUID,
            String snapshotUUID) {
        String resultStr = restService.deleteSnapshot(token, vmUUID,
                snapshotUUID);
        Result result = (Result) FormatUtil.fromXML(resultStr, new Class[] {
                Result.class, TaskIntermediateResult.class});
        if (result.getError() != null) {
            throw new RemoteException(result.getError().getMessage());
        } else {
            return (TaskIntermediateResult) ((List) result.getData()).get(0);
        }
    }

    @Override
    public List<VMSnapshot> getSnapshotsOfVM(String vmUUID) {
        String resultStr = restService.getSnapshots(token, vmUUID);
        Result result = (Result) FormatUtil.fromXML(resultStr, new Class[] {
                Result.class, VMSnapshot.class});
        if (result.getError() != null) {
            throw new RemoteException(result.getError().getMessage());
        } else if (result.getData() == null) {
            return new ArrayList<VMSnapshot>();
        } else {
            return (List<VMSnapshot>) result.getData();
        }
    }

    @Override
    public TaskIntermediateResult hotAddVNic(String vmUUID,
            VNic vnic) {
        String resultStr = restService.hotAddVNic(token, vmUUID,
                FormatUtil.toJson(vnic));
        Result result = (Result) FormatUtil.fromXML(resultStr, new Class[] {
                Result.class, TaskIntermediateResult.class});
        if (result.getError() != null) {
            throw new RemoteException(result.getError().getMessage());
        } else {
            return (TaskIntermediateResult) ((List) result.getData()).get(0);
        }
    }

    @Override
    public TaskIntermediateResult hotResizeDisk(String vmUUID,
            VirtualDisk disk) {
        String resultStr = restService.hotResizeDisk(token, vmUUID,
                FormatUtil.toJson(disk));
        Result result = (Result) FormatUtil.fromXML(resultStr, new Class[] {
                Result.class, TaskIntermediateResult.class});
        if (result.getError() != null) {
            throw new RemoteException(result.getError().getMessage());
        } else {
            return (TaskIntermediateResult) ((List) result.getData()).get(0);
        }
    }

    @Override
    public TaskIntermediateResult shutdownVM(String vmUUID) {
        String resultStr = restService.shutdownVM(token, vmUUID);
        Result result = (Result) FormatUtil.fromXML(resultStr, new Class[] {
                Result.class, TaskIntermediateResult.class});
        if (result.getError() != null) {
            throw new RemoteException(result.getError().getMessage());
        } else {
            return (TaskIntermediateResult) ((List) result.getData()).get(0);
        }
    }

    @Override
    public void forceUpdateVMStatus(String vmUUID) {
        String resultStr = restService.forceUpdateStatus(token, vmUUID);
        Result result = (Result) FormatUtil.fromXML(resultStr,
                new Class[] {Result.class});
        if (result.getError() != null) {
            throw new RemoteException(result.getError().getMessage());
        }
    }

    @Override
    public List<String> getSupportedOS() {
        String resultStr = restService.getSupportedOS(token);
        Result result = (Result) FormatUtil.fromXML(resultStr,
                new Class[] {Result.class});
        if (result.getError() != null) {
            throw new RemoteException(result.getError().getMessage());
        } else if (result.getData() == null) {
            return new ArrayList<String>();
        } else {
            return (List<String>) result.getData();
        }
    }

    @Override
    public List<String> getVNicType(String osType) {
        String resultStr = restService.getVnicType(token, osType);
        Result result = (Result) FormatUtil.fromXML(resultStr,
                new Class[] {Result.class});
        if (result.getError() != null) {
            throw new RemoteException(result.getError().getMessage());
        } else if (result.getData() == null) {
            return new ArrayList<String>();
        } else {
            return (List<String>) result.getData();
        }
    }

    @Override
    public TaskIntermediateResult exportVM(String vmUUID, String filePath) {
        String resultStr = restService.exportVM(token, vmUUID, filePath);
        Result result = (Result) FormatUtil.fromXML(resultStr, new Class[] {
                Result.class, TaskIntermediateResult.class});
        if (result.getError() != null) {
            throw new RemoteException(result.getError().getMessage());
        } else {
            return (TaskIntermediateResult) ((List) result.getData()).get(0);
        }
    }

    @Override
    public TaskIntermediateResult importVM(VM vm, String filePath) {
        for (VirtualDisk vd : vm.getConfig().getDisks()) {
            vd.setVm(null);
        }
        for (VNic nic : vm.getConfig().getNics()) {
            nic.setVm(null);
            if(nic.getPortGroup() instanceof StandardPortGroup){
            	StandardPortGroup pg = ((StandardPortGroup) nic.getPortGroup());
            	pg.setIscsiIface(null);
            }
        }
        String resultStr = restService.importVM(token, FormatUtil.toJson(vm), filePath);
        Result result = (Result) FormatUtil.fromXML(resultStr, new Class[] {
                Result.class, TaskIntermediateResult.class});
        if (result.getError() != null) {
            throw new RemoteException(result.getError().getMessage());
        } else {
            return (TaskIntermediateResult) ((List) result.getData()).get(0);
        }
    }

    @Override
    public List<File> getOvaFiles() {
        String resultStr = restService.getOvaFiles(token);
        Result result = (Result) FormatUtil.fromXML(resultStr,
                new Class[] {Result.class, File.class});
        if (result.getError() != null) {
            throw new RemoteException(result.getError().getMessage());
        } else if (result.getData() == null) {
            return new ArrayList<File>();
        } else {
            return (List<File>) result.getData();
        }
    }

    @Override
    public VM getOvaFileConfig(String hostUUID, String filePath) {
        String resultStr = restService.getOvaFileConfig(token, hostUUID, filePath);
        Result result = (Result) FormatUtil.fromXML(resultStr, new Class[] {
                Result.class, VM.class});
        if (result.getError() != null) {
            throw new RemoteException(result.getError().getMessage());
        } else {
            return (VM) ((List) result.getData()).get(0);
        }
    }

    @Override
    public List<CpuInfo> getCpuAndBindedVM(String hostUuid) {
        String resultStr = restService.getCpuAndBindedVM(token, hostUuid);
        Result result = (Result) FormatUtil.fromXML(resultStr, new Class[] {
                Result.class, CpuInfo.class});
        if (result.getError() != null) {
            throw new RemoteException(result.getError().getMessage());
        } else if (result.getData() == null) {
            return new ArrayList<CpuInfo>();
        } else {
            return (List<CpuInfo>) result.getData();
        }
    }

    @Override
    public TaskIntermediateResult openFTMode(String vmUUID, String hostUuid) {
        String resultStr = restService.openFTMode(token, vmUUID, hostUuid);
        Result result = (Result) FormatUtil.fromXML(resultStr, new Class[] {
                Result.class, TaskIntermediateResult.class});
        if (result.getError() != null) {
            throw new RemoteException(result.getError().getMessage());
        } else {
            return (TaskIntermediateResult) ((List) result.getData()).get(0);
        }
    }

    @Override
    public TaskIntermediateResult closeFTMode(String vmUUID) {
        String resultStr = restService.closeFTMode(token, vmUUID);
        Result result = (Result) FormatUtil.fromXML(resultStr, new Class[] {
                Result.class, TaskIntermediateResult.class});
        if (result.getError() != null) {
            throw new RemoteException(result.getError().getMessage());
        } else {
            return (TaskIntermediateResult) ((List) result.getData()).get(0);
        }
    }

    @Override
    public TaskIntermediateResult backupSnapshot(String vmUUID, String snapshotUUID, String dsUuid,
            VMBackup backup) {
        String resultStr = restService.backupSnapshot(token,
                vmUUID,
                snapshotUUID,
                dsUuid,
                FormatUtil.toJson(backup));
        Result result = (Result) FormatUtil.fromXML(resultStr, new Class[] {
                Result.class, TaskIntermediateResult.class});
        if (result.getError() != null) {
            throw new RemoteException(result.getError().getMessage());
        } else {
            return (TaskIntermediateResult) ((List) result.getData()).get(0);
        }
    }

    @Override
    public TaskIntermediateResult revertBackup(String vmUUID, String vmBackupUuid) {
        String resultStr = restService.revertBackup(token, vmUUID, vmBackupUuid);
        Result result = (Result) FormatUtil.fromXML(resultStr, new Class[] {
                Result.class, TaskIntermediateResult.class});
        if (result.getError() != null) {
            throw new RemoteException(result.getError().getMessage());
        } else {
            return (TaskIntermediateResult) ((List) result.getData()).get(0);
        }
    }

    @Override
    public List<VMBackup> getBackupsOfVm(String vmUUID) {
        String resultStr = restService.getBackupList(token, vmUUID);
        Result result = (Result) FormatUtil.fromXML(resultStr, new Class[] {
                Result.class, VMBackup.class});
        if (result.getError() != null) {
            throw new RemoteException(result.getError().getMessage());
        } else if (result.getData() == null) {
            return new ArrayList<VMBackup>();
        } else {
            return (List<VMBackup>) result.getData();
        }
    }

    @Override
    public TaskIntermediateResult configMaxHaLimit(String[] vmUUIDs, int maxHaLimit) {
        String resultStr = restService.configMaxHaLimit(token, vmUUIDs, String.valueOf(maxHaLimit));
        Result result = (Result) FormatUtil.fromXML(resultStr, new Class[] {
                Result.class, TaskIntermediateResult.class});
        if (result.getError() != null) {
            throw new RemoteException(result.getError().getMessage());
        } else {
            return (TaskIntermediateResult) ((List) result.getData()).get(0);
        }
    }
}
