package org.ioteatime.meonghanyangserver.member.dto;

import java.util.Collection;
import java.util.Collections;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.ioteatime.meonghanyangserver.cctv.domain.CctvEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Data
@RequiredArgsConstructor
public class CustomCctvDetail implements UserDetails {
    private CctvEntity cctvEntity;

    public CustomCctvDetail(CctvEntity cctvEntity) {
        this.cctvEntity = cctvEntity;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singletonList(new SimpleGrantedAuthority("ROLE_CCTV"));
    }

    @Override
    public String getPassword() {
        return cctvEntity.getCctvNickname();
    }

    @Override
    public String getUsername() {
        return cctvEntity.getCctvNickname();
    }

    public Long getId() {
        return cctvEntity.getId();
    }
}
