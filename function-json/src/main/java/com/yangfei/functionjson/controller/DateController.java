package com.yangfei.functionjson.controller;

import com.yangfei.functionjson.model.DateModel;
import com.yangfei.functionjson.service.DateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

/**
 * <p>
 *
 * </p>
 *
 * @author yangfei
 * @since 2022/5/20 14:19
 */
@RestController
@RequestMapping("/date")
public class DateController {
    @Autowired
    private DateService dateService;

    @PostMapping("/add")
    public Object add(@RequestBody DateModel entity,
            @DateTimeFormat(pattern = "yyyy-MM-dd") Date date) {
        DateModel ret = dateService.add(entity);
        return ret;
    }
}
