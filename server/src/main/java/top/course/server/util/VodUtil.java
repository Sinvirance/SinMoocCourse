package top.course.server.util;

import com.alibaba.fastjson.JSONObject;
import com.aliyun.oss.OSSClient;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.http.FormatType;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.vod.model.v20170321.CreateUploadVideoRequest;
import com.aliyuncs.vod.model.v20170321.CreateUploadVideoResponse;
import com.aliyuncs.vod.model.v20170321.RefreshUploadVideoRequest;
import com.aliyuncs.vod.model.v20170321.RefreshUploadVideoResponse;
import org.apache.commons.codec.binary.Base64;

import java.io.File;

/**
 * @Author: Sinvirance
 * @Date: 2021/3/3 23:22
 * @Description: 阿里云视频点播vod工具类
 */

public class VodUtil {

    /**
     * 使用AK初始化VOD客户端
     * @param accessKeyId
     * @param accessKeySecret
     * @return
     */
    public static DefaultAcsClient initVodClient(String accessKeyId, String accessKeySecret) throws ClientException {
        /* 根据点播接入服务所在的Region填写，例如：接入服务在上海，则填cn-shanghai */
        String regionId = "cn-shanghai";
        DefaultProfile profile = DefaultProfile.getProfile(regionId, accessKeyId, accessKeySecret);
        DefaultAcsClient client = new DefaultAcsClient(profile);
        return client;
    }


    /**
     * 获取视频上传地址和凭证
     * @param vodClient
     * @return
     * @throws ClientException
     */
    public static CreateUploadVideoResponse createUploadVideo(DefaultAcsClient vodClient) throws ClientException {
        CreateUploadVideoRequest request = new CreateUploadVideoRequest();
        request.setFileName("vod_test.mp4");
        request.setTitle("this is title");
        //request.setDescription("this is desc");
        //request.setTags("tag1,tag2");
        /* 视频封面 */
        request.setCoverURL("http://vod.aliyun.com/test_cover_url.jpg");
        /* 视频分类id */
        request.setCateId(1000259840L);
        /* 转码模板id */
        request.setTemplateGroupId("e72a1c45d98afb6c1c5553d6e7f0b044");
        //request.setWorkflowId("");
        //request.setStorageLocation("");
        //request.setAppId("app-1000000");
        //设置请求超时时间
        request.setSysReadTimeout(1000);
        request.setSysConnectTimeout(1000);
        return vodClient.getAcsResponse(request);
    }


    /**
     * 使用上传凭证和地址初始化OSS客户端（注意需要先Base64解码并Json Decode再传入）
     * @param uploadAuth
     * @param uploadAddress
     * @return
     */
    public static OSSClient initOssClient(JSONObject uploadAuth, JSONObject uploadAddress) {
        String endpoint = uploadAddress.getString("Endpoint");
        String accessKeyId = uploadAuth.getString("AccessKeyId");
        String accessKeySecret = uploadAuth.getString("AccessKeySecret");
        String securityToken = uploadAuth.getString("SecurityToken");
        return new OSSClient(endpoint, accessKeyId, accessKeySecret, securityToken);
    }


    /**
     * 上传本地文件
     * @param ossClient
     * @param uploadAddress
     * @param localFile
     */
    public static void uploadLocalFile(OSSClient ossClient, JSONObject uploadAddress, String localFile){
        String bucketName = uploadAddress.getString("Bucket");
        String objectName = uploadAddress.getString("FileName");
        File file = new File(localFile);
        /* 单文件上传 */
        ossClient.putObject(bucketName, objectName, file);

        /* 追加上传不支持 */
    }


    /**
     * 刷新上传凭证
     * @param vodClient
     * @return
     */
    public static RefreshUploadVideoResponse refreshUploadVideo(DefaultAcsClient vodClient) throws ClientException {
        RefreshUploadVideoRequest request = new RefreshUploadVideoRequest();
        request.setAcceptFormat(FormatType.JSON);
        request.setVideoId("VideoId");
        //设置请求超时时间
        request.setSysReadTimeout(1000);
        request.setSysConnectTimeout(1000);
        return vodClient.getAcsResponse(request);
    }


    public static void main(String[] argv) {
        //AccessKeyId
        String accessKeyId = "LTAI4G6S5opNkQwySdjfxFev";
        //AccessKeySecret
        String accessKeySecret = "qVrICZfqfBOe17jpSzcvZ8G2rCgbEs";
        //需要上传到VOD的本地视频文件的完整路径，需要包含文件扩展名
        String localFile = "D:\\Sinvirance\\WorkSpace\\JavaProject\\SinMooc\\static\\course\\section-video\\test2.mp4";
        try {
            // 初始化VOD客户端并获取上传地址和凭证
            DefaultAcsClient vodClient = initVodClient(accessKeyId, accessKeySecret);
            CreateUploadVideoResponse createUploadVideoResponse = createUploadVideo(vodClient);
            // 执行成功会返回VideoId、UploadAddress和UploadAuth
            String videoId = createUploadVideoResponse.getVideoId();
            JSONObject uploadAuth =
                    JSONObject.parseObject(Base64.decodeBase64(createUploadVideoResponse.getUploadAuth()), JSONObject.class);
            JSONObject uploadAddress =
                    JSONObject.parseObject(org.apache.commons.codec.binary.Base64.decodeBase64(createUploadVideoResponse.getUploadAddress()), JSONObject.class);
            // 使用UploadAuth和UploadAddress初始化OSS客户端
            OSSClient ossClient = initOssClient(uploadAuth, uploadAddress);
            // 上传文件，注意是同步上传会阻塞等待，耗时与文件大小和网络上行带宽有关
            uploadLocalFile(ossClient, uploadAddress, localFile);
            System.out.println("上传视频成功, VideoId : " + videoId);
        } catch (Exception e) {
            System.out.println("上传视频失败 : " + e.getLocalizedMessage());
        }
    }
}