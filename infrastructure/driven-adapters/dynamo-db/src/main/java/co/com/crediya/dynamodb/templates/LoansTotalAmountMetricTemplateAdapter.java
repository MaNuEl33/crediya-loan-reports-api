package co.com.crediya.dynamodb.templates;

import co.com.crediya.dynamodb.entities.LoansTotalAmountMetricEntity;
import co.com.crediya.dynamodb.helper.TemplateAdapterOperations;
import co.com.crediya.model.loanstotalamountmetric.LoansTotalAmountMetric;
import org.springframework.stereotype.Repository;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedAsyncClient;

import java.time.Instant;

@Repository
public class LoansTotalAmountMetricTemplateAdapter extends TemplateAdapterOperations<LoansTotalAmountMetric, String, LoansTotalAmountMetricEntity> {

    protected LoansTotalAmountMetricTemplateAdapter(DynamoDbEnhancedAsyncClient connectionFactory) {
        super(connectionFactory, "accepted-loans-report");
    }

    @Override
    protected LoansTotalAmountMetricEntity toEntity(LoansTotalAmountMetric model) {
        final var entity = new LoansTotalAmountMetricEntity();

        entity.setCode(LoansTotalAmountMetric.CODE);
        entity.setValue(model.getValue());
        entity.setUpdatedAt(model.getUpdatedAt().toString());

        return entity;
    }

    @Override
    protected LoansTotalAmountMetric toModel(LoansTotalAmountMetricEntity data) {
        return LoansTotalAmountMetric.builder()
                .value(data.getValue())
                .updatedAt(Instant.parse(data.getUpdatedAt()))
                .build();
    }
}
