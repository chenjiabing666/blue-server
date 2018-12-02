package com.techwells.blue.util.qrcode;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

import javax.imageio.ImageIO;


import com.google.zxing.BarcodeFormat;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.EncodeHintType;
import com.google.zxing.LuminanceSource;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.ReaderException;
import com.google.zxing.Result;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.QRCodeReader;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

/**
 * 二维码生成和读的工具类
 */
public class QrCodeUtil {
	/*
	 * 定义二维码的宽高
	 */
	private static int WIDTH = 300;
	private static int HEIGHT = 300;
	private static String FORMAT = "png";// 二维码格式

	/**
	 * 创建二维码
	 * @param content  二维码的内容
	 * @param file     存放的路径的File
  	 * @param width   宽
	 * @param height  高
	 * @param format  图片的格式，比如JPEG，png
	 * @throws Exception
	 */
	public static void createZxingqrCode(String content, File file, int width,
			int height, String format) throws Exception {
		// 定义二维码参数
		Map hints = new HashMap();
		hints.put(EncodeHintType.CHARACTER_SET, "utf-8");// 设置编码
		hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.M);// 设置容错等级
		hints.put(EncodeHintType.MARGIN, 5);// 设置边距默认是5

		BitMatrix bitMatrix = new MultiFormatWriter().encode(content,
				BarcodeFormat.QR_CODE, width, height, hints);
		Path path = file.toPath();
		MatrixToImageWriter.writeToPath(bitMatrix, format, path);// 写到指定路径下
	}

	// 读取二维码
	public static void readZxingQrCode() {
		MultiFormatReader reader = new MultiFormatReader();
		File file = new File("E:\\qr.png");
		try {
			BufferedImage image = ImageIO.read(file);
			BinaryBitmap binaryBitmap = new BinaryBitmap(new HybridBinarizer(
					new BufferedImageLuminanceSource(image)));
			Map hints = new HashMap();
			hints.put(EncodeHintType.CHARACTER_SET, "utf-8");// 设置编码
			Result result = reader.decode(binaryBitmap, hints);
			System.out.println("解析结果:" + result.toString());
			System.out.println("二维码格式:" + result.getBarcodeFormat());
			System.out.println("二维码文本内容:" + result.getText());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void main(String[] args) throws Exception {
		createZxingqrCode("weixin://wxpay/bizpayurl?pr=9jn5R0q", new File("C:\\images\\20.png"), 200, 200, "png");
	}

}