package com.web.shopping.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;

@Controller
@RequestMapping("/file")
public class FileUpload {

    @RequestMapping(value = "/upload")
    public ResponseEntity upload(MultipartFile file) throws IOException {
        //获得项目的类路径
        String path = ResourceUtils.getURL("classpath:").getPath();
        //空文件夹在编译时不会打包进入target中
        File uploadDir = new File(path + "/static/images/product");
        if (!uploadDir.exists()) {
            System.out.println("上传头像路径不存在，正在创建...");
            uploadDir.mkdir();
        }
        if (file != null) {
            //获得上传文件的文件名
            String oldName = file.getOriginalFilename();
            System.out.println("[上传的文件名]：" + oldName);
            //我的文件保存在static目录下的avatar/user
            File avatar = new File(path + "/static/images/product/", oldName);
            try {
                //保存图片
                file.transferTo(avatar);
                //返回成功结果，附带文件的相对路径
                return ResponseEntity.ok("images/product/" + oldName);
            } catch (IOException e) {
                e.printStackTrace();
                return ResponseEntity.ok("error");
            }
        } else {
            System.out.println("上传的文件为空");
            return ResponseEntity.ok("error");
        }

    }

}

