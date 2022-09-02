package com.group.KGMS;

import com.group.KGMS.entity.T_aircraft;
import com.group.KGMS.repository.AircraftRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

@SpringBootTest
class SpringboottestApplicationTests {
    @Autowired
    private AircraftRepository repository;

    @Test
    void contextLoads() {
        PageRequest pageRequest = PageRequest.of(0,6);
        Page<T_aircraft> page = repository.findAll(pageRequest);
        int i = 0;
    }

    @Test
    void save(){
        T_aircraft book = new T_aircraft();

        T_aircraft book1 = repository.save(book);
        System.out.println(book1);
    }

    @Test
    void findById(){
        T_aircraft book = repository.findById(1).get();
        System.out.println(book);
    }

    @Test
    void update(){
        T_aircraft book = new T_aircraft();

        T_aircraft book1 = repository.save(book);
        System.out.println(book1);
    }

    @Test
    void delete(){
        repository.deleteById(1);
    }
}
