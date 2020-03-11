package org.chanwr.config;

import org.flowable.dmn.engine.DmnEngine;
import org.flowable.dmn.engine.DmnEngineConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FlowableConfig {

    @Bean
    public DmnEngineConfiguration dmnEngineConfiguration() {
        return DmnEngineConfiguration.createStandaloneInMemDmnEngineConfiguration()
                .setDatabaseSchemaUpdate(DmnEngineConfiguration.DB_SCHEMA_UPDATE_TRUE);
    }

    @Bean
    public DmnEngine dmnEngine() {
        return dmnEngineConfiguration().buildDmnEngine();
    }

}
