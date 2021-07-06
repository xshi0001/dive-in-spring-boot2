# springboot-event-publisher

使用场景：

事件发布订阅，有这么一个业务场景：当用户注册后，发送邮件到其邮箱提示用户进行账号激活，且注册成功的同时需要赠送新人用户体验卡券。


使用方法：
- 自定义event

继承ApplicationEvent，定义事件构成（封装一些你需要的数据信息，比如user信息），[UserActionEvent.java](src/main/java/org/spring/boot/core/applicationeventpublisher/event/UserActionEvent.java)

- 发布事件

在业务类中通过自动注入ApplicationEventPublisher的bean实例，执行publishEvent方法，[UserServiceImpl.java](src/main/java/org/spring/boot/core/applicationeventpublisher/service/impl/UserServiceImpl.java)

- 监听事件

通过新建监听bean, 使用@EventListener注解，表明事件的条件condition,对事件进行处理

改进：

-  线程池异步执行多个监听任务

当有多个监听器的时候，并发操作如何保证不会阻塞、各个监听器之间互不影响呢，这里使用线程池异步的方式执行监听处理任务。


## Reference

- [SpringBoot 中发布ApplicationEventPublisher，监听ApplicationEvent 异步操作](https://blog.csdn.net/weixin_43770545/article/details/105971971)

