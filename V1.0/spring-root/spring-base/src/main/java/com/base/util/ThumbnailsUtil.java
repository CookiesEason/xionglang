package com.base.util;

import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Date;
import java.util.UUID;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import com.base.enums.ResourceType;
import com.base.one.entity.Resource;

import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.geometry.Positions;

/**
 * @author：熊浪
 * @Email：xiongl@sunline.cn 
 * @Time：2017年7月13日 
 * @此类作用：操作图片大小
 */
@SuppressWarnings("unused")
public class ThumbnailsUtil {
	// 指定大小进行缩放
	public static void sizeZoom(String newPath, byte[] bytes, int width, int height) throws Exception {
		// size(宽度, 高度)
		/*
		 * 若图片横比200小，高比300小，不变 若图片横比200小，高比300大，高缩小到300，图片比例不变
		 * 若图片横比200大，高比300小，横缩小到200，图片比例不变 若图片横比200大，高比300大，图片按比例缩小，横为200或高为300
		 */
		ByteArrayInputStream bytei = new ByteArrayInputStream(bytes);
		Thumbnails.of(bytei).size(width, height).toFile(newPath);
	}

	// 按照比例进行缩放
	public static void scaleZoom(String newPath, byte[] bytes) throws Exception {
		// scale(比例)
		ByteArrayInputStream bytei = new ByteArrayInputStream(bytes);
		Thumbnails.of(bytei).scale(0.75f).toFile(newPath);
	}

	public static void scaleZoom(File file, byte[] bytes) throws Exception {
		// scale(比例)
		ByteArrayInputStream bytei = new ByteArrayInputStream(bytes);
		Thumbnails.of(bytei).scale(0.75f).toFile(file);
	}

	// 不按照比例，指定大小进行缩放
	private void test3() throws Exception {
		// keepAspectRatio(false) 默认是按照比例缩放的
		Thumbnails.of("jars/a380_1280x1024.jpg").size(200, 200).keepAspectRatio(false).toFile("c:/a380_200x200.jpg");
	}

	// 旋转
	private void test4() throws Exception {
		// rotate(角度),正数：顺时针 负数：逆时针
		Thumbnails.of("jars/a380_1280x1024.jpg").size(1280, 1024).rotate(90).toFile("c:/a380_rotate+90.jpg");

		Thumbnails.of("jars/a380_1280x1024.jpg").size(1280, 1024).rotate(-90).toFile("c:/a380_rotate-90.jpg");
	}

	// 水印
	private void test5() throws Exception {
		// watermark(位置，水印图，透明度)
		Thumbnails.of("jars/a380_1280x1024.jpg").size(1280, 1024).watermark(Positions.BOTTOM_RIGHT, ImageIO.read(new File("jars/watermark.png")), 0.5f).outputQuality(0.8f).toFile("c:/a380_watermark_bottom_right.jpg");

		Thumbnails.of("jars/a380_1280x1024.jpg").size(1280, 1024).watermark(Positions.CENTER, ImageIO.read(new File("jars/watermark.png")), 0.5f).outputQuality(0.8f).toFile("c:/a380_watermark_center.jpg");
	}

	// 裁剪
	private void test6() throws Exception {
		// sourceRegion()

		// 图片中心400*400的区域
		Thumbnails.of("jars/a380_1280x1024.jpg").sourceRegion(Positions.CENTER, 400, 400).size(200, 200).keepAspectRatio(false).toFile("c:/a380_region_center.jpg");

		// 图片右下400*400的区域
		Thumbnails.of("jars/a380_1280x1024.jpg").sourceRegion(Positions.BOTTOM_RIGHT, 400, 400).size(200, 200).keepAspectRatio(false).toFile("c:/a380_region_bootom_right.jpg");

		// 指定坐标
		Thumbnails.of("jars/a380_1280x1024.jpg").sourceRegion(600, 500, 400, 400).size(200, 200).keepAspectRatio(false).toFile("c:/a380_region_coord.jpg");
	}

	// 转化图像格式
	private void test7() throws Exception {
		// outputFormat(图像格式)
		Thumbnails.of("jars/a380_1280x1024.jpg").size(1280, 1024).outputFormat("png").toFile("c:/a380_1280x1024.png");

		Thumbnails.of("jars/a380_1280x1024.jpg").size(1280, 1024).outputFormat("gif").toFile("c:/a380_1280x1024.gif");
	}

	// 输出到OutputStream
	private void test8() throws Exception {
		// toOutputStream(流对象)
		OutputStream os = new FileOutputStream("c:/a380_1280x1024_OutputStream.png");
		Thumbnails.of("jars/a380_1280x1024.jpg").size(1280, 1024).toOutputStream(os);
	}

	// 输出到BufferedImage
	private void test9() throws Exception {
		// asBufferedImage() 返回BufferedImage
		BufferedImage thumbnail = Thumbnails.of("jars/a380_1280x1024.jpg").size(1280, 1024).asBufferedImage();
		ImageIO.write(thumbnail, "jpg", new File("c:/a380_1280x1024_BufferedImage.jpg"));
	}

	public static Resource newCreateImage(String fileName, byte[] data) throws Exception {
		File file = FileMaker.getImageDirectory();
		Resource resource = new Resource();
		resource.setResourcePath(file.getAbsolutePath());
		resource.setResourceType(ResourceType.IMAGE);
		resource.setUploadDate(new Date());
		resource.setFileName(fileName);
		String stuff = getStuff(fileName);
		resource.setOriginal(UUID.randomUUID() + stuff);
		FileUtils.writeByteArrayToFile(new File(resource.getResourcePath(), resource.getOriginal()), data);
		resource.setThumbnail(UUID.randomUUID() + stuff);
		scaleZoom(new File(resource.getResourcePath(), resource.getThumbnail()), data);
		return resource;
	}

	private static String getStuff(String fileName) {
		int index = 0;
		if ((index = fileName.lastIndexOf(".")) > 0) {
			return fileName.substring(index);
		}
		return ".jpg";
	}

	/**
	 * 指定大小进行缩放 生成图片
	 * @throws IOException 
	 */
	public static Resource defaultMethodForCreateImage(String fileName, byte[] bytes) throws IOException {
		Date now = new Date();
		String path = FileMaker.getImageBasicPath() + FileMaker.createDatePath(now);
		File directory = new File(path);
		if (!directory.exists()) {
			directory.mkdirs();
		}
		Resource resource = new Resource();
		resource.setResourcePath(path + "/" + now.getTime() + fileName.length() + fileName.substring(fileName.indexOf("."), fileName.length())); // Linux
		resource.setUploadDate(now);
		resource.setFileName(fileName);
		resource.setResourceType(ResourceType.IMAGE);

		try {
			scaleZoom(resource.getResourcePath(), bytes);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resource;
	}

	/**
	 * 按照比例进行缩放 生成图片
	 * @throws IOException 
	 */
	public static Resource sizeZoomForCreateImage(String fileName, byte[] bytes, int width, int height) throws IOException {
		Date now = new Date();
		String path = FileMaker.getImageBasicPath() + FileMaker.createDatePath(now);
		File directory = new File(path);
		if (!directory.exists()) {
			directory.mkdirs();
		}
		Resource resource = new Resource();
		resource.setResourcePath(path + "/" + now.getTime() + fileName.length() + fileName.substring(fileName.indexOf("."), fileName.length())); // Linux
		resource.setUploadDate(now);
		resource.setFileName(fileName);
		resource.setResourceType(ResourceType.IMAGE);

		try {
			sizeZoom(resource.getResourcePath(), bytes, width, height);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resource;
	}

	/**
	 * 保存用户上传的Excel
	 * 
	 * @param response
	 * @param request
	 * @param file
	 * @return
	 * @throws IOException
	 */
	public static Resource uploadFile(MultipartFile file, String uploadDir) throws IOException {
		Resource resource = new Resource();
		byte[] bytes = file.getBytes();
		File dirPath = new File(uploadDir);
		if (!dirPath.exists()) {
			dirPath.mkdirs();
		}
		String sep = System.getProperty("file.separator");
		File uploadedFile = new File(dirPath + sep + file.getOriginalFilename());
		FileCopyUtils.copy(bytes, uploadedFile);
		return resource;
	}

	/**
	 * 导出Excel
	 * 
	 * @param fileName
	 * @param path
	 * @return
	 * @throws IOException
	 */
	public static void downLoadFile(String fileName, String path, HttpServletRequest request, HttpServletResponse response) throws IOException {
		response.setContentType("text/html;charset=UTF-8");
		request.setCharacterEncoding("UTF-8");
		String str = "attachment;filename=";
		String name = "";
		response.setCharacterEncoding("UTF-8");
		long fileLength = new File(path + "/" + fileName).length();
		response.setContentType("application/octet-stream");
		response.setHeader("Content-disposition", "attachment; filename=" + new String(fileName.getBytes("utf-8"), "ISO8859-1"));
		response.setHeader("Content-Length", String.valueOf(fileLength));

		BufferedInputStream fis = null;
		try {
			name = java.net.URLEncoder.encode(fileName, "UTF-8");
			fis = new BufferedInputStream(new FileInputStream(new File(path + "/" + fileName)));

			byte[] bytes = new byte[1024];
			while (fis.read(bytes) != -1) {
				response.getOutputStream().write(bytes);
			}
			response.getOutputStream().flush();
			response.getOutputStream().close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (fis != null) {
				try {
					fis.close();
				} catch (Exception e) {
				}
			}
		}
	}
}
