/**
 * @author : 孙留平
 * @since : 2018年11月22日 下午9:05:01
 * @see:
 */
package com.administrator.platform.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.administrator.platform.model.XmindParser;

/**
 * @author : Administrator
 * @since : 2018年11月22日 下午9:05:01
 * @see :
 */
public interface XmindParserService {
    /**
     * 查询地址列表
     * 
     * @see :
     * @param :
     * @return : List<XmindParser>
     * @return
     */
    List<XmindParser> findAllXmindParserList();

    /**
     * 添加地址
     * 
     * @see :
     * @param :
     * @return : XmindParser
     * @param goodType
     * @return
     */
    XmindParser addXmindParser(XmindParser goodType);

    /**
     * 根据id查询对象
     * 
     * @see :
     * @param :
     * @return : XmindParser
     * @param id
     * @return
     */
    XmindParser getXmindParserById(Long id);

    /**
     * 根据对象查询对象
     * 
     * @see :
     * @param :
     * @return : XmindParser
     * @param goodType
     * @return
     */
    XmindParser getXmindParserByObject(XmindParser goodType);

    /**
     * 根据id删除
     * 
     * @see :
     * @param :
     * @return : int
     * @param id
     * @return
     */
    int deleteXmindParser(Long id);

    /**
     * 根据id批量删除
     * 
     * @see :
     * @param :
     * @return : int
     * @param ids
     * @return
     */
    int deleteXmindParser(Long[] ids);

    /**
     * 根据名称查询
     * 
     * @see :
     * @param :
     * @return : List<XmindParser>
     * @param searchContent
     * @return
     */
    XmindParser findXmindParserByName(String searchContent);

    /**
     * 根据名称模糊查询
     * 
     * @see :
     * @param :
     * @return : List<XmindParser>
     * @param searchContent
     * @return
     */
    List<XmindParser> findXmindParsersByNameLike(String searchContent);

    /**
     * 分页查询
     * 
     * @see :
     * @param :
     * @return : Page<XmindParser>
     * @param page
     * @param size
     * @return
     */
    Page<XmindParser> findXmindParserByPage(Integer page, Integer size);
}
