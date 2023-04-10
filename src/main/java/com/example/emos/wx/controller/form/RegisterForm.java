package com.example.emos.wx.controller.form;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
@Schema
public class RegisterForm {
    @NotBlank(message = "注册码不能为空")
    @Pattern(regexp = "^[0-9]{6}$", message = "注册码必须是6位数字")
    private String registerCode;
    @NotBlank(message = "微信临时授权不能为空")
    private String code;
    @NotBlank(message = "昵称不能为空")
    private String nickName;
    @NotBlank(message = "头像不能为空")
    private String photo;
}
