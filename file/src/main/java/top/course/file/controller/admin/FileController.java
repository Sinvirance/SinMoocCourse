package top.course.file.controller.admin;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.course.server.dto.PageDto;
import top.course.server.dto.ResponseDto;
import top.course.server.service.FileService;

import javax.annotation.Resource;

/**
 * @Author: Sinvirance
 * @Date: 2021/02/09 01:53
 * @Description: File控制层
 */

@RestController
@RequestMapping(value = "/admin/file")
public class FileController {

    private static final Logger LOG = LoggerFactory.getLogger(FileController.class);

    /* File控制器 FileController 标识名 */
    public static final String BUSINESS_NAME = "文件";

    @Resource
    private FileService fileService;

    /**
     * 查询: File对象分页列表
     * @param pageDto 分页信息传输对象
     * @return 统一返回响应对象
     */
    @PostMapping(value = "/list")
    public ResponseDto<PageDto> list(@RequestBody(required = false) PageDto pageDto) {
        ResponseDto<PageDto> responseDto = new ResponseDto<>();
        fileService.list(pageDto);
        responseDto.setContent(pageDto);
        return responseDto;
    }
}