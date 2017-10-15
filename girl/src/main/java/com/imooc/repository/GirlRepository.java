package com.imooc.repository;

import com.imooc.domain.Girl;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GirlRepository extends JpaRepository<Girl, Integer> {

    /**
     * 通过年龄查询女生列表
     * @param age 年龄
     * @return List<Girl>
     */
    List<Girl> findByAge(Integer age);
}
