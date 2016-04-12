package com.inspur.autotest;

import org.testng.annotations.Test;
import org.testng.AssertJUnit;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;

import java.util.List;

import org.jboss.resteasy.plugins.providers.RegisterBuiltin;
import org.jboss.resteasy.spi.ResteasyProviderFactory;
import com.inspur.ics.client.ClientFactory;
import com.inspur.ics.client.NfsService;
import com.inspur.ics.client.TaskService;
import com.inspur.ics.pojo.ISOFile;

/**
 * @author liuyi.
 */
public class NfsServiceTest {
    /**
    *
    */
   private static NfsService client;
   /**
    * TaskService.
    */
   private static TaskService taskService;
//   private static String hostIP="100.1.8.9";

   /**
    * init.
    */
   @Parameters({"url", "username", "password", "userLocale"})
   @Test(groups={"init"})
   public static void init(String url, String username,
                           String password, String userLocale) {
	    ClientFactory factory = TestUtil.getClientFactory();
//        token = iVirtualClientFactory.getToken();
        client = factory.getNfsService();
        taskService = factory.getTaskService();
    }
    /**
     * 获取NFS服务器上所有ISO镜像文件.
     */
   @Test(groups={"query"},alwaysRun=true)
    public void getAllIsoFiles() {
        List<ISOFile> list = client.getAllIsoFiles();
        AssertJUnit.assertNotNull(list);
//        for (ISOFile f : list) {
//            System.out.println("IOS FIles ：" + f.getPath());
//        }
    }
    /**
     * IP标识的主机挂载NFS存储.
     */
   @Parameters({"host2ip"})
   @Test(groups={"create"},alwaysRun=true)
    public void mountNfsStorage(String host2ip) {
        client.mountNfsStorage(host2ip);
    }
    /**
     * 卸载IP标识的主机所挂载的NFS存储.
     */
   @Parameters({"host2ip"})
   @Test(groups={"delete"},alwaysRun=true)
    public void umountNfsStorage(String host2ip) {
        client.umountNfsStorage(host2ip);
    }
    /**
     * 目标主机是否挂载NFS存储.
     */
   @Parameters({"host2ip"})
   @Test(groups={"query"},alwaysRun=true)
    public void isNfsStorageMounted(String host2ip) {
        boolean s = client.isNfsStorageMounted(host2ip);
        AssertJUnit.assertTrue(s);
//        System.out.println("mount :" + s);
    }
    /**
     * 获取NFS导出目录路径.
     */
   @Test(groups={"query"},alwaysRun=true)
    public void getNfsExportPath() {
        String path = client.getNfsExportPath();
        AssertJUnit.assertNotNull(path);
//        System.out.println("path : " + path);
    }
    /**
     * 变更NFS导出目录路径.
     */
   @Parameters({"nfsExportPath"})
   @Test(groups={"update"},alwaysRun=true)
    public void modifyNfsExportPath(String nfsExportPath) {
        client.modifyNfsExportPath(nfsExportPath);
    }
//    /**
//     * 打开ISO目录.
//     */
//    @Test
//    public void openIsoDir() {
//        String vncStream = client.openIsoDir();
////        System.out.println(vncStream);
//    }
}
