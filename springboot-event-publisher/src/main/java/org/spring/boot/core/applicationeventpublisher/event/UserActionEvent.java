package org.spring.boot.core.applicationeventpublisher.event;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.spring.boot.core.applicationeventpublisher.domain.EnumUserOperate;
import org.spring.boot.core.applicationeventpublisher.domain.User;
import org.springframework.context.ApplicationEvent;

/**
 * @author wangyan@163.com
 * @version 1.0
 * @date 2020-05-06 15:40
 */
@Getter
@Setter
@ToString
public class UserActionEvent extends ApplicationEvent {

    // 操作是否成功
    private Boolean success;

    // 操作类型
    private EnumUserOperate operate;

    // 数据对象
    private User user;

    public UserActionEvent(Object source) {
        // ApplicationEvent构造函数中执行
        super(source);
    }
}
