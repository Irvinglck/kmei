//package com.lck.reverse.utils.gif;
//
//import com.madgag.gif.fmsware.AnimatedGifEncoder;
//import lombok.extern.slf4j.Slf4j;
//import org.bytedeco.javacv.FFmpegFrameGrabber;
//import org.bytedeco.javacv.Java2DFrameConverter;
//
//import java.io.FileOutputStream;
//import java.io.IOException;
//
//@Slf4j
//public class GifUtil {
//
//    /**
//     * 默认每截取一次跳过多少帧（默认：3）
//     */
//    private static final Integer DEFAULT_MARGIN = 2;
//    /**
//     * 默认帧频率（默认：3）
//     */
//    private static final Integer DEFAULT_FRAME_RATE = 10;
//
//    /**
//     * 截取视频指定帧生成gif,存储路径同级下
//     *
//     *            视频文件
//     * @param startFrame
//     *            开始帧
//     * @param frameCount
//     *            截取帧数
//     * @param frameRate
//     *            帧频率（默认：3）
//     * @param margin
//     *            每截取一次跳过多少帧（默认：3）
//     * @throws IOException
//     *
//     */
//    public static String buildGif(String filePath, int startFrame, int frameCount, Integer frameRate, Integer margin)
//            throws IOException {
//        if (margin == null) {
//            margin = DEFAULT_MARGIN;
//        }
//        if (frameRate == null) {
//            frameRate = DEFAULT_FRAME_RATE;
//        }
//        // gif存储路径
//        String gifPath = filePath.substring(0, filePath.lastIndexOf(".")) + ".gif";
//        // 输出文件流
//        FileOutputStream targetFile = new FileOutputStream(gifPath);
//        // 读取文件
//        FFmpegFrameGrabber ff = new FFmpegFrameGrabber(filePath);
//        Java2DFrameConverter converter = new Java2DFrameConverter();
//        ff.start();
//        try {
//            Integer videoLength = ff.getLengthInFrames();
//            // 如果用户上传视频极短,不符合自己定义的帧数取值区间,那么获取从1/5处开始至1/2处结束生成gif
//            if (startFrame > videoLength || (startFrame + frameCount) > videoLength) {
//                startFrame = videoLength / 5;
//                frameCount = videoLength / 2;
//            }
//            ff.setFrameNumber(startFrame);
//            AnimatedGifEncoder en = new AnimatedGifEncoder();
//            en.setFrameRate(frameRate);
//            en.start(targetFile);
//            for (int i = 0; i < frameCount; i++) {
//                en.addFrame(converter.convert(ff.grabFrame()));
//                ff.setFrameNumber(ff.getFrameNumber() + margin);
//            }
//            en.finish();
//        } finally {
//            ff.stop();
//            ff.close();
//        }
//        log.info("返回文件路径");
//        return gifPath;
//    }
//
//    public static void main(String[] args) {
//        try {
//            System.out.println(buildGif("D:\\sisteree.mp4", 5, 50, 6, 2));
//        } catch (Exception e) {
//            e.printStackTrace();
//            // TODO: handle exception
//        }
//    }
//}
