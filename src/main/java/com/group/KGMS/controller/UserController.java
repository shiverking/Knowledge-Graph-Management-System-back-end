package com.group.KGMS.controller;

import com.group.KGMS.entity.User;
import com.group.KGMS.service.RoleService;
import com.group.KGMS.service.UserService;
import com.group.KGMS.utils.JsonResult;
import org.hibernate.service.spi.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Controller
public class UserController {

    @Autowired
    UserService userService;
    @Autowired
    RoleService roleService;
    /**
     * 注册方法,添加一个用户
     * @return
     */
    @ResponseBody
    @PostMapping ("/user/register")
    public JsonResult registerUser(@RequestBody Map<String, Object> info) throws ServiceException {
        System.out.println(info.get("username"));
        User user = new User();
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encodedPassword = passwordEncoder.encode(((String) info.get("password")).trim());
        int id = userService.getLastId() + 1;
        user.setPassword(encodedPassword);
        user.setId(id);
        user.setUsername((String) info.get("username"));
        //添加用户
        userService.insertUser(user);
        if (roleService.addUserAndRole(id, 1)) {
            return JsonResult.success("注册成功!");
        }
        return JsonResult.error("注册失败");
    }
}
