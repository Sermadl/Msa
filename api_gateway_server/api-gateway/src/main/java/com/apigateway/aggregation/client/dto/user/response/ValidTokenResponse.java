package com.apigateway.aggregation.client.dto.user.response;

import com.apigateway.aggregation.model.UserRole;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ValidTokenResponse {
    private Long id;
    private String username;
    private String email;
    private UserRole role;

    public boolean isValid() {
        return id != null && username != null && email != null && role != null;
    }
}
