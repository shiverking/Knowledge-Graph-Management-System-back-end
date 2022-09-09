package com.group.KGMS.repository;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;
import com.group.KGMS.entity.Role;
import com.group.KGMS.entity.User;
import java.util.List;

@Mapper
@Component
public interface UserMapper {
     //通过用户名找到用户
     User findUserByUsername (String username);
     //添加一名用户
     int insertUser(@Param("id") int id, @Param("username") String username, @Param("password") String password, @Param("headurl") String headurl);
     //查看所有用户
     List<User> getAllUsers();
     //删除用户账号
     int deleteUserByUsername(@Param("username") String username);
     //找到数据库中最后一位ID
     int getLastId();
     //重要函数，登录自动调用
     User loadUserByUsername(@Param("username") String username);
     //获取用户的角色列表
     List<Role> getUserRolesByUid(@Param("id") int id);
     //根据ID找到密码
     String findPasswordById(@Param("id") int id);
     //根据ID修改密码
     int changePassWordById(@Param("password") String password, @Param("id") int id);
     //根据用户名修改密码
     int changePasswordByUsername(@Param("password") String password, @Param("username") String username);
     //根据ID获得用户
     User findUserById(@Param("id") int id);
     //根据RID获得用户
     List<User> getUserByRid(@Param("rid") int rid);
     //用户名是否存在
     boolean isUsernameExist(@Param("username") String username);
     //根据ID找到用户名
     String getUsernameById(@Param("id") int id);
     //根据用户名找到ID
     int getIdByUserName(@Param("username") String username);
     //根据id删除用户
     int deleteUserById(@Param("id") int id);
     //获取用户列表
     List<User> getUserList();
     //查询用户
     List<User> searchUser(String username, int rid);
     //查询用户(管理员)
     List<User> searchAdmin(String username);
     //获取用户总数
     int getUserCount();
     //更新用户信息
     int updateUserInfo(@Param("id") int id, @Param("password") String password, @Param("username") String username );
     //获取管理员列表
     List<User> getAdminList();
     //通过id获取用户
     User getUserById(@Param("id") int id);
     //根据ID找到聊天对象的头像地址
     String getHeadurlById(@Param("id")int id);
     //根据ID 修改聊天对象的头像地址
     int updateHeadUrlById(@Param("headurl") String headurl, @Param("id")int id);
     //返回所有应聘者的id
     List<Integer> getAllUserId();
}
