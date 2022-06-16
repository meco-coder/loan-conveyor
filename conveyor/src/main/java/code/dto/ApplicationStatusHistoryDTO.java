package code.dto;


import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor
public class ApplicationStatusHistoryDTO {

    private Enum status;

    private LocalDateTime time;

    private Enum changeType;


}
