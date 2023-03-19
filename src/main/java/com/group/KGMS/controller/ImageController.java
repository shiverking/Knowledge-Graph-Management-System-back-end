package com.group.KGMS.controller;

import com.group.KGMS.service.ImageService;
import com.group.KGMS.service.SemistructuredDataService;
import com.group.KGMS.utils.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/image")
public class ImageController {
    @Autowired
    ImageService imageService;

    @GetMapping("/getimage/{page}/{limit}/{cid}")
    public JsonResult getimage(@PathVariable("page") Integer page, @PathVariable("limit") Integer limit, @PathVariable("cid") Integer cid){
        return imageService.getimage(page,limit,cid);
    }
    @GetMapping("/statistic")
    public List<Map> statistic(){
        return imageService.statistic();
    }
    @GetMapping ("/getimagetype/{cid}")
    public List<String> getimagetype(@PathVariable("cid") Integer cid) {
        return imageService.getimagetype(cid);
    }
    @GetMapping("/getimagebytype/{page}/{limit}/{cid}/{type}")
    public JsonResult getimagebytype(@PathVariable("page") Integer page, @PathVariable("limit") Integer limit, @PathVariable("cid") Integer cid,@PathVariable("type") String type){
        return imageService.getimagebytype(page,limit,cid,type);
    }
}
