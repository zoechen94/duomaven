package com.zoe.controller;

import com.zoe.service.ArticleService;
import com.zoe.service.spring.resultInfo.ResultData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by 陈亚兰 on 2018/6/12.
 */
@RequestMapping("/article")
@RestController
public class ArticleController {
    @Autowired
    private ArticleService articleService;

    @GetMapping("/getAll")
    public ResultData getAllArticle(){
        return ResultData.success(articleService.selectAll());
    }
}
