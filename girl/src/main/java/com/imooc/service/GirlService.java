package com.imooc.service;

import com.imooc.domain.Girl;
import com.imooc.enums.ResultEnum;
import com.imooc.exception.GirlException;
import com.imooc.repository.GirlRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class GirlService {

    @Autowired
    private GirlRepository girlRepository;


    @Transactional
    public void insertTwo() {
        Girl girlA = new Girl();
        girlA.setCupSize("A");
        girlA.setAge(18);
        girlRepository.save(girlA);

        Girl girlB = new Girl();
        girlB.setCupSize("E");
        girlB.setAge(25);
        girlRepository.save(girlB);
    }

    public void getAge(Integer id) throws Exception {
        Girl girl = girlRepository.findOne(id);
        if(girl != null) {
            Integer age = girl.getAge();
            if(age < 10) {
                throw new GirlException(ResultEnum.PRIMARY_SCHOOL);
            } else if(age >= 10 && age < 16) {
                throw new GirlException(ResultEnum.MIDDLE_SCHOOL);
            }
        }
    }

    public Girl findOne(Integer id) {
        return girlRepository.findOne(id);
    }
}
