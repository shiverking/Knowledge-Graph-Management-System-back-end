package com.group.KGMS.service;

import com.group.KGMS.repository.RoleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author ：闫崇傲
 * @description：角色Service
 * @date ：2022/9/11 16:46
 */
@Service
public class RoleServiceImpl implements RoleService {
    @Autowired
    RoleMapper roleMapper;
    @Override
    public Boolean addUserAndRole(int userId, int roleId) {
        //插入成功
        if(roleMapper.addUserAndRole(userId,roleId)==1){
            return true;
        }
        return false;
    }
}
