package top.course.server.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import top.course.server.domain.File;
import top.course.server.domain.FileExample;
import top.course.server.dto.FileDto;
import top.course.server.dto.PageDto;
import top.course.server.mapper.FileMapper;
import top.course.server.util.CopyUtil;
import top.course.server.util.UUIDUtil;

import javax.annotation.Resource;
import java.util.List;
import java.util.Date;

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
     * 保存: FileDto对象有id属性值时更新，无值时新增
     * @param fileDto 数据传输对象
     */
    public void save(FileDto fileDto) {
        File file = CopyUtil.copy(fileDto, File.class);
        if (StringUtils.isEmpty(fileDto.getId())) {
            this.insert(file);
        } else {
            this.update(file);
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
}