package com.lnjecit.lifecycle;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * @author lnj
 * createTime 2018-11-07 22:25
 **/
@Component
public class CommandLineRunnerImpl implements CommandLineRunner {
    @Override
    public void run(String... args) throws Exception {
        System.out.println("通过实现CommandLineRunner接口，在spring boot项目启动后打印参数");
        for (String arg : args) {
            System.out.print(arg + " ");
        }
        System.out.println();
    }
}
