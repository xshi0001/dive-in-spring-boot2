package org.spring.boot.core.applicationeventpublisher.service.impl;


import lombok.extern.slf4j.Slf4j;
import org.spring.boot.core.applicationeventpublisher.domain.EnumUserOperate;
import org.spring.boot.core.applicationeventpublisher.domain.User;
import org.spring.boot.core.applicationeventpublisher.event.UserActionEvent;
import org.spring.boot.core.applicationeventpublisher.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

/**
 * @author wangyan@163.com
 * @version 1.0
 * @date 2020-05-06 15:57
 */
@Service
@Slf4j
public class UserServiceImpl implements UserService {
    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;

    @Override
    public User addUser(User user) {

        // 此处自定义操作持久层，该demo中不做演示（方法中直接使用入参对象返回了）......

        // 添加用户操作成功，发布通知：新增user
        UserActionEvent userActionEvent = new UserActionEvent(this);
        userActionEvent.setUser(user);
        userActionEvent.setOperate(EnumUserOperate.ADD);
        userActionEvent.setSuccess(true);

        applicationEventPublisher.publishEvent(userActionEvent);
        log.info("发布add事件:{}", userActionEvent);

        return user;
    }

    @Override
    public void updateUser(User user) {

        // 持久数据库不再演示.....

        // 发布通知修改user
        UserActionEvent userActionEvent = new UserActionEvent(this);
        userActionEvent.setUser(user);
        userActionEvent.setOperate(EnumUserOperate.UPDATE);
        userActionEvent.setSuccess(true);

        applicationEventPublisher.publishEvent(userActionEvent);
        log.info("用户:{},进行修改操作", user);
        log.info("发布update事件:{}", userActionEvent);
    }
}
