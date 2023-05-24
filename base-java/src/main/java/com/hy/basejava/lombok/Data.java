package com.hy.basejava.lombok;

import java.lang.annotation.*;

/**
* Description: 仿lombok编译器注解
* @createDate: 2023/5/24 18:01
* @author haoyong
* @lastModifyBy haoyong
*/
@Documented
@Retention(RetentionPolicy.SOURCE)
@Target(ElementType.TYPE)
public @interface Data {
}
