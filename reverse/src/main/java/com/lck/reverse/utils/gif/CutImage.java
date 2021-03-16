package com.lck.reverse.utils.gif;

import com.madgag.gif.fmsware.AnimatedGifEncoder;
import com.madgag.gif.fmsware.GifDecoder;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.ImageWriter;
import javax.imageio.stream.ImageInputStream;
import javax.imageio.stream.ImageOutputStream;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Iterator;

public class CutImage {

    /*************************  基于三方包解决方案    *****************************/
    /**
     * 剪切图片
     *
     * @param targetPath    裁剪后保存路径（默认为源路径）
     * @param x                起始横坐标
     * @param y                起始纵坐标
     * @param width            剪切宽度
     * @param height        剪切高度
     *
     * @returns            裁剪后保存路径（图片后缀根据图片本身类型生成）
     * @throws IOException
     */
    public static String cutImage(String sourcePath , String targetPath , int x , int y , int width , int height) throws IOException{
        File file = new File(sourcePath);
        if(!file.exists()) {
            throw new IOException("not found the image：" + sourcePath);
        }
        if(null == targetPath || targetPath.isEmpty()) targetPath = sourcePath;

        String formatName = getImageFormatName(file);
        if(null == formatName) return targetPath;
        formatName = formatName.toLowerCase();

        // 防止图片后缀与图片本身类型不一致的情况
        String pathPrefix = getPathWithoutSuffix(targetPath);
        targetPath = pathPrefix + formatName;

        // GIF需要特殊处理
        if(IMAGE_FORMAT.GIF.getValue() == formatName){
            GifDecoder decoder = new GifDecoder();
            int status = decoder.read(sourcePath);
            if (status != GifDecoder.STATUS_OK) {
                throw new IOException("read image " + sourcePath + " error!");
            }

            AnimatedGifEncoder encoder = new AnimatedGifEncoder();
            encoder.start(targetPath);
            encoder.setRepeat(decoder.getLoopCount());
            for (int i = 0; i < decoder.getFrameCount(); i ++) {
                encoder.setDelay(decoder.getDelay(i));
                BufferedImage childImage = decoder.getFrame(i);
                BufferedImage image = childImage.getSubimage(x, y, width, height);
                encoder.addFrame(image);
            }
            encoder.finish();
        }else{
            BufferedImage image = ImageIO.read(file);
            image = image.getSubimage(x, y, width, height);
            ImageIO.write(image, formatName, new File(targetPath));
        }
        //普通图片
        BufferedImage image = ImageIO.read(file);
        image = image.getSubimage(x, y, width, height);
        ImageIO.write(image, formatName, new File(targetPath));

        return targetPath;
    }
    /**
     * 获取不包含后缀的文件路径
     *
     * @param src
     * @return
     */
    public static String getPathWithoutSuffix(String src){
        String path = src;
        int index = path.lastIndexOf(".");
        if(index > 0){
            path = path.substring(0, index + 1);
        }
        return path;
    }
    /**
     * 获取图片格式
     * @param file   图片文件
     * @return    图片格式
     */
    public static String getImageFormatName(File file)throws IOException{
        String formatName = null;

        ImageInputStream iis = ImageIO.createImageInputStream(file);
        Iterator<ImageReader> imageReader =  ImageIO.getImageReaders(iis);
        if(imageReader.hasNext()){
            ImageReader reader = imageReader.next();
            formatName = reader.getFormatName();
        }

        return formatName;
    }

    public enum IMAGE_FORMAT{
        BMP("bmp"),
        JPG("jpg"),
        WBMP("wbmp"),
        JPEG("jpeg"),
        PNG("png"),
        GIF("gif");

        private String value;
        IMAGE_FORMAT(String value){
            this.value = value;
        }
        public String getValue() {
            return value;
        }
        public void setValue(String value) {
            this.value = value;
        }
    }
    /**
     * 获取某个文件的前缀路径
     *
     * 不包含文件名的路径
     *
     * @param path   当前文件路径
     * @return       不包含文件名的路径
     * @throws Exception
     */
    public static String getFilePrefixPath(String path) throws Exception{
        if(null == path || path.isEmpty()) throw new Exception("文件路径为空！");
        int index = path.lastIndexOf(File.separator);
        if(index > 0){
            path = path.substring(0, index + 1);
        }
        return path;
    }
    /**
     * 获取某个文件的前缀路径
     *
     * 不包含文件名的路径
     *
     * @param file   当前文件对象
     * @return
     * @throws IOException
     */
    public static String getFilePrefixPath(File file) throws IOException{
        String path = null;
        if(!file.exists()) {
            throw new IOException("not found the file !" );
        }
        String fileName = file.getName();
        path = file.getPath().replace(fileName, "");
        return path;
    }
    public static void main(String[] args) throws Exception {
        // 起始坐标，剪切大小
        int x = 30;
        int y = 75;
        int width = 260;
        int height = 120;
        // 参考图像大小
        int clientWidth = 300;
        int clientHeight = 250;


        File file = new File("E:\\sisteree.gif");
        BufferedImage image = ImageIO.read(file);
        double destWidth = image.getWidth();
        double destHeight = image.getHeight();

        if(destWidth < width || destHeight < height)
            throw new Exception("源图大小小于截取图片大小!");

        double widthRatio = destWidth / clientWidth;
        double heightRatio = destHeight / clientHeight;

        x = Double.valueOf(x * widthRatio).intValue();
        y = Double.valueOf(y * heightRatio).intValue();
        width = Double.valueOf(width * widthRatio).intValue();
        height = Double.valueOf(height * heightRatio).intValue();

        System.out.println("裁剪大小  x:" + x + ",y:" + y + ",width:" + width + ",height:" + height);

        /************************ 基于三方包解决方案 *************************/
        String formatName = getImageFormatName(file);
        String pathSuffix = "." + formatName;
        String pathPrefix = getFilePrefixPath(file);
        String targetPath = pathPrefix  + System.currentTimeMillis() + pathSuffix;
//         CutImage.cutImage(file.getPath(), targetPath, x , y , width, height);
        File destFile = new File(targetPath);
        CutImage.cutImage(file, destFile, x, y, width, height);
    }
    public static void cutImage(File sourceFile , File destFile, int x , int y , int width , int height) throws Exception{
        // 读取图片信息
        BufferedImage[] images = readerImage(sourceFile);
        // 处理图片
        images = processImage(images, x, y, width, height);
        // 获取文件后缀
        String formatName = getImageFormatName(sourceFile);
        destFile = new File(getPathWithoutSuffix(destFile.getPath()) + formatName);

        // 写入处理后的图片到文件
        writerImage(images, formatName , destFile);
    }
    /**
     * 读取图片
     * @param file 图片文件
     * @return     图片数据
     * @throws IOException
     */
    public static BufferedImage[] readerImage(File file) throws IOException{
        BufferedImage sourceImage = ImageIO.read(file);
        BufferedImage[] images = null;
        ImageInputStream iis = ImageIO.createImageInputStream(file);
        Iterator<ImageReader> imageReaders = ImageIO.getImageReaders(iis);
        if(imageReaders.hasNext()){
            ImageReader reader = imageReaders.next();
            reader.setInput(iis);
            int imageNumber = reader.getNumImages(true);
            images = new BufferedImage[imageNumber];
            for (int i = 0; i < imageNumber; i++) {
                BufferedImage image = reader.read(i);
                if(sourceImage.getWidth() > image.getWidth() || sourceImage.getHeight() > image.getHeight()){
                    image = zoom(image, sourceImage.getWidth(), sourceImage.getHeight());
                }
                images[i] = image;
            }
            reader.dispose();
            iis.close();
        }
        return images;
    }

    /**
     * 根据要求处理图片
     *
     * @param images    图片数组
     * @param x            横向起始位置
     * @param y         纵向起始位置
     * @param width      宽度
     * @param height    宽度
     * @return            处理后的图片数组
     * @throws Exception
     */
    public static BufferedImage[] processImage(BufferedImage[] images , int x , int y , int width , int height) throws Exception{
        if(null == images){
            return images;
        }
        BufferedImage[] oldImages = images;
        images = new BufferedImage[images.length];
        for (int i = 0; i < oldImages.length; i++) {
            BufferedImage image = oldImages[i];
            images[i] = image.getSubimage(x, y, width, height);
        }
        return images;
    }

    /**
     * 压缩图片
     * @param sourceImage    待压缩图片
     * @param width          压缩图片高度
     */
    private static BufferedImage zoom(BufferedImage sourceImage , int width , int height){
        BufferedImage zoomImage = new BufferedImage(width, height, sourceImage.getType());
        Image image = sourceImage.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        Graphics gc = zoomImage.getGraphics();
        gc.setColor(Color.WHITE);
        gc.drawImage( image , 0, 0, null);
        return zoomImage;
    }
    /**
     * 写入处理后的图片到file
     *
     * 图片后缀根据图片格式生成
     *
     * @param images        处理后的图片数据
     * @param formatName     图片格式
     * @param file            写入文件对象
     * @throws Exception
     */
    public static void writerImage(BufferedImage[] images ,  String formatName , File file) throws Exception{
        Iterator<ImageWriter> imageWriters = ImageIO.getImageWritersByFormatName(formatName);
        if(imageWriters.hasNext()){
            ImageWriter writer = imageWriters.next();
            String fileName = file.getName();
            int index = fileName.lastIndexOf(".");
            if(index > 0){
                fileName = fileName.substring(0, index + 1) + formatName;
            }
            String pathPrefix = getFilePrefixPath(file.getPath());
            File outFile = new File(pathPrefix + fileName);
            ImageOutputStream ios = ImageIO.createImageOutputStream(outFile);
            writer.setOutput(ios);

            if(writer.canWriteSequence()){
                writer.prepareWriteSequence(null);
                for (int i = 0; i < images.length; i++) {
                    BufferedImage childImage = images[i];
                    IIOImage image = new IIOImage(childImage, null , null);
                    writer.writeToSequence(image, null);
                }
                writer.endWriteSequence();
            }else{
                for (int i = 0; i < images.length; i++) {
                    writer.write(images[i]);
                }
            }

            writer.dispose();
            ios.close();
        }
    }
}
