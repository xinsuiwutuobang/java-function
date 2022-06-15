package com.yangfei.functionjson.service;

import com.yangfei.functionjson.model.DateModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * <p>
 *
 * </p>
 *
 * @author yangfei
 * @since 2022/5/20 14:16
 */
@Slf4j
@Service
public class DateServiceImpl implements DateService{
    @Override
    public DateModel add(DateModel entity) {
        log.info(entity.toString());
        return entity;
    }
}
