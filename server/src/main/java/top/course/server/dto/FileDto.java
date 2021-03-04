package top.course.server.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * @Author: Sinvirance
 * @Date: 2021/02/10 03:38
 * @Description: File数据传输对象
 */

@Getter
@Setter
public class FileDto {

    /* id */
    private String id;

    /* 相对路径 */
    private String path;

    /* 文件名 */
    private String name;

    /* 后缀 */
    private String suffix;

    /* 大小|字节B */
    private Integer size;

    /* 用途|枚举[FileUseEnum]：COURSE("C", "讲师"), TEACHER("T", "课程") */
    private String use;

    /* 创建时间 */
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date createdAt;

    /* 修改时间 */
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date updatedAt;

    /* 分片索引 */
    private Integer shardIndex;

    /* 分片大小 */
    private Integer shardSize;

    /* 分片总数 */
    private Integer shardTotal;

    /* 分片文件唯一标识 */
    private String key;

    /* base64 */
    private String shard;

    /* 阿里云vod */
    private String vod;

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("FileDto{");
        sb.append("id='").append(id).append('\'');
        sb.append(", path='").append(path).append('\'');
        sb.append(", name='").append(name).append('\'');
        sb.append(", suffix='").append(suffix).append('\'');
        sb.append(", size=").append(size);
        sb.append(", use='").append(use).append('\'');
        sb.append(", createdAt=").append(createdAt);
        sb.append(", updatedAt=").append(updatedAt);
        sb.append(", shardIndex=").append(shardIndex);
        sb.append(", shardSize=").append(shardSize);
        sb.append(", shardTotal=").append(shardTotal);
        sb.append(", key='").append(key).append('\'');
        sb.append(", shard='").append(shard).append('\'');
        sb.append(", vod='").append(vod).append('\'');
        sb.append('}');
        return sb.toString();
    }
}