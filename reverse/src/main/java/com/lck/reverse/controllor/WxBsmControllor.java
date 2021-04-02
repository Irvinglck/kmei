package com.lck.reverse.controllor;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.itextpdf.text.BadElementException;
import com.lck.reverse.commons.COSClientConfig;
import com.lck.reverse.entity.*;
import com.lck.reverse.entity.respon.PageInfo;
import com.lck.reverse.entity.respon.ResultMessage;
import com.lck.reverse.service.TProInfoService;
import com.lck.reverse.service.impl.*;
import com.lck.reverse.utils.map2bean.MapBeanConvert;
import com.lck.reverse.utils.reflect.Reflect;
import com.qcloud.cos.COSClient;
import com.qcloud.cos.model.ObjectMetadata;
import com.qcloud.cos.model.PutObjectRequest;
import com.qcloud.cos.model.PutObjectResult;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;
import org.codehaus.plexus.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;
import java.util.stream.Collectors;

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
    private TUserServiceImpl tUserService;

    @Autowired
    private TProInfoServiceImpl tProInfoService;

    @Autowired
    private COSClientConfig COSClientConfig;


    /**
     * 添加banner
     *
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
     *
     * @return
     */
    @DeleteMapping("delBanner")
    public ResultMessage delBanner(
            @RequestParam(name = "bannerId") String bannerId
    ) {
        boolean result = bannerImageService.remove(new QueryWrapper<TBannerImg>().lambda().eq(TBannerImg::getBannerId, bannerId));
        return result ? ResultMessage.getDefaultResultMessage(200, "删除成功") : ResultMessage.getDefaultResultMessage(500, "删除失败");
    }

    /**
     * 产品列表
     */
    @GetMapping("getProInfos")
    public ResultMessage getProInfos(
            @RequestParam(name = "startIndex", required = false, defaultValue = "1") String startIndex,
            @RequestParam(name = "pageSize", required = false, defaultValue = "10") String pageSize,
            @RequestParam(name = "proName", required = false, defaultValue = "") String proName
    ) {
        log.info("接口[{}]开始执行,参数[{},{},{}]", "getProInfos", startIndex, pageSize, proName);
        Map<String, Object> params = new HashMap<>(2);
        params.put("startIndex", startIndex);
        params.put("pageSize", pageSize);
        params.put("proName", proName);
        try {
            List<Map<String, Object>> proInfos = tProAttributeService.getProInfos(params);
            Integer proInfosCount = tProAttributeService.getProInfosCount(params);
            PageInfo<Map<String, Object>> result = new PageInfo<>(Integer.parseInt(startIndex), Integer.parseInt(pageSize), proInfosCount, proInfos);
            return ResultMessage.getDefaultResultMessage(200, result);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResultMessage.getDefaultResultMessage(500, "查询异常");
    }

    @GetMapping("getProInfos1")
    public ResultMessage getProInfos1(
            @RequestParam(name = "startIndex", required = false, defaultValue = "1") String startIndex,
            @RequestParam(name = "pageSize", required = false, defaultValue = "10") String pageSize,
            @RequestParam(name = "proName", required = false, defaultValue = "") String proName
    ) {
        log.info("接口[{}]开始执行,参数[{},{},{}]", "getProInfos", startIndex, pageSize, proName);
        Map<String, Object> params = new HashMap<>(2);
        params.put("startIndex", startIndex);
        params.put("pageSize", pageSize);
        params.put("proName", proName);
        try {
            List<Map<String, Object>> proInfos = tProAttributeService.getProInfos(params);
            Integer proInfosCount = tProAttributeService.getProInfosCount(params);
            PageInfo<Map<String, Object>> result = new PageInfo<>(Integer.parseInt(startIndex), Integer.parseInt(pageSize), proInfosCount, proInfos);
            return ResultMessage.getDefaultResultMessage(200, result);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResultMessage.getDefaultResultMessage(500, "查询异常");
    }

    /**
     * 删除产品
     */
    @GetMapping("delPro")
    public ResultMessage delPro(
            @RequestParam(name = "proId") String proId
    ) {
        log.info("接口[{}]开始执行,参数[{}]", "delPro", proId);
        boolean remove = tProAttributeService.remove(new QueryWrapper<TProAttribute>().lambda().eq(TProAttribute::getIdattr, Integer.valueOf(proId)));
        boolean remove1 = tProInfoService.remove(new QueryWrapper<TProInfo>().lambda().eq(TProInfo::getProid, proId));
        if (remove && remove1)
            return ResultMessage.getDefaultResultMessage(200, "删除成功");
        return ResultMessage.getDefaultResultMessage(500, "删除失败");
    }

    /**
     * 添加产品
     *
     * @param tProAttribute
     * @return
     */
    @PostMapping("addPro")
    public ResultMessage addPro(
            @RequestBody TProAttribute tProAttribute
    ) {
        boolean result = tProAttributeService.save(tProAttribute);
        return result ? ResultMessage.getDefaultResultMessage(200, "banner信息添加成功") :
                ResultMessage.getDefaultResultMessage(500, "banner信息添加失败");
    }

    /**
     * 多文件上传
     *
     * @param
     * @return
     */
    @PostMapping("uploadFiles")
    public ResultMessage uploadFiles(
            @RequestParam(name = "files") MultipartFile[] files,
            @RequestParam(name = "proDes") String proDes
    ) {
        if (files.length == 0) {
            return ResultMessage.getDefaultResultMessage(500, "无选中文件");
        }
        if (files.length > 8) {
            return ResultMessage.getDefaultResultMessage(500, "最多只能上传8张图片");
        }
        JSONObject jsonObject = JSONObject.parseObject(proDes);
        String proName = jsonObject.getString("proName");
        String idAttr = jsonObject.getString("idAttr");
        if (StringUtils.isEmpty(proName) || StringUtils.isEmpty(idAttr)) {
            log.info("idAttr:[{}],proName:[{}] 值有为空 ", idAttr, proName);
            return ResultMessage.getDefaultResultMessage(500, "idAttr:[{" + idAttr + "}],proName:[{" + proName + "}] 值有为空");
        }
        List<String> params = new ArrayList<>();
        Map<String, Object> maps = new HashMap<>(8);
        int i = 1;
        for (MultipartFile file : files) {
            String pathFile = EnumFilePath.PRODUCT.getValue();
            COSClient cosClient = COSClientConfig.getCOSClient();
            //文件名称
            String fileName = file.getOriginalFilename();
            String key = pathFile + fileName;

            ObjectMetadata objectMetadata = new ObjectMetadata();
            PutObjectRequest putObjectRequest = null;
            try {
                putObjectRequest = new PutObjectRequest(BUCKET_NAME, key, file.getInputStream(), objectMetadata);
                cosClient.putObject(putObjectRequest);
                params.add(key);
                maps.put("picurl" + i, KM_DOMAIN_NAME + "/" + key);
                i++;
            } catch (IOException e) {
                log.error("类型[ {} ]文件上传失败", fileName);
                e.printStackTrace();
            }

        }
        try {
            //map 2 bean
            TProInfo tProInfo = (TProInfo) MapBeanConvert.mapToObject(maps, TProInfo.class);
            tProInfo.setProid(idAttr);
            tProInfo.setProname(proName);
            tProInfo.setHaveimg("true");
            log.info("TProInfo映射之后的值为[{}]", tProInfo.toString());
            tProInfoService.saveOrUpdate(tProInfo, new UpdateWrapper<TProInfo>().lambda().eq(TProInfo::getProid, idAttr));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResultMessage.getDefaultResultMessage(200, "上传成功", params);
    }

    /**
     * 更新图片
     *
     * @param flagId
     * @param
     * @return
     */
    @GetMapping("/updateInfo")
    public ResultMessage updateInfo(
            @RequestParam(name = "flagId") String flagId,
            @RequestParam(name = "proId") String proId
    ) {
        if (StringUtils.isEmpty(flagId)) {
            return ResultMessage.getDefaultResultMessage(500, "入参未赋值");
        }
        TProInfo one = tProInfoService.getOne(new QueryWrapper<TProInfo>().lambda().eq(TProInfo::getProid, proId));
        TProInfo tProInfo = Reflect.reflectByObj(one, flagId);
        if (tProInfo == null) {
            return ResultMessage.getDefaultResultMessage(500, "反射赋值失败");
        }
        if (!isLastOne(tProInfo)) {
            tProInfo.setHaveimg("false");
            tProInfo.setDownpdf("");
            tProInfo.setHavepdf("false");
        }

        boolean result = tProInfoService.update(tProInfo, new UpdateWrapper<TProInfo>().lambda().eq(TProInfo::getProid, proId));
        return result ? ResultMessage.getDefaultResultMessage(200, "删除成功") :
                ResultMessage.getDefaultResultMessage(500, "删除失败");
    }

    /**
     * 更新产品属性
     *
     * @param tProAttribute
     * @return
     */
    @GetMapping("/updatePro")
    public ResultMessage updatePro(
            @RequestBody TProAttribute tProAttribute
    ) {
        if (tProAttribute == null || tProAttribute.getIdattr() == 0)
            return ResultMessage.getDefaultResultMessage(500, "对象为空");
        boolean update = tProAttributeService.update(tProAttribute, new UpdateWrapper<TProAttribute>().lambda().eq(TProAttribute::getIdattr, tProAttribute.getIdattr()));
        return update ? ResultMessage.getDefaultResultMessage(200, "修改成功") :
                ResultMessage.getDefaultResultMessage(500, "修改失败");
    }

    private boolean isLastOne(TProInfo tProInfo) {
        if (!StringUtils.isEmpty(tProInfo.getPicurl1()) ||
                !StringUtils.isEmpty(tProInfo.getPicurl2()) ||
                !StringUtils.isEmpty(tProInfo.getPicurl3()) ||
                !StringUtils.isEmpty(tProInfo.getPicurl4()) ||
                !StringUtils.isEmpty(tProInfo.getPicurl5()) ||
                !StringUtils.isEmpty(tProInfo.getPicurl6()) ||
                !StringUtils.isEmpty(tProInfo.getPicurl7()) ||
                !StringUtils.isEmpty(tProInfo.getPicurl8())) {
            return true;
        }
        return false;
    }

    /**
     * @param proId
     * @return
     */
    @GetMapping("/getProImgs")
    public ResultMessage getProImgs(
            @RequestParam(name = "proId") String proId
    ) {

        TProInfo tProInfo = tProInfoService.getOne(new QueryWrapper<TProInfo>().lambda().eq(TProInfo::getProid, proId));
        if (tProInfo == null) {
            return ResultMessage.getDefaultResultMessage(500, "产品id [ " + proId + " ] 没有对应产品");
        }
        List<String> prosUrl = Arrays.asList(
                tProInfo.getPicurl1(), tProInfo.getPicurl2(), tProInfo.getPicurl3(), tProInfo.getPicurl4(), tProInfo.getPicurl5(),
                tProInfo.getPicurl6(), tProInfo.getPicurl7(), tProInfo.getPicurl8()
        );

        //去重
//        List<String> res = prosUrl.stream().filter(item -> !StringUtils.isEmpty(item)).collect(Collectors.toList());

        List<Map<String, Object>> resList = new ArrayList<>();
        AtomicInteger atomicInteger = new AtomicInteger(1);
        prosUrl.forEach(item -> {
            if (StringUtils.isEmpty(item)) {
                atomicInteger.getAndIncrement();
                return;
            }
            Map<String, Object> result = new HashMap<>();
            result.put("bIndex", String.valueOf(atomicInteger.getAndIncrement()));
            result.put("bUrl", item);
            resList.add(result);
        });
        //List转map
//        Map<String, Integer> collect = res.stream().collect(Collectors.toMap(Function.identity(), item -> atomicInteger.getAndIncrement()));
        return ResultMessage.getDefaultResultMessage(200, "返回成功", resList);
    }

    /**
     * 根据类型上传文件
     *
     * @param imgFile
     * @param fileType
     * @return
     */
    @PostMapping("/uploadFileByType")
    public ResultMessage uploadFile(
            @RequestParam(name = "imgFile") MultipartFile imgFile,
            @RequestParam(name = "fileType") String fileType,
            //banner添加
            @RequestParam(name = "bannerName", required = false, defaultValue = "") String bannerName,
            //新闻添加
            @RequestParam(name = "title", required = false, defaultValue = "") String title,
            @RequestParam(name = "subtitle", required = false, defaultValue = "") String subtitle,
            @RequestParam(name = "nurl", required = false, defaultValue = "") String nurl,
            //人员添加
            @RequestParam(name = "account", required = false, defaultValue = "") String account,
            @RequestParam(name = "password", required = false, defaultValue = "") String password,
            @RequestParam(name = "emailAddress", required = false, defaultValue = "") String emailAddress,
            @RequestParam(name = "introduce", required = false, defaultValue = "") String introduce,
            @RequestParam(name = "nickName", required = false, defaultValue = "") String nickName,
            @RequestParam(name = "realName", required = false, defaultValue = "") String realName,
            @RequestParam(name = "sex", required = false, defaultValue = "") String sex

    ) {

        String result = this.uploadFile(fileType, imgFile);
        if (StringUtils.isEmpty(result)) {
            return ResultMessage.getDefaultResultMessage(500, "文件上传失败");
        }
        switch (fileType) {
            case "banner":
                saveBanner(result, bannerName);
                break;
            case "user":
                saveProduct(result, account, password, emailAddress, introduce, nickName, realName, sex);
                break;
            case "news":
                saveNews(result, title, subtitle, nurl);
                break;
            default:
                log.info("暂无上传文件");
        }

        return ResultMessage.getDefaultResultMessage(200, "文件上传成功");
    }

    private void saveNews(String result, String title, String subtitle, String nurl) {
        TNews tNews = new TNews()
                .setNewId(getDateTimeStr())
                .setNurl(nurl)
                .setTitle(title)
                .setSubtitle(subtitle)
                .setTitleUrl(KM_DOMAIN_NAME + "/" + result);
        tNewsService.save(tNews);
    }

    private void saveProduct(String key, String account, String password, String emailAddress, String introduce, String nickName, String realName, String sex) {
        TUser tUser = new TUser()
                .setTitleUrl(KM_DOMAIN_NAME + "/" + key)
                .setAccount(account)
                .setPassword(password)
                .setEmailAddress(emailAddress)
                .setIntroduce(introduce)
                .setNickName(nickName)
                .setRealName(realName)
                .setSex(sex);
        tUserService.save(tUser);
    }


    private void saveBanner(String result, String bannerName) {
        bannerImageService.save(new TBannerImg()
                .setId(null)
                .setBannerId(getDateTimeStr())
                .setBUrl(KM_DOMAIN_NAME + "/" + result)
                .setName(bannerName)
                .setType(EnumFilePath.BANNER.getMsg()));
    }


    private String getDateTimeStr() {
        SimpleDateFormat sdf = new SimpleDateFormat();// 格式化时间
        sdf.applyPattern("yyyyMMddHHmmss");
        return sdf.format(new Date());
    }

    private String uploadFile(String fileType, MultipartFile imgFile) {
        String pathFile = (EnumFilePath.NEWS.getMsg().equals(fileType)) ?
                EnumFilePath.NEWS.getValue() :
                (EnumFilePath.BANNER.getMsg().equals(fileType)) ?
                        EnumFilePath.BANNER.getValue() :
                        EnumFilePath.AVATAR.getValue();
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

    /**
     * news列表
     */
    @GetMapping("/getNews")
    public ResultMessage getNews(
            @RequestParam(name = "startIndex", required = false, defaultValue = "1") Integer startIndex,
            @RequestParam(name = "pageSize", required = false, defaultValue = "10") Integer pageSize,
            @RequestParam(name = "title", required = false, defaultValue = "") String title
    ) {
        try {
            List<TNews> list = !StringUtils.isEmpty(title) ?
                    tNewsService.list(new QueryWrapper<TNews>().lambda()
                            .like(TNews::getSubtitle, title)
                            .or()
                            .like(TNews::getTitle, title)
                    ) : tNewsService.list();
            List<TNews> result = list.stream()
                    .sorted(((o1, o2) -> -o1.getCreateTime().compareTo(o2.getCreateTime())))
                    .skip(startIndex).limit(pageSize).collect(Collectors.toList());
            PageInfo<TNews> pageInfos = new PageInfo<>(startIndex, pageSize, list.size(), result);
            return ResultMessage.getDefaultResultMessage(200, pageInfos);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResultMessage.getDefaultResultMessage(500, "获取列表失败");
    }

    /**
     * add news
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
     * del news
     *
     * @param newId
     * @return
     */
    @GetMapping("delNews")
    public ResultMessage delNews(
            @RequestParam(name = "newId") Integer newId
    ) {
        boolean result = tNewsService.removeById(newId);
        return result ? ResultMessage.getDefaultResultMessage(200, "新闻信息删除成功") :
                ResultMessage.getDefaultResultMessage(500, "新闻信息删除失败");
    }

    /**
     * users列表
     */
    @GetMapping("/getUsers")
    public ResultMessage getUsers(
            @RequestParam(name = "startIndex", required = false, defaultValue = "1") Integer startIndex,
            @RequestParam(name = "pageSize", required = false, defaultValue = "10") Integer pageSize,
            @RequestParam(name = "userName", required = false, defaultValue = "") String userName
    ) {
        try {
            List<TUser> list = !StringUtils.isEmpty(userName) ?
                    tUserService.list(new QueryWrapper<TUser>().lambda()
                            .like(TUser::getNickName, userName)
                            .or()
                            .like(TUser::getRealName, userName)
                    ) : tUserService.list();
            List<TUser> result = list.stream()
                    .sorted(((o1, o2) -> -o1.getCreateTime().compareTo(o2.getCreateTime())))
                    .skip(startIndex).limit(pageSize).collect(Collectors.toList());
            PageInfo<TUser> pageInfos = new PageInfo<>(startIndex, pageSize, list.size(), result);
            return ResultMessage.getDefaultResultMessage(200, pageInfos);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResultMessage.getDefaultResultMessage(500, "获取列表失败");
    }

    /**
     * del user
     *
     * @param userId
     * @return
     */
    @GetMapping("delUser")
    public ResultMessage delUser(
            @RequestParam(name = "userId") Integer userId
    ) {
        boolean result = tUserService.removeById(userId);
        return result ? ResultMessage.getDefaultResultMessage(200, "删除用户成功") :
                ResultMessage.getDefaultResultMessage(500, "用户删除失败");
    }

    /**
     * login user
     *
     * @param
     * @return
     */
    @GetMapping("userLogin")
    public ResultMessage userLogin(
            @RequestParam(name = "account") String account,
            @RequestParam(name = "password") String password
    ) {
        if (StringUtils.isEmpty(account) || StringUtils.isEmpty(password))
            return ResultMessage.getDefaultResultMessage(500, "账号或者密码不能为空");
        TUser one = tUserService.getOne(new QueryWrapper<TUser>().lambda().eq(TUser::getAccount, account));
        if(one==null)
            return ResultMessage.getDefaultResultMessage(500, "该用户不存在");
        if(!password.equals(one.getPassword()))
            return ResultMessage.getDefaultResultMessage(500, "密码错误");
        return ResultMessage.getDefaultResultMessage(200, "登录成功",one);
    }

}
