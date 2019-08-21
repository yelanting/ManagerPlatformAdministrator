package com.administrator.platform.mapper;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.administrator.platform.model.XmindParser;

/**
 * @author : Administrator
 * @since : 2018年12月27日 16:47:26
 * @see :
 */
public interface XmindParserMapper {
    /**
     * 根据主键删除
     * 
     * @see :
     * @param :
     * @return : int
     * @param id
     * @return
     */
    int deleteByPrimaryKey(Long id);

    int insert(XmindParser record);

    int insertSelective(XmindParser record);

    XmindParser selectByPrimaryKey(Long id);

    /**
     * 根据名称查询
     * 
     * @see :
     * @param :
     * @return : List<XmindParser>
     * @param name
     * @return
     */
    XmindParser findXmindParserByXmindFileName(String xmindFileName);

    /**
     * 根据名称模糊查询
     * 
     * @see :
     * @param :
     * @return : List<XmindParser>
     * @param name
     * @return
     */
    List<XmindParser> findXmindParsersByXmindFileNameLike(String xmindFileName);

    /**
     * 根据ID批量删除
     * 
     * @see :
     * @param :
     * @return : void
     * @param ids
     */
    void deleteByIds(Long[] ids);

    /**
     * 分页查询
     * 
     * @see :
     * @param pageable:分页信息
     * @return : Page<XmindParser>
     */
    Page<XmindParser> findAll(Pageable pageable);

    List<XmindParser> findAll();
}
