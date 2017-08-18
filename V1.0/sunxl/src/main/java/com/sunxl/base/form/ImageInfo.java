/**
 * 
 */
package com.sunxl.base.form;

import java.awt.image.BufferedImage;
import java.io.Serializable;

/**
 * @author 熊浪
 * @Email xiongl@sunline.cn
 * @Time 2017年8月16日 @此类的作用：
 */
public class ImageInfo implements Serializable {
	private static final long serialVersionUID = 1L;
	private BufferedImage buffImg;//图片
	private String info;//图片中的信息

	public BufferedImage getBuffImg() {
		return buffImg;
	}

	public void setBuffImg(BufferedImage buffImg) {
		this.buffImg = buffImg;
	}

	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}

	public ImageInfo(BufferedImage buffImg, String info) {
		super();
		this.buffImg = buffImg;
		this.info = info;
	}
}
