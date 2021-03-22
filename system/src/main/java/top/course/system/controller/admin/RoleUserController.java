package top.course.system.controller.admin;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import top.course.server.dto.RoleUserDto;
import top.course.server.dto.PageDto;
import top.course.server.dto.ResponseDto;
import top.course.server.service.RoleUserService;
import top.course.server.util.ValidatorUtil;

import javax.annotation.Resource;

/**
 * @Author: Sinvirance
 * @Date: 2021/xx/xx xx:xx
 * @Description: RoleUser控制层
 */

@RestController
@RequestMapping(value = "/admin/roleUser")
public class RoleUserController {

    private static final Logger LOG = LoggerFactory.getLogger(RoleUserController.class);

    /* RoleUser控制器 RoleUserController 标识名 */
    public static final String BUSINESS_NAME = "角色用户关联";

    @Resource
    private RoleUserService roleUserService;

    /**
     * 查询: RoleUser对象分页列表
     * @param pageDto 分页信息传输对象
     * @return 统一返回响应对象
     */
    @PostMapping(value = "/list")
    public ResponseDto<PageDto> list(@RequestBody(required = false) PageDto pageDto) {
        ResponseDto<PageDto> responseDto = new ResponseDto<>();
        roleUserService.list(pageDto);
        responseDto.setContent(pageDto);
        return responseDto;
    }

    /**
     * 保存: RoleUserDto对象有id属性值时更新，无值时新增
     * @param roleUserDto 数据传输对象
     * @return 统一返回响应对象
     */
    @PostMapping("/save")
    public ResponseDto<RoleUserDto> save(@RequestBody RoleUserDto roleUserDto) {
        /* 保存校验 */
        ValidatorUtil.require(roleUserDto.getRoleId(), "角色");
        ValidatorUtil.require(roleUserDto.getUserId(), "用户");

        ResponseDto<RoleUserDto> responseDto = new ResponseDto<>();
        roleUserService.save(roleUserDto);
        responseDto.setContent(roleUserDto);
        return responseDto;
    }

    /**
     * 删除: 指定id的RoleUser对象
     * @param id RoleUser对象id
     * @return 统一返回响应对象
     */
    @DeleteMapping("/delete/{id}")
    public ResponseDto<RoleUserDto> delete(@PathVariable String id) {
        ResponseDto<RoleUserDto> responseDto = new ResponseDto<>();
        roleUserService.delete(id);
        return responseDto;
    }
}