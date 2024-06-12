package com.sky.annotation;

import com.sky.enumeration.OperationType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


/**
 * 自动填充
 *
 * @author ZJHAO
 * @date 2024/06/11
 */
// 指定方法只能用于方法
@Target(ElementType.METHOD)
// 运行时有效
@Retention(RetentionPolicy.RUNTIME)
public @interface AutoFill {

    /**
     * 操作类型
     *
     * @return 操作类型
     */
    OperationType value();
}
