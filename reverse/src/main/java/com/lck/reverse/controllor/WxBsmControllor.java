package com.lck.reverse.controllor;

import com.lck.reverse.entity.TBannerImg;
import com.lck.reverse.entity.TNews;
import com.lck.reverse.entity.respon.ResultMessage;
import com.lck.reverse.service.impl.BannerImageServiceImpl;
import com.lck.reverse.service.impl.TNewsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.Date;

@RestController
@RequestMapping("/bsm")
public class WxBsmControllor {

    @Autowired
    private TNewsServiceImpl tNewsService;
    @Autowired
    private BannerImageServiceImpl bannerImageService;

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
}
