package com.chao.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.chao.dto.Result;
import com.chao.pojo.User;

public interface UserService extends IService<User> {
    Result register(String name, String password);
    Result login(String name, String password);
    Result showInfo(Integer id);
    Result update(String email,String avatar);
    Result updatePassword( String oldpassword, String newpassword);
}
