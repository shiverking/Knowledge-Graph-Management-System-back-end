package com.group.KGMS.service;

import com.group.KGMS.entity.Album;
import com.group.KGMS.entity.Image;
import com.group.KGMS.mapper.AlbumMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class AlbumServiceImpl implements AlbumService {
    @Autowired
    AlbumMapper albumMapper;

    @Override
    public List<Album> findAllAlbum() {
        return albumMapper.findAllAlbum();
    }

    @Override
    public int delete(int id) {
        return albumMapper.delete(id);
    }

    @Override
    public List<Image> findbyalbumid(int id) {
        return albumMapper.findbyalbumid(id);
    }

}
