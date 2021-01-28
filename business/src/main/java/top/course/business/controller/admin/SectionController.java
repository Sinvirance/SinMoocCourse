package top.course.business.controller.admin;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import top.course.server.dto.ResponseDto;
import top.course.server.dto.SectionDto;
import top.course.server.dto.SectionPageDto;
import top.course.server.service.SectionService;
import top.course.server.util.ValidatorUtil;

import javax.annotation.Resource;

/**
 * @Author: Sinvirance
 * @Date: 2021/01/25 21:11
 * @Description: Section控制层
 */

@RestController
@RequestMapping(value = "/admin/section")
public class SectionController {

    private static final Logger LOG = LoggerFactory.getLogger(SectionController.class);

    /* Section控制器 SectionController 标识名 */
    public static final String BUSINESS_NAME = "小节";

    @Resource
    private SectionService sectionService;

    /**
     * 查询: Section对象分页列表
     * @param sectionPageDto 分页信息传输对象
     * @return 统一返回响应对象
     */
    @PostMapping(value = "/list")
    public ResponseDto<SectionPageDto> list(@RequestBody(required = false) SectionPageDto sectionPageDto) {
        ResponseDto<SectionPageDto> responseDto = new ResponseDto<>();
        ValidatorUtil.require(sectionPageDto.getCourseId(), "课程Id");
        ValidatorUtil.require(sectionPageDto.getChapterId(), "大章Id");
        sectionService.list(sectionPageDto);
        responseDto.setContent(sectionPageDto);
        return responseDto;
    }

    /**
     * 保存: SectionDto对象有id属性值时更新，无值时新增
     * @param sectionDto 数据传输对象
     * @return 统一返回响应对象
     */
    @PostMapping("/save")
    public ResponseDto<SectionDto> save(@RequestBody SectionDto sectionDto) {
        /* 保存校验 */
        ValidatorUtil.require(sectionDto.getTitle(), "标题");
        ValidatorUtil.length(sectionDto.getTitle(), "标题", 1, 50);
        ValidatorUtil.length(sectionDto.getVideo(), "视频", 1, 200);

        ResponseDto<SectionDto> responseDto = new ResponseDto<>();
        sectionService.save(sectionDto);
        responseDto.setContent(sectionDto);
        return responseDto;
    }

    /**
     * 删除: 指定id的Section对象
     * @param id Section对象id
     * @return 统一返回响应对象
     */
    @DeleteMapping("/delete/{id}")
    public ResponseDto<SectionDto> delete(@PathVariable String id) {
        ResponseDto<SectionDto> responseDto = new ResponseDto<>();
        sectionService.delete(id);
        return responseDto;
    }
}