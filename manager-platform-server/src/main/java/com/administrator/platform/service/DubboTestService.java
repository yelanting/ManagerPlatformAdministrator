/**
 * @author : 孙留平
 * @since : 2018年11月30日 19:24:15
 * @see:
 */
package com.administrator.platform.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.administrator.platform.model.DubboTest;

/**
 * @author : Administrator
 * @since : 2018年11月30日 19:24:10
 * @see :
 */
public interface DubboTestService {
    /**
     * 查询地址列表
     * 
     * @see :
     * @param :
     * @return : List<DubboTest>
     * @return
     */
    List<DubboTest> findAllDubboTestList();

    /**
     * 添加地址
     * 
     * @see :
     * @param :
     * @return : DubboTest
     * @param envAddress
     * @return
     */
    DubboTest addDubboTest(DubboTest envAddress);

    /**
     * 修改地址
     * 
     * @see :
     * @param :
     * @return : DubboTest
     * @param envAddress
     * @return
     */
    DubboTest updateDubboTest(DubboTest envAddress);

    /**
     * 根据id查询对象
     * 
     * @see :
     * @param :
     * @return : DubboTest
     * @param id
     * @return
     */
    DubboTest getDubboTestById(Long id);

    /**
     * 根据对象查询对象
     * 
     * @see :
     * @param :
     * @return : DubboTest
     * @param envAddress
     * @return
     */
    DubboTest getDubboTestByObject(DubboTest envAddress);

    /**
     * 根据id删除
     * 
     * @see :
     * @param :
     * @return : int
     * @param id
     * @return
     */
    int deleteDubboTest(Long id);

    /**
     * 根据id批量删除
     * 
     * @see :
     * @param :
     * @return : int
     * @param ids
     * @return
     */
    int deleteDubboTest(Long[] ids);

    /**
     * 根据名称查询
     * 
     * @see :
     * @param :
     * @return : List<DubboTest>
     * @param searchContent
     * @return
     */
    List<DubboTest> findDubboTestesByInterfaceName(String searchContent);

    /**
     * 根据名称模糊查询
     * 
     * @see :
     * @param :
     * @return : List<DubboTest>
     * @param searchContent
     * @return
     */
    List<DubboTest> findDubboTestesByInterfaceNameLike(String searchContent);

    /**
     * 分页查询
     * 
     * @see :
     * @param :
     * @return : Page<DubboTest>
     * @param page
     * @param size
     * @return
     */
    Page<DubboTest> findDubboTestByPage(Integer page, Integer size);

    /**
     * 执行dubbo测试
     * 
     * @see :
     * @param :
     * @return : Object
     * @param dubboTest
     * @return
     */
    Object runDubboTest(DubboTest dubboTest);
}
