package com.mmall.param;


import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class UserParam {

    private Integer id;

    @NotBlank(message = "用户名不能为空")
    @Length(min = 1,max = 40,message = "用户长度不要超过40")
    private String username;
    @NotBlank(message = "电话不能为空")
    @Length(min = 1,max = 20,message = "电话号不能超过20")
    private String telephone;
    @NotBlank(message = "邮箱不能为空")
    @Length(min = 5,max = 30,message = "邮箱长度不要超过30")
    private String mail;
    @NotNull(message = "用户必须是在部门下")
    private Integer deptId;
    @NotNull(message = "必须指定用户状态")
    @Min(value = 0,message = "状态只能在0-2之间")
    @Max(value = 2)
    private Integer status;
    @Length(min = 0,max = 200,message = "长度不要超过200")
    private String remark ="";


}
