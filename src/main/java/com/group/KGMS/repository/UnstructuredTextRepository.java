package com.group.KGMS.repository;

import com.group.KGMS.entity.UnstructuredText;
import com.group.KGMS.entity.UnstructuredTextOriginal;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface UnstructuredTextRepository extends MongoRepository<UnstructuredText,String> {
    //获取所有的非结构文本
    List<UnstructuredText> findAll();
}
