package net.jrz.tlias.controller;

import lombok.extern.slf4j.Slf4j;
import net.jrz.tlias.pojo.Result;
import net.jrz.tlias.utils.AliyunOSSOperator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Objects;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/upload")
public class uploadController {

    // 阿里云oss服务
    @Autowired
    private AliyunOSSOperator aliyunOSSOperator;

    // 上传目录处
    private static final String UPLOAD_DIR = "/home/jrz/codes/spring/images/";

    /*
    * 上传文件 - 参数名 file 使用本地存储
    * */
    @PostMapping
    public Result upload(String username, Integer age, @RequestParam("file") MultipartFile image) throws IOException {
        /*
        * getOriginalFilename 获取原始文件名
        * transferTo 将接收的文件转存到磁盘文件中
        * getSize 获取文件大小(字节数)
        * getBytes 获取文件的字节数组
        * getInputStream 获取到接收到的文件内容的输入流
        * */
        log.info("上传文件: {}, {}, {}", username, age, image);
        if (!image.isEmpty()){
            // 生成唯一文件名
            String originalFileName = image.getOriginalFilename();
            String extName = Objects.requireNonNull(originalFileName).substring(originalFileName.lastIndexOf("."));
            String uniqueFileName = UUID.randomUUID().toString().replace("-", "") + extName;

            // 拼接完整的路径
            File targetFile = new File(UPLOAD_DIR + uniqueFileName);

            if (!targetFile.getParentFile().exists()){
                // 创建多级目录
                targetFile.getParentFile().mkdirs();
            }

            // 转存至如下本地位置
            image.transferTo(targetFile);
        }
        return Result.success();
    }

    /*
    * 上传文件，使用阿里云OSS对象存储服务
    * */
    @PostMapping("/oss")
    public Result uploadByOss(@RequestParam("file") MultipartFile image){
        log.info("上传文件: {}", image);
        if (!image.isEmpty()){
            // 生成唯一文件名
            String originalFilename = image.getOriginalFilename();
            String extName = originalFilename.substring(originalFilename.lastIndexOf("."));
            String uniqueFileName = UUID.randomUUID().toString().replace("-", "") + extName;

            // 上传文件
            try {
                String url = aliyunOSSOperator.upload(image.getBytes(), uniqueFileName);
                return Result.success(url);
            } catch (IOException e) {
                e.printStackTrace(System.out);
                return Result.error("upload failure");
            }
        }
        return Result.error("the file is allowed to be empty");
    }
}
