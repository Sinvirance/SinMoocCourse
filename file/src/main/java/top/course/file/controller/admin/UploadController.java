package top.course.file.controller.admin;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import top.course.server.dto.FileDto;
import top.course.server.dto.ResponseDto;
import top.course.server.enums.FileUseEnum;
import top.course.server.service.FileService;
import top.course.server.util.UUIDUtil;

import javax.annotation.Resource;
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

    @Value("${file.path}")
    private String FILE_PATH;

    @Value("${file.domain}")
    private String FILE_DOMAIN;

    @Resource
    private FileService fileService;

    /**
     * 讲师头像文件上传
     * @param file 上传的文件
     * @return 统一返回响应对象
     */
    @PostMapping("upload")
    public ResponseDto upload(@RequestParam MultipartFile file, String use) throws IOException {
        LOG.info("文件上传开始");
        String fileName = file.getOriginalFilename();
        LOG.info(fileName);
        LOG.info(String.valueOf(file.getSize()));

        /* 保存文件 */
        String key = UUIDUtil.getShortUUID();
        /* 获取前端传递的文件枚举类型 */
        FileUseEnum fileUseEnum = FileUseEnum.getByCode(use);

        /* 获取文件枚举类型名并根据名称创建文件夹当不存在时 */
        String dir = fileUseEnum.name().toLowerCase();
        File fullDir = new File(FILE_PATH + dir);
        if (!fullDir.exists()) {
            fullDir.mkdir();
        }

        /* 文件格式 */
        String suffix = fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();
        /* 相对路径: File.separator 相当于文件夹分隔符 \ */
        String path = dir + File.separator + key + "." + suffix;
        String fullPath = FILE_PATH + path;
        File dest = new File(fullPath);
        file.transferTo(dest);
        LOG.info(dest.getAbsolutePath());

        /* 保存文件记录 */
        LOG.info("保存文件记录开始");
        FileDto fileDto = new FileDto();
        fileDto.setPath(path);
        fileDto.setName(fileName);
        fileDto.setSize(Math.toIntExact(file.getSize()));
        fileDto.setSuffix(suffix);
        fileDto.setUse(use);
        fileService.save(fileDto);

        ResponseDto responseDto = new ResponseDto();
        /* 将图片位置返回前端 */
        fileDto.setPath(FILE_DOMAIN + path);
        responseDto.setContent(fileDto);
        return responseDto;
    }

}