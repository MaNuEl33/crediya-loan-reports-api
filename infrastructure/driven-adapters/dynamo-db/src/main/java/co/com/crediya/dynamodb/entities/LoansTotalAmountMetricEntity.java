package co.com.crediya.dynamodb.entities;

import lombok.Setter;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbAttribute;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey;

import java.math.BigDecimal;

@Setter
@DynamoDbBean
public class LoansTotalAmountMetricEntity {
    private String code;
    private BigDecimal value;
    private String updatedAt;

    @DynamoDbPartitionKey
    @DynamoDbAttribute("metric_code")
    public String getCode() {
        return code;
    }

    @DynamoDbAttribute("value")
    public BigDecimal getValue() {
        return value;
    }

    @DynamoDbAttribute("updated_at")
    public String getUpdatedAt() {
        return updatedAt;
    }
}
