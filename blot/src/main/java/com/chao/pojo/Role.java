package com.chao.pojo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

@Data
@TableName("m_role")
@Accessors(chain = true)
public class Role implements Serializable {
    private Integer id;
    @TableField("`name`")
    @NotEmpty
    private String name;
    @TableField("`desc`")
    private String desc;
}
