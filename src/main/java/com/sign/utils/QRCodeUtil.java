package com.sign.utils;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.DecodeHintType;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.Result;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import sun.misc.BASE64Encoder;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

/**
 * 生成二维码
 *
 * @version 1.0
 * @description TODO
 * @date 2020/5/20 10:44
 */
public class QRCodeUtil {

    //二维码宽度，单位像素
    private static final int CODE_WIDTH = 400;
    //二维码高度，单位像素
    private static final int CODE_HEIGHT = 400;
    //二维码图片格式
    private static final String FORMAT = "jpg";
    //编码格式
    private static final String CHARSET = "UTF-8";
    //默认二维码文件夹路径
    private static final String DEFAULT_FILE_DIR = "E:/tmp/";

    //二维码参数
    private static Map<EncodeHintType, Object> hints = new HashMap();

    static {
        //设置字符编码类型
        hints.put(EncodeHintType.CHARACTER_SET, CHARSET);
        //设置纠错等级L/M/Q/H，纠错等级越高越不易识别，当前设置等级为最高等级H
        hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.M);
        //设置二维码边距，单位像素，值越小二维码距离四周越近
        hints.put(EncodeHintType.MARGIN, 1);
    }

    /**
     * 生成二维码，写入输出流（例如：response.getOutputStream()）
     *
     * @param codeContent 二维码内容
     * @param out         输出流
     */
    public static void createQROutput(String codeContent, OutputStream out) throws Exception {
        BitMatrix bitMatrix = new MultiFormatWriter().encode(codeContent, BarcodeFormat.QR_CODE, CODE_WIDTH, CODE_HEIGHT, hints);
        MatrixToImageWriter.writeToStream(bitMatrix, FORMAT, out);
    }
    //------------------------------解析二维码------------------------------

    /**
     * 解析二维码文件
     *
     * @param filePath 文件路径
     * @return
     */
    public static String parseQRFile(String filePath) throws Exception {
        File file = new File(filePath);
        if (file == null || !file.exists() || file.isDirectory()) {
            return null;
        }
        BufferedImage bufferedImage = ImageIO.read(file);
        BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(new BufferedImageLuminanceSource(bufferedImage)));
        Hashtable hints = new Hashtable();
        hints.put(DecodeHintType.CHARACTER_SET, CHARSET);
        Result result = new MultiFormatReader().decode(bitmap, hints);
        return result.getText();
    }

    /**
     * 创建二维码
     *
     * @param url           微信二维码地址
     * @param fileName      保存的文件名
     * @param fileDirectory 保存的文件地址
     * @return
     * @throws IOException
     */
    public static String createQRCode(String url, String fileDirectory, String fileName) throws IOException {
        int width = 500;
        int height = 500;
        String format = "png";
        Hashtable hints = new Hashtable();
        hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
        hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.M);
        hints.put(EncodeHintType.MARGIN, 2);
        try {
            BitMatrix bitMatrix = new MultiFormatWriter().encode(url, BarcodeFormat.QR_CODE, width, height, hints);

            //在D盘生成二维码图片
            Path file = new File(fileDirectory, fileName + ".png").toPath();
            MatrixToImageWriter.writeToPath(bitMatrix, format, file);
            BufferedImage image = MatrixToImageWriter.toBufferedImage(bitMatrix);
            ByteArrayOutputStream os = new ByteArrayOutputStream();//新建流。
            ImageIO.write(image, format, os);//利用ImageIO类提供的write方法，将bi以png图片的数据模式写入流。
            byte b[] = os.toByteArray();//从流中获取数据数组。
            String str = new BASE64Encoder().encode(b);
            IOUtils.closeQuietly(os);
            return str;
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            //DeleteFileUtil.delete(fileDirectory);
        }
        return "NULL";
    }

}