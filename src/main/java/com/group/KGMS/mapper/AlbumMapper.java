package com.group.KGMS.mapper;

import com.group.KGMS.entity.Album;
import com.group.KGMS.entity.Image;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;


@Mapper
@Repository
public interface AlbumMapper {
    List<Album> findAllAlbum();
    int delete(int id);
    List<Image> findbyalbumid(int id);
}
