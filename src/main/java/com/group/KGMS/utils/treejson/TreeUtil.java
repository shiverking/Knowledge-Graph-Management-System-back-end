package com.group.KGMS.utils.treejson;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @BelongsProject: Knowledge-Graph-Management-System-back-end
 * @BelongsPackage: com.group.KGMS.utils.treejson
 * @Author: zt
 * @CreateTime: 2022-12-22  18:33
 * @Description:
 */

public class TreeUtil {

    private volatile static TreeUtil INSTANCE;

    // 构造方法私有化
    private TreeUtil() {

    }

    // 获取树工具单例 （DCL单例）
    public static TreeUtil getInstance() {
        if (INSTANCE == null) {
            synchronized (TreeUtil.class) {
                if (INSTANCE == null) {
                    INSTANCE = new TreeUtil();
                }
            }
        }
        return INSTANCE;
    }

    public TreeItem enquireTree(List<TreeItem> treeItemList) {

        if (treeItemList.isEmpty()) {
            return null;
        }

        // 过滤空对象
        List<TreeItem> treeItems = treeItemList.stream().filter(treeItem -> treeItem != null).collect(Collectors.toList());

        // 存储 id treeItem
        HashMap<String, TreeItem> itemMap = new HashMap<>();
        treeItems.forEach(treeItem -> {
            itemMap.put(treeItem.getId(), treeItem);
        });

        // 声明一个变量存放根节点
        TreeItem root = null;

        // 数据组装
        for (TreeItem treeItem : treeItems) {
            String pid = treeItem.getParentId();
            //如果父id为0,那就为根节点
            if (pid.isEmpty() || pid == "0") {
                // 说明该节点为根节点
                root = treeItem;
                continue;
            }
            TreeItem parent = itemMap.get(pid);
            parent.getChildren().add(treeItem);
        }
        return root;
    }

}

