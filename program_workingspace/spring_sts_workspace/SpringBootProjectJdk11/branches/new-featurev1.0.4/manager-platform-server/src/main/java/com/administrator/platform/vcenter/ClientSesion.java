/**
 * @author : 孙留平
 * @since : 2019年2月25日 上午11:28:30
 * @see:
 */
package com.administrator.platform.vcenter;

/**
 * @author : Administrator
 * @since : 2019年2月25日 上午11:28:30
 * @see :
 */
public class ClientSesion implements java.io.Serializable {
    /**
     * vcent url
     */
    private String host;
    /**
     * vcent 用户名
     */
    private String username;
    /**
     * vcent 密码
     */
    private String password;

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * 默认构造函数
     * 
     * @see :
     */
    public ClientSesion() {
        super();
    }

    /**
     * 构造函数
     * 
     * @see :
     * @param host
     * @param username
     * @param password
     */
    public ClientSesion(String host, String username, String password) {
        super();
        this.host = host;
        this.username = username;
        this.password = password;
    }
}
