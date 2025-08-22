// TareaBPMRequest.java
package com.ciudadania360.subsistematramitacion.application.dto.tareabpm;

import lombok.*;
import java.time.Instant;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TareaBPMRequest {
    private UUID procesoBpmId;
    private String engineTaskId;
    private String nombre;
    private String estado;
    private String assignee;
    private String candidateGroup;
    private Instant dueDate;
    private Integer priority;
    private String variables;
    private Instant created;
    private Instant completed;
}
