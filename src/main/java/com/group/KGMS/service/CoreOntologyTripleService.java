package com.group.KGMS.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.group.KGMS.entity.CoreOntologyTriple;

import java.io.IOException;

/**
 * @BelongsProject: Knowledge-Graph-Management-System-back-end
 * @BelongsPackage: com.group.KGMS.service
 * @Author: zt
 * @CreateTime: 2023-03-09  10:13
 * @Description:
 */
public interface CoreOntologyTripleService extends IService<CoreOntologyTriple> {

    /*
     * @Description: 向核心本体中添加关系
     * @Author: zt
     * @Date: 2023/3/9 13:21
     * @param: [headClassName, relationName, tailClassName]
     * @return: void
     **/
    void saveRelation(String headClassName, String relationName, String tailClassName) throws Exception;

    /*
     * @Description: 删除核心本体中的某条关系
     * @Author: zt
     * @Date: 2023/3/9 13:21
     * @param: [headClassId, relationName, tailClassId]
     * @return: void
     **/
    void removeRelation(Integer headClassId, String relationName, Integer tailClassId) throws IOException;

}
