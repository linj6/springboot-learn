package com.lnjecit.controller.contract;

import com.lnjecit.service.ContractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;

/**
 * @author
 * @create 2017-12-16 20:52
 **/
@RestController
@RequestMapping("/contract")
public class ContractController {

    @Autowired
    private ContractService contractService;

    /**
     * 合同下载 将html转换为pdf返回到浏览器下载
     * @return
     */
    @RequestMapping("/download")
    public void download() {
        File file = contractService.download();

    }

}
