package com.yangfei.functionasynctcp.controller;

import com.yangfei.functionasynctcp.service.NettyClientService;
import com.yangfei.functionasynctcp.service.impl.NettyClientServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/nc")
public class NettyClientController {
    @Autowired
    private NettyClientService nettyClientService;

    @RequestMapping("/sendMsg")
    public Object sendMsg(String text, String dataId, String serviceId) {
        boolean ret = nettyClientService.sendMsg(text, dataId, serviceId);
        return ret;
    }

    @RequestMapping("/sendSyncMsg")
    public Object sendSyncMsg(String text, String dataId, String serviceId) {
        String ret = nettyClientService.sendSyncMsg(text, dataId, serviceId);
        return ret;
    }

    @RequestMapping("/ackSyncMsg")
    public Object ackSyncMsg(String text) {
        String uuid = UUID.randomUUID().toString();

        nettyClientService.ackSyncMsg(uuid,text);
        return true;
    }
}
