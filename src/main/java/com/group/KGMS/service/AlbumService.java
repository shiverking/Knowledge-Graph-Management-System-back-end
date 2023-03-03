package com.group.KGMS.service;


import com.group.KGMS.entity.Album;
import com.group.KGMS.entity.Image;


import java.util.List;

public interface AlbumService {
    List<Album> findAllAlbum();

    int delete(int id);

    List<Image> findbyalbumid(int id);
}
