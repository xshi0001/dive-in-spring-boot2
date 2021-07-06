package org.spring.boot.core.applicationeventpublisher.domain;

import lombok.Getter;

import java.io.Serializable;

/**
 * 操作类型枚举类
 *
 * @author wangyan@163.com
 * @version 1.0
 * @date 2020-05-06 16:06
 */
public enum EnumUserOperate implements Serializable {
    ADD("add", 0, "新增"),
    UPDATE("update", 1, "修改"),
    DELETE("delete", 2, "删除");

    private String name;
    private Integer value;
    private String desc;

    EnumUserOperate(String name, Integer value, String desc) {
        this.name = name;
        this.value = value;
        this.desc = desc;
    }

    /**
     * 根据value获得EnumUserOperate
     *
     * @param value
     * @return
     */
    public static EnumUserOperate getByValue(Integer value) {
        for (EnumUserOperate e : values()) {
            if (e.getValue().equals(value)) {
                return e;
            }
        }
        return null;
    }

    public String getName() {
        return name;
    }

    public Integer getValue() {
        return value;
    }

    public String getDesc() {
        return desc;
    }
}
