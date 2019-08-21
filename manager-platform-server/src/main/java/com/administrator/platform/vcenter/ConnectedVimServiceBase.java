/**
 * @author : 孙留平
 * @since : 2019年2月25日 下午3:27:40
 * @see:
 */
package com.administrator.platform.vcenter;

import java.net.URL;

import javax.xml.ws.soap.SOAPFaultException;

import com.vmware.vim25.mo.ServerConnection;
import com.vmware.vim25.mo.ServiceInstance;

/**
 * @author : Administrator
 * @since : 2019年2月25日 下午3:27:40
 * @see :
 */

/**
 * @description 操作vcenter的连接和断开，以及定义公共应用类
 * @date 2017年2月8日14:35:38
 * @version 1.1
 * @author DiWk
 */
public class ConnectedVimServiceBase {
    public ServiceInstance si = null;

    /**
     * @description 链接vcenter
     * @date 2017年2月8日14:23:37
     * @version 1.1
     * @author DiWk
     */
    public void connect(String url, String userName, String passWord) {
        try {
            si = new ServiceInstance(new URL("https://" + url + "/sdk"),
                    userName, passWord, true);
        } catch (SOAPFaultException sfe) {
            // VcenterException.printSoapFaultException(sfe);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * @description 断开vcenter链接
     * @date 2017年2月8日14:23:37
     * @version 1.1
     * @author DiWk
     */
    public void disconnect() {
        try {
            si.getServerConnection().logout();
        } catch (SOAPFaultException sfe) {
            // VcenterException.printSoapFaultException(sfe);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * @description 获取链接URL
     * @date 2017年2月8日14:23:37
     * @version 1.1
     * @author DiWk
     */
    public URL getUrl() {
        ServerConnection serverConnection = si.getServerConnection();
        URL url = null;
        if (serverConnection != null) {
            url = serverConnection.getUrl();
        } else {
            return null;
        }
        return url;
    }
}
