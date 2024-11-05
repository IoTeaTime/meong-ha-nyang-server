package org.ioteatime.meonghanyangserver.config.mock;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import org.springframework.security.test.context.support.WithSecurityContext;

@Retention(RetentionPolicy.RUNTIME)
@WithSecurityContext(factory = WithCustomMockUserSecurityContextFactory.class)
public @interface WithCustomMockUser {
    String userId() default "1";

    String email() default "test@gmail.com";

    String username() default "test";

    String password() default "testpassword";
}
