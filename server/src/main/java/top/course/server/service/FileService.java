package top.course.server.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import top.course.server.domain.File;
import top.course.server.domain.FileExample;
import top.course.server.dto.FileDto;
import top.course.server.dto.PageDto;
import top.course.server.mapper.FileMapper;
import top.course.server.util.CopyUtil;
import top.course.server.util.UUIDUtil;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * @Author: Sinvirance
 * @Date: 2021/xx/xx xx:xx
 * @Description: File持久层接口
 */

@Service
public class FileService {
    @Resource
    private FileMapper fileMapper;

    /**
     * file表列表分页查询
     * @param pageDto 分页组件传输对象
     */
    public void list(PageDto pageDto) {
        PageHelper.startPage(pageDto.getPage(), pageDto.getSize());
        FileExample fileExample = new FileExample();
        List<File> fileList = fileMapper.selectByExample(fileExample);

        PageInfo<File> pageInfo = new PageInfo<>(fileList);
        pageDto.setTotal(pageInfo.getTotal());
        List<FileDto> fileDtoList = CopyUtil.copyList(fileList, FileDto.class);
        pageDto.setList(fileDtoList);
    }

    /**
     * 保存: FileDto对象文件标识key存在属性值时更新，无值时新增
     * @param fileDto 数据传输对象
     */
    public void save(FileDto fileDto) {
        File file = CopyUtil.copy(fileDto, File.class);
        File fileDb = selectByKey(fileDto.getKey());
        if (fileDb == null) {
            this.insert(file);
        } else {
            fileDb.setShardIndex(fileDto.getShardIndex());
            this.update(fileDb);
        }
    }

    /**
     * 新增:生成短id作为File对象id插入file表
     * @param file (无ID)File对象
     */
    private void insert(File file) {
        Date now = new Date();
        file.setCreatedAt(now);
        file.setUpdatedAt(now);
        file.setId(UUIDUtil.getShortUUID());
        fileMapper.insert(file);
    }

    /**
     * 更新:根据File对象id查询条件修改file表数据
     * @param file (有ID)File对象
     */
    private void update(File file) {
        file.setUpdatedAt(new Date());
        fileMapper.updateByPrimaryKey(file);
    }

    /**
     * 删除: 根据id删除file表数据
     * @param id id
     */
    public void delete(String id) {
        fileMapper.deleteByPrimaryKey(id);
    }

    /**
     * 查询: 根据File表标识key获取File对象
     * @param key 文件标识key
     * @return File对象
     */
    public File selectByKey(String key) {
        FileExample example = new FileExample();
        example.createCriteria().andKeyEqualTo(key);
        List<File> fileList = fileMapper.selectByExample(example);
        if (CollectionUtils.isEmpty(fileList)) {
            return null;
        } else {
            return fileList.get(0);
        }
    }


    /**
     * 查询: 根据文件标识获得对应文件记录
     * @param key 文件标识key
     * @return File传输对象
     */
    public FileDto findByKey(String key) {
        return CopyUtil.copy(selectByKey(key), FileDto.class);
    }

}