package top.course.system.controller.admin;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import top.course.server.dto.PageDto;
import top.course.server.dto.ResourceDto;
import top.course.server.dto.ResponseDto;
import top.course.server.service.ResourceService;
import top.course.server.util.ValidatorUtil;

import javax.annotation.Resource;

/**
 * @Author: Sinvirance
 * @Date: 2021/03/18 20:44
 * @Description: Resource控制层
 */

@RestController
@RequestMapping(value = "/admin/resource")
public class ResourceController {

    private static final Logger LOG = LoggerFactory.getLogger(ResourceController.class);

    /* Resource控制器 ResourceController 标识名 */
    public static final String BUSINESS_NAME = "资源";

    @Resource
    private ResourceService resourceService;

    /**
     * 查询: Resource对象分页列表
     * @param pageDto 分页信息传输对象
     * @return 统一返回响应对象
     */
    @PostMapping(value = "/list")
    public ResponseDto<PageDto> list(@RequestBody(required = false) PageDto pageDto) {
        ResponseDto<PageDto> responseDto = new ResponseDto<>();
        resourceService.list(pageDto);
        responseDto.setContent(pageDto);
        return responseDto;
    }

    /**
     * 保存: ResourceDto对象有id属性值时更新，无值时新增
     * @param jsonStr 数据传输对象
     * @return 统一返回响应对象
     */
    @PostMapping("/save")
    public ResponseDto<ResourceDto> save(@RequestBody String jsonStr) {
        /* 保存校验 */
        ValidatorUtil.require(jsonStr, "资源");

        ResponseDto<ResourceDto> responseDto = new ResponseDto<>();
        resourceService.saveJson(jsonStr);
        return responseDto;
    }

    /**
     * 删除: 指定id的Resource对象
     * @param id Resource对象id
     * @return 统一返回响应对象
     */
    @DeleteMapping("/delete/{id}")
    public ResponseDto<ResourceDto> delete(@PathVariable String id) {
        ResponseDto<ResourceDto> responseDto = new ResponseDto<>();
        resourceService.delete(id);
        return responseDto;
    }
}