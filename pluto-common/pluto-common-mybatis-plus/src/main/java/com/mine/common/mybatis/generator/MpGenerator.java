package com.mine.common.mybatis.generator;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.converts.MySqlTypeConvert;
import com.baomidou.mybatisplus.generator.config.po.TableFill;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.config.rules.DbColumnType;
import com.baomidou.mybatisplus.generator.config.rules.IColumnType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 代码生成器
 *
 * @author LiMing
 * @since com.baomidou.mybatisplus
 */
public class MpGenerator {

    /**
     * DataSource config
     * tableNames : {多个表用逗号分隔}
     */
    private static final String DATE_SOURCE_URL = "jdbc:mysql://192.168.1.64:3311/test-a?useUnicode=true&characterEncoding=utf8&autoReconnect=true&failOverReadOnly=false&serverTimezone=Asia/Shanghai&useOldAliasMetadataBehavior=true&useSSL=false";
    private static final String DATA_SOURCE_USER_NAME = "root";
    private static final String DATA_SOURCE_PASS_WORD = "MyNewPass4!";
    private static final String DATA_SOURCE_DRIVER_NAME = "com.mysql.cj.jdbc.Driver";
    private static final String[] TABLE_NAMES = {"sys_role"};

    /**
     * Project config
     */
    private static final String AUTHOR = "jax-li";
    private static final String PROJECT_DIR = "D://";
    private static final String PARENT = "com.mine";
    private static final String MODULE_NAME = "upmsx";


    /**
     * 下面是一些可选的配置
     */
    private static final String MAPPER_TEMPLATE_PATH = "/templates/mapper.xml.vm";
    private static final String DTO_TEMPLATE_PATH = "/templates/dto.java.vm";
    private static final String VO_TEMPLATE_PATH = "/templates/vo.java.vm";
    private static final String DELETE_FLAG = "is_deleted";
    private static final String VERSION = "version";
    private static final DbType DB_TYPE = DbType.MYSQL;
    private static final ITypeConvert I_TYPE_CONVERT = new MySqlTypeConvert();
    /**
     * 表名生成策略
     */
    private static final NamingStrategy TABLE_STRATEGY = NamingStrategy.underline_to_camel;

    private static final String FRAME_DIR = PARENT.replace(".", "/");
    private static final String ROOT_DIR = PROJECT_DIR + "/src/main/";
    private static final String DTO_DIR = ROOT_DIR + "java/" + FRAME_DIR + "/" + MODULE_NAME + "/dto/";
    private static final String VO_DIR = ROOT_DIR + "java/" + FRAME_DIR + "/" + MODULE_NAME + "/vo/";
    private static final String MAPPER_DIR = ROOT_DIR + "resources/mapper/";

    private static final List<TableFill> tableFills = new ArrayList<>();

    static {
        TableFill isDeleted = new TableFill("is_deleted", FieldFill.INSERT);
        TableFill createTime = new TableFill("create_time", FieldFill.INSERT);
        TableFill createUserId = new TableFill("create_user_id", FieldFill.INSERT);
        TableFill createUserName = new TableFill("create_user_name", FieldFill.INSERT);
        TableFill updateTime = new TableFill("update_time", FieldFill.UPDATE);
        TableFill updateUserId = new TableFill("update_user_id", FieldFill.UPDATE);
        TableFill updateUserName = new TableFill("update_user_name", FieldFill.UPDATE);
        tableFills.add(isDeleted);
        tableFills.add(createTime);
        tableFills.add(createUserId);
        tableFills.add(createUserName);
        tableFills.add(updateTime);
        tableFills.add(updateUserId);
        tableFills.add(updateUserName);
    }

    @SuppressWarnings("all")
    public static void main(String[] args) {

        AutoGenerator mpg = new AutoGenerator();
        // 全局配置
        GlobalConfig gc = new GlobalConfig();
        gc.setOutputDir(ROOT_DIR + "java");
        gc.setFileOverride(true);
        // 不需要ActiveRecord特性的请改为false
        gc.setActiveRecord(true);
        // XML 二级缓存
        gc.setEnableCache(false);
        // XML ResultMap
        gc.setBaseResultMap(true);
        // XML columList
        gc.setBaseColumnList(true);
        gc.setAuthor(AUTHOR);
        //
        gc.setSwagger2(true);
        mpg.setGlobalConfig(gc);
        // 数据源配置
        DataSourceConfig dsc = new DataSourceConfig();
        dsc.setDbType(DB_TYPE);
        dsc.setTypeConvert(I_TYPE_CONVERT);
        dsc.setDriverName(DATA_SOURCE_DRIVER_NAME);
        dsc.setUsername(DATA_SOURCE_USER_NAME);
        dsc.setPassword(DATA_SOURCE_PASS_WORD);
        dsc.setUrl(DATE_SOURCE_URL);
        dsc.setTypeConvert(new MySqlTypeConvert() {
            @Override
            public IColumnType processTypeConvert(GlobalConfig config, String fieldType) {
                if (fieldType.toLowerCase().contains("tinyint")) {
                    return DbColumnType.BOOLEAN;
                }
                return super.processTypeConvert(config, fieldType);
            }
        });
        mpg.setDataSource(dsc);
        // 策略配置
        StrategyConfig strategy = new StrategyConfig();
        // strategy.setCapitalMode(true);// 全局大写命名 ORACLE 注意
//        strategy.setTablePrefix(Prefix);// 此处可以修改为您的表前缀
        // 表名生成策略
        strategy.setNaming(TABLE_STRATEGY);
        // 需要生成的表
        strategy.setInclude(TABLE_NAMES);
        strategy.setLogicDeleteFieldName(DELETE_FLAG);
        strategy.setVersionFieldName(VERSION);
        strategy.setRestControllerStyle(true);
        strategy.setEntityLombokModel(true);
        strategy.setRestControllerStyle(true);
        strategy.setControllerMappingHyphenStyle(true);
        strategy.setTableFillList(tableFills);
        mpg.setStrategy(strategy);
        // 包配置
        PackageConfig pc = new PackageConfig();
        pc.setParent(PARENT);
        pc.setModuleName(MODULE_NAME);
        mpg.setPackageInfo(pc);
        // 注入自定义配置，可以在 VM 中使用 cfg.abc 【可无】
        InjectionConfig cfg = new InjectionConfig() {
            @Override
            public void initMap() {
                Map<String, Object> map = new HashMap<>(16);
                map.put("success", this.getConfig().getGlobalConfig().getAuthor() + "\t You are success");
                this.setMap(map);
            }
        };
        // 自定义 xxList.jsp 生成
        List<FileOutConfig> focList = new ArrayList<>();

        // 调整 xml 生成目录演示
        focList.add(new FileOutConfig(MAPPER_TEMPLATE_PATH) {
            @Override
            public String outputFile(TableInfo tableInfo) {
                return MAPPER_DIR + tableInfo.getEntityName() + "Mapper.xml";
            }
        });
        focList.add(new FileOutConfig(DTO_TEMPLATE_PATH) {
            @Override
            public String outputFile(TableInfo tableInfo) {
                return DTO_DIR + tableInfo.getEntityName() + "DTO.java";
            }
        });
        focList.add(new FileOutConfig(VO_TEMPLATE_PATH) {
            @Override
            public String outputFile(TableInfo tableInfo) {
                return VO_DIR + tableInfo.getEntityName() + "VO.java";
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