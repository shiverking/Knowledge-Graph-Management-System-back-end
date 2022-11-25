package com.group.KGMS.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.group.KGMS.entity.CandidateKG;
import com.group.KGMS.entity.CandidateTriple;
import com.group.KGMS.entity.Triple;
import com.group.KGMS.mapper.CandidateKGMapper;
import com.group.KGMS.mapper.TripleMapper;
import com.group.KGMS.utils.PropertyAppender;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.lang.reflect.InvocationTargetException;
import java.util.*;

@Service
public class TripleServiceImpl implements TripleService {
    @Autowired
    TripleMapper tripleMapper;
    @Autowired
    CandidateKGMapper candidateKGMapper;
    @Resource
    SqlSessionFactory sqlSessionFactory;

    /**
     * 将候选三元组批量插入三元组库中
     * @return
     */
    @Override
    public int insertIntoTriplesFromCandidateTriple(List<Triple> tripleList, Long candidateKgId) {
        int result = 0;
        SqlSession sqlSession = sqlSessionFactory.openSession(ExecutorType.BATCH);
        try{
            TripleMapper tripleMapperTemp =  sqlSession.getMapper(TripleMapper.class);
            String status="已入库";
            for(Triple triple:tripleList){
                triple.setStatus(status);
                tripleMapperTemp.insertCandidateTripleIntoTriple(triple);
            }
            sqlSession.commit();
            sqlSession.clearCache();
            result = 1;
        }catch(Exception e){
            System.out.println(e);
        }finally{
            sqlSession.close();
        }
        return result;
    }

    /**
     * 将候选图谱从库中删除
     * @param tripleList
     * @return
     */
    @Override
    public int deleteCandidateTriples(List<CandidateTriple> tripleList) {
        int result = 0;
        SqlSession sqlSession = sqlSessionFactory.openSession(ExecutorType.BATCH);
        try{
            TripleMapper tripleMapperTemp =  sqlSession.getMapper(TripleMapper.class);
            String status="已入库";
            for(CandidateTriple triple:tripleList){
                tripleMapperTemp.deleteCandidateTriples(triple);
            }
            sqlSession.commit();
            sqlSession.clearCache();
            result = 1;
        }catch(Exception e){
            System.out.println(e);
        }finally{
            sqlSession.close();
        }
        return result;
    }

    /**
     * 分页获取所有来自相同图谱的三元组
     * @param pageNum
     * @param limitNum
     * @param candidateId
     * @return
     */
    @Override
    public PageInfo<Triple> getTripleFromSameKgByPage(Integer pageNum, Integer limitNum, Long candidateId) {
        PageHelper.startPage(pageNum,limitNum);
        PageInfo<Triple> info = new PageInfo<Triple>(tripleMapper.getAllTriplesFromSameCandidateKg(candidateId));
        return info;
    }

    /**
     * 分页获取所有三元组
     * @param pageNum
     * @param limitNum
     * @return
     */
    @Override
    public PageInfo<Triple> getTripleByPage(Integer pageNum, Integer limitNum){
        PageHelper.startPage(pageNum,limitNum);
        List<Triple> tripleList = tripleMapper.getAllTriple();
        PageInfo<Triple> info = new PageInfo<Triple>(tripleList);
        //为类动态增加一个候选图谱名称的属性
//        List<Triple> newTripleList = new ArrayList<>();
//        for(Triple triple:tripleList){
//            Long id = triple.getId();
//            String candidateName = candidateKGMapper.getNameById(id);
//            Object studentVO = new StudentVO();
//            BeanUtils.copyProperties(studentDO, studentVO);
//            studentVO.setClassName("六年级二班");
//            studentVos.add(studentVO);
//            Map<String, Object> propertiesMap = new HashMap<>(1);
//            propertiesMap.put("candidateName", candidateName);
//            Object obj = PropertyAppender.generate(triple, propertiesMap);
//            newTripleList.add(obj);
//        }
        //这一步的作用是将封装后的列表放到分页对象中
//        info.setList(newTripleList);
        return info;
    }

    @Override
    public List<Triple> getTripleFromSameKg(Long candidateId) {
        return tripleMapper.getAllTriplesFromSameCandidateKg(candidateId);
    }
}
