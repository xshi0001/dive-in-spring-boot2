package org.spring.boot.core.applicationeventpublisher.domain;

import lombok.Getter;

import java.io.Serializable;

/**
 * 性别枚举类
 *
 * @author wangyan@163.com
 * @version 1.0
 * @date 2020-05-06 16:06
 */
@Getter
public enum EnumUserSex implements Serializable {
    MAN("man", 0, "男"),
    WOMAN("woman", 1, "女"),
    UNKNOW("unknow", 2, "未知");

    private String name;
    private Integer value;
    private String desc;

    EnumUserSex(String name, Integer value, String desc) {
        this.name = name;
        this.value = value;
        this.desc = desc;
    }

    /**
     * 根据value获得EnumUserSex
     *
     * @param value
     * @return
     */
    public static EnumUserSex getByValue(Integer value) {
        for (EnumUserSex e : values()) {
            if (e.getValue().equals(value)) {
                return e;
            }
        }
        return null;
    }
}
