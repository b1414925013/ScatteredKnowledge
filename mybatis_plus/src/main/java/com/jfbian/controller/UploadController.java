package com.jfbian.controller;

import com.jfbian.model.CodeMsg;
import com.jfbian.model.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@RestController
@Slf4j
public class UploadController {
    @PostMapping("/upload")
    public Result<Object> upload(@RequestParam("uploadfile") MultipartFile file) {
        if (file.isEmpty()) {
            return Result.error(CodeMsg.NOT_FIND_DATA,"上传失败");
        }
        String fileName = file.getOriginalFilename();
        String filePath = "D:/tmp/";
        File dest = new File(filePath + fileName);
        try {
            file.transferTo(dest);
            log.info("上传成功");
            return Result.success("上传成功");
        } catch (IOException e) {
            log.error(e.toString(), e);
        }
        return Result.error(CodeMsg.NOT_FIND_DATA,"上传失败");
    }
    @PostMapping("/multiUpload")
    public Result<Object> multiUpload(@RequestParam("uploadfile") MultipartFile[] files) {

        String filePath = "D:/tmp/";
        for (int i = 0; i < files.length; i++) {
            MultipartFile file = files[i];
            if (file.isEmpty()) {
                return Result.error(CodeMsg.NOT_FIND_DATA,"上传失败");
            }
            String fileName = file.getOriginalFilename();

            File dest = new File(filePath + fileName);
            try {
                file.transferTo(dest);
                log.info("第" + (i + 1) + "个文件上传成功");
            } catch (IOException e) {
                log.error(e.toString(), e);
                return Result.error(CodeMsg.NOT_FIND_DATA,"上传失败");
            }
        }
        return Result.success("上传成功");
    }
}
