package com.group.KGMS.controller;

import com.group.KGMS.entity.Album;
import com.group.KGMS.entity.Image;
import com.group.KGMS.service.AlbumService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/album")
public class AlbumController {

    @Autowired
    private AlbumService albumService;

    @GetMapping("/findAllAlbum")
    public List<Album> findAll(){
        return albumService.findAllAlbum();
    }
    @DeleteMapping("/deleteById/{id}")
    public void deleteById(@PathVariable("id") Integer id){
        albumService.delete(id);
    }
    @GetMapping("/findbyalbum/{id}")
    public List<Image> findbyalbumid(@PathVariable("id") int id){
        return albumService.findbyalbumid(id);
    }
}
