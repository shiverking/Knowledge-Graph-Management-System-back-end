package com.group.KGMS.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.group.KGMS.entity.CoreOntologyClass;

import java.io.IOException;

/**
 * @BelongsProject: Knowledge-Graph-Management-System-back-end
 * @BelongsPackage: com.group.KGMS.service
 * @Author: zt
 * @CreateTime: 2023-03-08  15:49
 * @Description:
 */

public interface CoreOntologyClassService extends IService<CoreOntologyClass> {

    /*
     * @Description: 获取核心本体的树形结构
     * @Author: zt
     * @Date: 2023/3/8 16:19
     * @param: []
     * @return: com.group.KGMS.entity.CoreOntologyClass
     **/
    CoreOntologyClass getRootClass();

    /*
     * @Description: 向核心本体中添加新的类
     * @Author: zt
     * @Date: 2023/3/9 0:33
     * @param: [className, parentId]
     * @return: boolean
     **/
    boolean save(String className, Integer parentId) throws Exception;

    void remove(String className) throws Exception;

}
