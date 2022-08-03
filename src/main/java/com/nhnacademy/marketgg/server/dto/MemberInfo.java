package com.nhnacademy.marketgg.server.dto;

import com.nhnacademy.marketgg.server.annotation.Role;
import com.nhnacademy.marketgg.server.entity.Cart;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class MemberInfo {

    private final Long id;
    private final Cart cart;
    private final String memberGrade;
    private final Character gender;
    private final LocalDate birthDate;
    private final LocalDateTime ggpassUpdatedAt;
    private Set<String> roles;

    public void addRoles(List<String> roles) {
        this.roles = new HashSet<>(roles);
    }

    public boolean isAdmin() {
        return this.roles.contains(Role.ROLE_ADMIN.name());
    }

    public boolean isUser() {
        return this.roles.contains(Role.ROLE_USER.name()) || this.roles.contains(Role.ROLE_ADMIN.name());
    }

}
