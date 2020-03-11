package org.chanwr.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.flowable.dmn.api.DmnDeployment;
import org.springframework.http.ResponseEntity;

import java.util.Map;

@Tag(name = "decision", description = "Decision API")
public interface DecisionApi {

    @Operation(summary = "Load decision model", description = "Load decision model")
    ResponseEntity<DmnDeployment> loadDecisionModel();

    @Operation(summary = "Calculate something", description = "Calculate something")
    ResponseEntity<Map<String, Object>> calculateDecision();

    @Operation(summary = "Delete decision model", description = "Delete decision model")
    ResponseEntity<Map<String, Object>> deleteDecisionModel(String deploymentId);

}
