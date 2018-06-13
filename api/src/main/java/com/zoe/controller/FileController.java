package com.zoe.controller;


import com.zoe.spring.resultInfo.ResultData;
import com.zoe.spring.utils.FileUploadUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
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
    @PostMapping(value = "/upload",consumes = "multipart/*", headers = "content-type=multipart/form-data")
    @ApiOperation(value = "上传")
    public ResultData uploadFile(@RequestBody @ApiParam("file") MultipartFile file) throws IOException {
        return ResultData.success(FileUploadUtils.uploadFile(file));
    }

    @PostMapping(value = "/batchUpload")
    @ApiOperation(value = "批量文件上传")
    public ResultData batchUploadFile(@RequestBody @ApiParam(value = "批量文件",required = true) MultipartFile[] files) throws IOException {
        List<MultipartFile> multipartFiles= Arrays.asList(files);
        StringBuffer sb=new StringBuffer();
        for(int i=0;i<files.length;i++){
            sb.append(FileUploadUtils.uploadFile(multipartFiles.get(i)));
        }
        return ResultData.success(sb.toString());
    }

}
