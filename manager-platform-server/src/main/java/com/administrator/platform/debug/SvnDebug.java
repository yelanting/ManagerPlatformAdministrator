
/**
 * @see : Project Name:manager-platform-server
 * @see : File Name:SvnDebug.java
 * @author : 孙留平
 * @since : 2019年7月12日 下午4:36:33
 * @see:
 */

package com.administrator.platform.debug;

import com.administrator.platform.tools.vcs.svn.SvnClientUtil;

public class SvnDebug {
	public static void main(String[] args) {
		String remoteUrl = "http://redmine.hztianque.com:9080/repos/productzz/trunk/AutoTest";
		String username = "sunliuping";

		String password = "Admin@1234";
		SvnClientUtil svnClientUtil = new SvnClientUtil(remoteUrl, username,
				password);

		svnClientUtil.displayDocFiles();
	}
}
