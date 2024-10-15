package com.vsouza.editora.config.security;

import com.vsouza.editora.controllers.exceptions.ResourceNotFoundException;
import com.vsouza.editora.entities.User;
import com.vsouza.editora.repositories.UserRepository;
import jakarta.annotation.Nonnull;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;

@Component
@AllArgsConstructor
@Slf4j
public class TokenFilter extends OncePerRequestFilter {

    private final TokenBuilder tokenBuilder;
    private final UserDetailsService userDetailsService;
    private final UserRepository userRepository;

    @Override
    protected void doFilterInternal(@Nonnull HttpServletRequest request,@Nonnull HttpServletResponse response, @Nonnull FilterChain filterChain) throws ServletException, IOException {
        log.info("TokenFilter");
        try {

            if (!isEndpointAllowed(request)) {
                String token = getTokenFromRequest(request);

                if (token != null) {
                    String username = tokenBuilder.extractUsername(token);
                    User user = userRepository.findByUsername(username).orElseThrow(() -> new ResourceNotFoundException(username));
                    UserDetailsImpl userDetails = new UserDetailsImpl(user);

                    //objeto de autenticacao
                    if (tokenBuilder.isTokenValid(token, userDetails)) {
                        log.info("Token valid? ");
                        Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails.getUsername(), null, userDetails.getAuthorities());

                        SecurityContextHolder.getContext().setAuthentication(authentication);
                    }
                }
            }

        } catch (Exception e) {
            log.error("LOGIN - Error processing authentication: {}", e.getMessage());
            SecurityContextHolder.clearContext();  // Limpa o contexto de autenticação em caso de erro
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);  // Retorna erro 401 Unauthorized
            return;  // Encerra o processamento sem continuar para o próximo filtro

        }
        filterChain.doFilter(request, response);
    }


    private String getTokenFromRequest(HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        log.info("getTokenFromRequest token {}", token);
        if (token != null && token.startsWith("Bearer ")) {
            return token.substring(7);
        }
        return null;
    }

    private boolean isEndpointAllowed(HttpServletRequest request) {
        String uri = request.getRequestURI();
        log.info("TokenFilter - uri: {}", uri);
        boolean isAllowed = Arrays.stream(SecurityConfig.PUBLIC_ENDPOINTS)
                .anyMatch(uri::startsWith);
        log.info("TokenFilter - isAllowed: {}", isAllowed);
        return isAllowed;

    }
}
