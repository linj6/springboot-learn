package com.lnjecit.fileupload.controller;

import com.lnjecit.fileupload.util.FileUploadUtil;
import com.lnjecit.fileupload.util.Result;
import com.lnjecit.fileupload.util.ResultCode;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * 文件上传控制器
 *
 * @author linj
 * @create 2017-11-28 15:46
 **/
@RestController
public class FileUploadController {

    @PostMapping(value = "/upload")
    public Result upload(HttpServletRequest request) {
        Result result = new Result(ResultCode.SUCCESS);
        if (request instanceof MultipartHttpServletRequest) {
            MultipartHttpServletRequest mhr = (MultipartHttpServletRequest) request;
            Map<String, MultipartFile> mmf = mhr.getFileMap();
            result = FileUploadUtil.upload(mmf, "file", "");
        } else {
            result.setStatus(ResultCode.ERROR.getStatus());
            result.setInfo("该请求不是multipart/form-data类型请求！");
        }
        return result;
    }

    /**
     * 上传单个文件
     *
     * @param file
     * @return
     */
    @PostMapping(value = "/uploadOneFile")
    public Result uploadOneFile(@RequestParam("file") MultipartFile file) {
        try {
            return Result.success(FileUploadUtil.upload(file, "file", ""));
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }

    /**
     * 上传多个文件
     *
     * @param fileList
     * @return
     */
    @PostMapping(value = "/uploadMultiFile")
    public Result uploadMultiFile(@RequestParam("fileList") List<MultipartFile> fileList) {
        try {
            return Result.success(FileUploadUtil.upload(fileList, "file", ""));
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }

}
