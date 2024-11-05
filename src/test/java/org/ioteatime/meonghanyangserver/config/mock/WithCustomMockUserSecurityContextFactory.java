package org.ioteatime.meonghanyangserver.config.mock;

import org.ioteatime.meonghanyangserver.user.domain.UserEntity;
import org.ioteatime.meonghanyangserver.user.dto.CustomUserDetail;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithSecurityContextFactory;

public class WithCustomMockUserSecurityContextFactory
        implements WithSecurityContextFactory<WithCustomMockUser> {
    @Override
    public SecurityContext createSecurityContext(WithCustomMockUser annotation) {
        String userId = annotation.userId();

        UserEntity user = UserEntity.builder().id(Long.parseLong(userId)).build();
        CustomUserDetail customUserDetail = new CustomUserDetail(user);

        UsernamePasswordAuthenticationToken token =
                new UsernamePasswordAuthenticationToken(
                        customUserDetail, null, customUserDetail.getAuthorities());
        SecurityContext context = SecurityContextHolder.getContext();
        context.setAuthentication(token);
        return context;
    }
}
