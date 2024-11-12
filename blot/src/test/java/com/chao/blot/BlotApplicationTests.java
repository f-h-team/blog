package com.chao.blot;

import cn.hutool.crypto.SecureUtil;
import com.chao.dto.Result;
import com.chao.mapper.BlogMapper;
import com.chao.mapper.CommentsMapper;
import com.chao.pojo.Blog;
import com.chao.pojo.Category;
import com.chao.pojo.Comments;
import com.chao.service.BlogService;
import com.chao.service.CommentsService;
import com.chao.service.UserService;
import com.chao.utils.Md5Encoding;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.aop.framework.AopContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@SpringBootTest
class BlotApplicationTests {

    @Autowired
    BlogService blogService;
    @Autowired
    CommentsService commentsService;
@Test

    public void text() {
    System.out.println(new Md5Encoding().encode("123456"));
}

}
