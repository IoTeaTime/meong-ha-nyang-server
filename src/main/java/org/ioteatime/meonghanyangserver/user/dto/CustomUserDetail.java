package org.ioteatime.meonghanyangserver.user.dto;

import java.util.Collection;
import java.util.Collections;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.ioteatime.meonghanyangserver.group.domain.enums.GroupUserRole;
import org.ioteatime.meonghanyangserver.user.domain.UserEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Data
@RequiredArgsConstructor
public class CustomUserDetail implements UserDetails {
    private UserEntity userEntity;
    private GroupUserRole groupUserRole;

    public CustomUserDetail(UserEntity userEntity, GroupUserRole groupUserRole) {
        this.userEntity = userEntity;
        this.groupUserRole = groupUserRole;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singletonList(new SimpleGrantedAuthority(groupUserRole.name()));
    }

    @Override
    public String getPassword() {
        return userEntity.getPassword();
    }

    @Override
    public String getUsername() {
        return userEntity.getEmail();
    }

    public Long getId() {
        return userEntity.getId();
    }
}
