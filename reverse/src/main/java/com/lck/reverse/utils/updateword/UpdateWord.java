//package com.lck.reverse.utils.updateword;
//
//import org.apache.poi.hwpf.HWPFDocument;
//import org.apache.poi.hwpf.usermodel.Range;
//
//import java.io.*;
//import java.util.HashMap;
//import java.util.Map;
//
//public class UpdateWord {
//
//    public static void CreatWordByModel(String  tmpFile, Map<String, String> contentMap, String exportFile) throws Exception{
//
//        InputStream in = null;
//        in = new FileInputStream(new File(tmpFile));
//
//        HWPFDocument document = null;
//        document = new HWPFDocument(in);
//        // 读取文本内容
//        Range bodyRange = document.getRange();
//        System.out.println(bodyRange.toString());
//        System.out.println(bodyRange.text());
//        // 替换内容
//        for (Map.Entry<String, String> entry : contentMap.entrySet()) {
//            bodyRange.replaceText("${" + entry.getKey() + "}", entry.getValue());
//        }
//
//        //导出到文件
//        try {
//            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
//            document.write((OutputStream)byteArrayOutputStream);
//            OutputStream outputStream = new FileOutputStream(exportFile);
//            outputStream.write(byteArrayOutputStream.toByteArray());
//            outputStream.close();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//
//    public static void main(String[] args) throws Exception {
//        Map map=new HashMap();
//        map.put("name","刁某某");
//        map.put("age","24");
//        map.put("sex","男");
//        CreatWordByModel("F:/docModel.doc",map,"F:/downWord.doc");
//
//    }
//}
