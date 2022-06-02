package com.yangfei.functionjson.convert;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import cn.hutool.core.collection.ListUtil;
import cn.hutool.core.util.StrUtil;

/**
 * <p>
 *
 * </p>
 *
 * @author yangfei
 * @since 2022/5/20 17:38
 */
@Component
@Slf4j
public class GlobalFormatDateConvert implements Converter<String, Date> {

    private static final List<String> dateFormats =
            ListUtil.of("yyyy-MM", "yyyy-MM-dd", "yyyy-MM-dd HH:mm", "yyyy-MM-dd HH:mm:ss");

    @Override
    public Date convert(String source) {
        if (StrUtil.isBlank(source)) {
            return null;
        }
        source = source.trim();
        String format = null;
        if (source.matches("^\\d{4}-\\d{1,2}$")) {
            format = dateFormats.get(0);
        } else if (source.matches("^\\d{4}-\\d{1,2}-\\d{1,2}$")) {
            format = dateFormats.get(0);
        } else if (source.matches("^\\d{4}-\\d{1,2}-\\d{1,2} {1}\\d{1,2}:\\d{1,2}$")) {
            format = dateFormats.get(0);
        } else if (source.matches("^\\d{4}-\\d{1,2}-\\d{1,2}  {1}\\d{1,2}:\\d{1,2}:\\d{1,2}$")) {
            format = dateFormats.get(0);
        } else {
            throw new IllegalArgumentException(
                    String.format("全局日期转换未定义该字符串转Date的日期转换格式，source：%s", source));
        }
        Date ret = parseDate(source, format);
        return ret;
    }

    private Date parseDate(String source, String format) {
        Date date = null;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
        try {
            date = simpleDateFormat.parse(source);
        } catch (ParseException e) {
            log.error(String.format("日期转换异常，source:$s,transform format:%s", source, format), e);
        }
        return date;
    }

}
