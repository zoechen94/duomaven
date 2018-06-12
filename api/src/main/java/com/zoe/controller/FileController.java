package com.zoe.controller;


import com.zoe.service.spring.resultInfo.ResultData;
import com.zoe.service.spring.utils.FileUploadUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/**
 * Created by 陈亚兰 on 2018/6/12.
 */
@RestController
@RequestMapping("/file")
@Api(description = "文件")
public class FileController {

    /**
     * 单个文件上传
     * @param file
     * @return
     * @throws IOException
     */
    @PostMapping(value = "/upload")
    @ApiOperation(value = "上传")
    public ResultData uploadFile(@RequestBody @RequestParam("file") MultipartFile file) throws IOException {
        return ResultData.success(FileUploadUtils.uploadFile(file));
    }

    @PostMapping(value = "/batchUpload")
    @ApiOperation(value = "批量文件上传")
    public ResultData batchUploadFile(@RequestBody@RequestParam("files")MultipartFile[] files) throws IOException {
        List<MultipartFile> multipartFiles= Arrays.asList(files);
        StringBuffer sb=new StringBuffer();
        for(int i=0;i<files.length;i++){
            sb.append(FileUploadUtils.uploadFile(multipartFiles.get(i)));
        }
        return ResultData.success(sb.toString());
    }

    @ApiOperation(value = "上传文件")
    @RequestMapping(value="/uploadFile.do", method = RequestMethod.POST)
    public ResultData uploadFile(HttpServletRequest request,@RequestParam("file") MultipartFile file){
        String fileName = file.getOriginalFilename();
        try {
            FileUploadUtils.uploadFile(file);
        } catch (Exception e) {
            return ResultData.success("upload fail");
        }
        return ResultData.success(fileName);
    }
}
