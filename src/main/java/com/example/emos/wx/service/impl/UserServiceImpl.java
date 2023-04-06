package com.example.emos.wx.service.impl;

import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.example.emos.wx.db.dao.TbUserDao;
import com.example.emos.wx.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Set;

@Service
@Slf4j
@Scope("prototype")
public class UserServiceImpl implements UserService {
    @Value("${wx.app-id}")
    private String appId;
    @Value("${wx.app-secret}")
    private String appSecret;
    @Autowired
    private TbUserDao userDao;
    private String getOpenId(String code){
        String url = "https://api.weixin.qq.com/sns/jscode2session";
        HashMap map = new HashMap();
        map.put("appid",appId);
        map.put("secret",appSecret);
        map.put("js_code",code);
        map.put("grant_type","authorization_code");
        String response = HttpUtil.post(url,map);
        JSONObject json = JSONUtil.parseObj(response);
        String openId = json.getStr("openid");
        if (openId == null || openId.length() == 0){
            throw new RuntimeException("临时登录凭证错误");
        }
        return openId;
    }

    @Override
    public int registerUser(String registerCode, String code, String nickName, String photo) {
        //如果邀请码是000000，代表是超级管理员
        if(registerCode.equals("000000")){
            //查询超级管理员账户是否已经绑定
            boolean bool = userDao.havaRootUser();
            if (!bool){
                //把当前用户绑定到ROOT账户
                String openId = getOpenId(code);
                HashMap param = new HashMap();
                param.put("openId",openId);
                param.put("nickname",nickName);
                param.put("photo",photo);
                param.put("role","[0");
                param.put("status",1);
                param.put("crateTime",new Date());
                param.put("root",true);
                userDao.insert(param);
                int id = userDao.searchIdByOpenId(openId);
                return id;
            }else {
                //如果root已经被绑定了，就抛出异常
                throw new RuntimeException("无法绑定超级管理员账号");
            }
        }
        //TODO 此处还有其他判断内容
        else {
            return 0;
        }
    }

    @Override
    public Set<String> searchUserPermissions(int userId) {
        Set<String> permissions=userDao.searchUserPermissions(userId);
        return permissions;
    }
}
