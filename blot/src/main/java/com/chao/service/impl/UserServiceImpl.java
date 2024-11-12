package com.chao.service.impl;

import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.digest.MD5;
import cn.hutool.jwt.JWT;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.conditions.query.QueryChainWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.chao.dto.Result;
import com.chao.dto.UserInfo;
import com.chao.mapper.RoleMapper;
import com.chao.mapper.UserMapper;
import com.chao.pojo.Role;
import com.chao.pojo.User;
import com.chao.service.UserService;
import com.chao.utils.ThreadLocalUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper,User> implements UserService {
    public static final byte[] JWT_KEY = "xiazidaihao".getBytes();
    @Autowired
    RoleMapper roleMapper;
    @Autowired
    AuthenticationManager authenticationManager;
        /*
        注册账户
        * */
    public Result register(String name,String password){
        //判断数据库中是否存在该用户
        if(getBaseMapper().selectCount(new QueryWrapper<User>().eq("username",name)) > 0){
            return Result.error("用户名已存在");
        }
        //不存在则注册
        boolean result = save(new User().setUserName(name).setPassword(SecureUtil.md5(password)));
        return result?Result.success():Result.error("失败");
    }
        /*
        * 登录
        * */
    @Override
    public Result login(String name, String password) {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(name,password);
        Authentication authenticate = authenticationManager.authenticate(authenticationToken);
        if(Objects.isNull(authenticate)){
            throw new RuntimeException("登录失败");
        }
        String token = JWT.create()
                        .setPayload("name", name)
                        .setKey(JWT_KEY).sign();
        return Result.success(token,"登录成功");
    }
/*
* 展现信息
* */
    @Override
    public Result showInfo(Integer id) {
        User user = getById(id);
        UserInfo userInfo = new UserInfo();
        userInfo.setId(user.getId()).setUserName(user.getUserName()).setEmail(user.getEmail())
                .setAvatar(user.getAvatar()).setLastLogin(user.getLastLogin()).setStatus(user.getStatus());
        Role role = roleMapper.selectById(user.getRoleId());
        userInfo.setRole(role);
        return Result.success(userInfo);
    }


    /*
* 更新信息
* */
    @Override
    public Result update(String email, String avatar) {
        Integer id = ThreadLocalUtils.get();
        boolean result = update().set("email", email).set("avatar", avatar).eq("id", id).update();
        return result?Result.success():Result.error("失败");
    }
    /*
     * 更新密码
     * */
    @Override
    public Result updatePassword(String oldpassword,String newpassword) {
        Integer id = ThreadLocalUtils.get();
        String password = getById(id).getPassword();
        //判断旧密码
        if(!password.equals(oldpassword)){
            return Result.error("密码错误");
        }
        //更新数据
        boolean result = update().set("password", SecureUtil.md5(password)).eq("id", id).update();
        return result?Result.success():Result.error("失败");
    }


}
