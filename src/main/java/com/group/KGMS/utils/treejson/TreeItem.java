package com.group.KGMS.utils.treejson;

import java.util.ArrayList;
import java.util.List;

/**
 * @BelongsProject: Knowledge-Graph-Management-System-back-end
 * @BelongsPackage: com.group.KGMS.utils.treejson
 * @Author: zt
 * @CreateTime: 2022-12-22  18:32
 * @Description:
 */

public class TreeItem {

    private String id;

    private String parentId;

    private String name;

    private List<TreeItem> children = new ArrayList<>();

    public TreeItem(String id, String parentId, String name) {
        this.id = id;
        this.parentId = parentId;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<TreeItem> getChildren() {
        return children;
    }

    public void setChildren(List<TreeItem> children) {
        this.children = children;
    }

}

