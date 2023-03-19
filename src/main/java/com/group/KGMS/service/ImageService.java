package com.group.KGMS.service;

import com.group.KGMS.entity.Image;
import com.group.KGMS.utils.JsonResult;

import java.util.List;
import java.util.Map;

public interface ImageService {
    JsonResult getimage(Integer pageNum, Integer limitNum, Integer cid);
    List<Map> statistic();
    List<String> getimagetype(Integer cid);
    JsonResult getimagebytype(Integer pageNum, Integer limitNum, Integer cid,String type);
}
