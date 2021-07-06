package org.spring.boot.core.applicationeventpublisher.event;

import lombok.extern.slf4j.Slf4j;
import org.spring.boot.core.applicationeventpublisher.domain.User;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 自定义监听类
 *
 * @author wangyan@163.com
 * @version 1.0
 * @date 2020-05-06 16:42
 */
@Component
@Slf4j
public class UserMonitor {

    /**
     * 统计监听事件个数
     */
    private static final AtomicInteger count = new AtomicInteger(0);

    /**
     * 监听新增用户事件
     * 异步操作，使用自定义线程池
     * EventListener中细化监听具体某种事件
     *
     * @param event
     * @return
     */
    @Async("lazyTraceExecutor")
    @EventListener(value = UserActionEvent.class, condition = "#event.operate.name()=='ADD'")
    public CompletableFuture<UserActionEvent> addUserApplicationEvent(UserActionEvent event) {
        try {
            count.incrementAndGet();
            User user = event.getUser();
            log.info("监听到新增用户：{}", user);
            // 自定义处理......
            log.info("此处可操作：发送邮件、赠送用户体验卡券.....");
        } catch (Exception e) {
            log.error("事件:{},执行异常:{}", event, e.getMessage());
            return CompletableFuture.completedFuture(event);
        }
        return CompletableFuture.completedFuture(null);
    }

    @Async("lazyTraceExecutor")
    @EventListener(value = UserActionEvent.class, condition = "#event.operate.name()=='UPDATE'")
    public CompletableFuture<UserActionEvent> updateUserApplicationEvent(UserActionEvent event) {
        try {
            User user = event.getUser();
            log.info("监听到修改用户：{}", user);
            // 自定义处理......
        } catch (Exception e) {
            log.error("事件:{},执行异常:{}", event, e.getMessage());
            return CompletableFuture.completedFuture(event);
        }
        return CompletableFuture.completedFuture(null);
    }

    @Async
    @EventListener(value = UserActionEvent.class, condition = "#event.operate.name()=='DELETE'")
    public CompletableFuture<UserActionEvent> deleteUserApplicationEvent(UserActionEvent event) {
        try {
            User user = event.getUser();
            log.info("监听到删除用户：{}", user);
            // 自定义处理......
        } catch (Exception e) {
            log.error("事件:{},执行异常:{}", event, e.getMessage());
            return CompletableFuture.completedFuture(event);
        }
        return CompletableFuture.completedFuture(null);
    }


}
