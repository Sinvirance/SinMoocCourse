package top.course.file.controller.admin;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import top.course.server.dto.FileDto;
import top.course.server.dto.ResponseDto;
import top.course.server.enums.FileUseEnum;
import top.course.server.service.FileService;
import top.course.server.util.Base64ToMultipartFile;

import javax.annotation.Resource;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * @Author: Sinvirance
 * @Date: 2021/02/07 01:13
 * @Description: file upload 控制层
 */

@RestController
@RequestMapping(value = "/admin")
public class UploadController {

    private static final Logger LOG = LoggerFactory.getLogger(UploadController.class);

    public static final String BUSINESS_NAME = "文件上传";

    @Value("${file.path}")
    private String FILE_PATH;

    @Value("${file.domain}")
    private String FILE_DOMAIN;

    @Resource
    private FileService fileService;

    /**
     * 讲师头像文件上传
     * @return 统一返回响应对象
     */
    @PostMapping("upload")
    public ResponseDto upload(@RequestBody FileDto fileDto) throws Exception {
        LOG.info("上传文件开始");

        String use = fileDto.getUse();
        String key = fileDto.getKey();
        String suffix = fileDto.getSuffix();
        String shardBase64 = fileDto.getShard();
        MultipartFile shard = Base64ToMultipartFile.base64ToMultipart(shardBase64);


        /* 保存文件 */
        /* 获取前端传递的文件枚举类型 */
        FileUseEnum fileUseEnum = FileUseEnum.getByCode(use);

        /* 获取文件枚举类型名并根据名称创建文件夹当不存在时 */
        String dir = fileUseEnum.name().toLowerCase();
        File fullDir = new File(FILE_PATH + dir);
        if (!fullDir.exists()) {
            fullDir.mkdir();
        }

        /* 相对路径: File.separator 相当于文件夹分隔符 \ */
        String path = new StringBuffer(dir).
                append(File.separator).
                append(key).
                append(".").
                append(suffix).
                toString();
        String localPath = new StringBuffer(path).
                append(".").
                append(fileDto.getShardIndex()).
                toString();

        String fullPath = FILE_PATH + localPath;
        File dest = new File(fullPath);
        shard.transferTo(dest);
        LOG.info(dest.getAbsolutePath());

        /* 保存文件记录 */
        LOG.info("保存文件记录开始");
        fileDto.setPath(FILE_DOMAIN + path);
        fileService.save(fileDto);

        ResponseDto responseDto = new ResponseDto();
        /* 将图片位置返回前端 */
        fileDto.setPath(FILE_DOMAIN + path);
        responseDto.setContent(fileDto);

        if (fileDto.getShardIndex() == fileDto.getShardTotal()) {
            this.merge(fileDto);
        }

        return responseDto;
    }


    /**
     * 测试：已有的分片追加合并
     * @return 统一返回响应对象
     */
    public void merge(FileDto fileDto) throws Exception {
        LOG.info("合并分片开始");
        String path = fileDto.getPath().replace(FILE_DOMAIN, "");
        Integer shardTotal = fileDto.getShardTotal();
        File newFile = new File(FILE_PATH + path);
        FileOutputStream outputStream = new FileOutputStream(newFile, true);//文件追加写入
        FileInputStream fileInputStream = null;//分片文件
        byte[] byt = new byte[10 * 1024 * 1024];
        int len;

        try {
            for (int i = 0; i < shardTotal; i++) {
                fileInputStream = new FileInputStream(new File(FILE_PATH + path + "." + (i + 1)));
                while ((len = fileInputStream.read(byt)) != -1) {
                    outputStream.write(byt, 0, len);
                }
            }
        } catch (IOException e) {
            LOG.error("分片合并异常", e);
        } finally {
            try {
                if (fileInputStream != null) {
                    fileInputStream.close();
                }
                outputStream.close();
                LOG.info("IO流关闭");
            } catch (Exception e) {
                LOG.error("IO流关闭", e);
            }
        }
        LOG.info("合并分片结束");
    }
}