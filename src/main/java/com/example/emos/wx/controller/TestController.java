package com.example.emos.wx.controller;

import com.example.emos.wx.common.util.R;
//import io.swagger.annotations.Api;
//import io.swagger.annotations.ApiOperation;
import com.example.emos.wx.controller.form.TestSayHelloForm;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/test")
@Tag(name = "测试Web接口", description = "操作描述")
public class TestController {
    @GetMapping("/sayHello")
    @Operation(summary = "最简单的测试方法")
    public R sayHello(@Valid @RequestBody TestSayHelloForm form){
        System.out.println("sayHello method");
        return R.ok().put("message", "**************************************HelloWorld," + form.getName());
    }
}
