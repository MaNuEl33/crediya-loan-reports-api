package co.com.crediya.dynamodb.templates;

import co.com.crediya.dynamodb.entities.LoanCountMetricEntity;
import co.com.crediya.dynamodb.helper.TemplateAdapterOperations;
import co.com.crediya.model.loancountmetric.LoanCountMetric;
import org.springframework.stereotype.Repository;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedAsyncClient;

import java.time.Instant;

@Repository
public class LoanCountMetricTemplateAdapter extends TemplateAdapterOperations<LoanCountMetric, String, LoanCountMetricEntity> {

    public LoanCountMetricTemplateAdapter(DynamoDbEnhancedAsyncClient connectionFactory) {
        super(connectionFactory, "accepted-loans-report");
    }

    @Override
    protected LoanCountMetricEntity toEntity(LoanCountMetric model) {
        final var entity = new LoanCountMetricEntity();

        entity.setCode(LoanCountMetric.CODE);
        entity.setValue(model.getValue());
        entity.setUpdatedAt(model.getUpdatedAt().toString());

        return entity;
    }

    @Override
    protected LoanCountMetric toModel(LoanCountMetricEntity data) {
        return LoanCountMetric.builder()
                .value(data.getValue())
                .updatedAt(Instant.parse(data.getUpdatedAt()))
                .build();
    }
}
