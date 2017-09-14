/**
 * 
 */
package com.base.util;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.Random;

import com.base.form.ImageInfo;

/**
 * @author 熊浪
 * @Email xiongl@sunline.cn
 * @Time 2017年8月16日 @此类的作用：
 */
public class CodeUtil {

	// 验证码图片的宽度。
	private static int width = 55;
	// 验证码图片的高度。
	private static int height = 30;
	// 验证码字符个数
	private static int codeCount = 4;
	private static int x = 0;
	// 字体高度
	private static int fontHeight;
	private static int codeY;
	// 生成的验证码
	private static char[] codeSequence = { 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9' };
	// 收集发送的验证码信息
	public static int[] telePhone = { 1, 2, 3, 4, 5, 6, 7, 8, 9, 0 };
	static {
		// 从web.xml中获取初始信息
		// 宽度
		String strWidth = "80";
		// 高度
		String strHeight = "34";
		// 字符个数
		String strCodeCount = "4";
		// 将配置的信息转换成数值
		try {
			if (strWidth != null && strWidth.length() != 0) {
				width = Integer.parseInt(strWidth);
			}
			if (strHeight != null && strHeight.length() != 0) {
				height = Integer.parseInt(strHeight);
			}
			if (strCodeCount != null && strCodeCount.length() != 0) {
				codeCount = Integer.parseInt(strCodeCount);
			}
		} catch (NumberFormatException e) {
		}
		x = width / (codeCount + 1);
		fontHeight = height - 5;
		codeY = height - 4;
	}

	public static ImageInfo buildCodeRandomForJsp() {
		// 定义图像buffer
		BufferedImage buffImg = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		Graphics2D g = buffImg.createGraphics();
		// 创建一个随机数生成器类
		Random random = new Random();
		// 将图像填充为白色
		g.setColor(new Color(255, 193, 37));
		g.fillRect(0, 0, width, height);
		// 创建字体，字体的大小应该根据图片的高度来定。
		Font font = new Font("微软雅黑", Font.BOLD, fontHeight);
		// 设置字体。
		g.setFont(font);
		// 画边框。
		g.setColor(Color.BLACK);
		g.drawRect(0, 0, width - 1, height - 1);
		g.setBackground(new Color(255, 255, 255));
		for (int i = 0; i < 10; i++) {
			int x = random.nextInt(width);
			int y = random.nextInt(height);
			int xl = random.nextInt(5);
			int yl = random.nextInt(5);
			g.drawLine(x, y, x + xl, y + yl);
		}
		// randomCode用于保存随机产生的验证码，以便用户登录后进行验证。
		StringBuffer randomCode = new StringBuffer();
		int red = 0, green = 0, blue = 0;
		// 随机产生codeCount数字的验证码。
		for (int i = 0; i < codeCount; i++) {
			// 得到随机产生的验证码数字。
			String strRand = null;
			strRand = String.valueOf(codeSequence[random.nextInt(10)]);
			// 产生随机的颜色分量来构造颜色值，这样输出的每位数字的颜色值都将不同。
			red = random.nextInt(255);
			green = random.nextInt(255);
			blue = random.nextInt(255);
			// 用随机产生的颜色将验证码绘制到图像中。
			g.setColor(new Color(red, green, blue));
			g.drawString(strRand, (i * x) + 3, codeY);
			// 将产生的四个随机数组合在一起。
			randomCode.append(strRand);
		}
		g.dispose();
		return new ImageInfo(buffImg, randomCode.toString());
	}
}
