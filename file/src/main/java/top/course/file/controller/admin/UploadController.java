package top.course.file.controller.admin;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import top.course.server.dto.ResponseDto;
import top.course.server.util.UUIDUtil;

import java.io.File;
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


    /**
     * 讲师头像文件上传
     * @param file 上传的文件
     * @return 统一返回响应对象
     */
    @PostMapping("upload")
    public ResponseDto upload(@RequestParam MultipartFile file) throws IOException {
        LOG.info("文件上传开始：{}", file);
        String filename = file.getOriginalFilename();
        LOG.info(filename);
        LOG.info(String.valueOf(file.getSize()));

        /* 保存文件 */
        String key = UUIDUtil.getShortUUID();
        String fullPath = "D:\\Sinvirance\\WorkSpace\\JavaProject\\SinMooc\\static\\teacher\\" + key + "-" + filename;
        File dest = new File(fullPath);
        file.transferTo(dest);
        LOG.info(dest.getAbsolutePath());

        ResponseDto responseDto = new ResponseDto();
        /* 将图片位置返回前端 */
        responseDto.setContent("http://127.0.0.1:9000/file/f/teacher/" + key + "-" + filename);
        return responseDto;
    }

}