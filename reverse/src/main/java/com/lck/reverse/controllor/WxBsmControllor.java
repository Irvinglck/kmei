package com.lck.reverse.controllor;

import com.lck.reverse.commons.COSClientConfig;
import com.lck.reverse.entity.EnumFilePath;
import com.lck.reverse.entity.TBannerImg;
import com.lck.reverse.entity.TNews;
import com.lck.reverse.entity.respon.ResultMessage;
import com.lck.reverse.service.impl.BannerImageServiceImpl;
import com.lck.reverse.service.impl.TNewsServiceImpl;
import com.qcloud.cos.COSClient;
import com.qcloud.cos.model.ObjectMetadata;
import com.qcloud.cos.model.PutObjectRequest;
import com.qcloud.cos.model.PutObjectResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

@RestController
@RequestMapping("/bsm")
public class WxBsmControllor {
    // 指定要上传到的存储桶
    private static final String BUCKET_NAME = "km-wx-1304476764";

    @Autowired
    private TNewsServiceImpl tNewsService;
    @Autowired
    private BannerImageServiceImpl bannerImageService;
    @Autowired
    private COSClientConfig COSClientConfig;

    /**
     * 添加新闻
     * @param tNews
     * @return
     */
    @PostMapping("addNews")
    public ResultMessage addNews(
            @RequestBody TNews tNews
    ) {
        boolean result = tNewsService.save(tNews.setNewId(new SimpleDateFormat("yyyyMMddHHmmss").format(new Date())));
        return result?ResultMessage.getDefaultResultMessage(200,"新闻信息添加成功"):
                ResultMessage.getDefaultResultMessage(500,"新闻信息添加失败");
    }
    /**
     * 添加banner
     * @param bannerImg
     * @return
     */
    @PostMapping("addBanner")
    public ResultMessage addBanner(
            @RequestBody TBannerImg bannerImg
    ) {
        boolean result = bannerImageService.save(bannerImg.setBannerId(new SimpleDateFormat("yyyyMMddHHmmss").format(new Date())));
        return result?ResultMessage.getDefaultResultMessage(200,"banner信息添加成功"):
                ResultMessage.getDefaultResultMessage(500,"banner信息添加失败");
    }

    /**
     * banner列表
     * @return
     */
    @GetMapping("getBanner")
    public ResultMessage getBanner(
    ) {
        return ResultMessage.getDefaultResultMessage(200,
                "新闻信息",
                bannerImageService.list());
    }

    /**
     * 根据类型上传文件
     * @param imgFile
     * @param fileType
     * @return
     */
    @PostMapping("/uploadFileByType")
    public ResultMessage uploadFile(
            @RequestParam(name = "imgFile") MultipartFile imgFile,
            @RequestParam(name = "fileType") Map<String,String> fileType
    ) {
        String pathFile=(EnumFilePath.NEWS.getMsg().equals(fileType))?
                EnumFilePath.NEWS.getValue():
                (EnumFilePath.BANNER.getMsg().equals(fileType))?
                        EnumFilePath.BANNER.getValue():
                        EnumFilePath.PRODUCT.getValue();
        COSClient cosClient = COSClientConfig.getCOSClient();
        String key = pathFile + imgFile.getOriginalFilename();
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
}
