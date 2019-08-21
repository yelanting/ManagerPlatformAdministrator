/**
 * @author : 孙留平
 * @since : 2019年3月8日 下午4:25:03
 * @see:
 */
package com.administrator.platform.tools.vcs.jgit;

import org.eclipse.jgit.transport.JschConfigSessionFactory;
import org.eclipse.jgit.transport.OpenSshConfig.Host;
import org.eclipse.jgit.transport.SshSessionFactory;

import com.jcraft.jsch.Session;

/**
 * @author : Administrator
 * @since : 2019年3月8日 下午4:25:03
 * @see :
 */
public class JavaGitAuthUti {
    private static SshSessionFactory sshSessionFactory;

    public static SshSessionFactory getSshSessionFactory(Host host,
            String password) {
        sshSessionFactory = new JschConfigSessionFactory() {

            @Override
            protected void configure(Host host, Session session) {
                session.setPassword(password);
            }
        };
        return sshSessionFactory;
    }
}
