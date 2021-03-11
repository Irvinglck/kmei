package com.lck.reverse.controllor;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.draw.DottedLineSeparator;
import com.itextpdf.text.pdf.draw.LineSeparator;
import com.lck.reverse.commons.COSClientConfig;
import com.lck.reverse.entity.TProInfo;
import com.lck.reverse.entity.respon.ResultMessage;
import com.lck.reverse.service.TProInfoService;
import com.lck.reverse.service.WxClientService;
import com.lck.reverse.utils.createpdf.MyHeaderFooter;
import com.lck.reverse.utils.createpdf.Watermark;
import com.qcloud.cos.COSClient;
import com.qcloud.cos.model.Bucket;
import com.qcloud.cos.model.ObjectMetadata;
import com.qcloud.cos.model.PutObjectRequest;
import com.qcloud.cos.model.PutObjectResult;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.codehaus.plexus.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@RestController
@RequestMapping("/wx")
@Slf4j
public class WxControllor {
    //产品页图片
    private static final String DIR_IMG = "PRODUCT";
    // 指定要上传到的存储桶
    private static final String BUCKET_NAME = "km-wx-1304476764";

    @Value("${local.product.temp.dir}")
    private String LOCAL_PRODUCT_DIR;

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

    @Autowired
    private TProInfoService tProInfoService;


    @GetMapping("/st")
    public ResultMessage uploadFile1(

    ) {
        Page page=new Page(1,10);          //1表示当前页，而10表示每页的显示显示的条目数
        System.out.println(tProInfoService.selectUserPage(page, "print"));
        return ResultMessage.getDefaultResultMessage();

    }
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

        TProInfo tProInfo =null;


        List<String> prosUrl = Arrays.asList(
                tProInfo.getPicurl1(), tProInfo.getPicurl2(), tProInfo.getPicurl3(), tProInfo.getPicurl4(), tProInfo.getPicurl5(),
                tProInfo.getPicurl6(), tProInfo.getPicurl7(), tProInfo.getPicurl8(), tProInfo.getPicurl9()
        );

        List<Image> images = new ArrayList<>();
        int i = 1;
        for (String strs : prosUrl) {
            String productName = "product" + (++i);
            //文件写入本地
            downloadHttpResource(strs, productName, LOCAL_PRODUCT_DIR);
            images.add(Image.getInstance(LOCAL_PRODUCT_DIR + "/" + productName + ".jpg"));
        }

        try {
            // 1.新建document对象
            Document document = new Document(PageSize.A4);

            // 2.建立一个书写器(Writer)与document对象关联
            File file = new File(LOCAL_PRODUCT_DIR+"/"+"pdf"+productId+".pdf");
            file.createNewFile();
            PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(file));
            writer.setPageEvent(new Watermark("HELLO KM"));// 水印
            writer.setPageEvent(new MyHeaderFooter());// 页眉/页脚

            // 3.打开文档
            document.open();
            document.addTitle("Title@PDF-Java");// 标题
            document.addAuthor("Author@lck");// 作者
            document.addSubject("Subject@iText pdf sample");// 主题
            document.addKeywords("Keywords@" + productId);// 关键字
            document.addCreator("Creator@lck");// 创建者
            // 5.关闭文档
            document.close();
            // 4.向文档中添加内容
            return generatePDF(document, images) ? ResultMessage.getDefaultResultMessage(200).setMsg("pdf合成成功").setData("https://km-wx-1304476764.cos.ap-nanjing.myqcloud.com/PRODUCT/print/pdf/printMachine.pdf")
                    : ResultMessage.getDefaultResultMessage(501).setMsg("pdf合成失败");
        } catch (Exception e) {
            e.printStackTrace();
        }

        return ResultMessage.getDefaultResultMessage(502).setMsg("pdf合成异常");
    }

    // 生成PDF文件
    private Boolean generatePDF(Document document, List<Image> images) throws Exception {

        // 段落
        Paragraph paragraph = new Paragraph("柯尼卡美能达", titlefont);
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
        AtomicInteger flag = new AtomicInteger(1);
        images.forEach(item -> {
            try {
                document.add(item);
                flag.getAndIncrement();
            } catch (DocumentException e) {
                e.printStackTrace();
            }
        });

        if (flag.get() != images.size()) {
            log.info("图片文件添加失败");
            return false;
        }
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

        return document.add(paragraph);
//        document.add(anchor);
//        document.add(p2);
//        document.add(gotoP);
//        document.add(p1);
//        document.add(table);

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
