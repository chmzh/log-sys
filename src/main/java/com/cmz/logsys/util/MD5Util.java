package com.cmz.logsys.util;

import org.apache.commons.codec.digest.DigestUtils;

public class MD5Util {
	/**
	 * 计算出MD5串
	 * @param data
	 * @return
	 */
	public final static String getMD5(String data) {
		return DigestUtils.md5Hex(data);
	}
	
	/**
	 * 计算出MD5串
	 * @param data
	 * @return
	 */
	public final static String getMD5(byte[] data) {
		return DigestUtils.md5Hex(data);
	}
	
}
