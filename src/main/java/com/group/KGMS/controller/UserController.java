package com.group.KGMS.controller;

import com.group.KGMS.entity.RespBean;
import com.group.KGMS.entity.User;
import com.group.KGMS.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

@Controller
public class UserController {

    @Autowired
    UserService userService;

    @ResponseBody
    @GetMapping("/hello")
    public String hello(){
        return "hello";
    }

//    /**
//     * 用户登录
//     * @return
//     */
//    @ResponseBody
//    @GetMapping("/login")
//    public String userLogin(){
//        userService;
//    }
    
    /**
     * 注册方法,添加一个用户
     */
    @ResponseBody
    @RequestMapping("/register")
    public RespBean test(@RequestBody Map<String, Object> info, BindingResult bindingResult) throws ServiceException {
        Map<String, Object> map = new HashMap<>();
        if (bindingResult.hasErrors()) {
            StringBuffer sb = new StringBuffer();
            sb.append(bindingResult.getAllErrors().get(0).getDefaultMessage());
            return RespBean.error(sb.toString());
        } else {
            User user = new User();
            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            String encodedPassword = passwordEncoder.encode(((String) info.get("password")).trim());
            int id = userService.getLastId() + 1;
            user.setPassword(encodedPassword);
            user.setId(id);
            user.setUsername((String) info.get("username"));
            user.setEmail((String) info.get("email"));
            user.setTelephone((String) info.get("telephone"));
            //添加用户
            userservice.insertUser(user);
            String identity = (String) info.get("identity");
            if (identity.equals("employer")) {
                if (roleServiceimpl.addUserAndRole(id, 1)) {
                    return RespBean.ok("注册成功!");
                }
            }
            if (identity.equals("employee")) {
                if (roleServiceimpl.addUserAndRole(id, 2)) {
                    return RespBean.ok("注册成功!");
                }
            }
            return RespBean.error("注册失败");

        }
    }
}
