/**
 * @author : 孙留平
 * @since : 2019年3月12日 上午9:35:43
 * @see:
 */
package com.administrator.platform.core.base.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Enumeration;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.tools.zip.ZipEntry;
import org.apache.tools.zip.ZipFile;
import org.apache.tools.zip.ZipOutputStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.administrator.platform.config.GlobalExceptionMessage;
import com.administrator.platform.exception.base.BusinessValidationException;
import com.beust.jcommander.ParameterException;

/**
 * @author : Administrator
 * @since : 2019年3月12日 上午9:35:43
 * @see :
 */
public class ZipCompress {
	private static final Logger logger = LoggerFactory
	        .getLogger(ZipCompress.class);
	/**
	 * 使用GBK编码可以避免压缩中文文件名乱码
	 */
	private static final String CHINESE_CHARSET = "UTF-8";

	/**
	 * 文件读取缓冲区大小
	 */
	private static final int CACHE_SIZE = 2048;

	/**
	 * 压缩文件
	 * 
	 * @see :
	 * @param :
	 * @return : void
	 * @param source
	 * @param dest
	 * @throws IOException
	 */
	public static String zip(String source, String dest) throws IOException {
		logger.debug("开始压缩文件");
		if (StringUtil.isEmpty(dest)) {
			dest = source + ".zip";
		}

		File destFile = new File(dest);

		if (destFile.exists()) {
			FileUtils.forceDelete(destFile);
		}

		// try (// 创建zip输出流
		// ZipOutputStream outputStream = new ZipOutputStream(
		// new BufferedOutputStream(new FileOutputStream(dest)));) {
		try {// 创建zip输出流
			zipFolderToFile(source, destFile.getAbsolutePath());
			// File sourceFile = new File(source);
			// // 调用压缩
			// compress(outputStream, sourceFile, sourceFile.getName());
			// outputStream.close();

			logger.debug("压缩完成");
			return destFile.getAbsolutePath();
		} catch (Exception e) {
			logger.error("压缩文件失败:{}", e.getMessage());
			throw new BusinessValidationException("文件不存在!");
		}
	}

	/**
	 * 压缩文件
	 * 
	 * @see :
	 * @param :
	 * @return : void
	 * @param out
	 * @param bos
	 * @param sourceFile
	 * @param base
	 * @throws IOException
	 * @throws Exception
	 */
	private static void compress(ZipOutputStream out, File sourceFile,
	        String base) throws IOException {
		// 如果路径为目录（文件夹）
		if (sourceFile.isDirectory()) {
			// 取出文件夹中的文件（或子文件夹）
			File[] flist = sourceFile.listFiles();
			// 如果文件夹为空，则只需在目的地zip文件中写入一个目录进入点
			if (flist.length == 0) {
				out.putNextEntry(new ZipEntry(base + "/"));
			} else// 如果文件夹不为空，则递归调用compress，文件夹中的每一个文件（或文件夹）进行压缩
			{
				for (int i = 0; i < flist.length; i++) {
					compress(out, flist[i], base + "/" + flist[i].getName());
				}
			}
		} else {// 如果不是目录（文件夹），即为文件，则先写入目录进入点，之后将文件写入zip文件中
			out.putNextEntry(new ZipEntry(base));
			FileInputStream fos = new FileInputStream(sourceFile);
			BufferedInputStream bis = new BufferedInputStream(fos);
			int tag;
			// 将源文件写入到zip文件中
			while ((tag = bis.read()) != -1) {
				out.write(tag);
			}
			bis.close();
			fos.close();
		}
	}

	/**
	 * 解压缩zip包
	 * 
	 * @param zipFilePath
	 *            zip文件的全路径
	 * @param unzipFilePath
	 *            解压后的文件保存的路径
	 * @param includeZipFileName
	 *            解压后的文件保存的路径是否包含压缩文件的文件名。true-包含；false-不包含
	 */
	@SuppressWarnings("unchecked")
	public static String unzip(String zipFilePath, String unzipFilePath,
	        boolean includeZipFileName) throws Exception {

		/**
		 * 待解压文件不能为空
		 */
		if (StringUtils.isEmpty(zipFilePath)) {
			throw new ParameterException(
			        GlobalExceptionMessage.NULL_PARAMETER_MESSAGE);
		}

		File zipFile = new File(zipFilePath);

		if (StringUtil.isEmpty(unzipFilePath)) {
			// unzipFilePath = zipFilePath.substring(0, zipFilePath.length() -
			// 4);
			// String zipFileName = zipFile.getName();
			// unzipFilePath = zipFile.getParent() + File.separator + zipFileName.substring(0,
			// zipFileName.indexOf((".")));
			unzipFilePath = zipFile.getParent();
		}

		// 如果解压后的文件保存路径包含压缩文件的文件名，则追加该文件名到解压路径
		if (includeZipFileName) {
			String fileName = zipFile.getName();
			if (StringUtils.isNotEmpty(fileName)) {
				fileName = fileName.substring(0, fileName.lastIndexOf("."));
			}
			unzipFilePath = unzipFilePath + File.separator + fileName;
		}

		// 创建解压缩文件保存的路径
		File unzipFileDir = new File(unzipFilePath);

		logger.debug("开始解压文件:{}，到:{}", zipFilePath,
		        unzipFileDir.getAbsolutePath());
		String unzipFileDirTo = unZip(zipFilePath,
		        unzipFileDir.getAbsolutePath());

		logger.debug("解压文件:{}，到{}完成", zipFilePath, unzipFilePath);

		return unzipFileDirTo;

	}

	/**
	 * <p>
	 * 压缩文件
	 * </p>
	 * 
	 * @param sourceFolder
	 *            压缩文件夹
	 * @param zipFilePath
	 *            压缩文件输出路径
	 * @throws Exception
	 */
	public static void zipFolderToFile(String sourceFolder, String zipFilePath)
	        throws Exception {
		OutputStream out = new FileOutputStream(zipFilePath);
		BufferedOutputStream bos = new BufferedOutputStream(out);
		ZipOutputStream zos = new ZipOutputStream(bos);
		// 解决中文文件名乱码
		zos.setEncoding(CHINESE_CHARSET);
		File file = new File(sourceFolder);
		String basePath = null;
		if (file.isDirectory()) {
			basePath = file.getPath();
		} else {
			basePath = file.getParent();
		}
		zipFile(file, basePath, zos);
		zos.closeEntry();
		zos.close();
		bos.close();
		out.close();
	}

	/**
	 * <p>
	 * 递归压缩文件
	 * </p>
	 * 
	 * @param parentFile
	 * @param basePath
	 * @param zos
	 * @throws Exception
	 */
	private static void zipFile(File parentFile, String basePath,
	        ZipOutputStream zos) throws Exception {
		File[] files = new File[0];
		if (parentFile.isDirectory()) {
			files = parentFile.listFiles();
		} else {
			files = new File[1];
			files[0] = parentFile;
		}
		String pathName;
		InputStream is;
		BufferedInputStream bis;
		byte[] cache = new byte[CACHE_SIZE];
		for (File file : files) {
			if (file.isDirectory()) {
				zipFile(file, basePath, zos);
			} else {
				pathName = file.getPath().substring(basePath.length() + 1);
				is = new FileInputStream(file);
				bis = new BufferedInputStream(is);
				zos.putNextEntry(new ZipEntry(pathName));
				int nRead = 0;
				while ((nRead = bis.read(cache, 0, CACHE_SIZE)) != -1) {
					zos.write(cache, 0, nRead);
				}
				bis.close();
				is.close();
			}
		}
	}

	/**
	 * <p>
	 * 解压压缩包
	 * </p>
	 * 
	 * @param zipFilePath
	 *            压缩文件路径
	 * @param destDir
	 *            压缩包释放目录
	 * @throws Exception
	 */
	public static String unZip(String zipFilePath, String destDir)
	        throws Exception {

		if (!destDir.endsWith(File.separator)) {
			destDir += File.separator;
		}

		BufferedInputStream bis;
		FileOutputStream fos;
		BufferedOutputStream bos;
		File file;
		File parentFile;
		ZipEntry entry;
		byte[] cache = new byte[CACHE_SIZE];
		try (ZipFile zipFile = new ZipFile(zipFilePath, CHINESE_CHARSET);) {
			Enumeration<?> emu = zipFile.getEntries();
			while (emu.hasMoreElements()) {
				entry = (ZipEntry) emu.nextElement();
				if (entry.isDirectory()) {
					new File(destDir + entry.getName()).mkdirs();
					continue;
				}
				bis = new BufferedInputStream(zipFile.getInputStream(entry));
				file = new File(destDir + entry.getName());
				parentFile = file.getParentFile();
				if (parentFile != null && (!parentFile.exists())) {
					parentFile.mkdirs();
				}
				fos = new FileOutputStream(file);
				bos = new BufferedOutputStream(fos, CACHE_SIZE);
				int nRead = 0;
				while ((nRead = bis.read(cache, 0, CACHE_SIZE)) != -1) {
					fos.write(cache, 0, nRead);
				}
				bos.flush();
				bos.close();
				fos.close();
				bis.close();
			}
			zipFile.close();
		}

		return destDir;
	}
}
