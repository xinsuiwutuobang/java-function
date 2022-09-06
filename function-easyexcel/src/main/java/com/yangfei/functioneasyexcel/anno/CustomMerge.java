package com.yangfei.functioneasyexcel.anno;

import java.lang.annotation.*;

/**
 * <p>
 * 自定义合并，用于判断师傅需要合并以及合并的关联主键
 * </p>
 *
 * @author yangfei
 * @since 2022/6/14 16:05
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface CustomMerge {
    /**
     * 是否需要合并单元格
     * @return
     */
    boolean needMerge() default false;

    /**
     * 是否为主键,即该字段相同的行合并
     * @return
     */
    boolean isPk() default false;
}
