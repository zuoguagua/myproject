package com.inspur.autotest;

import org.testng.annotations.Test;
import org.testng.AssertJUnit;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.inspur.ics.client.ClientFactory;
import com.inspur.ics.client.ClusterService;
import com.inspur.ics.client.DataStoreService;
import com.inspur.ics.client.HostService;
import com.inspur.ics.client.TaskService;
import com.inspur.ics.client.VMService;
import com.inspur.ics.client.VMTemplateService;
import com.inspur.ics.client.VNetService;
import com.inspur.ics.common.Types;
import com.inspur.ics.common.Types.BootDevice;
import com.inspur.ics.common.Types.PortGroupServiceType;
import com.inspur.ics.common.Types.TaskState;
import com.inspur.ics.common.Types.TaskTargetType;
import com.inspur.ics.common.Types.VmStatus;
import com.inspur.ics.core.task.TaskIntermediateResult;
import com.inspur.ics.pojo.BlockDevice;
import com.inspur.ics.pojo.CdromDevice;
import com.inspur.ics.pojo.Cluster;
import com.inspur.ics.pojo.CpuInfo;
import com.inspur.ics.pojo.DataStore;
import com.inspur.ics.pojo.Host;
import com.inspur.ics.pojo.ISOFile;
import com.inspur.ics.pojo.License;
import com.inspur.ics.pojo.OcfsDataStore;
import com.inspur.ics.pojo.OptionValue;
import com.inspur.ics.pojo.PortGroup;
import com.inspur.ics.pojo.StandardPortGroup;
import com.inspur.ics.pojo.TaskInfo;
import com.inspur.ics.pojo.VM;
import com.inspur.ics.pojo.VMBackup;
import com.inspur.ics.pojo.VMConfigInfo;
import com.inspur.ics.pojo.VMSnapshot;
import com.inspur.ics.pojo.VNic;
import com.inspur.ics.pojo.VSanDataStore;
import com.inspur.ics.pojo.VirtualCdrom;
import com.inspur.ics.pojo.VirtualDisk;
import com.inspur.ics.pojo.monitor.VmMonitorInfo;

public class VMServiceTest {
    private static VMService service;
    private static DataStoreService dsService;
    private static HostService hostService;
    private static TaskService taskService;
    private static ClusterService clusterService;
    private static VNetService vNetService;
    private static VMTemplateService vmTemplateClient;

    // private String vmUUID = "6ec60afa-9819-4b0c-bc11-174d55644d65"; // 200
    private String vmUUID = "017d7329-a874-41db-906a-51bcc0afe353"; // 202
    private String movevmUUID = null;
    // private String clusterUUID = "cb1c1945-49f1-4245-83bc-18d319a85423"; // 200
    private String clusterUUID = "bf3fb7a5-a328-460e-900e-e4538dafa437"; // 202
    private String dsUUID = "9ad0fd14-fcc8-4bad-83b5-353ea1837e08";
    private String pgUUID = "29712986-aabf-485a-b2c5-41c24c9c3739";
    private String hostUUID = "09253e27-c1b9-419c-ab8b-f6e1195c7a23";
    private String hostUUID1 = "cf1d8d70-193e-4cd3-b6f4-eea4208bce73";
    private String tempUUID = "09253e27-c1b9-419c-ab8b-f6e1195c7a23";
    private String snapshotUUID = "cca2c7e9-01c2-42f6-a8ff-a15c33d7f59a";
    private String ovaFile = null;
    private String vmBackupUuid = "c90d4e76-db97-44a2-9016-ec1a78a3aa4c";
    private String templateName = "test_sdk_create_vmTemplate";

    /**
     * init.
     */
    @Parameters({ "url", "username", "password", "userLocale" })
    @Test(groups = { "init" })
    public void init(String url, String username,
            String password, String userLocale) {
        ClientFactory factory = TestUtil.getClientFactory();
        service = factory.getVMService();
        dsService = factory.getDataStoreService();
        hostService = factory.getHostService();
        taskService = factory.getTaskService();
        clusterService = factory.getClusterService();
        vNetService = factory.getVNetService();
        vmTemplateClient = factory.getVMTemplateService();
    }

    @Parameters({ "host1ip", "host2ip" })
    @Test(groups = { "create" }, dependsOnMethods = {
            "com.inspur.ics.client.autotest.IscsiServiceTest.addPortGroupToIscsiTarget" }, alwaysRun = true)
    public void initPara(String host1ip, String host2ip) {
        // 获取主机uuid 两个
        List<Host> is = hostService.getAllHostList();
        for (Host host : is) {
            if (host.getIp().equals(host1ip)) {
                this.hostUUID = host.getUuid(); // huoqu UUID
            }
            if (host.getIp().equals(host2ip)) {
                this.hostUUID1 = host.getUuid(); // huoqu UUID
            }
        }

        List<Cluster> clusterList = clusterService.getAllCluster();
        // AssertJUnit.assertNotNull(clusterList);
        if (clusterList.size() > 0) {
            for (int i = 0; i < clusterList.size(); i++) {
                if ("sdk_cluster".equals(clusterList.get(i).getDisplayName())) {
                    clusterUUID = clusterList.get(i).getUuid();
                }
            }
        }

        List<DataStore> dataStoreList = dsService.getAllDataStore();
        if (dataStoreList.size() > 0) {
            for (DataStore ds : dataStoreList) {
                if (ds instanceof OcfsDataStore) {
                    if (ds.getName().equals("sdk_ocfs_datastore") || ds.getName().equals("sdk_ocfs_datastore_new")) {
                        dsUUID = ds.getUuid();
                        break;
                    }
                }
            }
        }

        List<PortGroup> result = vNetService.listPortGroupByHostUuid(hostUUID);
        if (result.size() > 0) {
            pgUUID = result.get(0).getUuid();
        }
    }

    //
    @Test(groups = { "create" }, dependsOnMethods = { "initPara" }, alwaysRun = true)
    public void createVMByIso() throws Exception {
        VM vm = new VM();
        VMConfigInfo config = new VMConfigInfo();
        config.setName("vmtest0001");
        config.setBoot(BootDevice.CDROM);
        config.setGuestOsLabel("CentOS5.4");
        config.setMaxMem(1000);
        // config.setNicType("e1000");
        config.setVcpus(1);

        List<VirtualDisk> disks = new ArrayList<VirtualDisk>();
        VirtualDisk disk = new VirtualDisk();
        disk.setSize(20);
        disk.setFileName("3dc2a11e-a86a-4de3-9f3a-e4b0dcde8711.img");
        DataStore ds = new DataStore();
        ds.setUuid(dsUUID);
        disk.setDataStore(ds);
        disks.add(disk);

        config.setDisks(disks);

        List<VNic> nics = new ArrayList<VNic>();
        VNic nic = new VNic();
        nic.setAutoGenerated(true);
        PortGroup pgVM = new PortGroup();
        pgVM.setUuid(pgUUID);
        // PortGroup pg = netService.getManagementPortgroupOnHost("759c447d-4843-46f2-81ab-ed9d40af2c89");
        nic.setPortGroup(pgVM);
        // if (pg instanceof StandardPortGroup) {
        // StandardPortGroup pgVM = (StandardPortGroup)pg;
        // pgVM.setIscsiIface(null);
        // nic.setPortGroup(pgVM);
        // } else {
        // nic.setPortGroup(pgVM);
        // }

        nics.add(nic);

        // nic = new VNic();
        // PNic pnic = new PNic();
        // pnic.setUuid("b8cc6193-e558-4dae-b450-1a6c2a7e00f3");
        // nic.setPnic(pnic);
        //
        // nics.add(nic);

        config.setNics(nics);
        //
        // List<OptionValue> ovs = new ArrayList<OptionValue>();
        // OptionValue ov = new OptionValue();
        // // ov.setKey("as");
        // // ov.setValue("ds");
        // ovs.add(ov);
        //
        // config.setOptionConfig(ovs);

        Host host = new Host();
        // Host host = hostService.getHostInfo("759c447d-4843-46f2-81ab-ed9d40af2c89");
        host.setUuid(hostUUID);

        vm.setHost(host);
        // vm.setDataStore(ds);
        vm.setConfig(config);
        vm.setMaxSnap(2);

        TaskIntermediateResult taskResult = service.createVMByIso(vm);
        TaskInfo taskInfo = TestUtil.waitFor(taskResult.getTaskId());
        AssertJUnit.assertEquals(taskInfo.getLocalizeState() + ":" + taskInfo.getLocalizedError() + "!!!",
                TaskState.FINISHED, taskInfo.getState());
    }

    /**
     * 挂载ISO镜像.****在开机或关机的状态下进行
     * @throws Exception
     */
    @Parameters({ "isofile" })
    @Test(groups = { "create" }, dependsOnMethods = { "getAllVMs" }, alwaysRun = true)
    public void attachCdrom(String isofile) throws Exception {
//        VirtualCdrom cdrom = new VirtualCdrom();
//        ISOFile isoFile = new ISOFile();
//        isoFile.setPath(isofile);
//        cdrom.setIsoFile(isoFile);
//        TaskIntermediateResult taskResult = service.attachCdrom(vmUUID, cdrom);
//        TaskInfo taskInfo = TestUtil.waitFor(taskResult.getTaskId());
//        AssertJUnit.assertEquals(taskInfo.getLocalizeState() + ":" + taskInfo.getLocalizedError() + "!!!",
//                TaskState.FINISHED, taskInfo.getState());

    }

    @Test(groups = { "create" }, dependsOnMethods = { "getAllVMs" }, alwaysRun = true)
    public void createVmTemplate() throws Exception {
        String tmplDesp = "test sdk create vmTemplate";
        TaskIntermediateResult taskResult = vmTemplateClient.createVMTemplateByVM(
                vmUUID, templateName, tmplDesp);
        TaskInfo taskInfo = TestUtil.waitFor(taskResult.getTaskId());
        AssertJUnit.assertEquals(taskInfo.getLocalizeState() + ":" + taskInfo.getLocalizedError() + "!!!",
                TaskState.FINISHED, taskInfo.getState());
        // System.out.print("create template taskid :" + result.getTaskId()
        // + "\nresut:" + result.getResult().toString());
    }

    //
    /**
     * 根据模板创建虚拟机.
     * @throws Exception
     */
    @Test(groups = { "create" }, dependsOnMethods = { "createVmTemplate" }, alwaysRun = true)
    public void createVMByTemplate() throws Exception {
        List<VM> templates = vmTemplateClient.getVmTemplateByName(templateName);
        AssertJUnit.assertEquals(templates.get(0).getName(), templateName);
        if (templates != null) {
            tempUUID = templates.get(0).getConfig().getUuid();
        }
        VM vm = vmTemplateClient.getVMTemplateInfo(tempUUID);
        TaskIntermediateResult taskResult = service.createVMByTemplate(tempUUID,
                "sdkvm_from_Template", hostUUID, vm.getConfig().getDisks());
        TaskInfo taskInfo = TestUtil.waitFor(taskResult.getTaskId());
        AssertJUnit.assertEquals(taskInfo.getLocalizeState() + ":" + taskInfo.getLocalizedError() + "!!!",
                TaskState.FINISHED, taskInfo.getState());
    }

    //
    /**
     * 根据模板批量创建虚拟机.
     * @throws Exception
     */
    @Test(groups = { "create" }, dependsOnMethods = { "createVMByTemplate" }, alwaysRun = true)
    public void batchCreateVMByTemplate() throws Exception {
        TaskIntermediateResult taskResult = service.batchCreateVMByTemplate(tempUUID,
                "vmttttt", 3);
        TaskInfo taskInfo = TestUtil.waitFor(taskResult.getTaskId());
        AssertJUnit.assertEquals(taskInfo.getLocalizeState() + ":" + taskInfo.getLocalizedError() + "!!!",
                TaskState.FINISHED, taskInfo.getState());
    }

    /**
     * 复制虚拟机.
     * @throws Exception
     */
    @Test(groups = { "create" }, dependsOnMethods = { "batchCreateVMByTemplate" }, alwaysRun = true)
    public void copyVM() throws Exception {
        TaskIntermediateResult taskResult = service.copyVM(vmUUID, "vmcopy1111");
        TaskInfo taskInfo = TestUtil.waitFor(taskResult.getTaskId());
        AssertJUnit.assertEquals(taskInfo.getLocalizeState() + ":" + taskInfo.getLocalizedError() + "!!!",
                TaskState.FINISHED, taskInfo.getState());
    }

    /**
     * 创建虚拟机快照. 快照命名限制：不能包含字母、数字和下划线之外的字符，长度不超过32位；描述长度不超过64位，可以包含中文
     * @throws Exception
     */
    @Test(groups = { "create" }, dependsOnMethods = { "copyVM" }, alwaysRun = true)
    public void createSnapshot() throws Exception {
        VMSnapshot snapshot = new VMSnapshot();
        snapshot.setName("test_sdk_snapshot");
        snapshot.setDescription("test sdk -- create snapshot");

        TaskIntermediateResult taskResult = service.createSnapshot(vmUUID, snapshot);
        TaskInfo taskInfo = TestUtil.waitFor(taskResult.getTaskId());
        AssertJUnit.assertEquals(taskInfo.getLocalizeState() + ":" + taskInfo.getLocalizedError() + "!!!",
                TaskState.FINISHED, taskInfo.getState());
    }

    @Test(groups = { "query" }, alwaysRun = true)
    public void getVMsInCluster() {
        List<VM> vmList = service.getVMInCluster(clusterUUID);
        // System.out.println(vmList.size());
        // for (VM vm : vmList) {
        // System.out.println("vm name:" + vm.getName());
        // }
    }

    //
    @Test(groups = { "create" }, dependsOnMethods = { "createVMByIso" }, alwaysRun = true)
    public void getAllVMs() {
        List<VM> vmList = service.getAll();
        for (VM vm : vmList) {
            if (vm.getName().equals("vmtest0001")) {
                vmUUID = vm.getConfig().getUuid();
                continue;
            } else if (vm.getName().equals("vmttttt_0")) {
                movevmUUID = vm.getConfig().getUuid();
            }
        }
        // System.out.println(vmList.size());
        // for (VM vm : vmList) {
        // System.out.println("vm name:" + vm.getName());
        // System.out.println("vm uuid:" + vm.getConfig().getUuid());
        // }
    }

    //
    @Test(groups = { "query" }, alwaysRun = true)
    public void getAllCustomVMs() {
        List<VM> vmList = service.getVMList(TaskTargetType.HOST, hostUUID);
        // System.out.println(vmList.size());
    }

    //
    /**
     * 获取可迁入虚拟机的主机列表.
     */
    @Test(groups = { "query" }, alwaysRun = true)
    public void getHostsMovedIn() {
        List<Host> hosts = service.getAvailableHostsToMigrateVM(vmUUID);
        // System.out.println(hosts.size());
        // for (Host host : hosts) {
        // System.out.println(host.getIp());
        // }
    }

    //

    //
    @Test(groups = { "query" }, alwaysRun = true)
    public void getAvailableHostsToRelocateVM() {
        List<Host> hosts = service.getAvailableHostsToRelocateVM(vmUUID);
        System.out.println(hosts.size());
        for (Host host : hosts) {
            System.out.println(host.getIp());
        }
    }

    /**
     * 获取虚拟机信息.
     */
    @Test(groups = { "query" }, alwaysRun = true)
    public void getVMInfo() {
        VM vm = service.getVMInfo(vmUUID);
        // System.out.println(vm.getName());
        // System.out.println(vm.getConfig().getNics().get(0).getMacAddress());
        // System.out.println(vm.getConfig().getDisks().get(0).getFileName());
        // System.out.println(vm.getvCluster() == null ? "" : vm.getvCluster().getName());
    }
    //

    //
    /**
     * 获取虚拟机的运行信息,主要是性能相关信息以及所在的主机.
     */
    @Test(groups = { "query" }, alwaysRun = true)
    public void getVMRuntimeInfo() {
        VmMonitorInfo info = service.getVMRuntimeInfo(vmUUID);
        // System.out.println("Memory percent:" + info.getMemPercent());
        // System.out.println("CPU percent:" + info.getCpuPercent());
        // System.out.println(info.getCpuNum());
    }
    //
    // /**
    // * 打开虚拟机的VNC控制台.
    // */
    // @Test(groups={"query"})
    // public void openVncConsole() {
    // String str = service.openVncConsole(vmUUID);
    // System.out.println(str);
    // }
    //

    //
    // /**
    // * 卸载虚拟机挂载的ISO镜像.
    // */
    // @Test
    // public void detachCdrom() {
    // service.detachCdrom(vmUUID);
    // }
    //
    // /**
    // * 热添加磁盘.
    // */
    // @Test
    // public void hotAddDisk() {
    // VirtualDisk disk = new VirtualDisk();
    // disk.setSize("20");
    //
    // service.hotAddDisk(vmUUID, disk);
    // }
    //
    // /**
    // * 配置虚拟机内部网卡获取ip的方式，是dhcp还是static，若是static，需配置ip和mask、gateway，该网卡连接的必须是直连网络.
    // */
    // @Test
    // public void configDirectNetVNicForVM() {
    // VMConfigInfo config = new VMConfigInfo();
    // config.setUuid(vmUUID);
    //
    // List<VNic> vnics = new ArrayList<VNic>();
    // VNic vnic = new VNic();
    // vnic.setMacAddress("00:16:3e:77:8d:5d");
    //
    // // vnic.setDhcp(true);
    //
    // vnic.setDhcp(false);
    // vnic.setIp("10.151.5.221");
    // vnic.setMask("255.255.255.0");
    // vnic.setGateway("10.151.255.254");
    // vnics.add(vnic);
    //
    // config.setNics(vnics);
    //
    // service.configDirectNetVNicForVM(config);
    // }
    //
    // /**
    // * 挂载usb设备.
    // */
    // @Test
    // public void attachUsb() {
    // service.attachUsb(vmUUID, "046b:ff10");
    // }
    //
    // /**
    // * 卸载usb设备.
    // */
    // @Test
    // public void detachUsb() {
    // service.detachUsb(vmUUID);
    // }
    //
    /* 界面上没找到本功能 */
    @Test(groups = { "update" }, alwaysRun = true)
    public void relocateVM() throws Exception {
        if (movevmUUID != null) {
            VM vm = service.getVMInfo(movevmUUID);
            TaskIntermediateResult taskResult = service.relocate(movevmUUID, hostUUID, vm.getConfig().getDisks());
            TaskInfo taskInfo = TestUtil.waitFor(taskResult.getTaskId());
            AssertJUnit.assertEquals(taskInfo.getLocalizeState() + ":" + taskInfo.getLocalizedError() + "!!!",
                    TaskState.FINISHED, taskInfo.getState());
        }
    }

    /**
     * 迁移虚拟机.在关机和开机状态下均可进行
     * @throws Exception
     */
    @Test(groups = { "update" }, alwaysRun = true)
    public void migrateVM() throws Exception {
        if (movevmUUID != null) {
            TaskIntermediateResult taskResult = service.migrateVM(movevmUUID, hostUUID1);
            TaskInfo taskInfo = TestUtil.waitFor(taskResult.getTaskId());
            AssertJUnit.assertEquals(taskInfo.getLocalizeState() + ":" + taskInfo.getLocalizedError() + "!!!",
                    TaskState.FINISHED, taskInfo.getState());
        }
    }

    /**
     * 打开/关闭虚拟机HA.开机关机均可进行
     * @throws Exception
     */
    @Test(groups = { "update" }, alwaysRun = true)
    public void configHA() throws Exception {
        TaskIntermediateResult taskResult = service.configHA(new String[] { vmUUID }, true);
        TaskInfo taskInfo = TestUtil.waitFor(taskResult.getTaskId());
        AssertJUnit.assertEquals(taskInfo.getLocalizeState() + ":" + taskInfo.getLocalizedError() + "!!!",
                TaskState.FINISHED, taskInfo.getState());
    }

    /**
     * 配置迁移.开机关机均可进行
     * @throws Exception
     */
    @Test(groups = { "update" }, alwaysRun = true)
    public void configMigratable() throws Exception {
        // String vmuuid1 = "8e7cbbf6-c13d-4c41-a052-fead72cb303d";
        // String vmuuid2 = "4cc634df-839c-4a5f-b036-15ad7ccd2843";
        // String vmuuid3 = "fedfd8ce-8a90-4e5e-a751-ca6bba1ceb61";
        String[] vmuuids = new String[] { vmUUID };
        TaskIntermediateResult taskResult = service.configMigratable(vmuuids, true);
        TaskInfo taskInfo = TestUtil.waitFor(taskResult.getTaskId());
        AssertJUnit.assertEquals(taskInfo.getLocalizeState() + ":" + taskInfo.getLocalizedError() + "!!!",
                TaskState.FINISHED, taskInfo.getState());
    }
    //

    //
    /**
     * 恢复快照.仅关机状态下可执行
     * @throws Exception
     */
    @Test(groups = { "update" }, dependsOnMethods = { "createSnapshot" }, alwaysRun = true)
    public void revertToSnapshot() throws Exception {
        // 保证虚拟机关机
        VM vm = service.getVMInfo(vmUUID);
        if (vm.getStatus() != VmStatus.STOPPED) {
            TaskIntermediateResult taskResult = service.powerOffVM(vmUUID);
            TaskInfo taskInfo = TestUtil.waitFor(taskResult.getTaskId());
        }
        TaskIntermediateResult taskResult = service.revertToSnapshot(vmUUID, snapshotUUID);
        TaskInfo taskInfo = TestUtil.waitFor(taskResult.getTaskId());
        AssertJUnit.assertEquals(taskInfo.getLocalizeState() + ":" + taskInfo.getLocalizedError() + "!!!",
                TaskState.FINISHED, taskInfo.getState());
    }

    /**
     * 导出虚拟机.关机状态下才可以进行
     * @throws Exception
     */
    @Test(groups = { "update" }, alwaysRun = true)
    public void exportVM() throws Exception {
        VM vm = service.getVMInfo(vmUUID);
        if (vm.getStatus() != VmStatus.STOPPED) {
            TaskIntermediateResult taskResult = service.powerOffVM(vmUUID);
            // wait
            TaskInfo taskInfo = TestUtil.waitFor(taskResult.getTaskId());
        }
        TaskIntermediateResult taskResult = service.exportVM(vmUUID, "/storage/nfs/");
        // wait
        TaskInfo taskInfo = TestUtil.waitFor(taskResult.getTaskId());
        AssertJUnit.assertEquals(taskInfo.getLocalizeState() + ":" + taskInfo.getLocalizedError() + "!!!",
                TaskState.FINISHED, taskInfo.getState());
    }

    /**
     * 导入虚拟机.
     * @throws Exception
     */
    @Test(groups = { "update" },dependsOnMethods = { "getOvaFileConfig" }, alwaysRun = true)
    public void importVM() throws Exception {
        if (ovaFile != null) {
            VM vm = service.getOvaFileConfig(hostUUID, ovaFile);
            vm.setName("deployVM_From_SDK");
            Host host = new Host();
            host.setUuid(hostUUID);
            vm.setHost(host);
            DataStore data = new DataStore();
			for (DataStore datatmp : dsService.getDataStoreOnHost(hostUUID)) {
				if (datatmp instanceof OcfsDataStore) {
					data.setUuid(datatmp.getUuid());
				}
			}
            for (VirtualDisk vds : vm.getConfig().getDisks()) {
            	vds.setDataStore(data);
            }
            PortGroup portgroup = new PortGroup();
            portgroup.setUuid(vNetService.getPortGroupList(TaskTargetType.HOST, hostUUID).get(0).getUuid());
            for (VNic vnic : vm.getConfig().getNics()) {
            	vnic.setPortGroup(portgroup);
            }
            TaskIntermediateResult taskResult = service.importVM(vm, ovaFile);
            TaskInfo taskInfo = TestUtil.waitFor(taskResult.getTaskId());
            AssertJUnit.assertEquals(taskInfo.getLocalizeState() + ":" + taskInfo.getLocalizedError() + "!!!",
                    TaskState.FINISHED, taskInfo.getState());
        } else {
            AssertJUnit.assertEquals("ggg", true, false);
        }
        // service.importVM(vm, "/storage/nfs/a7e7a3e0-bee7-4e4a-99b6-3a79b298cee9.ova");
    }

    /** 打开FT.开机状态可进行. */
    @Test(groups = { "update" }, dependsOnMethods = { "powerOnVM" }, alwaysRun = true)
    public void openFTMode() throws Exception {
        VM vm = service.getVMInfo(vmUUID);
        if (vm.getStatus() == VmStatus.STOPPED) {
            TaskIntermediateResult taskResult = service.powerOnVM(vmUUID);
            TaskInfo taskInfo = TestUtil.waitFor(taskResult.getTaskId());
            try {
				java.lang.Thread.sleep(120000);
			} catch (Exception e) {
				// TODO: handle exception
			}
        }
        TaskIntermediateResult taskResult = service.openFTMode(vmUUID, hostUUID);
        TaskInfo taskInfo = TestUtil.waitFor(taskResult.getTaskId());
        AssertJUnit.assertEquals(taskInfo.getLocalizeState() + ":" + taskInfo.getLocalizedError() + "!!!",
                TaskState.FINISHED, taskInfo.getState());
    }

    /* 关闭FT.开机状态可进行 */
    @Test(groups = { "update" }, dependsOnMethods = { "openFTMode" }, alwaysRun = true)
    public void closeFTMode() throws Exception {
		VM vm = service.getVMInfo(vmUUID);
		TaskIntermediateResult taskVM = service.restartVM(vmUUID);
		TestUtil.waitFor(taskVM.getTaskId());
		try {
			java.lang.Thread.sleep(60000);
		} catch (Exception e) {
			// TODO: handle exception
		}
    	String ftHostUUid = "";
    	DataStore data = dsService.getDataStoreList(TaskTargetType.VM, vmUUID).get(0);
    	data = dsService.getDataStoreInfo(data.getUuid());
    	String ftHostUUID = null;
    	for(Host host : data.getHosts()) {
    		if(!host.getUuid().equals(vm.getHost().getUuid())) {
    			ftHostUUID = host.getUuid();
    		}
    	}
    	TaskIntermediateResult taskResult1 = service.openFTMode(vmUUID, ftHostUUID);
    	TestUtil.waitFor(taskResult1.getTaskId());
        TaskIntermediateResult taskResult = service.closeFTMode(vmUUID);
        TaskInfo taskInfo = TestUtil.waitFor(taskResult.getTaskId());
        AssertJUnit.assertEquals(taskInfo.getLocalizeState() + ":" + taskInfo.getLocalizedError() + "!!!",
                TaskState.FINISHED, taskInfo.getState());
    }

    /* 备份快照.创建快照以后进行 */
    @Test(groups = { "update" }, dependsOnMethods = { "createSnapshot" }, alwaysRun = true)
    public void backupSnapshot() throws Exception {
        VMBackup backup = new VMBackup();
        backup.setName("test_sdk_backup");
        backup.setDescription("test sdk -- backup snapshot");
        TaskIntermediateResult taskResult = service.backupSnapshot(vmUUID, snapshotUUID,
                dsUUID,
                backup);
        TaskInfo taskInfo = TestUtil.waitFor(taskResult.getTaskId());
        AssertJUnit.assertEquals(taskInfo.getLocalizeState() + ":" + taskInfo.getLocalizedError() + "!!!",
                TaskState.FINISHED, taskInfo.getState());
    }

    /** 恢复备份.备份快照以后进行，仅关机状态可执行. */
    @Test(groups = { "update" }, dependsOnMethods = { "backupSnapshot" }, alwaysRun = true)
    public void revertBackup() throws Exception {
    	String vmBackupUuid1 = null;
    	List<VMBackup> backupLst = service.getBackupsOfVm(vmUUID);
        for (VMBackup vmb : backupLst) {
            if (vmb.getName().equals("test_sdk_backup")) {
                vmBackupUuid1 = vmb.getUuid();
                break;
            }
        }
        VM vm = service.getVMInfo(vmUUID);
        if (vm.getStatus() != VmStatus.STOPPED) {
            TaskIntermediateResult taskResult = service.powerOffVM(vmUUID);
            // wait
            TaskInfo taskInfo = TestUtil.waitFor(taskResult.getTaskId());
        }
        TaskIntermediateResult taskResult = service.revertBackup(vmUUID, vmBackupUuid1);
        TaskInfo taskInfo = TestUtil.waitFor(taskResult.getTaskId());
        AssertJUnit.assertEquals(taskInfo.getLocalizeState() + ":" + taskInfo.getLocalizedError() + "!!!",
                TaskState.FINISHED, taskInfo.getState());
    }

    /* 配置最大HA数.开机或关机状态下均可进行 */
    @Test(groups = { "update" }, alwaysRun = true)
    public void configMaxHaLimit() throws Exception {
        String[] vmUUIDs = new String[] { vmUUID };
        TaskIntermediateResult taskResult = service.configMaxHaLimit(vmUUIDs, 5);
        TaskInfo taskInfo = TestUtil.waitFor(taskResult.getTaskId());
        AssertJUnit.assertEquals(taskInfo.getLocalizeState() + ":" + taskInfo.getLocalizedError() + "!!!",
                TaskState.FINISHED, taskInfo.getState());
    }
    //

    //

    //
    // /**
    // * 热添加网卡.
    // */
    // @Test
    // public void hotAddVNic() {
    // VNic vnic = new VNic();
    // vnic.setAutoGenerated(true);
    // vnic.setOperation(VirtualDeviceConfigOperation.ADD);
    //
    // VirtualNet vnet = new DirectNet();
    // vnet.setUuid("0a028291-bb7f-424a-a973-10b43c360054");
    // vnic.setVnet(vnet);
    //
    // service.hotAddVNic(vmUUID, vnic);
    // }
    //
    // /**
    // * 热扩展磁盘，该磁盘不能是系统盘. 需指定磁盘属性fileOperation为{@link VirtualDeviceConfigFileOperation}中的EDIT
    // */
    // @Test
    // public void hotResizeDisk() {
    // VirtualDisk disk = new VirtualDisk();
    // disk.setFileOperation(VirtualDeviceConfigFileOperation.EDIT);
    // disk.setUuid("1e2ff2be-4de9-4289-a6e1-d90ddbbd7a71");
    // disk.setName("05635862-1598-4716-b654-db764f1dbd69.img");
    // disk.setSize("40");
    //
    // service.hotResizeDisk(vmUUID, disk);
    // }
    //
    /**
     * 关闭虚拟机.开机状态下进行
     * @throws Exception
     */
    @Test(groups = { "update" }, dependsOnMethods = { "resumeVM" }, alwaysRun = true)
    public void shutdownVM() throws Exception {
        TaskIntermediateResult taskResult = service.shutdownVM(vmUUID);
        TaskInfo taskInfo = TestUtil.waitFor(taskResult.getTaskId());
        AssertJUnit.assertEquals(taskInfo.getLocalizeState() + ":" + taskInfo.getLocalizedError() + "!!!",
                TaskState.FINISHED, taskInfo.getState());
    }

    /* 打开虚拟机电源.关机状态下进行 */
    @Test(groups = { "update" }, alwaysRun = true)
    public void powerOnVM() throws Exception {
        TaskIntermediateResult taskResult = service.powerOnVM(vmUUID);
        TaskInfo taskInfo = TestUtil.waitFor(taskResult.getTaskId());
        AssertJUnit.assertEquals(taskInfo.getLocalizeState() + ":" + taskInfo.getLocalizedError() + "!!!",
                TaskState.FINISHED, taskInfo.getState());
        try {
			java.lang.Thread.sleep(120000);
		} catch (Exception e) {
			// TODO: handle exception
		}
    }

    /// 关闭虚拟机电源.开机状态下进行*/
    @Test(groups = { "update" }, dependsOnMethods = { "powerOnVM" }, alwaysRun = true)
    public void powerOffVM() throws Exception {
        TaskIntermediateResult taskResult = service.powerOffVM(vmUUID);
        TaskInfo taskInfo = TestUtil.waitFor(taskResult.getTaskId());
        AssertJUnit.assertEquals(taskInfo.getLocalizeState() + ":" + taskInfo.getLocalizedError() + "!!!",
                TaskState.FINISHED, taskInfo.getState());
    }
    //
    // /**
    // * 强制更新虚拟机的业务状态，若该虚拟机正在操作中将更新失败.
    // */
    // @Test
    // public void forceUpdateVMStatus() {
    // service.forceUpdateVMStatus(vmUUID);
    // }
    //

    //
    // /**
    // * 获取指定的操作系统支持的网卡类型，目前最多支持两种：e1000和virtio.
    // */
    // @Test
    // public void getVNicType() {
    // List<String> nicTypes = service.getVNicType("Windows 7");
    // System.out.println(nicTypes.size());
    // }

    /// *重启虚拟机.开机状态下进行*/
    @Test(groups = { "update" }, alwaysRun = true)
    public void restartVM() throws Exception {
        VM vm = service.getVMInfo(vmUUID);
        if (vm.getStatus() == VmStatus.STOPPED) {
            TaskIntermediateResult taskResult = service.powerOnVM(vmUUID);
            // wait finished.
            TaskInfo taskInfo = TestUtil.waitFor(taskResult.getTaskId());
            try {
				java.lang.Thread.sleep(120000);
			} catch (Exception e) {
				// TODO: handle exception
			}
        }
        TaskIntermediateResult taskResult = service.restartVM(vmUUID);
        TaskInfo taskInfo = TestUtil.waitFor(taskResult.getTaskId());
        AssertJUnit.assertEquals(taskInfo.getLocalizeState() + ":" + taskInfo.getLocalizedError() + "!!!",
                TaskState.FINISHED, taskInfo.getState());
        try {
			java.lang.Thread.sleep(120000);
		} catch (Exception e) {
			// TODO: handle exception
		}
    }

    //
    /* 暂停虚拟机.开机状态下进行 */
    @Test(groups = { "update" }, dependsOnMethods = { "restartVM" }, alwaysRun = true)
    public void pauseVM() throws Exception {
        VM vm = service.getVMInfo(vmUUID);
        if (vm.getStatus() == VmStatus.STOPPED) {
            TaskIntermediateResult taskResult = service.powerOnVM(vmUUID);
            // wait finished.
            TaskInfo taskInfo = TestUtil.waitFor(taskResult.getTaskId());
            try {
				java.lang.Thread.sleep(120000);
			} catch (Exception e) {
				// TODO: handle exception
			}
        }
        TaskIntermediateResult taskResult = service.pauseVM(vmUUID);
        TaskInfo taskInfo = TestUtil.waitFor(taskResult.getTaskId());
        AssertJUnit.assertEquals(taskInfo.getLocalizeState() + ":" + taskInfo.getLocalizedError() + "!!!",
                TaskState.FINISHED, taskInfo.getState());
    }

    //
    /* 恢复虚拟机.暂停状态下进行 */
    @Test(groups = { "update" }, dependsOnMethods = { "pauseVM" }, alwaysRun = true)
    public void resumeVM() throws Exception {
        VM vm = service.getVMInfo(vmUUID);
        if (vm.getStatus() != VmStatus.STARTED) {
            TaskIntermediateResult taskResult = service.powerOffVM(vmUUID);
            // wait finished.
            TaskInfo taskInfo = TestUtil.waitFor(taskResult.getTaskId());
            TaskIntermediateResult taskResult1 = service.powerOnVM(vmUUID);
            // wait finished.
            TaskInfo taskInfo1 = TestUtil.waitFor(taskResult1.getTaskId());
            try {
				java.lang.Thread.sleep(120000);
			} catch (Exception e) {
				// TODO: handle exception
			}
        }
        TaskIntermediateResult taskResult1 = service.pauseVM(vmUUID);
        // wait finished.
        TaskInfo taskInfo1 = TestUtil.waitFor(taskResult1.getTaskId());
        TaskIntermediateResult taskResult = service.resumeVM(vmUUID);
        TaskInfo taskInfo = TestUtil.waitFor(taskResult.getTaskId());
        AssertJUnit.assertEquals(taskInfo.getLocalizeState() + ":" + taskInfo.getLocalizedError() + "!!!",
                TaskState.FINISHED, taskInfo.getState());
    }
    //

    //
    /* 修改虚拟机.开机或关机状态下进行 */
    @Test(groups = { "update" }, alwaysRun = true)
    public void modifyVM() throws Exception {
        // 5fb777fb-7475-4aa7-b14b-422980e34ecc
        // VM vm1 = service.getVMInfo("77d767ef-07bb-428f-b06b-8b08d57fd920");
        VM vm = service.getVMInfo(vmUUID);
        // vm.getConfig().setName("modify");
        vm.setName("afterModify");
        TaskIntermediateResult taskResult = service.modifyVM(vm);
        TaskInfo taskInfo = TestUtil.waitFor(taskResult.getTaskId());
        AssertJUnit.assertEquals(taskInfo.getLocalizeState() + ":" + taskInfo.getLocalizedError() + "!!!",
                TaskState.FINISHED, taskInfo.getState());
    }

    /**
     * 获取快照列表.
     */
    @Test(groups = { "querybefore" }, alwaysRun = true)
    public void getSnapshotsOfVM() {
        List<VMSnapshot> snapList = service.getSnapshotsOfVM(vmUUID);
        for (VMSnapshot wmst : snapList) {
            if (wmst.getName().equals("test_sdk_snapshot")) {
                snapshotUUID = wmst.getUuid();
                break;
            }
        }
    }

    /**
     * 获取支持的操作系统列表.
     */
    @Test(groups = { "query" }, alwaysRun = true)
    public void getSupportedOS() {
        List<String> os = service.getSupportedOS();
        // System.out.println(os.size());
        // for (String o : os) {
        // System.out.println("supported os---" + o);
        // }
    }

    @Test(groups = { "querybefore" }, alwaysRun = true)
    public void getBackupList() {
        List<VMBackup> backupLst = service.getBackupsOfVm(vmUUID);
        for (VMBackup vmb : backupLst) {
            if (vmb.getName().equals("test_sdk_backup")) {
                vmBackupUuid = vmb.getUuid();
                break;
            }
        }
        // System.out.println(backupLst.size());
    }

    /**
     * 获取ova文件列表.
     */
    @Test(groups = { "update" }, dependsOnMethods = {"exportVM"}, alwaysRun = true)
    public void getOvaFiles() {
        List<File> ovas = service.getOvaFiles();
        String ovafilename = vmUUID;
        if (ovas != null) {
            for (File of : ovas) {
                if (of.getName().contains(vmUUID)) {
                    ovaFile = "/storage/nfs/"+of.getName();
                    break;
                }
            }
        }
        AssertJUnit.assertEquals("ovaFile: ",
        		"/storage/nfs/"+ovafilename+".ova", ovaFile);
    }

    @Test(groups = { "update" }, dependsOnMethods = {"getOvaFiles"}, alwaysRun = true)
    public void getOvaFileConfig() {
        VM ovaConfig = service.getOvaFileConfig(hostUUID,
                ovaFile);
        AssertJUnit.assertEquals("ovaFile: ",
        		ovaConfig.getConfig(), ovaConfig.getConfig());
    }

    @Test(groups = { "query" }, alwaysRun = true)
    public void getCpuAndBindedVM() {
        List<CpuInfo> vmCpuInfoLst = service.getCpuAndBindedVM(hostUUID);
        System.out.println(vmCpuInfoLst.size());
        // for (CpuInfo info : vmCpuInfoLst) {
        // System.out.println(info.getKey());
        // }

    }

    /**
     * 删除快照.
     * @throws Exception
     */
    @Test(groups = { "delete" }, dependsOnMethods = {
            "com.inspur.ics.client.autotest.VmTemplateServiceTest.deleteVmTemplate" }, alwaysRun = true)
    public void deleteSnapshot() throws Exception {
        TaskIntermediateResult taskResult = service.deleteSnapshot(vmUUID, snapshotUUID);
        TaskInfo taskInfo = TestUtil.waitFor(taskResult.getTaskId());
        AssertJUnit.assertEquals(taskInfo.getLocalizeState() + ":" + taskInfo.getLocalizedError() + "!!!",
                TaskState.FINISHED, taskInfo.getState());
    }

    @Test(groups = { "delete" }, dependsOnMethods = { "deleteSnapshot" }, alwaysRun = true)
    public void deleteVM() throws Exception {
        List<VM> vmList = service.getVMInCluster(clusterUUID);
        for (VM vm : vmList) {
            if (vm.getStatus().equals(Types.VmStatus.STARTED) || !vm.getStatus().equals(Types.VmStatus.STOPPED)) {
                TaskIntermediateResult taskResult = service.powerOffVM(vm.getConfig().getUuid());
                TaskInfo taskInfo = TestUtil.waitFor(taskResult.getTaskId());
            }
            TaskIntermediateResult taskResult = service.deleteVM(vm.getConfig().getUuid());
            TaskInfo taskInfo = TestUtil.waitFor(taskResult.getTaskId());
            AssertJUnit.assertEquals(taskInfo.getLocalizeState() + ":" + taskInfo.getLocalizedError() + "!!!",
                    TaskState.FINISHED, taskInfo.getState());
        }
    }
}
