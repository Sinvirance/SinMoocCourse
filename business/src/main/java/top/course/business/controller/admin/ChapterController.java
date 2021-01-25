package top.course.business.controller.admin;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import top.course.server.dto.ChapterDto;
import top.course.server.dto.PageDto;
import top.course.server.dto.ResponseDto;
import top.course.server.service.ChapterService;
import top.course.server.util.ValidatorUtil;

import javax.annotation.Resource;

/**
 * @Author: Sinvirance
 * @Date: 2020/12/27 19:30
 * @Description: 大章查询控制层
 */

@RestController
@RequestMapping(value = "/admin/chapter")
public class ChapterController {

    private static final Logger LOG = LoggerFactory.getLogger(ChapterController.class);

    /*
     * 大章控制器 ChapterController 标识名
     */
    public static final String BUSINESS_NAME = "大章";

    @Resource
    private ChapterService chapterService;

    /**
     * 所有大章数据查询显示列表
     * @param pageDto 分页信息传输对象
     * @return 统一返回响应对象
     */
    @PostMapping(value = "/list")
    public ResponseDto<PageDto> list(@RequestBody(required = false) PageDto pageDto) {
        ResponseDto<PageDto> responseDto = new ResponseDto<>();
        chapterService.list(pageDto);
        responseDto.setContent(pageDto);
        return responseDto;
    }

    /**
     * 表单添加大章保存功能
     * @param chapterDto 大章数据传输对象
     * @return 统一返回响应对象
     */
    @PostMapping("/save")
    public ResponseDto<ChapterDto> save(@RequestBody ChapterDto chapterDto) {
        /*
         * 后端保存进行校验
         */
        ValidatorUtil.require(chapterDto.getName(), "名称");
        ValidatorUtil.require(chapterDto.getCourseId(), "课程ID");
        ValidatorUtil.length(chapterDto.getCourseId(), "课程ID", 1, 8);

        ResponseDto<ChapterDto> responseDto = new ResponseDto<>();
        chapterService.save(chapterDto);
        responseDto.setContent(chapterDto);
        return responseDto;
    }

    /**
     * 删除指定id的大章
     * @param id 大章数据传输对象
     * @return 统一返回响应对象
     */
    @DeleteMapping("/delete/{id}")
    public ResponseDto<ChapterDto> delete(@PathVariable String id) {
        ResponseDto<ChapterDto> responseDto = new ResponseDto<>();
        chapterService.delete(id);
        return responseDto;
    }

}