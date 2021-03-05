package com.lck.reverse.controllor;

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
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.*;
import java.util.List;

@RestController
@RequestMapping("/wx")
public class WxControllor {


    @Value("${km.oos.secretId}")
    private String secretId;
    @Value("${km.oos.secretKey}")
    private String secretKey;


    @GetMapping("/getList")
    public String uploadFile() throws FileNotFoundException {
        //上传
        //https://cloud.tencent.com/document/product/436/35215#.E7.AE.80.E5.8D.95.E4.B8.8A.E4.BC.A0.E5.AF.B9.E8.B1.A1


        // 1 初始化用户身份信息（secretId, secretKey）。
        COSCredentials cred = new BasicCOSCredentials(secretId, secretKey);
// 2 设置 bucket 的区域, COS 地域的简称请参照 https://cloud.tencent.com/document/product/436/6224
// clientConfig 中包含了设置 region, https(默认 http), 超时, 代理等 set 方法, 使用可参见源码或者常见问题 Java SDK 部分。
//        <BucketName-APPID>.cos.ap-shanghai.myqcloud.com
//        examplebucket-1250000000.cos.ap-guangzhou.myqcloud.com

        String regionValue="ap-nanjing";
        Region region = new Region(regionValue);
        ClientConfig clientConfig = new ClientConfig(region);
// 3 生成 cos 客户端。
        COSClient cosClient = new COSClient(cred, clientConfig);

        List<Bucket> buckets = cosClient.listBuckets();
        for (Bucket bucketElement : buckets) {
            String bucketName = bucketElement.getName();
            String bucketLocation = bucketElement.getLocation();
            System.out.println("bucketName == "+bucketName);
            System.out.println("bucketLocation == "+bucketLocation);
        }
        String filePath="C:\\Users\\Administrator\\Desktop\\img.jpg";
        // 指定要上传的文件
        File localFile = new File(filePath);
        FileInputStream fileInputStream = new FileInputStream(localFile);
// 指定要上传到的存储桶
        String bucketName = "km-wx-1304476764";
// 指定要上传到 COS 上对象键
//        String key = "lckkg/img.jpg";
        String key = "img.jpg";
        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentLength(500);
        PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, key, fileInputStream,objectMetadata);
        PutObjectResult putObjectResult = cosClient.putObject(putObjectRequest);
        System.out.println("---------------");
//        String bucketName1 = bucketName;
//        String key1 = "folder/images/";
//// 目录对象即是一个/结尾的空文件，上传一个长度为 0 的 byte 流
//        InputStream input = new ByteArrayInputStream(new byte[0]);
//        ObjectMetadata objectMetadata = new ObjectMetadata();
//        objectMetadata.setContentLength(0);
//        PutObjectRequest putObjectRequest1 =
//                new PutObjectRequest(bucketName1, key, input, objectMetadata);
//        PutObjectResult putObjectResult1 = cosClient.putObject(putObjectRequest);

        return null;
    }
}
