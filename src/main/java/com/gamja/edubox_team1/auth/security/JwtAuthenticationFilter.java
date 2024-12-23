package com.gamja.edubox_team1.auth.security;

import com.gamja.edubox_team1.auth.dto.entity.AuthUser;
import com.gamja.edubox_team1.auth.exception.EmptyAccessTokenException;
import com.gamja.edubox_team1.auth.exception.ExpiredAccessTokenException;
import com.gamja.edubox_team1.auth.repository.AuthRepository;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtTokenProvider jwtTokenProvider;
    private final AuthRepository authRepository;


    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        String requestURI = request.getRequestURI();
        if (requestURI.startsWith("/v1/auth/login") ||
            requestURI.startsWith("/v1/auth/reissue") ||
            requestURI.startsWith("/v1/auth/register")) {
            filterChain.doFilter(request, response);
            return;
        }

        String accessToken = resolveToken(request);
        if (accessToken == null) {
            throw new EmptyAccessTokenException();
        }

        if (!jwtTokenProvider.validateToken(accessToken)) {
            throw new ExpiredAccessTokenException();
        }

        long userNo = jwtTokenProvider.getId(accessToken);
        Optional<AuthUser> userDetails = authRepository.findById(userNo);

        if (userDetails.isEmpty()) {
            throw new UsernameNotFoundException("User not found");
        }

        AuthUser authUser = userDetails.get();

        var auth = new UsernamePasswordAuthenticationToken(authUser,
                null, List.of(new SimpleGrantedAuthority("ROLE_" + authUser.getRole())));
        SecurityContextHolder.getContext().setAuthentication(auth);

        filterChain.doFilter(request, response);
    }

    private String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }
}
