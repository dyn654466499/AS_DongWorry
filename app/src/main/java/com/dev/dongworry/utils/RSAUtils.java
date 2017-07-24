package com.dev.dongworry.utils;

import android.util.Base64;
import android.util.Log;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.ArrayList;
import java.util.List;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

public class RSAUtils {

	/**
	 * @param publicKeyFilePath
	 * @param privateKeyFilePath
	 */
	public RSAUtils(String publicKeyFilePath, String privateKeyFilePath)
			throws Exception {
		String public_key = getKeyFromFile(publicKeyFilePath);
		String private_key = getKeyFromFile(privateKeyFilePath);
		loadPublicKey(public_key);
		loadPrivateKey(private_key);
	}

	public RSAUtils() {
		// load the PublicKey and PrivateKey manually
	}

	public String getKeyFromFile(String filePath) throws Exception {
		BufferedReader bufferedReader = new BufferedReader(new FileReader(
				filePath));

		String line = null;
		List<String> list = new ArrayList<String>();
		while ((line = bufferedReader.readLine()) != null) {
			list.add(line);
		}

		// remove the firt line and last line
		StringBuilder stringBuilder = new StringBuilder();
		for (int i = 1; i < list.size() - 1; i++) {
			stringBuilder.append(list.get(i)).append("\r");
		}

		String key = stringBuilder.toString();
		return key;
	}

	public static final String privateKeyStr = "";

	public static byte[] base64decode(String source) {
		return Base64.decode(source.getBytes(), Base64.DEFAULT);
	}

	public static String base64encode(byte[] baseData) {
		return new String(Base64.encode(baseData, Base64.DEFAULT));
	}

	public static final String publicKeyStr = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDP8Ex5pyeHH3j2fWXOhWNsYqrn/5H2V47xK1eJrM59tqA8byi0lrhUveClq7PSzqKbE0scvsDv/xTYUlhOQ0eseREchxcHRpy084CHDeK0e4/LG9K9IFYQ2DT8jhQ4WXYXbzF+wDlUSIzM5HkDh2Xx+eaGspt2EZep0kKb+Xv+QwIDAQAB";

	// convenient properties
	public static RSAUtils sharedInstance = null;

	public static void setSharedInstance(RSAUtils rsaEncryptor) {
		sharedInstance = rsaEncryptor;
	}

	/**
	 * 私钥
	 */
	private static RSAPrivateKey privateKey;

	/**
	 * 公钥
	 */
	private static RSAPublicKey publicKey;

	/**
	 * 获取私钥
	 * 
	 * @return 当前的私钥对象
	 */
	public static RSAPrivateKey getPrivateKey() {
		return privateKey;
	}

	/**
	 * 获取公钥
	 * 
	 * @return 当前的公钥对象
	 */
	public static RSAPublicKey getPublicKey() {
		return publicKey;
	}

	/**
	 * 随机生成密钥对
	 */
	public void genKeyPair() {
		KeyPairGenerator keyPairGen = null;
		try {
			keyPairGen = KeyPairGenerator.getInstance("RSA");
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		keyPairGen.initialize(1024, new SecureRandom());
		KeyPair keyPair = keyPairGen.generateKeyPair();
		this.privateKey = (RSAPrivateKey) keyPair.getPrivate();
		this.publicKey = (RSAPublicKey) keyPair.getPublic();
	}

	/**
	 * 从文件中输入流中加载公钥
	 * 
	 * @param in
	 *            公钥输入流
	 * @throws Exception
	 *             加载公钥时产生的异常
	 */
	public void loadPublicKey(InputStream in) throws Exception {
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			String readLine = null;
			StringBuilder sb = new StringBuilder();
			while ((readLine = br.readLine()) != null) {
				if (readLine.charAt(0) == '-') {
					continue;
				} else {
					sb.append(readLine);
					sb.append('\r');
				}
			}
			loadPublicKey(sb.toString());
		} catch (IOException e) {
			throw new Exception("公钥数据流读取错误");
		} catch (NullPointerException e) {
			throw new Exception("公钥输入流为空");
		}
	}

	/**
	 * 从字符串中加载公钥
	 * 
	 * @param publicKeyStr
	 *            公钥数据字符串
	 * @throws Exception
	 *             加载公钥时产生的异常
	 */
	public static void loadPublicKey(String publicKeyStr) throws Exception {
		try {
			// java的用法
			// BASE64Decoder base64Decoder= new BASE64Decoder();
			// byte[] buffer= base64Decoder.decodeBuffer(publicKeyStr);
			// android的用法
			byte[] buffer = base64decode(publicKeyStr);
			KeyFactory keyFactory = KeyFactory.getInstance("RSA");
			X509EncodedKeySpec keySpec = new X509EncodedKeySpec(buffer);
			publicKey = (RSAPublicKey) keyFactory.generatePublic(keySpec);
		} catch (NoSuchAlgorithmException e) {
			throw new Exception("无此算法");
		} catch (InvalidKeySpecException e) {
			throw new Exception("公钥非法");
		} catch (NullPointerException e) {
			throw new Exception("公钥数据为空");
		}
	}

	/**
	 * 从文件中加载私钥
	 * 
	 * @return 是否成功
	 * @throws Exception
	 */
	public void loadPrivateKey(InputStream in) throws Exception {
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			String readLine = null;
			StringBuilder sb = new StringBuilder();
			while ((readLine = br.readLine()) != null) {
				if (readLine.charAt(0) == '-') {
					continue;
				} else {
					sb.append(readLine);
					sb.append('\r');
				}
			}
			loadPrivateKey(sb.toString());
		} catch (IOException e) {
			throw new Exception("私钥数据读取错误");
		} catch (NullPointerException e) {
			throw new Exception("私钥输入流为空");
		}
	}

	public static void loadPrivateKey(String privateKeyStr) throws Exception {
		try {

			byte[] buffer = base64decode(privateKeyStr);
			PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(buffer);
			KeyFactory keyFactory = KeyFactory.getInstance("RSA");
			privateKey = (RSAPrivateKey) keyFactory.generatePrivate(keySpec);
		} catch (NoSuchAlgorithmException e) {
			throw new Exception("无此算法");
		} catch (InvalidKeySpecException e) {
			e.printStackTrace();
			throw new Exception("私钥非法");
		} catch (NullPointerException e) {
			throw new Exception("私钥数据为空");
		}
	}

	/**
	 * 字节数据转字符串专用集合
	 */
	private static final char[] HEX_CHAR = { '0', '1', '2', '3', '4', '5', '6',
			'7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };

	/**
	 * 字节数据转十六进制字符串
	 * 
	 * @param data
	 *            输入数据
	 * @return 十六进制内容
	 */
	public static String byteArrayToString(byte[] data) {
		StringBuilder stringBuilder = new StringBuilder();
		for (int i = 0; i < data.length; i++) {
			// 取出字节的高四位 作为索引得到相应的十六进制标识符 注意无符号右移
			stringBuilder.append(HEX_CHAR[(data[i] & 0xf0) >>> 4]);
			// 取出字节的低四位 作为索引得到相应的十六进制标识符
			stringBuilder.append(HEX_CHAR[(data[i] & 0x0f)]);
			if (i < data.length - 1) {
				stringBuilder.append(' ');
			}
		}
		return stringBuilder.toString();
	}

	// 公钥加密=================
	public static String encryptAndToHex(String string) {
		String hexStr = null;
		try {
			loadPublicKey(publicKeyStr);
			byte[] binaryData = encrypt(getPublicKey(),
					string.getBytes("UTF-8"));
			hexStr = HexUtil.bytesToHex(binaryData);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return hexStr;
	}

	// 公钥解密======================
	public static String decryptHextString(String hexString) {
		String string = null;
		try {
			loadPublicKey(publicKeyStr);
			/* 联调的时候后台base64转码去掉 换成HexUtil.hexToBytes(base64String) */
			// byte[] binaryData = decrypt(getPublicKey(), HexUtil.hexToBytes(
			// base64String));
			byte[] binaryData = decrypt(getPublicKey(),
					HexUtil.hexToBytes(hexString));

			string = new String(binaryData, "UTF-8");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return string;
	}

	/**
	 * 公钥加密过程
	 * 
	 * @param publicKey 公钥
	 * @param plainTextData
	 *            明文数据
	 * @return
	 * @throws Exception
	 *             加密过程中的异常信息
	 */
	public static byte[] encrypt(RSAPublicKey publicKey, byte[] plainTextData)
			throws Exception {
		if (publicKey == null) {
			throw new Exception("加密公钥为空, 请设置");
		}
		Cipher cipher = null;
		try {
			cipher = Cipher.getInstance("RSA/None/PKCS1Padding");// , new
			// BouncyCastleProvider());
			cipher.init(Cipher.ENCRYPT_MODE, publicKey);
			byte[] output = cipher.doFinal(plainTextData);
			return output;
		} catch (NoSuchAlgorithmException e) {
			throw new Exception("无此加密算法");
		} catch (NoSuchPaddingException e) {
			e.printStackTrace();
			return null;
		} catch (InvalidKeyException e) {
			throw new Exception("加密公钥非法,请检查");
		} catch (IllegalBlockSizeException e) {
			throw new Exception("明文长度非法");
		} catch (BadPaddingException e) {
			throw new Exception("明文数据已损坏");
		}
	}

	/**
	 * 公钥解密过程
	 *
	 *            私钥
	 * @param cipherData
	 *            密文数据
	 * @return 明文
	 * @throws Exception
	 *             解密过程中的异常信息
	 */
	public static byte[] decrypt(RSAPublicKey publicKey, byte[] cipherData)
			throws Exception {
		if (publicKey == null) {
			throw new Exception("解密私钥为空, 请设置");
		}
		Cipher cipher = null;
		try {
			cipher = Cipher.getInstance("RSA/None/PKCS1Padding");// , new
			// BouncyCastleProvider());
			cipher.init(Cipher.DECRYPT_MODE, publicKey);
			byte[] output = cipher.doFinal(cipherData);
			return output;
		} catch (NoSuchAlgorithmException e) {
			throw new Exception("无此解密算法");
		} catch (NoSuchPaddingException e) {
			e.printStackTrace();
			return null;
		} catch (InvalidKeyException e) {
			throw new Exception("解密私钥非法,请检查");
		} catch (IllegalBlockSizeException e) {
			throw new Exception("密文长度非法");
		} catch (BadPaddingException e) {
			throw new Exception("密文数据已损坏");
		}
	}

	public static String decryPri(String hexStr) {
		return decryPri(hexStr, "UTF-8");
	}

	public static String decryPri(String hexStr, String charsetName) {
		String string = null;
		try {
			loadPrivateKey(privateKeyStr);

			byte[] binaryData = decryptPri(getPrivateKey(),
					HexUtil.hexToBytes(hexStr) /*
												 * org.apache.commons.codec.binary
												 * .Base64.
												 * decodeBase64(base46String
												 * .getBytes())
												 */);

			string = new String(binaryData, charsetName);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return string;
	}

	/**
	 * 私钥解密过程
	 *            私钥
	 * @param cipherData
	 *            密文数据
	 * @return 明文
	 * @throws Exception
	 *             解密过程中的异常信息
	 */
	public static byte[] decryptPri(RSAPrivateKey privateKey, byte[] cipherData)
			throws Exception {
		if (privateKey == null) {
			throw new Exception("解密私钥为空, 请设置");
		}
		Cipher cipher = null;
		try {
			cipher = Cipher.getInstance("RSA/None/PKCS1Padding");// , new
			// BouncyCastleProvider());
			cipher.init(Cipher.DECRYPT_MODE, privateKey);
			byte[] output = cipher.doFinal(cipherData);
			return output;
		} catch (NoSuchAlgorithmException e) {
			throw new Exception("无此解密算法");
		} catch (NoSuchPaddingException e) {
			e.printStackTrace();
			return null;
		} catch (InvalidKeyException e) {
			throw new Exception("解密私钥非法,请检查");
		} catch (IllegalBlockSizeException e) {
			throw new Exception("密文长度非法");
		} catch (BadPaddingException e) {
			throw new Exception("密文数据已损坏");
		}
	}
}