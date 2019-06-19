package com.gj.pubsub;

import lombok.extern.slf4j.Slf4j;

/**
 * @author Gjing
 *
 * 消息监听者
 **/
@Slf4j
public class Receiver {

    public void receiveMessage(String message) {
        log.info("监听者1收到消息：{}", message);
    }
}
