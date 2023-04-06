package com.example.emos.wx.controller.form;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Schema(description = "say hello test")
@Data
public class TestSayHelloForm {
    @NotBlank
//    @Pattern(regexp = "^[\\u4e00-\\u9fa5]{2,15}$", message = "*******************************请输入中文******************************")
    @Schema(description = "姓名")
    private String name;
}
