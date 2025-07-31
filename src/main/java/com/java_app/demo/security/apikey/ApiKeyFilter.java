package com.java_app.demo.security.apikey;

import com.java_app.demo.apikey.KeysRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
public class ApiKeyFilter extends OncePerRequestFilter {

  KeysRepository keysRepository;

  public ApiKeyFilter(KeysRepository keysRepository) {
    this.keysRepository = keysRepository;
  }

  @Override
  protected void doFilterInternal(
      HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
      throws ServletException, IOException {
    if (keysRepository.existsByValue(getApiKeyFromRequest(request))) {
      String apiKey = getApiKeyFromRequest(request);
      List<GrantedAuthority> authorities = List.of(new SimpleGrantedAuthority("ROLE_API"));
      AnonymousAuthenticationToken authentication =
          new AnonymousAuthenticationToken(apiKey, "anonymousApiUser", authorities);

      authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

      SecurityContextHolder.getContext().setAuthentication(authentication);
    }
    filterChain.doFilter(request, response);
  }

  public String getApiKeyFromRequest(HttpServletRequest request) {
    String key = request.getHeader("Authorization");
    if (key != null && key.startsWith("Bearer ")) {
      return key.substring(7);
    }

    return null;
  }
}
