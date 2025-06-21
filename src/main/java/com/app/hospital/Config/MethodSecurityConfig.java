package com.app.hospital.Config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;

@Configuration
@EnableMethodSecurity(prePostEnabled = true)  // ✅ Enables @PreAuthorize & @PostAuthorize
public class MethodSecurityConfig {
}
