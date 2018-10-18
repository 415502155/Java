package tk.mybatis.springboot.druid;

import com.alibaba.druid.pool.DruidDataSource;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;
import java.sql.SQLException;

/**
 * 配置单一数据源.
 */
///***
@Configuration
@EnableConfigurationProperties(DruidProperties.class)
@ConditionalOnClass(DruidDataSource.class)
@ConditionalOnProperty(prefix = "vani", name = "url")
@AutoConfigureBefore(DataSourceAutoConfiguration.class)
//扫描 Mapper 接口并容器管理
@MapperScan(basePackages = DruidAutoConfiguration.PACKAGE)
public class DruidAutoConfiguration {

    @Autowired
    private DruidProperties properties;
    
    // 精确到 master 目录，以便跟其他数据源隔离
    static final String PACKAGE = "tk.mybatis.springboot.dao.vani";
    
    @Bean
    @Primary
    public DataSource dataSource() {
        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setUrl(properties.getUrl());
        dataSource.setUsername(properties.getUsername());
        dataSource.setPassword(properties.getPassword());
        if (properties.getInitialSize() > 0) {
            dataSource.setInitialSize(properties.getInitialSize());
        }
        if (properties.getMinIdle() > 0) {
            dataSource.setMinIdle(properties.getMinIdle());
        }
        if (properties.getMaxActive() > 0) {
            dataSource.setMaxActive(properties.getMaxActive());
        }
        dataSource.setTestOnBorrow(properties.isTestOnBorrow());
        try {
            dataSource.init();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return dataSource;
    }
}
//***/