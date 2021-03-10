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
import com.qcloud.cos.ClientConfig;
import com.qcloud.cos.auth.BasicCOSCredentials;
import com.qcloud.cos.auth.COSCredentials;
import com.qcloud.cos.model.Bucket;
import com.qcloud.cos.model.ObjectMetadata;
import com.qcloud.cos.model.PutObjectRequest;
import com.qcloud.cos.model.PutObjectResult;
import com.qcloud.cos.region.Region;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.bouncycastle.asn1.cms.MetaData;
import org.codehaus.plexus.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.annotation.MultipartConfig;
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
            return ResultMessage.getDefaultResultMessage(200,putObjectResult.getMetadata().getUserMetadata());
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
            @RequestParam(name="productId") String productId
    ) throws IOException, BadElementException {
        List<String> prosUrl= Arrays.asList(
                "https://km-wx-1304476764.cos.ap-nanjing.myqcloud.com/PRODUCT/print/product1.jpg"
                ,"https://km-wx-1304476764.cos.ap-nanjing.myqcloud.com/PRODUCT/print/product2.jpg"
                ,"https://km-wx-1304476764.cos.ap-nanjing.myqcloud.com/PRODUCT/print/product3.jpg");

        List<Image> images=new ArrayList<>();
        for(String strs: prosUrl){
            InputStream fileSource = getFileSource(strs, null);
            byte[] bytes = inputConvetByte(fileSource);
            images.add(Image.getInstance(bytes));
        }

        try {
            // 1.新建document对象
            Document document = new Document(PageSize.A4);// 建立一个Document对象

            // 2.建立一个书写器(Writer)与document对象关联
            File file = new File("F:\\PDFDemoimg.pdf");
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
            new CreatePDFFile().generatePDF(document,images);

            // 5.关闭文档
            document.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    // main测试
//    public static void main(String[] args) throws Exception {
//        try {
//            // 1.新建document对象
//            Document document = new Document(PageSize.A4);// 建立一个Document对象
//
//            // 2.建立一个书写器(Writer)与document对象关联
//            File file = new File("F:\\PDFDemoimg.pdf");
//            file.createNewFile();
//            PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(file));
//            writer.setPageEvent(new Watermark("HELLO ITEXTPDF"));// 水印
//            writer.setPageEvent(new MyHeaderFooter());// 页眉/页脚
//
//            // 3.打开文档
//            document.open();
//            document.addTitle("Title@PDF-Java");// 标题
//            document.addAuthor("Author@umiz");// 作者
//            document.addSubject("Subject@iText pdf sample");// 主题
//            document.addKeywords("Keywords@iTextpdf");// 关键字
//            document.addCreator("Creator@umiz`s");// 创建者
//
//            // 4.向文档中添加内容
//            new CreatePDFFile().generatePDF(document);
//
//            // 5.关闭文档
//            document.close();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }

    //字节流转成byte[] 数组
    private byte [] inputConvetByte(InputStream  ins){
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024*4];
        int n = 0;
        while (true) {
            try {
                if (!(-1 != (n = ins.read(buffer)))) break;
            } catch (IOException e) {
                e.printStackTrace();
            }
            output.write(buffer, 0, n);
        }
        return output.toByteArray();
    }

    // 生成PDF文件
    public void generatePDF(Document document) throws Exception {

        // 段落
        Paragraph paragraph = new Paragraph("美好的一天从早起开始！", titlefont);
        paragraph.setAlignment(1); //设置文字居中 0靠左   1，居中     2，靠右
        paragraph.setIndentationLeft(12); //设置左缩进
        paragraph.setIndentationRight(12); //设置右缩进
        paragraph.setFirstLineIndent(24); //设置首行缩进
        paragraph.setLeading(20f); //行间距
        paragraph.setSpacingBefore(5f); //设置段落上空白
        paragraph.setSpacingAfter(10f); //设置段落下空白

        // 直线
        Paragraph p1 = new Paragraph();
        p1.add(new Chunk(new LineSeparator()));

        // 点线
        Paragraph p2 = new Paragraph();
        p2.add(new Chunk(new DottedLineSeparator()));

        // 超链接
        Anchor anchor = new Anchor("baidu");
        anchor.setReference("www.baidu.com");

        // 定位
        Anchor gotoP = new Anchor("goto");
        gotoP.setReference("#top");
        for (int i = 0; i < 3; i++) {
            //图片资源为本地流方式
            File fileImage=new File("F:\\dog.jpg");
            FileInputStream fileInputStream = new FileInputStream(fileImage);

//            byte[] ib = new byte[(int)fileImage.length()];
            byte[] ib = new byte[fileInputStream.available()];
            fileInputStream.read(ib);
            // 图片资源为网络流的方式
//        Image image = Image.getInstance("https://img-blog.csdn.net/20180801174617455?watermark/2/text/aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3dlaXhpbl8zNzg0ODcxMA==/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70");
            Image image = Image.getInstance(ib);
            //Image image = new Jpeg(baseUrl.toString().getBytes());
            image.setAlignment(Image.ALIGN_CENTER);
            image.scalePercent(40); //依照比例缩放
            document.add(image);
        }

//        image.setAbsolutePosition(420,30);


        // 表格
        PdfPTable table = createTable(new float[]{40, 120, 120, 120, 80, 80});
        table.addCell(createCell("美好的一天", headfont, Element.ALIGN_LEFT, 6, false));
        table.addCell(createCell("早上9:00", keyfont, Element.ALIGN_CENTER));
        table.addCell(createCell("中午11:00", keyfont, Element.ALIGN_CENTER));
        table.addCell(createCell("中午13:00", keyfont, Element.ALIGN_CENTER));
        table.addCell(createCell("下午15:00", keyfont, Element.ALIGN_CENTER));
        table.addCell(createCell("下午17:00", keyfont, Element.ALIGN_CENTER));
        table.addCell(createCell("晚上19:00", keyfont, Element.ALIGN_CENTER));
        Integer totalQuantity = 0;
        for (int i = 0; i < 5; i++) {
            table.addCell(createCell("起床", textfont));
            table.addCell(createCell("吃午饭", textfont));
            table.addCell(createCell("午休", textfont));
            table.addCell(createCell("下午茶", textfont));
            table.addCell(createCell("回家", textfont));
            table.addCell(createCell("吃晚饭", textfont));
            totalQuantity++;
        }
        table.addCell(createCell("总计", keyfont));
        table.addCell(createCell("", textfont));
        table.addCell(createCell("", textfont));
        table.addCell(createCell("", textfont));
        table.addCell(createCell(String.valueOf(totalQuantity) + "件事", textfont));
        table.addCell(createCell("", textfont));

        document.add(paragraph);
        document.add(anchor);
        document.add(p2);
        document.add(gotoP);
        document.add(p1);
        document.add(table);

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
    //图片输入流
    private InputStream getFileSource(String urlStr, String fileName){
        // 下载网络文件
        InputStream inStream = null;
        try {
            URL url = new URL(urlStr);
            URLConnection conn = url.openConnection();
            fileName = getFileName(url, fileName);
            // 设置超时间为3秒
            conn.setConnectTimeout(3 * 1000);
            //模拟浏览器访问,防止屏蔽程序抓取而返回403错误
            conn.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/86.0.4240.111 Safari/537.36");
            // 拿到输入流就相当于拿到了文件
            inStream = conn.getInputStream();
            return inStream;
        } catch (Exception e) {
            log.error("下载网络资源 {} 失败，请及时处理，", fileName, e);
        } finally {
            IOUtils.closeQuietly(inStream, null);
        }
        return null;
    }

    public  void downloadNetResource(String urlStr, String fileName, String dir) {
        // 下载网络文件
        int byteSum = 0;
        int byteRead = 0;
        InputStream inStream = null;
        FileOutputStream fos = null;
        try {
            URL url = new URL(urlStr);
            URLConnection conn = url.openConnection();
            fileName = getFileName(url, fileName);
            // 设置超时间为3秒
            conn.setConnectTimeout(3 * 1000);
            //模拟浏览器访问,防止屏蔽程序抓取而返回403错误
            conn.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/86.0.4240.111 Safari/537.36");
            // 拿到输入流就相当于拿到了文件
            inStream = conn.getInputStream();
            // 文件保存位置
            File saveDir = new File(dir);
            if (!saveDir.exists()) {
                saveDir.mkdir();
            }
            File file = new File(saveDir + File.separator + fileName);
            fos = new FileOutputStream(file);
            byte[] buffer = new byte[1204];
            while ((byteRead = inStream.read(buffer)) != -1) {
                byteSum += byteRead;
                fos.write(buffer, 0, byteRead);
            }
            log.info("文件 {} 的大小为 {}", fileName, byteSum);
        } catch (Exception e) {
            log.error("下载网络资源 {} 失败，请及时处理，", fileName, e);
        } finally {
            IOUtils.closeQuietly(inStream, null);
            IOUtils.closeQuietly(fos, null);
        }
    }


    private  String getFileName(URL url, String fileName) {
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
