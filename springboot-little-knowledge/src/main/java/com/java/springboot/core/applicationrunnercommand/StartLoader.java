package com.java.springboot.core.applicationrunnercommand;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@Order(value = 1)
public class StartLoader implements ApplicationRunner {
    @Override
    public void run(ApplicationArguments args) {
        loadSysParams();
    }

    public void loadSysParams() {
        System.out.println("====================ApplicationRunner================");

        System.out.println("【系统参数1】加载中...");

        System.out.println("【系统参数1】加载完成...");
    }
}