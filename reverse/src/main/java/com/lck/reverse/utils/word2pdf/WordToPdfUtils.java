//package com.lck.reverse.utils.word2pdf;
//
//
//import com.aspose.words.Document;
//import com.aspose.words.License;
//import com.aspose.words.SaveFormat;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.core.io.ClassPathResource;
//
//import java.io.File;
//import java.io.FileOutputStream;
//
//public class WordToPdfUtils {
//    private static Logger logger = LoggerFactory.getLogger(WordToPdfUtils.class);
//
//    public static void main(String[] args) {
//        doc2pdf("F:\\demo.doc", "F:\\demo1.pdf");
//    }
//
//    /**
//     * Word 转 PDF
//     *
//     * @param inPath  word文件地址
//     * @param outPath pdf文件地址
//     */
//    public static void doc2pdf(String inPath, String outPath) {
//        File wordFile = new File(inPath);
//        if (!wordFile.exists()) {
//            logger.info("源文件不存在:{}", inPath);
//            return;
//        }
//
//        // 验证License 若不验证则转化出的pdf文档会有水印产生
//        if (!getLicense()) {
//            return;
//        }
//        try {
//            logger.info("PDF转换开始");
//            //开始时间
//            long old = System.currentTimeMillis();
//            //获取文件
//            File file = new File(outPath);
//            //获取文件流
//            FileOutputStream os = new FileOutputStream(file);
//            // Address是将要被转化的word文档
//            Document doc = new Document(inPath);
//            // 全面支持DOC, DOCX, OOXML, RTF HTML, OpenDocument, PDF
//            doc.save(os, SaveFormat.PDF);
//            //结束时间
//            long now = System.currentTimeMillis();
//            logger.info("PDF转换结束 共耗时：" + ((now - old) / 1000.0) + "秒");
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    /**
//     * 去除水印
//     *
//     * @return
//     */
//    public static boolean getLicense() {
//        boolean result = false;
//        try {
//            // license.xml应放在..\WebRoot\WEB-INF\classes路径下
//            ClassPathResource classPathResource = new ClassPathResource("license.xml");
//
////            InputStream is = WordToPdfUtils.class.getClassLoader().getResourceAsStream("license.xml");
//            License aposeLic = new License();
//            aposeLic.setLicense(classPathResource.getInputStream());
//            result = true;
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return result;
//    }
//}
