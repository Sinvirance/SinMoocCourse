package top.course.${module}.controller.admin;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import top.course.server.dto.${Domain}Dto;
import top.course.server.dto.PageDto;
import top.course.server.dto.ResponseDto;
import top.course.server.service.${Domain}Service;
import top.course.server.util.ValidatorUtil;

import javax.annotation.Resource;

/**
 * @Author: Sinvirance
 * @Date: 2021/xx/xx xx:xx
 * @Description: ${Domain}控制层
 */

@RestController
@RequestMapping(value = "/admin/${domain}")
public class ${Domain}Controller {

    private static final Logger LOG = LoggerFactory.getLogger(${Domain}Controller.class);

    /* ${Domain}控制器 ${Domain}Controller 标识名 */
    public static final String BUSINESS_NAME = "${tableNameCn}";

    @Resource
    private ${Domain}Service ${domain}Service;

    /**
     * 查询: ${Domain}对象分页列表
     * @param pageDto 分页信息传输对象
     * @return 统一返回响应对象
     */
    @PostMapping(value = "/list")
    public ResponseDto<PageDto> list(@RequestBody(required = false) PageDto pageDto) {
        ResponseDto<PageDto> responseDto = new ResponseDto<>();
        ${domain}Service.list(pageDto);
        responseDto.setContent(pageDto);
        return responseDto;
    }

    /**
     * 保存: ${Domain}Dto对象有id属性值时更新，无值时新增
     * @param ${domain}Dto 数据传输对象
     * @return 统一返回响应对象
     */
    @PostMapping("/save")
    public ResponseDto<${Domain}Dto> save(@RequestBody ${Domain}Dto ${domain}Dto) {
        /* 保存校验*/

        ResponseDto<${Domain}Dto> responseDto = new ResponseDto<>();
        ${domain}Service.save(${domain}Dto);
        responseDto.setContent(${domain}Dto);
        return responseDto;
    }

    /**
     * 删除: 指定id的${Domain}对象
     * @param id ${Domain}对象id
     * @return 统一返回响应对象
     */
    @DeleteMapping("/delete/{id}")
    public ResponseDto<${Domain}Dto> delete(@PathVariable String id) {
        ResponseDto<${Domain}Dto> responseDto = new ResponseDto<>();
        ${domain}Service.delete(id);
        return responseDto;
    }
}