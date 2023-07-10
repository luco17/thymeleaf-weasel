package com.tamingthymeleaf.application.team;

import com.tamingthymeleaf.application.user.UserId;

public record TeamPlayerParameters(UserId playerId, PlayerPosition position) {
    
}
