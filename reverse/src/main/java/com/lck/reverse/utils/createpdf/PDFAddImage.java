package com.lck.reverse.utils.createpdf;

import com.itextpdf.text.Image;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

public class PDFAddImage {
    public static void main(String[] args) {
        try {
            // 模板文件路径
            String templatePath = "F:\\demo1.pdf";
            // 生成的文件路径
            String targetPath = "F:\\e.pdf";
            // 书签名
            String fieldName = "table";
            // 图片路径
            String imagePath = "F:\\img.jpg";

            // 读取模板文件
            InputStream input = new FileInputStream(new File(templatePath));
            PdfReader reader = new PdfReader(input);
            PdfStamper stamper = new PdfStamper(reader, new FileOutputStream(
                    targetPath));
            // 提取pdf中的表单
            AcroFields form = stamper.getAcroFields();
            //表示在哪一页签字
            int numberOfPages = reader.getNumberOfPages();
            PdfContentByte  over = stamper.getOverContent(4);
            form.addSubstitutionFont(BaseFont.createFont("STSong-Light",
                    "UniGB-UCS2-H", BaseFont.NOT_EMBEDDED));

            int pageCount = reader.getNumberOfPages()+1;
            Rectangle size = reader.getPageSize(1);
            float pdfY = size.getTop();

            // 通过域名获取所在页和坐标，左下角为起点
//            int pageNo = form.getFieldPositions(fieldName).get(0).page;
//            Rectangle signRect = form.getFieldPositions(fieldName).get(0).position;
//            float x = signRect.getLeft();
//            float y = signRect.getBottom();

            // 读图片
            Image image = Image.getInstance(imagePath);
            image.setAlignment(Image.ALIGN_CENTER);
            image.scalePercent(20); //依照比例缩放
            image.setAbsolutePosition(420,30);
            // 获取操作的页面
//            PdfContentByte under = stamper.getOverContent(pageNo);
            // 根据域的大小缩放图片
//            image.scaleToFit(signRect.getWidth(), signRect.getHeight());
            // 添加图片
            over.addImage(image);

            stamper.close();
            reader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 插入图片
     *
     * @author vio
     * @date 2019/08/20
     */
    public static void insertImg(PdfStamper ps, AcroFields s, PdfReader reader, Map<String, String> imgInsert) {

        for (Map.Entry<String, String> entry : imgInsert.entrySet()) {
            // 获取key对应参数
            List<AcroFields.FieldPosition> list = s.getFieldPositions(entry.getKey());
            if (null == list || null == list.get(0)) {
                return;
            }
            int pageNo = list.get(0).page;
            PdfContentByte cb = ps.getOverContent(pageNo);
            Rectangle rect = list.get(0).position;
//            float x = rect.getLeft();
//            float y = rect.getBottom();
            // 读图片
            try {
                Image image = Image.getInstance(entry.getValue());
                // 根据域的大小缩放图片
                image.scaleToFit(rect.getWidth(), rect.getHeight());
                System.out.println(image.getHeight());
                System.out.println(image.getScaledHeight());
                float x = 10;
                // 获取页面的高度
                Rectangle rectPage = reader.getPageSize(1);

                float y = rectPage.getHeight() - image.getScaledHeight() - 10;
                // 添加图片
                image.setAbsolutePosition(x, y);

                cb.addImage(image);
            } catch (Exception e) {

            }
        }
    }

}
