package top.course.system.controller.admin;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import top.course.server.dto.PageDto;
import top.course.server.dto.ResponseDto;
import top.course.server.dto.RoleResourceDto;
import top.course.server.service.RoleResourceService;
import top.course.server.util.ValidatorUtil;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author: Sinvirance
 * @Date: 2021/xx/xx xx:xx
 * @Description: RoleResource控制层
 */

@RestController
@RequestMapping(value = "/admin/roleResource")
public class RoleResourceController {

    private static final Logger LOG = LoggerFactory.getLogger(RoleResourceController.class);

    /* RoleResource控制器 RoleResourceController 标识名 */
    public static final String BUSINESS_NAME = "角色资源关联";

    @Resource
    private RoleResourceService roleResourceService;

    /**
     * 查询: RoleResource对象分页列表
     * @param pageDto 分页信息传输对象
     * @return 统一返回响应对象
     */
    @PostMapping(value = "/list")
    public ResponseDto<PageDto> list(@RequestBody(required = false) PageDto pageDto) {
        ResponseDto<PageDto> responseDto = new ResponseDto<>();
        roleResourceService.list(pageDto);
        responseDto.setContent(pageDto);
        return responseDto;
    }

    /**
     * 保存: RoleResourceDto对象有id属性值时更新，无值时新增
     * @param roleResourceDto 数据传输对象
     * @return 统一返回响应对象
     */
    @PostMapping("/save")
    public ResponseDto<RoleResourceDto> save(@RequestBody RoleResourceDto roleResourceDto) {
        /* 保存校验 */
        ValidatorUtil.require(roleResourceDto.getRoleId(), "角色");
        ValidatorUtil.require(roleResourceDto.getResourceId(), "资源");

        ResponseDto<RoleResourceDto> responseDto = new ResponseDto<>();
        roleResourceService.save(roleResourceDto);
        responseDto.setContent(roleResourceDto);
        return responseDto;
    }

    /**
     * 删除: 指定id的RoleResource对象
     * @param id RoleResource对象id
     * @return 统一返回响应对象
     */
    @DeleteMapping("/delete/{id}")
    public ResponseDto<RoleResourceDto> delete(@PathVariable String id) {
        ResponseDto<RoleResourceDto> responseDto = new ResponseDto<>();
        roleResourceService.delete(id);
        return responseDto;
    }

    /**
     * 查询: 对应角色的所有资源
     * @param roleId 角色对应Id
     * @return 统一返回响应对象
     */
    @GetMapping("/list-resource/{roleId}")
    public ResponseDto<List<String>> listResource(@PathVariable String roleId) {
        LOG.info("加载资源开始");
        ResponseDto<List<String>> responseDto = new ResponseDto<>();
        List<String> resourceIdList = roleResourceService.listResource(roleId);
        responseDto.setContent(resourceIdList);
        return responseDto;
    }
}