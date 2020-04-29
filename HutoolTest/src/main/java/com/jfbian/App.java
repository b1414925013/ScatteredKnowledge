package com.jfbian;

import cn.hutool.setting.Setting;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.File;

@SpringBootApplication
@Slf4j
public class App implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println("============================");
        log.info("我是lombok插件输出的日志");

        //读取classpath下的XXX.setting，不使用变量
        // Setting setting = new Setting("example.setting");
        String path = System.getProperty("user.dir") + File.separator + "config" + File.separator;
        Setting setting = new Setting(path + "example.setting");
        //获取分组为group下key为name的值
        String[] demos = setting.getStrings("ds.setting.path", "demo");
        for (String demo : demos) {
            System.out.println(demo);
        }
        String str = setting.get("demo", "ds.setting.path");
        String s = setting.get("ds.setting.path");
        System.out.println(str);
        System.out.println(s);
    }
}
