package com.group.KGMS.utils;

import com.group.KGMS.entity.CandidateOntologyClass;
import com.group.KGMS.utils.treejson.TreeItem;
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

public class TreeJsonCandidateOntologyClass {
    private volatile static TreeJsonCandidateOntologyClass INSTANCE;

    // 构造方法私有化
    private TreeJsonCandidateOntologyClass() {

    }

    // 获取树工具单例 （DCL单例）
    public static TreeJsonCandidateOntologyClass getInstance() {
        if (INSTANCE == null) {
            synchronized (TreeUtil.class) {
                if (INSTANCE == null) {
                    INSTANCE = new TreeJsonCandidateOntologyClass();
                }
            }
        }
        return INSTANCE;
    }

    /*
     * @Description: 给一个candidateOntologyClassList类型的List集合，返回一个树形的json
     * @Author: zt
     * @Date: 2022/12/22 19:46
     * @param: [candidateOntologyClassList]
     * @return: com.group.KGMS.entity.CandidateOntologyClass
     **/
    public CandidateOntologyClass enquireTree(List<CandidateOntologyClass> candidateOntologyClassList) {

        if (candidateOntologyClassList.isEmpty()) {
            return null;
        }

        // 过滤空对象
        List<CandidateOntologyClass> treeItems = candidateOntologyClassList.stream().filter(treeItem -> treeItem != null).collect(Collectors.toList());

        // 存储 id CandidateOntologyClass
        HashMap<Integer, CandidateOntologyClass> itemMap = new HashMap<>();
        treeItems.forEach(treeItem -> {
            itemMap.put(treeItem.getId(), treeItem);
        });

        // 声明一个变量存放根节点
        CandidateOntologyClass root = null;

        // 数据组装
        for (CandidateOntologyClass treeItem : treeItems) {
            //获取list集合中每一个父id
            Integer pid = treeItem.getParentId();
            //如果父id为0,那就为根节点
            if (pid == null || pid == 0) {
                // 说明该节点为根节点
                root = treeItem;
                continue;
            }
            CandidateOntologyClass parent = itemMap.get(pid);
            parent.getChildren().add(treeItem);
        }
        return root;
    }
}
