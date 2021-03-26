package com.lck.reverse.controllor;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lck.reverse.commons.COSClientConfig;
import com.lck.reverse.entity.EnumFilePath;
import com.lck.reverse.entity.TBannerImg;
import com.lck.reverse.entity.TNews;
import com.lck.reverse.entity.TProAttribute;
import com.lck.reverse.entity.respon.PageInfo;
import com.lck.reverse.entity.respon.ResultMessage;
import com.lck.reverse.service.impl.BannerImageServiceImpl;
import com.lck.reverse.service.impl.TNewsServiceImpl;
import com.lck.reverse.service.impl.TProAttributeServiceImpl;
import com.qcloud.cos.COSClient;
import com.qcloud.cos.model.ObjectMetadata;
import com.qcloud.cos.model.PutObjectRequest;
import com.qcloud.cos.model.PutObjectResult;
import lombok.extern.slf4j.Slf4j;
import org.codehaus.plexus.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/bsm")
@Slf4j
public class WxBsmControllor {
    // 指定要上传到的存储桶
    private static final String BUCKET_NAME = "km-wx-1304476764";
    //文件存储域名
    @Value("${km.tencent.filePath}")
    private String KM_DOMAIN_NAME;

    @Autowired
    private TNewsServiceImpl tNewsService;
    @Autowired
    private BannerImageServiceImpl bannerImageService;

    @Autowired
    private TProAttributeServiceImpl tProAttributeService;

    @Autowired
    private COSClientConfig COSClientConfig;

    /**
     * 添加新闻
     *
     * @param tNews
     * @return
     */
    @PostMapping("addNews")
    public ResultMessage addNews(
            @RequestBody TNews tNews
    ) {
        boolean result = tNewsService.save(tNews.setNewId(new SimpleDateFormat("yyyyMMddHHmmss").format(new Date())));
        return result ? ResultMessage.getDefaultResultMessage(200, "新闻信息添加成功") :
                ResultMessage.getDefaultResultMessage(500, "新闻信息添加失败");
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
        return result ? ResultMessage.getDefaultResultMessage(200, "banner信息添加成功") :
                ResultMessage.getDefaultResultMessage(500, "banner信息添加失败");
    }

    /**
     * banner列表
     *
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
     * 删除banner，没有删除文件系统里面的文件
     * @return
     */
    @DeleteMapping("delBanner")
    public ResultMessage delBanner(
            @RequestParam(name="bannerId") String bannerId
    ) {
        boolean result = bannerImageService.remove(new QueryWrapper<TBannerImg>().lambda().eq(TBannerImg::getBannerId, bannerId));
        return result?ResultMessage.getDefaultResultMessage(200,"删除成功"):ResultMessage.getDefaultResultMessage(500,"删除失败");
    }

    /**
     *
     */
    @GetMapping("getProInfos")
    public ResultMessage getProInfos(
            @RequestParam(name = "startIndex",required = false,defaultValue = "1") String startIndex,
            @RequestParam(name = "pageSize",required = false,defaultValue = "10") String pageSize
    ){
        log.info("接口[{}]开始执行,分页参数[{},{}]","getProInfos",startIndex,pageSize);
        Map<String,Object> params=new HashMap<>(2);
        params.put("startIndex",startIndex);
        params.put("pageSize",pageSize);
        try {
            List<Map<String, Object>> proInfos = tProAttributeService.getProInfos(params);
            Integer proInfosCount = tProAttributeService.getProInfosCount();
            PageInfo<Map<String, Object>> result=new PageInfo<>(Integer.parseInt(startIndex),Integer.parseInt(pageSize),proInfosCount,proInfos);
            return ResultMessage.getDefaultResultMessage(200,result);
        }catch (Exception e){
            e.printStackTrace();
        }
        return ResultMessage.getDefaultResultMessage(500,"查询异常");
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
            @RequestParam(name = "fileType") String fileType,
            @RequestParam(name = "bannerName", required = false, defaultValue = "") String bannerName
    ) {
        String result = this.uploadFile(fileType, imgFile);
        if (StringUtils.isEmpty(result)) {
            return ResultMessage.getDefaultResultMessage(500, "文件上传失败");
        }
        switch (fileType){
            case "banner":
                saveBanner(result,bannerName);
                break;
            case "pro":
                saveProduct();
                break;
            case "news":
                saveNews();
                break;
            default:
                log.info("暂无上传文件");
        }

        return ResultMessage.getDefaultResultMessage(200, "文件上传成功");
    }

    private void saveNews() {

    }

    private void saveProduct() {

    }

    private void saveBanner(String result,String bannerName) {
        bannerImageService.save(new TBannerImg()
                .setId(null)
                .setBannerId(getDateTimeStr())
                .setBUrl(KM_DOMAIN_NAME+"/"+result)
                .setName(bannerName)
                .setType(EnumFilePath.BANNER.getMsg()));
    }

    private String getDateTimeStr(){
        SimpleDateFormat sdf = new SimpleDateFormat();// 格式化时间
        sdf.applyPattern("yyyyMMddHHmmss");
        return sdf.format(new Date());
    }

    private String uploadFile(String fileType, MultipartFile imgFile) {
        String pathFile = (EnumFilePath.NEWS.getMsg().equals(fileType)) ?
                EnumFilePath.NEWS.getValue() :
                (EnumFilePath.BANNER.getMsg().equals(fileType)) ?
                        EnumFilePath.BANNER.getValue() :
                        EnumFilePath.PRODUCT.getValue();
        COSClient cosClient = COSClientConfig.getCOSClient();
        String key = pathFile + imgFile.getOriginalFilename();
        ObjectMetadata objectMetadata = new ObjectMetadata();
        PutObjectRequest putObjectRequest = null;
        try {
            putObjectRequest = new PutObjectRequest(BUCKET_NAME, key, imgFile.getInputStream(), objectMetadata);
            cosClient.putObject(putObjectRequest);
            return key;
        } catch (IOException e) {
            log.error("类型[ {} ]文件上传失败", fileType);
            e.printStackTrace();
        }
        return null;
    }
}
