package com.lnjecit.controller;

import com.lnjecit.domain.Result;
import com.lnjecit.domain.ResultEnum;
import com.lnjecit.domain.User;
import com.lnjecit.factory.UserDataFacotry;
import com.lnjecit.util.ResultUtil;
import com.lnjecit.vo.CreateUserVO;
import com.lnjecit.vo.UpdateUserVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.List;

/**
 * @author lnj
 * @description HttpClientController
 * @date 2019-04-20 20:43
 **/
@Slf4j
@RestController
@RequestMapping("/user")
public class UserController {

    @GetMapping("/getUserList")
    public Result<List<User>> getUserList(@RequestParam(required = false) Long userId, @RequestParam(required = false) String username) {
        log.info("请求参数：userId=" + userId + ", username=" + username);
        return ResultUtil.success(UserDataFacotry.getUserList(userId, username));
    }

    @PostMapping("/createUser")
    public Result<Long> createUser(@Valid @RequestBody CreateUserVO createUserVO, BindingResult bindingResult) {
        log.info("请求参数: {}", createUserVO);
        if (bindingResult.hasErrors()) {
            return ResultUtil.error(ResultEnum.PARAM_ERROR.getCode(), bindingResult.getFieldError().getDefaultMessage());
        }
        return ResultUtil.success(UserDataFacotry.createUser(User.builder().username(createUserVO.getUsername()).build()));
    }

    @PostMapping("/updateUser")
    public Result<Integer> updateUser(@Valid UpdateUserVO updateUser, BindingResult bindingResult) {
        log.info("请求参数: {}", updateUser);
        if (bindingResult.hasErrors()) {
            return ResultUtil.error(ResultEnum.PARAM_ERROR.getCode(), bindingResult.getFieldError().getDefaultMessage());
        }
        return ResultUtil.success(UserDataFacotry.updateUser(User.builder()
                .userId(updateUser.getUserId())
                .username(updateUser.getUsername())
                .build()));
    }

    @PostMapping("/uploadFile")
    public Result<String> uploadFile(@RequestParam MultipartFile file) {
        log.info(file.getOriginalFilename());
        return ResultUtil.success("上传成功后的图片url地址");
    }
}
