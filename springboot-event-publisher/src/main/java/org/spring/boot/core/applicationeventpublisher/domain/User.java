package org.spring.boot.core.applicationeventpublisher.domain;

import lombok.Data;

/**
 * 用户实体类
 *
 * @author wangyan@163.com
 * @version 1.0
 * @date 2020-05-06 15:46
 */
@Data
public class User {
    private String name;
    private Integer sex;
    private Integer age;
    private String phone;
    private String email;
    private String address;
}
