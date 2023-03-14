package com.group.KGMS.mapper;

import com.group.KGMS.entity.CandidateKG;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface UnstructuredTextMapper {
    //获取所有新鲜的融合图谱
    List<Map<Object, String>> getAllUnStructuredText();
}
