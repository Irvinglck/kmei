package com.lck.reverse.controllor;

import com.lck.reverse.commons.COSClientConfig;
import com.qcloud.cos.COSClient;
import com.qcloud.cos.ClientConfig;
import com.qcloud.cos.auth.BasicCOSCredentials;
import com.qcloud.cos.auth.COSCredentials;
import com.qcloud.cos.model.Bucket;
import com.qcloud.cos.model.ObjectMetadata;
import com.qcloud.cos.model.PutObjectRequest;
import com.qcloud.cos.model.PutObjectResult;
import com.qcloud.cos.region.Region;
import org.bouncycastle.asn1.cms.MetaData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.annotation.MultipartConfig;
import java.io.*;
import java.util.List;

@RestController
@RequestMapping("/wx")
public class WxControllor {

    private static final String DIR_IMG = "PRODUCT";
    @Autowired
    private COSClientConfig COSClientConfig;


    @GetMapping("/getList")
    public String uploadFile(
            @RequestParam(name = "imgFile") MultipartFile imgFile
    ) throws FileNotFoundException {

        COSClient cosClient = COSClientConfig.getCOSClient();
        List<Bucket> buckets = cosClient.listBuckets();
//        for (Bucket bucketElement : buckets) {
//            String bucketName = bucketElement.getName();
//            String bucketLocation = bucketElement.getLocation();
//            System.out.println("bucketName == "+bucketName);
//            System.out.println("bucketLocation == "+bucketLocation);
//        }
//        String filePath="C:\\Users\\Administrator\\Desktop\\temp.zip";
//        // 指定要上传的文件
//        File localFile = new File(filePath);
//        FileInputStream fileInputStream = new FileInputStream(localFile);
        // 指定要上传到的存储桶
        String bucketName = "km-wx-1304476764";
        // 指定要上传到 COS 上对象键
        String key = DIR_IMG + "/" + imgFile.getOriginalFilename();
        ObjectMetadata objectMetadata = new ObjectMetadata();
        PutObjectRequest putObjectRequest = null;
        try {
            putObjectRequest = new PutObjectRequest(bucketName, key, imgFile.getInputStream(), objectMetadata);
        } catch (IOException e) {
            e.printStackTrace();
        }
        PutObjectResult putObjectResult = cosClient.putObject(putObjectRequest);
        System.out.println("---------------" + putObjectResult.toString());

        return putObjectResult.toString();
    }

    //创建文件夹
    private String createDirs() {
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
