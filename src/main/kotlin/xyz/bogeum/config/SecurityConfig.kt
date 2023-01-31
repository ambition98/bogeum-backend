package xyz.bogeum.config

import org.springframework.context.annotation.Bean
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.UrlBasedCorsConfigurationSource
import xyz.bogeum.auth.JwtAuthEntryPoint
import xyz.bogeum.enum.UserRole
import xyz.bogeum.filter.AccessControlFilter
import xyz.bogeum.filter.JwtAuthenticationFilter

@EnableWebSecurity
class SecurityConfig(
    val jwtAuthenticationFilter: JwtAuthenticationFilter,
    val accessControlFilter: AccessControlFilter
) {

    private val allowedHeaders = listOf("Content-Type", "Access-Control-Allow-Origin", "Access-Control-Allow-Credentials")
    private val allowedOrigins = listOf("http://localhost:8080", "https://bogeum.xyz")
    private val allowedMethods = listOf("GET", "POST", "PUT", "DELETE", "OPTION")

        @Bean
    fun filterChain(http: HttpSecurity): SecurityFilterChain {
        return http
            .formLogin().disable()
            .logout().disable()

            .headers().frameOptions().sameOrigin()
            .and()

            .cors().configurationSource(corsConfigSource())
            .and()

            .csrf().disable()

            .authorizeRequests()
            .antMatchers("/user/**").hasRole(UserRole.USER.key)
            .anyRequest().permitAll()
            .and()

            .exceptionHandling().authenticationEntryPoint(JwtAuthEntryPoint())
            .and()

            .addFilterBefore(accessControlFilter, UsernamePasswordAuthenticationFilter::class.java)
            .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter::class.java)

            .build()
    }

    @Bean
    fun corsConfigSource() = UrlBasedCorsConfigurationSource().also { source ->
        source.registerCorsConfiguration(
            "/**",
            CorsConfiguration().also {
                it.allowedHeaders = allowedHeaders
                it.allowedOrigins = allowedOrigins
//                it.allowedOriginPatterns = listOf("*")
                it.allowedMethods = allowedMethods
                it.allowCredentials = true
                it.maxAge = 3600L
            }
        )
    }
}