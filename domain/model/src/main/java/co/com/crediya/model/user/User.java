package co.com.crediya.model.user;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@EqualsAndHashCode
@Builder(toBuilder = true)
public class User {
    private Long id;
    private String email;
}
