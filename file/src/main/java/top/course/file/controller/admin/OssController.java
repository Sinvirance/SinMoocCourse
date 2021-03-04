package top.course.file.controller.admin;

import com.alibaba.fastjson.JSONObject;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.model.AppendObjectRequest;
import com.aliyun.oss.model.AppendObjectResult;
import com.aliyun.oss.model.ObjectMetadata;
import com.aliyun.oss.model.PutObjectRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import top.course.server.dto.FileDto;
import top.course.server.dto.ResponseDto;
import top.course.server.enums.FileUseEnum;
import top.course.server.service.FileService;
import top.course.server.util.Base64ToMultipartFile;
import top.course.server.util.UUIDUtil;

import javax.annotation.Resource;
import java.io.ByteArrayInputStream;
import java.io.IOException;

/**
 * @Author: Sinvirance
 * @Date: 2021/3/1 17:38
 * @Description: OSS对象存储Controller
 */

@RestController
@RequestMapping(value = "/admin")
public class OssController {

    private static final Logger LOG = LoggerFactory.getLogger(OssController.class);

    @Value("${accessKeyId}")
    private String accessKeyId;

    @Value("${accessKeySecret}")
    private String accessKeySecret;

    @Value("${endpoint}")
    private String endpoint;

    @Value("${ossDomain}")
    private String ossDomain;

    @Value("${bucket}")
    private String bucket;

    @Resource
    private FileService fileService;

    public static final String BUSINESS_NAME = "文件上传";

    /**
     * 阿里云OSS 追加上传
     * @param fileDto 上传文件传输对象
     * @return 统一返回响应对象
     */
    @PostMapping("oss-append")
    public ResponseDto fileUpload(@RequestBody FileDto fileDto) throws IOException {
        LOG.info("OSS文件上传开始");
        String use = fileDto.getUse();
        String key = fileDto.getKey();
        String suffix = fileDto.getSuffix();
        Integer shardIndex = fileDto.getShardIndex();
        Integer shardSize = fileDto.getShardSize();
        String shardBase64 = fileDto.getShard();
        MultipartFile shard = Base64ToMultipartFile.base64ToMultipart(shardBase64);

        FileUseEnum useEnum = FileUseEnum.getByCode(use);

        String dir = useEnum.name().toLowerCase();

        String path = new StringBuffer(dir).
                append("/").
                append(key).
                append(".").
                append(suffix).
                toString();

        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
        ObjectMetadata meta = new ObjectMetadata();
        // 指定上传的内容类型。
        meta.setContentType("text/plain");
        // 通过AppendObjectRequest设置多个参数。
        AppendObjectRequest appendObjectRequest =
                new AppendObjectRequest(bucket, path, new ByteArrayInputStream(shard.getBytes()),meta);

        // 第一次追加
        /* 设置文件的追加位置 */
        appendObjectRequest.setPosition((long) ((shardIndex - 1) * shardSize));
        AppendObjectResult appendObjectResult = ossClient.appendObject(appendObjectRequest);
        /* 文件的64位CRC值。此值根据ECMA-182标准计算得出 */
        System.out.println(appendObjectResult.getObjectCRC());
        System.out.println(JSONObject.toJSONString(appendObjectResult));

        // 关闭OSSClient
        ossClient.shutdown();

        /* 保存文件记录 */
        LOG.info("OSS保存文件记录开始");
        fileDto.setPath(path);
        fileService.save(fileDto);

        ResponseDto responseDto = new ResponseDto();
        /* 将图片位置返回前端 */
        fileDto.setPath(ossDomain + path);
        responseDto.setContent(fileDto);
        return responseDto;
    }


    /**
     * 阿里云OSS简单上传
     * @param file 上传文件
     * @param use 上传文件名
     * @return 统一返回响应对象
     */
    @PostMapping("/oss-simple")
    public ResponseDto fileUpload(@RequestParam MultipartFile file, String use) throws Exception {
        LOG.info("OSS上传文件开始");
        FileUseEnum useEnum = FileUseEnum.getByCode(use);
        String key = UUIDUtil.getShortUUID();
        String fileName = file.getOriginalFilename();
        String suffix = fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();
        String dir = useEnum.name().toLowerCase();
        String path = dir + "/" + key + "." + suffix;

        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);

        // 创建PutObjectRequest对象。
        // <yourObjectName>表示上传文件到OSS时需要指定包含文件后缀在内的完整路径，例如abc/efg/123.jpg。
        PutObjectRequest putObjectRequest = new PutObjectRequest(bucket, path, new ByteArrayInputStream(file.getBytes()));

        // 上传字符串。
        ossClient.putObject(putObjectRequest);

        ResponseDto responseDto = new ResponseDto();
        FileDto fileDto = new FileDto();
        fileDto.setPath(ossDomain + path);
        responseDto.setContent(fileDto);

        return responseDto;
    }


    /**
     * 根据文件标识检查分片是否被上传
     * @param key 文件标识key
     * @return 统一返回响应对象
     */
    @GetMapping("/oss-check/{key}")
    public ResponseDto check(@PathVariable String key) {
        LOG.info("OSS检查上传分片开始：{}", key);
        ResponseDto responseDto = new ResponseDto();
        FileDto fileDto = fileService.findByKey(key);
        if (fileDto != null) {
            fileDto.setPath(ossDomain + fileDto.getPath());
        }
        responseDto.setContent(fileDto);
        return responseDto;
    }


}