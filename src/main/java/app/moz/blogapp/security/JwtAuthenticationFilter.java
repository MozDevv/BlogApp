package app.moz.blogapp.security;

import app.moz.blogapp.jwt.JWTUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.validation.constraints.NotNull;
import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JWTUtil jwtUtil;

    private final UserDetailsService userDetailsService;



    @Override
    protected void doFilterInternal(
          @NotNull HttpServletRequest request,
         @NotNull   HttpServletResponse response,
         @NotNull   FilterChain filterChain
    ) throws ServletException, IOException {

        final String authHeader = request.getHeader("Authorization");

        final String  jwt;
        final String userEmail;

        if (authHeader == null || !authHeader.startsWith("Bearer ")){
            filterChain.doFilter(request, response);
            return;
        }

        jwt = authHeader.substring(7);

      userEmail = jwtUtil.extractUsername(jwt);

      //Check if the user is authenticated
      if (userEmail != null && SecurityContextHolder.getContext().getAuthentication() == null) {

          UserDetails userDetails = this.userDetailsService.loadUserByUsername(userEmail);

          if (jwtUtil.isTokenValid(jwt, userDetails)){

              //creates an authentication object if token is valid
              UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                      userDetails,
                      null,
                      userDetails.getAuthorities()
              );

              // it captures details about the current HTTP request, such as the IP address, session ID, and other request-specific information.
              authenticationToken.setDetails(
                      new WebAuthenticationDetailsSource().buildDetails(request)
              );

              //sets the authenticated authenticationToken in the security context.
              //the security context holds information about the current user's authentication status.
              SecurityContextHolder.getContext().setAuthentication(authenticationToken);

          }
      }
      filterChain.doFilter(request,response);


    }
}
