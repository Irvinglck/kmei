package com.lck.reverse.controllor;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.draw.DottedLineSeparator;
import com.itextpdf.text.pdf.draw.LineSeparator;
import com.lck.reverse.commons.COSClientConfig;
import com.lck.reverse.entity.respon.ResultMessage;
import com.lck.reverse.utils.createpdf.CreatePDFFile;
import com.lck.reverse.utils.createpdf.MyHeaderFooter;
import com.lck.reverse.utils.createpdf.Watermark;
import com.qcloud.cos.COSClient;
import com.qcloud.cos.model.Bucket;
import com.qcloud.cos.model.ObjectMetadata;
import com.qcloud.cos.model.PutObjectRequest;
import com.qcloud.cos.model.PutObjectResult;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.codehaus.plexus.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/wx")
@Slf4j
public class WxControllor {
    //产品页图片
    private static final String DIR_IMG = "PRODUCT";
    // 指定要上传到的存储桶
    private static final String BUCKET_NAME = "km-wx-1304476764";

    // 定义全局的字体静态变量
    private static Font titlefont;
    private static Font headfont;
    private static Font keyfont;
    private static Font textfont;
    // 最大宽度
    private static int maxWidth = 520;

    // 静态代码块
    static {
        try {
            // 不同字体（这里定义为同一种字体：包含不同字号、不同style）
            BaseFont bfChinese = BaseFont.createFont("STSong-Light", "UniGB-UCS2-H", BaseFont.NOT_EMBEDDED);
            titlefont = new Font(bfChinese, 16, Font.BOLD);
            headfont = new Font(bfChinese, 14, Font.BOLD);
            keyfont = new Font(bfChinese, 10, Font.BOLD);
            textfont = new Font(bfChinese, 10, Font.NORMAL);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Autowired
    private COSClientConfig COSClientConfig;


    @GetMapping("/uploadFile")
    public ResultMessage uploadFile(
            @RequestParam(name = "imgFile") MultipartFile imgFile,
            @RequestParam(name = "path") String path
    ) {

        COSClient cosClient = COSClientConfig.getCOSClient();
        String key = DIR_IMG + "/" + path + "/" + imgFile.getOriginalFilename();
        ObjectMetadata objectMetadata = new ObjectMetadata();
        PutObjectRequest putObjectRequest = null;
        PutObjectResult putObjectResult = null;
        try {
            putObjectRequest = new PutObjectRequest(BUCKET_NAME, key, imgFile.getInputStream(), objectMetadata);
            putObjectResult = cosClient.putObject(putObjectRequest);
            return ResultMessage.getDefaultResultMessage(200, putObjectResult.getMetadata().getUserMetadata());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 下载pdf
     *
     * @param productId
     * @return
     */
    @GetMapping("/downProPdf")
    public ResultMessage downProPdf(
            @RequestParam(name = "productId") String productId
    ) throws IOException, BadElementException {
        List<String> prosUrl = Arrays.asList(
                "https://km-wx-1304476764.cos.ap-nanjing.myqcloud.com/PRODUCT/print/product1.jpg"
                , "https://km-wx-1304476764.cos.ap-nanjing.myqcloud.com/PRODUCT/print/product2.jpg"
                , "https://km-wx-1304476764.cos.ap-nanjing.myqcloud.com/PRODUCT/print/product3.jpg");

        List<Image> images = new ArrayList<>();
        int i = 1;
        for (String strs : prosUrl) {
            String productName = "product" + (++i);
            downloadHttpResource(strs, productName, "D:/Download1");


            images.add(Image.getInstance("D:/Download1/" + productName+".jpg"));
        }

        try {
            // 1.新建document对象
            Document document = new Document(PageSize.A4);// 建立一个Document对象

            // 2.建立一个书写器(Writer)与document对象关联
            File file = new File("D:\\printMachine.pdf");
            file.createNewFile();
            PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(file));
            writer.setPageEvent(new Watermark("HELLO ITEXTPDF"));// 水印
            writer.setPageEvent(new MyHeaderFooter());// 页眉/页脚

            // 3.打开文档
            document.open();
            document.addTitle("Title@PDF-Java");// 标题
            document.addAuthor("Author@umiz");// 作者
            document.addSubject("Subject@iText pdf sample");// 主题
            document.addKeywords("Keywords@iTextpdf");// 关键字
            document.addCreator("Creator@umiz`s");// 创建者

            // 4.向文档中添加内容
            new CreatePDFFile().generatePDF(document, images);

            // 5.关闭文档
            document.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }





    /**------------------------创建表格单元格的方法start----------------------------*/
    /**
     * 创建单元格(指定字体)
     *
     * @param value
     * @param font
     * @return
     */
    public PdfPCell createCell(String value, Font font) {
        PdfPCell cell = new PdfPCell();
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setPhrase(new Phrase(value, font));
        return cell;
    }

    /**
     * 创建指定列宽、列数的表格
     *
     * @param widths
     * @return
     */
    public PdfPTable createTable(float[] widths) {
        PdfPTable table = new PdfPTable(widths);
        try {
            table.setTotalWidth(maxWidth);
            table.setLockedWidth(true);
            table.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.getDefaultCell().setBorder(1);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return table;
    }

    /**
     * 创建单元格（指定字体、水平..）
     *
     * @param value
     * @param font
     * @param align
     * @return
     */
    public PdfPCell createCell(String value, Font font, int align) {
        PdfPCell cell = new PdfPCell();
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setHorizontalAlignment(align);
        cell.setPhrase(new Phrase(value, font));
        return cell;
    }

    /**
     * 创建单元格（指定字体、水平居..、单元格跨x列合并、设置单元格内边距）
     *
     * @param value
     * @param font
     * @param align
     * @param colspan
     * @param boderFlag
     * @return
     */
    public PdfPCell createCell(String value, Font font, int align, int colspan, boolean boderFlag) {
        PdfPCell cell = new PdfPCell();
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setHorizontalAlignment(align);
        cell.setColspan(colspan);
        cell.setPhrase(new Phrase(value, font));
        cell.setPadding(3.0f);
        if (!boderFlag) {
            cell.setBorder(0);
            cell.setPaddingTop(15.0f);
            cell.setPaddingBottom(8.0f);
        } else if (boderFlag) {
            cell.setBorder(0);
            cell.setPaddingTop(0.0f);
            cell.setPaddingBottom(15.0f);
        }
        return cell;
    }
    /**--------------------------创建表格的方法start------------------- ---------*/
    /**
     * 创建默认列宽，指定列数、水平(居中、右、左)的表格
     *
     * @param colNumber
     * @param align
     * @return
     */
    public PdfPTable createTable(int colNumber, int align) {
        PdfPTable table = new PdfPTable(colNumber);
        try {
            table.setTotalWidth(maxWidth);
            table.setLockedWidth(true);
            table.setHorizontalAlignment(align);
            table.getDefaultCell().setBorder(1);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return table;
    }

    /**
     * 使用第三方jar包 org.apache.commons.io.FileUtils 简捷地下载网络文件
     *
     * @param urlStr   资源URL
     * @param dir      存储目录
     * @param fileName 存储文件名
     * @return
     */
    public void downloadHttpResource(String urlStr, String fileName, String dir) {
        try {
            URL httpUrl = new URL(urlStr);
            fileName = getFileName(httpUrl, fileName);
            File dirFile = new File(dir);
            if (!dirFile.exists()) {
                dirFile.mkdirs();
            }
            FileUtils.copyURLToFile(httpUrl, new File(dir + File.separator + fileName));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }






    private String getFileName(URL url, String fileName) {
        if (StringUtils.isBlank(fileName)) {
            fileName = Long.toString(System.currentTimeMillis());
        }
        String urlFileName = url.getFile();
        log.info("网络资源原始名称：{}", urlFileName);
        if (StringUtils.isNotEmpty(urlFileName)) {
            String subfix = urlFileName.substring(urlFileName.lastIndexOf("."));
            fileName = fileName + subfix;
        }
        return fileName;
    }

    //创建文件夹
    private String createDirs() {
        COSClient cosClient = COSClientConfig.getCOSClient();
        List<Bucket> buckets = cosClient.listBuckets();
//        for (Bucket bucketElement : buckets) {
//            String bucketName = bucketElement.getName();
//            String bucketLocation = bucketElement.getLocation();
//            System.out.println("bucketName == "+bucketName);
//            System.out.println("bucketLocation == "+bucketLocation);
//        }
//        String bucketName1 = bucketName;
//        String key1 = "folder/images/";
//        // 目录对象即是一个/结尾的空文件，上传一个长度为 0 的 byte 流
//        InputStream input = new ByteArrayInputStream(new byte[0]);
//        ObjectMetadata objectMetadata = new ObjectMetadata();
//        objectMetadata.setContentLength(0);
//        PutObjectRequest putObjectRequest1 =
//                new PutObjectRequest(bucketName1, key, input, objectMetadata);
//        PutObjectResult putObjectResult1 = cosClient.putObject(putObjectRequest);
//    }
        return null;
    }
}
