package com.nio.util;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author 熊浪
 * @Email xiongl@sunline.cn
 * @Time 2017年9月9日 @此类的作用：
 */
public class NioUtil {
	private static final Logger logger = LoggerFactory.getLogger(NioUtil.class);
	private static ByteBuffer readBuffer = ByteBuffer.allocate(32);
	private static ByteBuffer writeBuffer = ByteBuffer.allocate(32);

	/**
	 * 处理读取客户端发来的信息 的事件
	 * 
	 * @param key
	 * @throws IOException
	 */
	public static void read(String cs, SelectionKey key) throws IOException {
		// 服务器可读取消息:得到事件发生的Socket通道
		SocketChannel channel = (SocketChannel) key.channel();
		channel.read(readBuffer);
		byte[] data = readBuffer.array();
		String msg = new String(data).trim();
		logger.debug(cs + "读取的信息为" + msg);
		writeBuffer = ByteBuffer.wrap((cs + "写回的信息为:" + msg).getBytes());
		channel.write(writeBuffer);// 将消息回送给客户端
	}
}
