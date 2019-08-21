/**
 * @author : 孙留平
 * @since : 2019年2月25日 下午3:28:40
 * @see:
 */
package com.administrator.platform.vcenter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.xml.ws.soap.SOAPFaultException;

import com.vmware.vim25.ClusterComputeResourceSummary;
import com.vmware.vim25.mo.ClusterComputeResource;
import com.vmware.vim25.mo.Datastore;
import com.vmware.vim25.mo.InventoryNavigator;
import com.vmware.vim25.mo.ManagedEntity;

/**
 * @author : Administrator
 * @since : 2019年2月25日 下午3:28:40
 * @see :
 */
/**
 * @description 操作vcenter中的集群对象
 * @date 2017年2月8日14:35:38
 * @version 1.1
 * @author DiWk
 */
public class ClusterComputerResourceSummary {

    /**
     * 连接类声明
     * 
     */
    private ConnectedVimServiceBase cs = null;

    public ConnectedVimServiceBase getCs() {
        return cs;
    }

    public void setCs(ConnectedVimServiceBase cs) {
        this.cs = cs;
    }

    /**
     * @description 获取vcenter中所有的集群对象
     * @date 2017年2月3日10:42:09
     * @return clusterList 集群对象集合
     * @version 1.1
     * @author DiWk
     */
    public List<ClusterComputeResource> getClusterList() {
        List<ClusterComputeResource> clusterList = new ArrayList<ClusterComputeResource>();
        ClusterComputeResource clusterComputeResource = null;
        try {
            ManagedEntity[] managedEntities = new InventoryNavigator(
                    cs.si.getRootFolder())
                            .searchManagedEntities("ClusterComputeResource");
            if (managedEntities != null && managedEntities.length > 0) {
                for (ManagedEntity managedEntity : managedEntities) {
                    clusterComputeResource = (ClusterComputeResource) managedEntity;
                    clusterList.add(clusterComputeResource);
                }
            }
        } catch (SOAPFaultException sfe) {
            // VcenterException.printSoapFaultException(sfe);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return clusterList;
    }

    /**
     * @description 根据集群名称获取对应的集群对象
     * @date 2017年2月3日10:44:02
     * @return clusterList 集群对象集合
     * @version 1.1
     * @author DiWk
     */
    public List<ClusterComputeResource> getClusterListByName(
            List<String> clustersName) {
        List<ClusterComputeResource> clusterList = new ArrayList<ClusterComputeResource>();
        ClusterComputeResource clusterComputeResource = null;
        try {
            if (clustersName == null || clustersName.size() < 0) {
                return null;
            }
            List<ClusterComputeResource> clusterList2 = getClusterList();
            if (clusterList2 == null || clusterList2.size() < 0) {
                return null;
            }
            for (String string : clustersName) {
                for (ClusterComputeResource clusterComputeResource2 : clusterList2) {
                    if (clusterComputeResource2.getName().equals(string)) {
                        clusterList.add(clusterComputeResource);
                    }
                }
            }
        } catch (SOAPFaultException sfe) {
            // VcenterException.printSoapFaultException(sfe);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return clusterList;
    }

    /**
     * @description 根据集群名称获取对应的集群summary
     * @date 2017年2月3日10:54:18
     * @return clusterSumList 集群对象summary集合
     * @version 1.1
     * @author DiWk
     */
    public List<ClusterComputeResourceSummary> getClusterComputeResourceSummary(
            List<String> clustersName) {
        List<ClusterComputeResourceSummary> clusterSumList = new ArrayList<ClusterComputeResourceSummary>();
        List<ClusterComputeResource> clusterListByName = null;
        try {
            clusterListByName = getClusterListByName(clustersName);
            if (clusterListByName != null && clusterListByName.size() > 0) {
                for (ClusterComputeResource cluster : clusterListByName) {
                    ClusterComputeResourceSummary summary = (ClusterComputeResourceSummary) cluster
                            .getSummary();
                    clusterSumList.add(summary);
                }
            } else {
                return null;
            }
        } catch (SOAPFaultException sfe) {
            // VcenterException.printSoapFaultException(sfe);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return clusterSumList;
    }

    /**
     * @description 根据集群名称获取集群关联的数据存储
     * @date 2017年2月3日11:02:09
     * @return clusterDataStore 集群所关联的数据存储的集合
     * @version 1.1
     * @author DiWk
     */
    public List<Datastore> getDataStoreByClusterNm(List<String> clustersName) {
        List<Datastore> clusterDataStore = new ArrayList<Datastore>();
        List<ClusterComputeResource> clusterListByName = null;
        try {
            clusterListByName = getClusterListByName(clustersName);
            if (clusterListByName != null && clusterListByName.size() > 0) {
                for (ClusterComputeResource cluster : clusterListByName) {
                    Datastore[] datastores = cluster.getDatastores();
                    clusterDataStore.addAll(Arrays.asList(datastores));
                }
            } else {
                return null;
            }
        } catch (SOAPFaultException sfe) {
        } catch (Exception e) {
            e.printStackTrace();
        }
        return clusterDataStore;
    }

    /**
     * 集群测试方法
     * 
     * @see :
     * @param :
     * @return : void
     * @param args
     */
    public static void main(String[] args) {
        ConnectedVimServiceBase cs = new ConnectedVimServiceBase();
        cs.connect("192.168.100.72", "administrator@vsphere.local",
                "Admin@123");
        ClusterComputerResourceSummary cluster = new ClusterComputerResourceSummary();
        cluster.setCs(cs);
        List<ClusterComputeResource> clusterList = cluster.getClusterList();

        System.out.println(clusterList.size());
        if (clusterList != null && clusterList.size() > 0) {
            for (ClusterComputeResource clusterComputeResource : clusterList) {
                System.out.println(clusterComputeResource.getName());
            }
        }
    }
}
