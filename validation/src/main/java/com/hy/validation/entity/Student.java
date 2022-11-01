package com.hy.validation.entity;


import com.hy.validation.group.AddGroup;
import com.hy.validation.group.UpdateGroup;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Student {

    @NotNull(groups = {UpdateGroup.class})
    private Integer id;

    @NotBlank
    private String name;

    @NotNull(groups = AddGroup.class)
    private Date createTime;

}
