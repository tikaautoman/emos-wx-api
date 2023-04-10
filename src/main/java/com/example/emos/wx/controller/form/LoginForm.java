package com.example.emos.wx.controller.form;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Schema
@Data
public class LoginForm {
    @NotBlank
    private String code;
}
