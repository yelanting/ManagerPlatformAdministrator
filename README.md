管理平台功能介绍
=================================

## 1.概览：

    此平台出现的初衷，源于日常工作中的琐事，比如乱七八糟的测试环境地址和账号管理，测试组内部乱七八糟的资产分布，以及其他其他一些重复性多的工作，平台化或  者工具化可以适当减少投入时间。
    因此此平台也就产生了。


## 2.	有哪些功能
### 2.1 测试环境地址

含测试服务器地址、内部常用工具地址如禅道、云效等
### 2.2 物品管理
    可以记录终端、PC等信息(本可以用excel替代)
### 2.3 工具管理
    目前常用功能为代码覆盖率统计
    还有代码解析，可以从源码中解析出所有请求路径
## 3.	框架介绍
主要框架：
Springboot2.1.1
Mysql(请注意Mysql版本和connector的版本对应，否则会出错)
Mybatis
Layui
Jquery
因为有jacoco的功能，所以也有jacoco的源码，
整个工程通过多模块来实现统一打包。
## 4. 部署方式
假如下载到/home/admin/manager_platform中，此时目录下有诸多工程。
Org.jacoco开始的为jacoco源码相关；
manager-platform-server为web端
在manager_platform下运行
mvn clean package -Dmavek.test.skip=true -Dmaven.javadoc.skip=true
此步骤为打包，后面两个参数设置为跳过测试和javadoc生成，因为有些jacoco工程生成javadoc会报错，所以以此忽略。

## 5. 设置数据库
目前引用数据库为mysql，数据库为autotest_platform_administrator，登陆账号和密码自行设置,已经设置好允许远程连接。
如果自己要重新部署，则按照以下步骤
1.	安装mysql，或者直接找一台现成的，建一个新的库，记得设置好允许远程连接(此步骤请自行百度解决)
2.	到manager_platform\manager-platform-server\src\main\resources下，找到application-product.properties
修改里面的数据库实例名称、登录用户名和密码
3.	后续步骤，按照后面的顺序即可
后端主要代码在manager-platform-server工程中，
数据库初始化sql在：manager-platform-server/src/main/resources/data/sql/data-mysql.sql
以此sql初始化即可，执行完之后，顺序执行update*.sql文件，按照日期先后执行。
如果是全新的数据库里面没有任何表结构，则可以直接执行data-mysql-full.sql里面的内容，执行此一个文件即可(未做测试，不保证成功哈)。
## 6. 关于配置文件

配置文件在以下目录中，使用的为properties文件，也可以转为相应的yml文件
manager-platform-server/src/main/resources
激活的配置文件在application.properties 中默认是dev

## 7. 启动
日志的产生，跟运行命令的目录有关，
建议，cd到manager-platform-server中运行

nohup java -jar target/manager-platform-server-0.0.1.jar > log.file 2>&1 &

运行之后,不切换目录，运行
tail -200f log.file
等待日志中出现，Started **Application in 5.437 seconds (JVM running for 6.246)
如此内容，代表启动成功，默认端口为8020
稍后可以http://xxxx:8020访问

可以查看启动日志
另外每天的日志备份，和及时日志，在当前运行命令目录的上一层，也就是manager_platform下的logs中，因此运行：
cd ..
cd logs
ls
可以查看当前日志，最新的日志为springboot.log
   另外，跟logs同级的还有个my-tomcat目录，其下的logs文件夹下，存放了access日志，就是在网页中方位的url记录



