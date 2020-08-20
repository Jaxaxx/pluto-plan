package com.mine.common.mybatis.generator;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.converts.MySqlTypeConvert;
import com.baomidou.mybatisplus.generator.config.po.TableFill;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @description: 代码生成器
 * @from: com.baomidou.mybatisplus
 */
public class MpGenerator {

    /**
     * DataSource config
     * tableNames : {多个表用逗号分隔}
     */
    private static String dateSourceUrl = "jdbc:mysql://127.0.0.1:3306/test-a?useUnicode=true&characterEncoding=utf8&autoReconnect=true&failOverReadOnly=false&serverTimezone=Asia/Shanghai&useOldAliasMetadataBehavior=true&useSSL=false";
    private static String dataSourceUserName = "root";
    private static String dataSourcePassWord = "MyNewPass4!";
    private static String dataSourceDriverName = "com.mysql.cj.jdbc.Driver";
    private static String[] tableNames = {"sys_log"};

    /**
     * Project config
     */
    private static String author = "jax-li";
    private static String projectDir = "D:\\";
    private static String parent = "com.mine";
    private static String moduleName = "upmsx";


    /**
     * 下面是一些可选的配置
     */
    private static String mapperTemplatePath = "/templates/mapper.xml.vm";
    private static String dtoTemplatePath = "/templates/dto.java.vm";
    private static String voTemplatePath = "/templates/vo.java.vm";
    private static String deleteFlag = "is_deleted";
    private static String version = "version";
    private static DbType dbType = DbType.MYSQL;
    private static ITypeConvert iTypeConvert = new MySqlTypeConvert();
    private static NamingStrategy tableStrategy = NamingStrategy.underline_to_camel;     // 表名生成策略

    private static String frameDir = parent.replace(".", "/");
    private static String rootDir = projectDir + "/src/main/";
    private static String dtoDir = rootDir + "java/" + frameDir + "/" + moduleName + "/dto/";
    private static String voDir = rootDir + "java/" + frameDir + "/" + moduleName + "/vo/";
    private static String mapperDir = rootDir + "resources/mapper/";

    private static List<TableFill> tableFills = new ArrayList<>();

    /**
     *  自动填充字段
     */
    static {
        TableFill createTime = new TableFill("create_time", FieldFill.INSERT);
        TableFill createUserId = new TableFill("create_user_id", FieldFill.INSERT);
        TableFill createUserName = new TableFill("create_user_name", FieldFill.INSERT);
        TableFill updateTime = new TableFill("update_time", FieldFill.UPDATE);
        TableFill updateUserId = new TableFill("update_user_id", FieldFill.UPDATE);
        TableFill updateUserName = new TableFill("update_user_name", FieldFill.UPDATE);
        tableFills.add(createTime);
        tableFills.add(createUserId);
        tableFills.add(createUserName);
        tableFills.add(updateTime);
        tableFills.add(updateUserId);
        tableFills.add(updateUserName);
    }

    public static void main(String[] args) {

        AutoGenerator mpg = new AutoGenerator();
        // 全局配置
        GlobalConfig gc = new GlobalConfig();
        gc.setOutputDir(rootDir + "java");
        gc.setFileOverride(true);   //
        gc.setActiveRecord(true);   // 不需要ActiveRecord特性的请改为false
        gc.setEnableCache(false);   // XML 二级缓存
        gc.setBaseResultMap(true);  // XML ResultMap
        gc.setBaseColumnList(true); // XML columList
        gc.setAuthor(author);
        mpg.setGlobalConfig(gc);
        // 数据源配置
        DataSourceConfig dsc = new DataSourceConfig();
        dsc.setDbType(dbType);
        dsc.setTypeConvert(iTypeConvert);
        dsc.setDriverName(dataSourceDriverName);
        dsc.setUsername(dataSourceUserName);
        dsc.setPassword(dataSourcePassWord);
        dsc.setUrl(dateSourceUrl);
        mpg.setDataSource(dsc);
        // 策略配置
        StrategyConfig strategy = new StrategyConfig();
        // strategy.setCapitalMode(true);// 全局大写命名 ORACLE 注意
//        strategy.setTablePrefix(Prefix);// 此处可以修改为您的表前缀
        strategy.setNaming(tableStrategy);// 表名生成策略
        strategy.setInclude(tableNames); // 需要生成的表
        strategy.setLogicDeleteFieldName(deleteFlag);
        strategy.setVersionFieldName(version);
        strategy.setRestControllerStyle(true);
        strategy.setEntityLombokModel(true);
        strategy.setTableFillList(tableFills);
        mpg.setStrategy(strategy);
        // 包配置
        PackageConfig pc = new PackageConfig();
        pc.setParent(parent);
        pc.setModuleName(moduleName);
        mpg.setPackageInfo(pc);
        // 注入自定义配置，可以在 VM 中使用 cfg.abc 【可无】
        InjectionConfig cfg = new InjectionConfig() {
            @Override
            public void initMap() {
                Map<String, Object> map = new HashMap<>();
                map.put("success", this.getConfig().getGlobalConfig().getAuthor() + "\t You are success");
                this.setMap(map);
            }
        };
        // 自定义 xxList.jsp 生成
        List<FileOutConfig> focList = new ArrayList<>();

        // 调整 xml 生成目录演示
        focList.add(new FileOutConfig(mapperTemplatePath) {
            @Override
            public String outputFile(TableInfo tableInfo) {
                return mapperDir + tableInfo.getEntityName() + "Mapper.xml";
            }
        });
        focList.add(new FileOutConfig(dtoTemplatePath) {
            @Override
            public String outputFile(TableInfo tableInfo) {
                return dtoDir + tableInfo.getEntityName() + "DTO.java";
            }
        });
        focList.add(new FileOutConfig(voTemplatePath) {
            @Override
            public String outputFile(TableInfo tableInfo) {
                return voDir + tableInfo.getEntityName() + "VO.java";
            }
        });
        cfg.setFileOutConfigList(focList);
        mpg.setCfg(cfg);
        // 关闭默认 xml 生成，调整生成 至 根目录
        TemplateConfig tc = new TemplateConfig();
        tc.setXml(null);
        mpg.setTemplate(tc);
        // 执行生成
        mpg.execute();
        // 打印注入设置【可无】
        System.out.println(mpg.getCfg().getMap().get("success"));
    }

}