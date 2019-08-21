/**
 * @author : 孙留平
 * @since : 2018年11月30日 下午4:56:17
 * @see:
 */
package com.administrator.platform.testcase.dubbotest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.administrator.platform.core.base.util.StringUtil;
import com.administrator.platform.core.base.util.ValidationUtil;
import com.administrator.platform.model.DubboTest;
import com.administrator.platform.testcase.dubbotest.dto.InParams;
import com.administrator.platform.testcase.dubbotest.dto.IncomeParamsParser;
import com.alibaba.dubbo.config.ApplicationConfig;
import com.alibaba.dubbo.config.ReferenceConfig;
import com.alibaba.dubbo.config.RegistryConfig;
import com.alibaba.dubbo.config.utils.ReferenceConfigCache;
import com.alibaba.dubbo.rpc.service.GenericService;
import com.alibaba.fastjson.JSON;

/**
 * @author : Administrator
 * @since : 2018年11月30日 下午4:56:17
 * @see :
 */
public class DubboTestGeneralizationInvoke {
    private static final Logger logger = LoggerFactory
            .getLogger(DubboTestGeneralizationInvoke.class);
    private ApplicationConfig application;
    private RegistryConfig registry;
    private static DubboTestGeneralizationInvoke instance = new DubboTestGeneralizationInvoke();

    /**
     * 获取DubboGeneralizationService实例
     * 
     * @see :
     * @param :
     * @return : DubboTestGeneralizationInvoke
     * @return
     */
    public static DubboTestGeneralizationInvoke getInstance() {
        return instance;
    }

    /**
     * 实例化该对象后立即初始化dubbo配置信息
     * 
     * @see :
     * @param :
     */
    private DubboTestGeneralizationInvoke() {
    }

    /**
     * 实例化该对象后立即初始化dubbo配置信息
     * 
     * @see :
     * @param :
     */
    private void initDubboTestConfig(DubboTest dubboTest) {
        ValidationUtil.validateNull(dubboTest, "dubboTest");

        // 配置dubbo的application信息
        ApplicationConfig applicationConfig = new ApplicationConfig();
        applicationConfig.setName("dubbo-consumer");
        // 配置dubbo的注册中心信息，我的信息都配置在xconfig里面，从里面拿数据，你们可以根据自己的配置修改就可以了
        RegistryConfig registryConfig = new RegistryConfig();
        registryConfig.setProtocol(dubboTest.getProtocolName());
        registryConfig.setAddress(dubboTest.getAddress());
        registryConfig.setGroup(dubboTest.getGroupName());
        registryConfig.setClient(dubboTest.getClient());
        registryConfig.setId(dubboTest.getId().toString());
        this.application = applicationConfig;
        this.registry = registryConfig;
    }

    public Object dubboInterfaceInvoke(DubboTest dubboTest) {
        initDubboTestConfig(dubboTest);

        // 连接注册中心配置
        ReferenceConfig<GenericService> reference = new ReferenceConfig<GenericService>();
        reference.setApplication(this.application);
        reference.setRegistry(this.registry);
        reference.setInterface(dubboTest.getInterfaceName());
        // 声明为泛化接口
        reference.setGeneric(true);
        reference.setAsync(false);

        if (StringUtil.isStringAvaliable(dubboTest.getVersion())) {
            reference.setVersion(dubboTest.getVersion());
        }

        /**
         * @author administrator created on 2018年12月3日 21:29:58
         *         ReferenceConfig实例很重，封装了与注册中心的连接以及与提供者的连接，
         *         需要缓存，否则重复生成ReferenceConfig可能造成性能问题并且会有内存和连接泄漏。
         *         API方式编程时，容易忽略此问题。这里使用dubbo内置的简单缓存工具类进行缓存
         *                  
         */
        ReferenceConfigCache cache = ReferenceConfigCache.getCache();
        GenericService genericService = cache.get(reference);

        IncomeParamsParser incomeParamsParser = new IncomeParamsParser();
        incomeParamsParser.psrseIncomePrams(
                JSON.parseArray(dubboTest.getIncomeParams(), InParams.class));
        Object result = genericService.$invoke(dubboTest.getMethodName(),
                incomeParamsParser.getParamTypes(),
                incomeParamsParser.getParamValues());

        logger.debug("dubboInterfaceInvoke result：{}", result);
        return result;
    }
}
