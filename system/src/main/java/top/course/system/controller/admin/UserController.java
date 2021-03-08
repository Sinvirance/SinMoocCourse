package top.course.system.controller.admin;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;
import top.course.server.constant.Constants;
import top.course.server.dto.LoginUserDto;
import top.course.server.dto.UserDto;
import top.course.server.dto.PageDto;
import top.course.server.dto.ResponseDto;
import top.course.server.service.UserService;
import top.course.server.util.ValidatorUtil;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * @Author: Sinvirance
 * @Date: 2021/03/07 14:20
 * @Description: User控制层
 */

@RestController
@RequestMapping(value = "/admin/user")
public class UserController {

    private static final Logger LOG = LoggerFactory.getLogger(UserController.class);

    /* User控制器 UserController 标识名 */
    public static final String BUSINESS_NAME = "用户";

    @Resource
    private UserService userService;

    /**
     * 查询: User对象分页列表
     * @param pageDto 分页信息传输对象
     * @return 统一返回响应对象
     */
    @PostMapping(value = "/list")
    public ResponseDto<PageDto> list(@RequestBody(required = false) PageDto pageDto) {
        ResponseDto<PageDto> responseDto = new ResponseDto<>();
        userService.list(pageDto);
        responseDto.setContent(pageDto);
        return responseDto;
    }

    /**
     * 保存: UserDto对象有id属性值时更新，无值时新增
     * @param userDto 数据传输对象
     * @return 统一返回响应对象
     */
    @PostMapping("/save")
    public ResponseDto<UserDto> save(@RequestBody UserDto userDto) {
        userDto.setPassword(DigestUtils.md5DigestAsHex(userDto.getPassword().getBytes()));
        /* 保存校验 */
        ValidatorUtil.require(userDto.getLoginName(), "登陆名");
        ValidatorUtil.length(userDto.getLoginName(), "登陆名", 1, 50);
        ValidatorUtil.length(userDto.getName(), "昵称", 1, 50);
        ValidatorUtil.require(userDto.getPassword(), "密码");

        ResponseDto<UserDto> responseDto = new ResponseDto<>();
        userService.save(userDto);
        responseDto.setContent(userDto);
        return responseDto;
    }

    /**
     * 删除: 指定id的User对象
     * @param id User对象id
     * @return 统一返回响应对象
     */
    @DeleteMapping("/delete/{id}")
    public ResponseDto<UserDto> delete(@PathVariable String id) {
        ResponseDto<UserDto> responseDto = new ResponseDto<>();
        userService.delete(id);
        return responseDto;
    }

    /**
     * 更新：指定idUser对象的password
     * @param userDto User数据传输对象
     * @return 统一返回响应对象
     */
    @PostMapping("/save-password")
    public ResponseDto savePassword(@RequestBody UserDto userDto) {
        userDto.setPassword(DigestUtils.md5DigestAsHex(userDto.getPassword().getBytes()));
        ResponseDto responseDto = new ResponseDto();
        userService.savePassword(userDto);
        responseDto.setContent(userDto);
        return responseDto;
    }

    /**
     * 用户登录
     * @param userDto User数据传输对象
     * @param request 请求对象
     * @return 统一返回响应对象：携带登录用户信息传输对象 LoginUserDto 返回给前端
     */
    @PostMapping("/login")
    public ResponseDto login(@RequestBody UserDto userDto, HttpServletRequest request) {
        userDto.setPassword(DigestUtils.md5DigestAsHex(userDto.getPassword().getBytes()));
        ResponseDto responseDto = new ResponseDto();
        LoginUserDto loginUserDto = userService.login(userDto);
        request.getSession().setAttribute(Constants.LOGIN_USER, loginUserDto);
        responseDto.setContent(loginUserDto);
        return responseDto;
    }


    /**
     * 用户退出登录
     * @param request 请求对象
     * @return 统一返回响应对象：携带登录用户信息传输对象 LoginUserDto 返回给前端
     */
    @GetMapping("/logout")
    public ResponseDto login(HttpServletRequest request) {
        ResponseDto responseDto = new ResponseDto();
        request.getSession().removeAttribute(Constants.LOGIN_USER);
        return responseDto;
    }
}