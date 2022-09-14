package com.group.KGMS.controller;

import com.group.KGMS.entity.RespBean;
import com.group.KGMS.entity.User;
import com.group.KGMS.service.RoleService;
import com.group.KGMS.service.UserService;
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

//    @ResponseBody
//    @GetMapping("/hello")
//    public String hello(){
//        return "hello";
//    }
    
    /**
     * 注册方法,添加一个用户
     */
//    @ResponseBody
//    @RequestMapping("/user/register")
//    public RespBean test(@RequestBody Map<String, Object> info, BindingResult bindingResult) throws ServiceException {
//        Map<String, Object> map = new HashMap<>();
//        if (bindingResult.hasErrors()) {
//            StringBuffer sb = new StringBuffer();
//            sb.append(bindingResult.getAllErrors().get(0).getDefaultMessage());
//            return RespBean.error(sb.toString());
//        } else {
//            User user = new User();
//            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
//            String encodedPassword = passwordEncoder.encode(((String) info.get("password")).trim());
//            int id = userService.getLastId() + 1;
//            user.setPassword(encodedPassword);
//            user.setId(id);
//            user.setUsername((String) info.get("username"));
//            //添加用户
//            userService.insertUser(user);
//            String identity = (String) info.get("identity");
//            if (identity.equals("employer")) {
//                if (roleServiceimpl.addUserAndRole(id, 1)) {
//                    return RespBean.ok("注册成功!");
//                }
//            }
//            if (identity.equals("employee")) {
//                if (roleServiceimpl.addUserAndRole(id, 2)) {
//                    return RespBean.ok("注册成功!");
//                }
//            }
//            return RespBean.error("注册失败");
//
//        }
//    }
    /**
     * 注册方法,添加一个用户
     * @return
     */
    @ResponseBody
    @PostMapping ("/user/register")
    public RespBean registerUser(@RequestBody Map<String, Object> info) throws ServiceException {
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
            return RespBean.ok("注册成功!");
        }
        return RespBean.error("注册失败");
    }
}
