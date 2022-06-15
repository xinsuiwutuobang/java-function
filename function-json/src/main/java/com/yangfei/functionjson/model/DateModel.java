package com.yangfei.functionjson.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

import cn.hutool.core.date.DatePattern;

/**
 * <p>
 *
 * </p>
 *
 * @author yangfei
 * @since 2022/5/20 14:14
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class DateModel implements Serializable {
    private Long id;
    private String name;
    @DateTimeFormat(pattern = "yy-MM-dd HH:mm:ss")
    private Date createTime;
    private Date updateTime;
}
