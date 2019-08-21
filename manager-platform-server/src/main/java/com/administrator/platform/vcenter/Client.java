/**
 * @author : 孙留平
 * @since : 2019年2月25日 下午1:36:00
 * @see:
 */
package com.administrator.platform.vcenter;

import java.net.URL;

import com.vmware.vim25.AboutInfo;
import com.vmware.vim25.mo.Folder;
import com.vmware.vim25.mo.ManagedEntity;
import com.vmware.vim25.mo.ServiceInstance;

/**
 * @author : Administrator
 * @since : 2019年2月25日 下午1:36:00
 * @see :
 */
public class Client {
    public static void main(String[] args) {

        try {
            ClientSesion session = new ClientSesion("192.168.100.72",
                    "administrator@vsphere.local", "Admin@123");
            URL url = new URL("https", session.getHost(), "/sdk");
            ServiceInstance si = new ServiceInstance(url, session.getUsername(),
                    session.getPassword(), true);

            Folder rootFolder = si.getRootFolder();
            // VCenter vc = new VCenter();
            AboutInfo ai = si.getAboutInfo();
            System.out.println("名称" + ai.getFullName());
            System.out.println("版本：" + ai.getVersion());
            System.out.println(ai.apiType);
            System.out.println(ai);

            System.out.println(si);
            System.out.println(si.getClusterProfileManager());
            System.out.println(rootFolder.getName());

            ManagedEntity[] children = rootFolder.getChildEntity();
            for (int i = 0; i < children.length; i++) {
                ManagedEntity managedEntity = children[i];
                System.out.println(managedEntity.getName());
            }

            si.currentTime();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
