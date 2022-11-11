package com.group.KGMS.repository;

import com.group.KGMS.entity.T_aircraft;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.Optional;

@SpringBootTest
class BookRepositoryTest {

//    @Autowired
//    private AircraftRepository aircraftRepository;
//
//    @Test
//    void findAll(){
//
//    }
//    @Test
//    public void search(){
//        PageRequest request = PageRequest.of(0, 5);
//        Optional<T_aircraft> aircraft = aircraftRepository.findOne(new Specification<T_aircraft>() {
//            @Override
//            public Predicate toPredicate(Root<T_aircraft> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
//                Predicate aircraftpredicate = null;
//                aircraftpredicate=criteriaBuilder.like(root.get("aircraft_name").as(String.class),"%"+"EA-18G"+"%");
//                return aircraftpredicate;
//            }
//        });
//        T_aircraft ar = null;
//        ar.setAircraft_name("ea");
//        System.out.println(ar.getAircraft_name());
//
//    }

}