package top.course.system.controller.admin;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import top.course.server.dto.RoleDto;
import top.course.server.dto.PageDto;
import top.course.server.dto.ResponseDto;
import top.course.server.service.RoleService;
import top.course.server.util.ValidatorUtil;

import javax.annotation.Resource;

/**
 * @Author: Sinvirance
 * @Date: 2021/xx/xx xx:xx
 * @Description: Role控制层
 */

@RestController
@RequestMapping(value = "/admin/role")
public class RoleController {

    private static final Logger LOG = LoggerFactory.getLogger(RoleController.class);

    /* Role控制器 RoleController 标识名 */
    public static final String BUSINESS_NAME = "角色";

    @Resource
    private RoleService roleService;

    /**
     * 查询: Role对象分页列表
     * @param pageDto 分页信息传输对象
     * @return 统一返回响应对象
     */
    @PostMapping(value = "/list")
    public ResponseDto<PageDto> list(@RequestBody(required = false) PageDto pageDto) {
        ResponseDto<PageDto> responseDto = new ResponseDto<>();
        roleService.list(pageDto);
        responseDto.setContent(pageDto);
        return responseDto;
    }

    /**
     * 保存: RoleDto对象有id属性值时更新，无值时新增
     * @param roleDto 数据传输对象
     * @return 统一返回响应对象
     */
    @PostMapping("/save")
    public ResponseDto<RoleDto> save(@RequestBody RoleDto roleDto) {
        /* 保存校验 */
        ValidatorUtil.require(roleDto.getName(), "角色");
        ValidatorUtil.length(roleDto.getName(), "角色", 1, 50);
        ValidatorUtil.require(roleDto.getDesc(), "描述");
        ValidatorUtil.length(roleDto.getDesc(), "描述", 1, 100);

        ResponseDto<RoleDto> responseDto = new ResponseDto<>();
        roleService.save(roleDto);
        responseDto.setContent(roleDto);
        return responseDto;
    }

    /**
     * 删除: 指定id的Role对象
     * @param id Role对象id
     * @return 统一返回响应对象
     */
    @DeleteMapping("/delete/{id}")
    public ResponseDto<RoleDto> delete(@PathVariable String id) {
        ResponseDto<RoleDto> responseDto = new ResponseDto<>();
        roleService.delete(id);
        return responseDto;
    }
}