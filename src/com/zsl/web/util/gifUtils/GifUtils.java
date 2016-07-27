package com.zsl.web.util.gifUtils;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.zsl.web.util.ImageUtils;

public class GifUtils {
 
 	/**
	 * 等比例图片缩放
	 * 
	 * @param srcFile
	 *            源文件
	 * @param destFile
	 *            目标文件
	 * @param destWidth
	 *            目标宽度
	 * @param destHeight
	 *            目标高度
	 * @param dest_quality
	 * 			  图片质量（0-100）
	 */
	public static boolean zoom(File srcFile, File destFile, int destWidth, int destHeight, int dest_quality) {
		boolean issuccee = false;
		try {
			// 构建目录结构
			File pic[] = splitGif(srcFile);
			File destPic[] = new File[pic.length];
			for (int i = 0; i < pic.length; i++) {
				destPic[i] = new File(System.getProperty("java.io.tmpdir") + "\\" + String.valueOf(i) + "_zip.jpg");
				ImageUtils.zoom(pic[i], destPic[i], destWidth, destHeight, dest_quality);
			}
			jpgToGif(destPic, destFile);
			for(File f : pic){
				if(f.isFile() && f.exists()){
					f.delete();
				}
			}
			for(File f : destPic){
				if(f.isFile() && f.exists()){
					f.delete();
				}
			}
			issuccee = true;
		} catch (Exception e) {
			e.printStackTrace();
			return issuccee;
		}
		return issuccee;
	}
	
	/**
	 * gif分解为多个图片
	 * @param srcFile
	 * @return
	 */
	public synchronized static File[] splitGif(File srcFile) {
		try {
			GifDecoder decoder = new GifDecoder();
			decoder.read(srcFile);
			int n = decoder.getFrameCount(); // 得到frame的个数
			File[] subPic = new File[n];
			for (int i = 0; i < n; i++) {
				BufferedImage frame = decoder.getFrame(i); // 得到帧
				subPic[i] = new File(System.getProperty("java.io.tmpdir") + "\\" + String.valueOf(i) + ".jpg");
				if (!subPic[i].getParentFile().exists()) {
					subPic[i].getParentFile().mkdirs();
				}
				FileOutputStream out = new FileOutputStream(subPic[i]);
				ImageIO.write(frame, "jpeg", out);
				//JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
				//encoder.encode(frame); // 存盘
				out.flush();
				out.close();
			}
			return subPic;
		} catch (Exception e) {
			System.out.println("splitGif Failed!");
			e.printStackTrace();
			return null;
		}
	}
	
	/*
	 * jpgToGif
	 * *********************************************
	 */
	public synchronized static void jpgToGif(File pic[],
			File outFile) {
		try {
			AnimatedGifEncoder e = new AnimatedGifEncoder(); 
			e.setRepeat(0);
			e.start(outFile);
			BufferedImage src[] = new BufferedImage[pic.length];
			for (int i = 0; i < src.length; i++) {
				e.setDelay(200); // 设置播放的延迟时间
				src[i] = ImageIO.read(pic[i]); // 读入需要播放的jpg文件
				e.addFrame(src[i]); // 添加到帧中
			}
			e.finish();//刷新任何未决的数据，并关闭输出文件
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	public static void main(String[] args) throws IOException {
		File srcFile = new File("F:/1.gif");
		BufferedImage src = ImageIO.read(srcFile);
		int old_w = src.getWidth(); 
		int old_h = src.getHeight(); 
		zoom(srcFile, new File("F:/2.gif"), old_w, old_h, 50);
	}
}
