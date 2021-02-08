package top.course.business.controller.admin;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import top.course.server.dto.FileDto;
import top.course.server.dto.PageDto;
import top.course.server.dto.ResponseDto;
import top.course.server.service.FileService;
import top.course.server.util.ValidatorUtil;

import javax.annotation.Resource;

/**
 * @Author: Sinvirance
 * @Date: 2021/02/09 01:35
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

    /**
     * 保存: FileDto对象有id属性值时更新，无值时新增
     * @param fileDto 数据传输对象
     * @return 统一返回响应对象
     */
    @PostMapping("/save")
    public ResponseDto<FileDto> save(@RequestBody FileDto fileDto) {
        /* 保存校验 */
        ValidatorUtil.require(fileDto.getPath(), "相对路径");
        ValidatorUtil.length(fileDto.getPath(), "相对路径", 1, 100);
        ValidatorUtil.length(fileDto.getName(), "文件名", 1, 100);
        ValidatorUtil.length(fileDto.getSuffix(), "后缀", 1, 10);

        ResponseDto<FileDto> responseDto = new ResponseDto<>();
        fileService.save(fileDto);
        responseDto.setContent(fileDto);
        return responseDto;
    }

    /**
     * 删除: 指定id的File对象
     * @param id File对象id
     * @return 统一返回响应对象
     */
    @DeleteMapping("/delete/{id}")
    public ResponseDto<FileDto> delete(@PathVariable String id) {
        ResponseDto<FileDto> responseDto = new ResponseDto<>();
        fileService.delete(id);
        return responseDto;
    }
}