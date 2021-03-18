//package com.lck.reverse.utils.createpdf;
//
//import com.itextpdf.awt.geom.Dimension;
//import com.itextpdf.text.DocumentException;
//import com.itextpdf.text.Image;
//import com.itextpdf.text.Rectangle;
//import com.itextpdf.text.pdf.PdfContentByte;
//import com.itextpdf.text.pdf.PdfReader;
//import com.itextpdf.text.pdf.PdfStamper;
//import com.sun.imageio.plugins.common.ImageUtil;
//
//import java.io.FileOutputStream;
//import java.io.IOException;
//
//public class FindPdfLastPage {
//
////    public static void createWaterMarkSpread2PDF(String srcFilePath, String savePath, String waterMarkPath,float spaceX,float spaceY) throws IOException, DocumentException{
////
////        PdfReader pdfReader = null;
////
////        PdfStamper pdfStamper = null;
////
////        try{
////
////            pdfReader = new PdfReader(srcFilePath);
////
////            pdfStamper = new PdfStamper(pdfReader, new FileOutputStream(savePath));;
////
////            int pageCount = pdfReader.getNumberOfPages()+1;//获取PDF页数
////
////            Image image = Image.getInstance(waterMarkPath);
////
////            Rectangle size = pdfReader.getPageSize(1);
////
////            float pdfY = size.top();//PDF页面高度
////
////            float pdfX = size.right();//PDF页面宽度
////
////            Dimension dim = ImageUtil.getDim4Image(waterMarkPath);
////
////            PageSpreadSize pss = getPageSpreadSize(size, dim, spaceX, spaceY);
////
////            PdfContentByte content=null;
////
////            float heightWithSpace = (float)dim.getHeight()+(Float.isNaN(spaceY)
//////                    ------解决思路----------------------
////            spaceY<=0?0:spaceY/2);
////
////            float widthWithSpace = (float)dim.getWidth()+(Float.isNaN(spaceX)
//////                    ------解决思路----------------------
////            spaceX<=0?0:spaceX/2);
////
////            boolean isSpread = isImageSpread(pss);//是否平铺
////
////            //起始位置信息
////
////            float startX = isSpread?(pdfX-widthWithSpace*pss.getSizeX())/2:(float)(pdfX/2-dim.getWidth()/2);
////
////            float startY = isSpread?pdfY -(pdfY-heightWithSpace*pss.getSizeY())/2:((float)(pdfY/2-dim.getHeight()/2));
////
////            //给每页增加水印
////
////            for (int i = 1; i < pageCount; i++) {
////
////                content = pdfStamper.getOverContent(i);
////
////                float posY = pdfY;//Y坐标绝对路径
////
////                if(!isSpread){
////
////                    image.setAbsolutePosition(startX, startY);
////
////                    content.addImage(image);
////
////                }
////
////                else{
////
////                    //行
////
////                    for(int pi=1; pi<=pss.getSizeY(); pi++){
////
////                        posY = startY - pi*heightWithSpace;
////
////                        float posX=0f; //X坐标绝对路径
////
////                        //列
////
////                        for(int py=1; py<=pss.getSizeX(); py++){
////
////                            posX = startX+(py-1)*widthWithSpace;
////
////                            image.setAbsolutePosition(posX, posY);
////
////                            content.addImage(image);
////
////                        }
////
////                    }
////
////                }
////
////                content = null;//便于及时回收
////
////            }
////
////        } catch (DocumentException e) {
////            e.printStackTrace();
////        } catch (IOException e) {
////            e.printStackTrace();
////        } finally{
////
////            if(pdfStamper!=null){
////
////                pdfStamper.close();
////
////                pdfStamper = null;
////
////                pdfReader = null;
////
////            }
////
////        }
////
////    }
////
//
//}
