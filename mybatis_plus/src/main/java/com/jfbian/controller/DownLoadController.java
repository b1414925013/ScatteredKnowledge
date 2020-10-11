package com.jfbian.controller;

import com.jfbian.util.FileUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

@RestController
@Slf4j
public class DownLoadController {
    /**
     * 下载文件
     *
     * @param response
     */
    @RequestMapping("/downFile")
    public void Download(HttpServletResponse response) {
        String fileName = "测试下载文件.xlsx";
//        String fileName = "allure-commandline-2.13.3.zip";
        String result = FileUtils.downloadFile(response, fileName);
        log.info("下载结果:{}", result);
    }

    /**
     * 下载文件
     *
     * @param response
     */
    @PostMapping("/downFile2")
    public void Download2(@RequestParam("name1") String name1, @RequestParam("name2") String name2, HttpServletResponse response) {
        System.out.println("name1:" + name1);
        System.out.println("name2:" + name2);
        String fileName = "测试下载文件.xlsx";
        String result = FileUtils.downloadFile(response, fileName);
        log.info("下载结果:{}", result);
    }
}
