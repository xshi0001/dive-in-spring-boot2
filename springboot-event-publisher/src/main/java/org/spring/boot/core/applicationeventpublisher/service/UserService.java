package org.spring.boot.core.applicationeventpublisher.service;


import org.spring.boot.core.applicationeventpublisher.domain.User;

/**
 * @author wangyan@163.com
 * @version 1.0
 * @date 2020-05-06 15:57
 */
public interface UserService {

    /**
     * add user
     *
     * @param user
     * @return
     */
    User addUser(User user);

    /**
     * updateUser
     *
     * @param user
     */
    void updateUser(User user);
}
