package com.lnjecit.service.impl;

import com.lnjecit.service.ContractService;
import com.lnjecit.util.PdfUtil;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileNotFoundException;

/**
 * @author
 * @create 2017-12-18 22:04
 **/
@Service
public class ContractServiceImpl implements ContractService {

    @Override
    public File download() {
        File htmlFile;
        File pdfFile;
        String htmlFilePath;
        try {
            //读取html页面
            htmlFile = ResourceUtils.getFile("classpath:templates/index.html");
            htmlFilePath = "classpath:templates/index.html";
            // 中文字体存储路径
            String chineseFontPath = "classpath:Fonts/simsun.ttc";
            // html转pdf
            PdfUtil.html2pdf(htmlFilePath,"classpath:templates/index.html.pdf", chineseFontPath);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        //将html转换为pdf文件并保存

        //将pdf文件返回
        return null;
    }
}
