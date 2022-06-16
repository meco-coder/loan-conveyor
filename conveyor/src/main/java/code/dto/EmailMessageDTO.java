package code.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@NoArgsConstructor
@Getter
@Setter
public class EmailMessageDTO {

    private String address;

    private Enum theme;

    private Long applicationId;
}
