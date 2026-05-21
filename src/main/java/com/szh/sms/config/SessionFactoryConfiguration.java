package com.szh.sms.config;

import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.lang.NonNull;

import javax.sql.DataSource;
import java.io.IOException;
import java.util.Objects;

/**
 * MyBatis session factory configuration.
 */
@Configuration
public class SessionFactoryConfiguration {
  private final String mybatisConfigFilePath;
  private final String mapperPath;
  private final String entityPackage;
  private final DataSource dataSource;

  public SessionFactoryConfiguration(
      @Value("${mybatis_config_file}") @NonNull String mybatisConfigFilePath,
      @Value("${mapper_path}") @NonNull String mapperPath,
      @Value("${entity_package}") @NonNull String entityPackage,
      @Qualifier("dataSource") @NonNull DataSource dataSource) {
    this.mybatisConfigFilePath = Objects.requireNonNull(mybatisConfigFilePath, "mybatisConfigFilePath");
    this.mapperPath = Objects.requireNonNull(mapperPath, "mapperPath");
    this.entityPackage = Objects.requireNonNull(entityPackage, "entityPackage");
    this.dataSource = Objects.requireNonNull(dataSource, "dataSource");
  }

  @Bean(name = "sqlSessionFactory")
  public SqlSessionFactoryBean createSqlSessionFactoryBean() throws IOException {
    SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
    sqlSessionFactoryBean.setConfigLocation(new ClassPathResource(mybatisConfigFilePath));

    PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
    String packageSearchPath = PathMatchingResourcePatternResolver.CLASSPATH_ALL_URL_PREFIX + mapperPath;
    sqlSessionFactoryBean.setMapperLocations(resolver.getResources(packageSearchPath));
    sqlSessionFactoryBean.setDataSource(dataSource);
    sqlSessionFactoryBean.setTypeAliasesPackage(entityPackage);
    return sqlSessionFactoryBean;
  }
}
