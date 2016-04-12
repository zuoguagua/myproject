package com.inspur.ics.client.rest;

import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;

import org.jboss.resteasy.annotations.Form;

import com.inspur.ics.pojo.VM;

/**
 * Rest service of vm module.
 */
@Path("/")
public interface VMRestService {
    /**
     * get vms in cluster.
     * @param token
     *            user token
     * @param clusterUUID
     *            cluster uuid
     * @return result
     */
    @POST
    @Path("vm_getVMInCluster")
    String getVMsInCluster(@FormParam("token") String token,
            @FormParam("clusterUUID") String clusterUUID);

    /**
     * get all vms.
     * @param token
     *            user token
     * @return result
     */
    @POST
    @Path("vm_getAll")
    String getAllVMs(@FormParam("token") String token);

    /**
     * get all custom vms.
     * @param token
     *        user token
     * @param targetType
     *        task target type
     * @param targetUuid
     *        target uuid
     * @return result
     */
    @POST
    @Path("vm_getVMList")
    String getAllCustomVMs(@FormParam("token") String token,
            @FormParam("targetType") String targetType,
            @FormParam("targetUuid") String targetUuid);

    /**
     * create vm by iso.
     * @param token
     *        user token
     * @param vm
     *        vm info(json fomat)
     * @return result
     */
    @POST
    @Path("vm_createVMByIso")
    String createVMByIso(@FormParam("token") String token, @FormParam("vm") String vm);

    /**
     * power on vm.
     * @param token
     *            user token
     * @param vmUUID
     *            vm uuid
     * @return result
     */
    @POST
    @Path("vm_powerOn")
    String powerOnVM(@FormParam("token") String token,
            @FormParam("vmUUID") String vmUUID);

    /**
     * power off vm.
     * @param token
     *            user token
     * @param vmUUID
     *            vm uuid
     * @return result
     */
    @POST
    @Path("vm_powerOff")
    String powerOffVM(@FormParam("token") String token,
            @FormParam("vmUUID") String vmUUID);

    /**
     * pause vm.
     * @param token
     *            user token
     * @param vmUUID
     *            vm uuid
     * @return result
     */
    @POST
    @Path("vm_pause")
    String pauseVM(@FormParam("token") String token,
            @FormParam("vmUUID") String vmUUID);

    /**
     * resume vm.
     * @param token
     *            user token
     * @param vmUUID
     *            vm uuid
     * @return result
     */
    @POST
    @Path("vm_resume")
    String resumeVM(@FormParam("token") String token,
            @FormParam("vmUUID") String vmUUID);

    /**
     * delete vm.
     * @param token
     *            user token
     * @param vmUUID
     *            vm uuid
     * @return result
     */
    @POST
    @Path("vm_delete")
    String deleteVM(@FormParam("token") String token,
            @FormParam("vmUUID") String vmUUID);

    /**
     * restart vm.
     * @param token
     *            user token
     * @param vmUUID
     *            vm uuid
     * @return result
     */
    @POST
    @Path("vm_restart")
    String restartVM(@FormParam("token") String token,
            @FormParam("vmUUID") String vmUUID);

    /**
     * @param token
     *        user token
     * @param vm
     *        vm info (json fomat)
     * @return result
     */
    @POST
    @Path("vm_modify")
    String modifyVM(@FormParam("token") String token,
            @FormParam("vm") String vm);

    /**
     * copy vm.
     * @param token
     *            user token
     * @param vmUUID
     *            vm uuid
     * @param vmName
     *            vm name
     * @return result
     */
    @POST
    @Path("vm_copy")
    String copyVM(@FormParam("token") String token,
            @FormParam("vmUUID") String vmUUID,
            @FormParam("vmName") String vmName);

    /**
     * create vm by template.
     * @param token
     *            user token
     * @param templateUUID
     *            template uuid
     * @param vmName
     *            vm name
     * @param hostUuid
     *        host uuid
     * @param disks
     *        vm disks
     * @return result
     */
    @POST
    @Path("vm_createByTemplate")
    String createVMByTemplate(@FormParam("token") String token,
            @FormParam("templateUUID") String templateUUID,
            @FormParam("vmName") String vmName,
            @FormParam("hostUuid") String hostUuid,
            @FormParam("disks") String[] disks);

    /**
     * batch create vm by template.
     * @param token
     *            user token
     * @param templateUUID
     *            template uuid
     * @param vmPrefixName
     *            prefix name of vm
     * @param num
     *            number of vms to create
     * @return result
     */
    @POST
    @Path("vm_batchCreate")
    String batchCreateVMByTemplate(@FormParam("token") String token,
            @FormParam("templateUUID") String templateUUID,
            @FormParam("vmName") String vmName,
            @FormParam("num") int num);

    /**
     * get hosts can migrate vm to.
     * @param token
     *            user token
     * @param vmUUID
     *            vm uuid
     * @return result
     */
    @POST
    @Path("vm_getAvailableHostsToMigrateVM")
    String getHostsMovedIn(@FormParam("token") String token,
            @FormParam("vmUUID") String vmUUID);

    /**
     * migrate vm.
     * @param token
     *            user token
     * @param vmUUID
     *            vm uuid
     * @param hostUuid
     *            dest host's uuid
     * @return result
     */
    @POST
    @Path("vm_migrate")
    String migrateVM(@FormParam("token") String token,
            @FormParam("vmUUID") String vmUUID,
            @FormParam("hostUuid") String hostUuid);

    /**
     * get hosts can relocate vm to.
     * @param token
     *        user token
     * @param vmUUID
     *        vm uuid
     * @return result
     */
    @POST
    @Path("vm_getAvailableHostsToRelocateVM")
    String getAvailableHostsToRelocateVM(@FormParam("token") String token,
           @FormParam("vmUUID") String vmUUID);

    /**
     * relocate vm.
     * @param token
     *        user token
     * @param vmUUID
     *        vm uuid
     * @param hostUuid
     *        host uuid
     * @param disks
     *        virtual disks of vm
     * @return result
     */
    @POST
    @Path("vm_relocate")
    String relocate(@FormParam("token") String token,
                    @FormParam("vmUUID") String vmUUID,
                    @FormParam("hostUuid") String hostUuid,
                    @FormParam("disks") String[] disks);
    /**
     * get vm info.
     * @param token
     *            user token
     * @param vmUUID
     *            vm uuid
     * @return result
     */
    @POST
    @Path("vm_getInfo")
    String getVMInfo(@FormParam("token") String token,
            @FormParam("vmUUID") String vmUUID);

    /**
     * get vm info by name.
     * @param token
     *            user token
     * @param vmName
     *            vm name
     * @return result
     */
    @POST
    @Path("vm_getInfoByName")
    String getVMInfoByName(@FormParam("token") String token,
            @FormParam("vmName") String vmName);

    /**
     * get vm runtime info.
     * @param token
     *            user token
     * @param vmUUID
     *            vm uuid
     * @return result
     */
    @POST
    @Path("vm_getRuntimeInfo")
    String getVMRuntimInfo(@FormParam("token") String token,
            @FormParam("vmUUID") String vmUUID);

    /**
     * open console of vm.
     * @param token
     *            user token
     * @param vmUUID
     *            vm uuid
     * @return result
     */
    @POST
    @Path("vm_openConsole")
    String openConsole(@FormParam("token") String token,
            @FormParam("vmUUID") String vmUUID);

    /**
     * attach cdrom to vm.
     * @param token
     *            user token
     * @param vmUUID
     *            vm uuid
     * @param cdrom
     *            cdrom
     * @return result
     */
    @POST
    @Path("vm_attachCdrom")
    String attachCdrom(@FormParam("token") String token,
            @FormParam("vmUUID") String vmUUID,
            @FormParam("cdrom") String cdrom);

    /**
     * detach cdrom from vm.
     * @param token
     *            user token
     * @param vmUUID
     *            vm uuid
     * @return result
     */
    @POST
    @Path("vm_detachCdrom")
    String detachCdrom(@FormParam("token") String token,
            @FormParam("vmUUID") String vmUUID);

    /**
     * hot add disk to vm.
     * @param token
     *            user token
     * @param vmUUID
     *            vm uuid
     * @param hotAddDisk
     *            disk to hot add
     * @return result
     */
    @POST
    @Path("vm_hotAddDisk")
    String hotAddDisk(@FormParam("token") String token,
            @FormParam("vmUUID") String vmUUID,
            @FormParam("hotAddDisk") String hotAddDisk);

    /**
     * config direct net of vm.
     * @param token
     *            user token
     * @param netConfig
     *            config of network
     * @return result
     */
    @POST
    @Path("vm_configDirectNet")
    String configDirectNet(@FormParam("token") String token,
            @FormParam("config") String netConfig);

    /**
     * attach usb to vm.
     * @param token
     *            user token
     * @param vmUUID
     *            vm uuid
     * @param usbDevice
     *            usb device path
     * @param usbType
     *            usb type
     * @return result
     */
    @POST
    @Path("vm_attachUsb")
    String attachUsb(@FormParam("token") String token,
            @FormParam("vmUUID") String vmUUID,
            @FormParam("usbDevice") String usbDevice,
            @FormParam("usbType") String usbType);

    /**
     * detach usb from vm.
     * @param token
     *            user token
     * @param vmUUID
     *            vm uuid
     * @return result
     */
    @POST
    @Path("vm_detachUsb")
    String detachUsb(@FormParam("token") String token,
            @FormParam("vmUUID") String vmUUID);

    /**
     * open ha of vms.
     * @param token
     *            user token
     * @param vmUUIDs
     *            uuid of vms
     * @param openHA
     *            true:open,false:close
     * @return result
     */
    @POST
    @Path("vm_configHA")
    String configHA(@FormParam("token") String token,
            @FormParam("vmUUIDs") String[] vmUUIDs,
            @FormParam("openHA") boolean openHA);

    /**
     * configure vm whether to allow migrate.
     * @param token
     *            user token
     * @param vmUUIDs
     *            uuid of vms
     * @param migratable
     *            true:migratable,false:unmigratable
     * @return result
     */
    @POST
    @Path("vm_configMigratable")
    String configMigratable(@FormParam("token") String token,
            @FormParam("vmUUIDs") String[] vmUUIDs,
            @FormParam("migratable") boolean migratable);

    /**
     * create snapshot of vm.
     * @param token
     *            user token
     * @param vmUUID
     *            vm uuid
     * @param snapInfo
     *            snapshot info
     * @return result
     */
    @POST
    @Path("vm_createSnapshot")
    String createSnapshot(@FormParam("token") String token,
            @FormParam("vmUUID") String vmUUID,
            @FormParam("snapshot") String snapInfo);


    /**
     * revert to snapshot.
     * @param token
     *            user token
     * @param vmUUID
     *            vm uuid
     * @param snapshotUUID
     *            snapshot uuid
     * @return result
     */
    @POST
    @Path("vm_revertToSnapshot")
    String revertToSnapshot(@FormParam("token") String token,
            @FormParam("vmUUID") String vmUUID,
            @FormParam("snapshotUUID") String snapshotUUID);

    /**
     * delete snapshot.
     * @param token
     *            user token
     * @param vmUUID
     *            vm uuid
     * @param snapshotUUID
     *            snapshot uuid
     * @return result
     */
    @POST
    @Path("vm_deleteSnapshot")
    String deleteSnapshot(@FormParam("token") String token,
            @FormParam("vmUUID") String vmUUID,
            @FormParam("snapshotUUID") String snapshotUUID);

    /**
     * get snapshot list.
     * @param token
     *            user token
     * @param vmUUID
     *            vm uuid
     * @return result
     */
    @POST
    @Path("vm_getSnapshots")
    String getSnapshots(@FormParam("token") String token,
            @FormParam("vmUUID") String vmUUID);

    /**
     * hot add vnic to vm.
     * @param token
     *            user token
     * @param vmUUID
     *            vm uuid
     * @param hotAddVNic
     *            vnic info to hot add
     * @return result
     */
    @POST
    @Path("vm_hotAddVNic")
    String hotAddVNic(@FormParam("token") String token,
            @FormParam("vmUUID") String vmUUID,
            @FormParam("hotAddVNic") String hotAddVNic);

    /**
     * hot resize disk.
     * @param token
     *            user token
     * @param vmUUID
     *            vm uuid
     * @param hotResizeDisk
     *            disk to hot resize
     * @return result
     */
    @POST
    @Path("vm_hotResizeDisk")
    String hotResizeDisk(@FormParam("token") String token,
            @FormParam("vmUUID") String vmUUID,
            @FormParam("hotResizeDisk") String hotResizeDisk);

    /**
     * shutdown vm.
     * @param token
     *            user token
     * @param vmUUID
     *            vm uuid
     * @return result
     */
    @POST
    @Path("vm_shutdown")
    String shutdownVM(@FormParam("token") String token,
            @FormParam("vmUUID") String vmUUID);

    /**
     * force update status of vm.
     * @param token
     *            user token
     * @param vmUUID
     *            vm uuid
     * @return result
     */
    @POST
    @Path("vm_forceUpdateStatus")
    String forceUpdateStatus(@FormParam("token") String token,
            @FormParam("vmUUID") String vmUUID);

    /**
     * get supported OS.
     * @param token
     *            user token
     * @return result
     */
    @POST
    @Path("vm_getSupportedOS")
    String getSupportedOS(@FormParam("token") String token);

    /**
     * get vnic types that supported by specified OS.
     * @param token
     *            user token
     * @param osType
     *            os type
     * @return result
     */
    @POST
    @Path("vm_getVnicType")
    String getVnicType(@FormParam("token") String token,
            @FormParam("osType") String osType);

    /**
     * export vm.
     * @param token
     *        user token
     * @param vmUUID
     *        vm uuid
     * @param filePath
     *        file path of exported vm
     * @return result
     */
    @POST
    @Path("vm_exportVM")
    String exportVM(@FormParam("token") String token,
                    @FormParam("vmUUID") String vmUUID,
                    @FormParam("filePath") String filePath);

    /**
     * import vm.
     * @param token
     *        user token
     * @param vm
     *        vm info(json format)
     * @param filePath
     *        file path of exported vm
     * @return result
     */
    @POST
    @Path("vm_importVM")
    String importVM(@FormParam("token") String token,
                    @FormParam("vm") String vm,
                    @FormParam("filePath") String filePath);

    /**
     * get Ova Files.
     * @param token
     *        user token
     * @return result
     */
    @POST
    @Path("vm_getOvaFiles")
    String getOvaFiles(@FormParam("token") String token);

    /**
     * get Config of ova File.
     * @param token
     *        user token
     * @param hostUuid
     *        host uuid
     * @param filePath
     *        file path of exported vm
     * @return result
     */
    @POST
    @Path("vm_getOvaFileConfig")
    String getOvaFileConfig(@FormParam("token") String token,
            @FormParam("hostUuid") String hostUuid,
            @FormParam("filePath") String filePath);

    /**
     * get cpu and bindedVM.
     * @param token
     *        user token
     * @param hostUuid
     *        host uuid
     * @return result
     */
    @POST
    @Path("vm_getCpuAndBindedVM")
    String getCpuAndBindedVM(@FormParam("token") String token, @FormParam("hostUuid") String hostUuid);

    /**
     * open FT mode.
     * @param token
     *        user token
     * @param vmUUID
     *        vm UUID
     * @param hostUuid
     *        host uuid
     * @return result
     */
    @POST
    @Path("vm_openFTMode")
    String openFTMode(@FormParam("token") String token,
                    @FormParam("vmUUID") String vmUUID,
                    @FormParam("hostUuid") String hostUuid);

    /**
     * close FT mode.
     * @param token
     *        user token
     * @param vmUUID
     *        vm UUID
     * @return result
     */
    @POST
    @Path("vm_closeFTMode")
    String closeFTMode(@FormParam("token") String token, @FormParam("vmUUID") String vmUUID);

    /**
     * backup snapshot.
     * @param token
     *        user token
     * @param vmUUID
     *        vm uuid
     * @param snapshotUUID
     *        snapshot uuid
     * @param dsUuid
     *        backup datastore uuid
     * @param backup
     *        backup info(json format)
     * @return result
     */
    @POST
    @Path("vm_backupSnapshot")
    String backupSnapshot(@FormParam("token") String token,
                          @FormParam("vmUUID") String vmUUID,
                          @FormParam("snapshotUUID") String snapshotUUID,
                          @FormParam("dsUuid") String dsUuid,
                          @FormParam("backup") String backup);

    /**
     * revert backup.
     * @param token
     *        user token
     * @param vmUUID
     *        vm uuid
     * @param vmBackupUuid
     *        vm backup uuid
     * @return result
     */
    @POST
    @Path("vm_revertBackup")
    String revertBackup(@FormParam("token") String token,
                        @FormParam("vmUUID") String vmUUID,
                        @FormParam("vmBackupUuid") String vmBackupUuid);

    /**
     * get backup list.
     * @param token
     *        user token
     * @param vmUUID
     *        vm uuid
     * @return result
     */
    @POST
    @Path("vm_getBackupList")
    String getBackupList(@FormParam("token") String token,
                         @FormParam("vmUUID") String vmUUID);

    /**
     * config Max Ha Limit.
     * @param token
     *        user token
     * @param maxHaLimit
     *        max ha limit
     * @return task
     */
    @POST
    @Path("vm_configMaxHaLimit")
    String configMaxHaLimit(@FormParam("token") String token,
                            @FormParam("vmUUIDs") String[] vmUUIDs,
                            @FormParam("maxHaLimit") String maxHaLimit);
}
