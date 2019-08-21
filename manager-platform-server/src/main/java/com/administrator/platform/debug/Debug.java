
/***
 * @see:Project Name:manager-platform-server*@see:File Name:Debug.java*@author:孙留平*@since:2019 年7月9日 下午4:01:59*@see:
 */

package com.administrator.platform.debug;

import java.io.File;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.administrator.platform.core.base.util.FileUtil;

public class Debug {

	private static final Logger LOGGER = LoggerFactory.getLogger(Debug.class);

	// private GradleConnector gradleConnector = GradleConnector.newConnector();
	//
	// /**
	// * 执行任务
	// *
	// * @param rootProjectDir
	// * 根项目所在目录。
	// * @param subProjectName
	// * 子工程名称，如果子项目名称为空,则表示在整个工程中执行任务
	// * @param tasks
	// * 任务
	// * @return 执行结果
	// */
	// public ExecuteResult execute(String rootProjectDir, String subProjectName,
	// String... tasks) {
	// if (StringUtil.isEmpty(rootProjectDir)) {
	// throw new IllegalArgumentException("根项目所在的目录不能为空!");
	// }
	// final File root = new File(rootProjectDir);
	// if (!root.exists()) {
	// throw new IllegalArgumentException("根项目所在的目录不存在!");
	// }
	// if (tasks == null) {
	// throw new IllegalArgumentException("没有可执行的任务!");
	// }
	// final List<String> arguments = new ArrayList<String>(1);
	// arguments.add("-xTest");
	// final Map<String, String> jvmDefines = new HashMap<String, String>();
	// final String[] taskes = tasks;
	// if (StringUtil.isStringAvaliable(subProjectName)) {
	// String sub = this.findSubProject(root, subProjectName);
	// if (StringUtil.isStringAvaliable(sub)) {
	// for (int i = 0; i < taskes.length; i++) {
	// taskes[i] = sub + ":" + tasks[i];
	// }
	// }
	// }
	// ExecutorService service = Executors.newSingleThreadExecutor();
	// Future<ExecuteResult> future = service
	// .submit(new Callable<ExecuteResult>() {
	//
	// @Override
	// public ExecuteResult call() throws Exception {
	// return execute(root, arguments, jvmDefines, taskes);
	// }
	//
	// });
	// try {
	// while (!future.isDone()) {
	// // 等待结果
	// }
	// LOGGER.info("{}::执行【{}】完成。", rootProjectDir,
	// Arrays.toString(tasks));
	// return future.get();
	// } catch (Exception e) {
	// LOGGER.error("任务执行失败。", e);
	// return new ExecuteResult(Result.FAIL, e);
	// } finally {
	// service.shutdown();
	// }
	// }
	//
	// /**
	// * 执行Gradle命令
	// *
	// * @param projectDir
	// * 项目目录
	// * @param arguments
	// * 执行参数
	// * @param jvmDefines
	// * jvm参数
	// * @param tasks
	// * 任务
	// * @return 执行结果
	// */
	// public ExecuteResult execute(File projectDir, List<String> arguments,
	// Map<String, String> jvmDefines, String... tasks) {
	// ProjectConnection connection = gradleConnector
	// .forProjectDirectory(projectDir).connect();
	// final CountDownLatch latch = new CountDownLatch(1);
	// final ExecuteResult ret = new ExecuteResult();
	// try {
	// BuildLauncher build = connection.newBuild().forTasks(tasks)
	// .withArguments(
	// arguments.toArray(new String[arguments.size()]));
	// String[] jvmArgs = new String[jvmDefines.size()];
	// if (!jvmDefines.isEmpty()) {
	// int index = 0;
	// for (Map.Entry<String, String> entry : jvmDefines.entrySet()) {
	// jvmArgs[index++] = "-D" + entry.getKey() + "="
	// + entry.getValue();
	// }
	// }
	// build.setJvmArguments(jvmArgs);
	// build.addProgressListener(new ProgressListener() {
	//
	// @Override
	// public void statusChanged(ProgressEvent event) {
	// LOGGER.info("execute gradle task status changed:"
	// + event.getDescription());
	// }
	// });
	// build.run(new ResultHandler<Void>() {
	//
	// @Override
	// public void onComplete(Void result) {
	// ret.setResult(Result.SUCCESS);
	// latch.countDown();
	// }
	//
	// @Override
	// public void onFailure(GradleConnectionException exception) {
	// ret.setResult(Result.FAIL);
	// ret.setException(exception);
	// latch.countDown();
	// }
	// });
	// latch.await();
	// } catch (InterruptedException e) {
	// LOGGER.error("执行Gradle命令出错:" + e.getMessage(), e);
	// } finally {
	// connection.close();
	// }
	// return ret;
	// }
	//
	// private String findSubProject(File root, String subProjectName) {
	//
	// return null;
	//
	// }

	public static void main(String[] args) throws Exception {

		// String projectPath = "D:\\TianQue\\linkage_android";
		//
		// Debug debug = new Debug();
		//
		// ProjectConnection projectConnection = debug.gradleConnector
		// .forProjectDirectory(new File(projectPath)).connect();
		//
		// GradleProject gradleProject = projectConnection
		// .getModel(GradleProject.class);
		//
		// System.out.println(gradleProject.getName());
		//
		// System.out.println(gradleProject.getPath());
		//
		// System.out.println(gradleProject.getTasks());
		// System.out.println("---------------tasks");
		//
		// for (int i = 0; i < gradleProject.getTasks().size(); i++) {
		// System.out.println(
		// "eachtask:" + gradleProject.getTasks().getAt(i).getName());
		// }
		// DomainObjectSet<? extends GradleProject> children = gradleProject
		// .getChildren();
		//
		// for (int i = 0; i < children.size(); i++) {
		//
		// GradleProject childGradleProject = children.getAt(i);
		// System.out.println("child name:" + childGradleProject.getName());
		//
		// DomainObjectSet<? extends GradleTask> sDomainObjectSet = childGradleProject
		// .getTasks();
		// for (int j = 0; j < sDomainObjectSet.size(); j++) {
		// GradleTask gradleTask = sDomainObjectSet.getAt(j);
		// System.out.println("child name:" + childGradleProject.getName()
		// + "--task:" + gradleTask.getName());
		// }
		//
		// }
		// projectConnection.close();

		File file = new File(
		        "D:\\manager_platform\\uploadFile\\classes_data\\newshengPingTai_release_2019.07.29_release\\classes.zip");

		File file2 = new File(
		        "D:\\manager_platform\\uploadFile\\classes_data\\test2\\");

		FileUtil.copyDir(
		        "D:\\manager_platform\\uploadFile\\classes_data\\newshengPingTai_release_2019.07.29_release\\classes\\",
		        "D:\\manager_platform\\uploadFile\\classes_data\\test2\\");
	}
}
