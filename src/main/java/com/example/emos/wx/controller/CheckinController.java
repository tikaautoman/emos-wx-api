package com.example.emos.wx.controller;

import cn.hutool.core.date.DateUtil;
import com.example.emos.wx.common.util.R;
import com.example.emos.wx.config.shiro.JwtUtil;
import com.example.emos.wx.service.CheckinService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/checkin")
@RestController
@Schema(name = "签到模块Web接口")
@Slf4j
public class CheckinController {
    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private CheckinService checkinService;
    @GetMapping("/validCanCheckIn")
    @Operation(summary = "查看用户今天是否可以签到")
    public R validCanCHeckIn(@RequestHeader("token") String token){
        int userId = jwtUtil.getUserId(token);
        String result = checkinService.validCanCheckIn(userId, DateUtil.today());
        return R.ok(result);
    }
}
