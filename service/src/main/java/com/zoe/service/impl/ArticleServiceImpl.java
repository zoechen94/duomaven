package com.zoe.service.impl;

import com.zoe.entity.Article;
import com.zoe.mapper.ArticleMapper;
import com.zoe.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by 陈亚兰 on 2018/6/12.
 */
@Service
public class ArticleServiceImpl implements ArticleService {
    @Autowired
    private ArticleMapper articleMapper;
    @Override
    public List<Article> selectAll() {
        return articleMapper.selectAll();
    }
}
