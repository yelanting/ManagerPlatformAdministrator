增量代码功能测试覆盖率
=================================

## 通过以下命令完成当前项目编译，生成jacoco-maven插件：

```
mvn install -Dmaven.test.skip=true -pl jacoco-maven-plugin -am
```

## 配置应用服务器

覆盖率情况的收集我们使用的是Jacoco工具的on-the-fly模式.(关于Jacoco的详细介绍可参考:http://www.eclemma.org/jacoco/trunk/doc/index.html)

我们需要对应用服务器做如下配置:

- 1.下载![jacocoagent.jar](https://github.com/AngryTester/jacoco/raw/master/jacocoagent.jar)放置在应用服务器的`lib`中.

    - tomcat放置于`$CATALINA_HOME/lib`

    - jboss放置于`$JBOSS_HOME/lib`

- 2.修改应用服务器启动配置.

    - tomcat:修改`$CATALINA_HOME/bin/catalina.sh`,最后一行中加入如下内容,其中`2014`为获取覆盖率的端口,可自定义为其他空闲端口.

    ```shell
    JAVA_OPTS="$JAVA_OPTS -javaagent:$CATALINA_HOME/lib/jacocoagent.jar=includes=*,output=tcpserver,port=2014,address=*"
    ```

    - jboss:修改`$JBOSS_HOME/bin/run.conf`,最后一行中加入如下内容,其中`2014`为获取覆盖率的端口,可自定义为其他空闲端口.

    ```shell
    JAVA_OPTS="$JAVA_OPTS -javaagent:$JBOSS_HOME/lib/jacocoagent.jar=includes=*,output=tcpserver,port=2014,address=*"
    ```
> 注意，根据不同系统配置，变量`$CATALINA_HOME`和`$JBOSS_HOME`有可能获取不到值，jar包路径也可设置为绝对路径。


## 下载当前版本代码和需要对比的历史版本代码

> 注意：两个版本代码需处于同一级目录

## 导出增量覆盖率报告

在完成应用服务器配置之后,部署,启动和测试过程与原流程无异,在完成手动测试之后,我们希望导出增量代码部分的覆盖率报告,可在项目工作空间(即项目根目录)执行如下命令:



```shell
# compile(注意，编译环境需要与部署到测试环境的包的编译环境一致)
mvn compile

# dump
mvn org.jacoco:jacoco-maven-plugin:0.8.1-SNAPSHOT:dump -Djacoco.address=$ADDRESS -Djacoco.reset=false -Djacoco.port=$PORT -Djacoco.append=true

# report
mvn org.jacoco:jacoco-maven-plugin:0.8.1-SNAPSHOT:report-aggregate -DbaseDir=$baseDir
```

`$ADDRESS`为应用访问IP,`$PORT`为配置应用服务器时配置的端口(如上为2014),`$baseDir`为需要对比的历史版本代码文件夹名称。

*以上两行命令可重复执行,即在发现覆盖率情况不佳之后,可根据覆盖率情况新增测试用例执行,然后执行以上两行命令重新生成覆盖率报告,覆盖率情况默认会叠加.*

> 例如有两个分支,第一次生成覆盖率报告发现覆盖率分支1,未覆盖分支2,则可根据分支2业务新增测试用例执行覆盖分支2,重新生成覆盖率报告就会发现两个分支均得到了覆盖.


