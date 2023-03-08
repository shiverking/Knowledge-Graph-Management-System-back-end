package com.group.KGMS.utils;

import com.group.KGMS.entity.CoreOntologyClass;
import com.group.KGMS.utils.treejson.TreeUtil;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @BelongsProject: Knowledge-Graph-Management-System-back-end
 * @BelongsPackage: com.group.KGMS.utils
 * @Author: zt
 * @CreateTime: 2022-12-22  19:23
 * @Description:
 */

public class TreeJsonCoreOntologyClass {
    private volatile static TreeJsonCoreOntologyClass INSTANCE;

    // 构造方法私有化
    private TreeJsonCoreOntologyClass() {

    }

    // 获取树工具单例 （DCL单例）
    public static TreeJsonCoreOntologyClass getInstance() {
        if (INSTANCE == null) {
            synchronized (TreeUtil.class) {
                if (INSTANCE == null) {
                    INSTANCE = new TreeJsonCoreOntologyClass();
                }
            }
        }
        return INSTANCE;
    }

    /*
     * @Description: 给一个CoreOntologyClass类型的List集合，返回一个树形的json
     * @Author: zt
     * @Date: 2022/12/22 19:46
     * @param: [coreOntologyClassList]
     * @return: com.group.KGMS.entity.CoreOntologyClass
     **/
    public CoreOntologyClass enquireTree(List<CoreOntologyClass> coreOntologyClassList) {

        if (coreOntologyClassList.isEmpty()) {
            return null;
        }

        // 过滤空对象
        List<CoreOntologyClass> treeItems = coreOntologyClassList.stream().filter(treeItem -> treeItem != null).collect(Collectors.toList());

        // 存储 id CoreOntologyClass
        HashMap<Integer, CoreOntologyClass> itemMap = new HashMap<>();
        treeItems.forEach(treeItem -> {
            itemMap.put(treeItem.getId(), treeItem);
        });

        // 声明一个变量存放根节点
        CoreOntologyClass root = null;

        // 数据组装
        for (CoreOntologyClass treeItem : treeItems) {
            //获取list集合中每一个父id
            Integer pid = treeItem.getParentId();
            //如果父id为0,那就为根节点
            if (pid == null || pid == 0) {
                // 说明该节点为根节点
                root = treeItem;
                continue;
            }
            CoreOntologyClass parent = itemMap.get(pid);
            parent.getChildren().add(treeItem);
        }
        return root;
    }
}
