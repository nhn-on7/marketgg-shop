package com.nhnacademy.marketgg.server.dto.info;

import com.nhnacademy.marketgg.server.annotation.Role;
import com.nhnacademy.marketgg.server.entity.Cart;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class MemberInfo {

    private Long id;
    private Cart cart;
    private String memberGrade;
    private Character gender;
    private LocalDate birthDate;
    private Set<String> roles;

    public MemberInfo(Long id, Cart cart, String memberGrade, Character gender, LocalDate birthDate) {
        this.id = id;
        this.cart = cart;
        this.memberGrade = memberGrade;
        this.gender = gender;
        this.birthDate = birthDate;
        this.roles = new HashSet<>();
    }

    public void addRoles(List<String> roles) {
        this.roles = new HashSet<>(roles);
    }

    public boolean isAdmin() {
        return this.roles.contains(Role.ROLE_ADMIN.name());
    }

    public boolean isUser() {
        return this.roles.contains(Role.ROLE_USER.name()) || this.roles.contains(Role.ROLE_ADMIN.name());
    }

    public boolean isNull() {
        return Objects.isNull(id) || Objects.isNull(cart) || Objects.isNull(memberGrade);
    }

}
