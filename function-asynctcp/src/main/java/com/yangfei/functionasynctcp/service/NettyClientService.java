package com.yangfei.functionasynctcp.service;

public interface NettyClientService {
    boolean sendMsg(String text, String dataId, String serviceId);

    String sendSyncMsg(String text, String dataId, String serviceId);

    void ackSyncMsg(String uuid,String msg);
}
