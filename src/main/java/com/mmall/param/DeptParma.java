package com.mmall.param;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;

@Setter
@Getter
@ToString
public class DeptParma {

    private Integer id;

    @NotBlank(message = "部门名称不能为空")
    @Length(max = 15,min = 2,message = "名字长度在2-15之间")
    private String name;

    private Integer parentId =0;

    @NotNull(message = "展示顺序不可以为空")
    private Integer seq;

    @Length(max = 150,message = "注释需要小于150")
    private String remark;
}
