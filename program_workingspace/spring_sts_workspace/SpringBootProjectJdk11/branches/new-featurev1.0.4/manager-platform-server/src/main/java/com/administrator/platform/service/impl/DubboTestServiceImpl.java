/**
 * @author : 孙留平
 * @since : 2018年9月3日 下午8:17:27
 * @see:
 */
package com.administrator.platform.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.administrator.platform.core.base.util.StringUtil;
import com.administrator.platform.core.base.util.ValidationUtil;
import com.administrator.platform.definition.form.DubboTestFormDefinition;
import com.administrator.platform.exception.base.BusinessValidationException;
import com.administrator.platform.mapper.DubboTestMapper;
import com.administrator.platform.model.DubboTest;
import com.administrator.platform.service.DubboTestService;
import com.administrator.platform.testcase.dubbotest.DubboTestGeneralizationInvoke;

/**
 * @author : Administrator
 * @since : 2018年9月3日 下午8:17:27
 * @see :
 */
@Service
public class DubboTestServiceImpl implements DubboTestService {
    private static final Logger logger = LoggerFactory
            .getLogger(DubboTestServiceImpl.class);
    @Autowired
    private DubboTestMapper dubboTestMapper;

    /**
     * @see com.administrator.platform.service.DubboTestService#findAllDubboTestList()
     */
    @Override
    public List<DubboTest> findAllDubboTestList() {
        logger.info("查询dubboTest信息列表");
        return dubboTestMapper.findAllDubboTests();
    }

    /**
     * 
     * 添加测试环境地址信息实现方法
     * 
     * @see
     *      com.administrator.platform.service.DubboTestService#addDubboTest(com.administrator.platform.model.DubboTest)
     */
    @Override
    public DubboTest addDubboTest(DubboTest dubboTest) {
        ValidationUtil.validateNull(dubboTest, null);
        validateInput(dubboTest);
        try {
            logger.info("添加dubbo接口测试");
            dubboTestMapper.insert(dubboTest);
            return dubboTest;
        } catch (Exception e) {
            logger.error("新增失败:{},失败信息{}", dubboTest, e.getMessage());
            throw new BusinessValidationException("新增dubbo接口测试失败");
        }
    }

    /**
     * 根据ID删除的具体实现
     * 
     * 
     * @see com.administrator.platform.service.DubboTestService#deleteDubboTest(java.lang.Long)
     */
    @Override
    public int deleteDubboTest(Long id) {
        ValidationUtil.validateNull(id, null);
        try {
            return dubboTestMapper.deleteByPrimaryKey(id);
        } catch (Exception e) {
            throw new BusinessValidationException("根据ID删除失败");
        }
    }

    /**
     * 根据ID批量删除
     * 
     * @see com.administrator.platform.service.DubboTestService#deleteDubboTest(java.lang.Long[])
     */
    @Override
    public int deleteDubboTest(Long[] ids) {
        ValidationUtil.validateArrayNullOrEmpty(ids, null);
        try {
            logger.info("批量删除dubbo接口测试");
            dubboTestMapper.deleteByIds(ids);
            return 1;
        } catch (Exception e) {
            throw new BusinessValidationException("根据IDS批量删除失败");
        }
    }

    /**
     * 根据名称查询
     * 
     * @see com.administrator.platform.service.DubboTestService#findEnvAddrrsByProjectName(java.lang.String)
     */
    @Override
    public List<DubboTest> findDubboTestesByInterfaceName(
            String searchContent) {
        ValidationUtil.validateStringNullOrEmpty(searchContent, null);
        return dubboTestMapper.findDubboTestesByInterfaceName(searchContent);
    }

    /**
     * 根据名称模糊查询
     * 
     * @see com.administrator.platform.service.DubboTestService#findEnvAddrrsByProjectName(java.lang.String)
     */
    @Override
    public List<DubboTest> findDubboTestesByInterfaceNameLike(
            String searchContent) {
        ValidationUtil.validateStringNullOrEmpty(searchContent, null);
        logger.info("根据项目名称查询dubboTest信息列表");
        return dubboTestMapper
                .findDubboTestesByInterfaceNameLike(searchContent);
    }

    /**
     * 校验输入内容
     * 
     * @see :
     * @return : void
     * @param dubboTest
     *            : 待校验的地址对象
     */
    private void validateInput(DubboTest dubboTest) {
        // 判空
        ValidationUtil.validateNull(dubboTest, null);

        if (!StringUtil.isStringAvaliable(dubboTest.getCaseName())) {
            throw new BusinessValidationException("测试用例名称不能为空");
        }

        ValidationUtil.validateStringAndLength(dubboTest.getCaseName(), null,
                DubboTestFormDefinition.CASE_NAME_MAX_LENGTH, "测试用例名称");

        ValidationUtil.validateStringAndLength(dubboTest.getProtocolName(),
                null, DubboTestFormDefinition.COMMON_FORM_FIELD_LENGTH, "协议名称");
        ValidationUtil.validateStringAndLength(dubboTest.getAddress(), null,
                DubboTestFormDefinition.COMMON_FORM_FIELD_LENGTH, "地址");
        ValidationUtil.validateStringAndLength(dubboTest.getIncomeParams(),
                null, DubboTestFormDefinition.PARAM_FIELD_MAX_LENGTH, "入参参数列表");
        ValidationUtil.validateStringAndLength(
                dubboTest.getDubboContextParams(), null,
                DubboTestFormDefinition.PARAM_FIELD_MAX_LENGTH, "传入dubbo上下文参数");

    }

    /**
     * 修改测试环境地址
     * 
     * @param dubboTest:地址对象
     * @see com.administrator.platform.service.DubboTestService#updateDubboTest(com.administrator.platform.model.DubboTest)
     */
    @Override
    public DubboTest updateDubboTest(DubboTest dubboTest) {
        ValidationUtil.validateNull(dubboTest, null);
        DubboTest currentDubboTest = getDubboTestByObject(dubboTest);

        if (null == currentDubboTest) {
            throw new BusinessValidationException("待修改的对象不存在，不能修改");
        }
        dubboTest.setId(currentDubboTest.getId());

        // 校验输入内容
        validateInput(dubboTest);
        try {
            logger.info("修改dubbo接口测试");
            dubboTestMapper.updateByPrimaryKey(dubboTest);
        } catch (Exception e) {
            logger.error("更新失败:{}", dubboTest);
            throw new BusinessValidationException("更新失败!");
        }

        return null;
    }

    /**
     * 根据ID查询实体
     * 
     * @param Long
     *            id:待查询的id
     * @see com.administrator.platform.service.DubboTestService#getDubboTestById(java.lang.Long)
     */
    @Override
    public DubboTest getDubboTestById(Long id) {
        ValidationUtil.validateNull(id, null);
        return dubboTestMapper.selectByPrimaryKey(id);
    }

    /**
     * 根据实体查询实体是否存在
     * 
     * @see com.administrator.platform.service.DubboTestService#getDubboTestByObject(com.administrator.platform.model.DubboTest)
     */
    @Override
    public DubboTest getDubboTestByObject(DubboTest dubboTest) {
        ValidationUtil.validateNull(dubboTest, null);
        return getDubboTestById(dubboTest.getId());
    }

    /**
     * 分页查询
     * 
     * @return
     * 
     * @see com.administrator.platform.service.DubboTestService#findDubboTestByPage(java.lang.Integer,
     *      java.lang.Integer)
     */
    @Override
    public Page<DubboTest> findDubboTestByPage(Integer page, Integer size) {
        ValidationUtil.validateNull(page, null);
        ValidationUtil.validateNull(size, null);
        Pageable pageable = PageRequest.of(page - 1, size);
        return dubboTestMapper.findAll(pageable);
    }

    @Override
    public Object runDubboTest(DubboTest dubboTest) {
        return DubboTestGeneralizationInvoke.getInstance()
                .dubboInterfaceInvoke(dubboTest);
    }
}
