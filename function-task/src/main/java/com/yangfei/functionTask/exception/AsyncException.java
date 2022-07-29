package com.yangfei.functionTask.exception;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * <p>
 *
 * </p>
 *
 * @author yangfei
 * @since 2022/7/28 17:41
 */
@Data
@AllArgsConstructor
public class AsyncException extends Exception{
    private int code;
    private String errorMessage;
}
