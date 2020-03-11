package org.chanwr.controller;

import org.apache.ibatis.annotations.Delete;
import org.chanwr.api.DecisionApi;
import org.flowable.dmn.api.DmnDeployment;
import org.flowable.dmn.engine.DmnEngine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.Map;

@RestController
@RequestMapping("/v1/decisions")
public class DecisionApiV1Controller implements DecisionApi {

    private static final Logger LOGGER = LoggerFactory.getLogger(DecisionApiV1Controller.class);

    @Autowired
    private DmnEngine dmnEngine;

    @Override
    @PostMapping(value = "", produces = { "application/json" })
    public ResponseEntity<DmnDeployment> loadDecisionModel() {
        try {
            DmnDeployment dmnDeployment = dmnEngine.getDmnRepositoryService().createDeployment()
                    .name("Deployment of Dinner Decisions")
                    .addInputStream("dinnerDecisions.dmn", new ClassPathResource("dinnerDecisions.dmn").getInputStream())
                    .disableSchemaValidation()
                    .deploy();
            // return dmnEngine.getDmnRepositoryService().get
            return new ResponseEntity<>(dmnDeployment, HttpStatus.OK);
        } catch (IOException e) {
            LOGGER.error("Failed to load Decision Model due to IOException", e);
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    @GetMapping(value = "/calculate", produces = { "application/json" })
    public ResponseEntity<Map<String, Object>> calculateDecision() {
        Map<String, Object> executionResult = dmnEngine.getDmnRuleService()
                .createExecuteDecisionBuilder()
                .decisionKey("dish")
                .variable("season", "Spring")
                .variable("guestCount", 10)
                .executeWithSingleResult();
        return new ResponseEntity<>(executionResult, HttpStatus.OK);
    }

    @Override
    @DeleteMapping(value = "/{deploymentId}", produces = { "application/json" })
    public ResponseEntity<Map<String, Object>> deleteDecisionModel(@PathVariable(name = "deploymentId") String deploymentId) {
        dmnEngine.getDmnRepositoryService().deleteDeployment(deploymentId);
        return new ResponseEntity<>(null, HttpStatus.OK);
    }

}
