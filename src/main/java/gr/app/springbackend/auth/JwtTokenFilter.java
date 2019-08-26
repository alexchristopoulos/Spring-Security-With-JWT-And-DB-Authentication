package gr.app.springbackend.auth;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

import gr.app.springbackend.db.entity.UserEntity;
import gr.app.springbackend.service.UserServiceJWT;

public class JwtTokenFilter extends GenericFilterBean{

    
    private UserServiceJWT userServiceJWT;

    public JwtTokenFilter(UserServiceJWT userServiceJWT){
        this.userServiceJWT = userServiceJWT;
    }
    
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain)
			throws IOException, ServletException {
        
        try{
            HttpServletRequest httpServletRequest = (HttpServletRequest) request;
            String token = userServiceJWT.resolveToken(httpServletRequest.getHeader("Authorization"));

            if(userServiceJWT.isTokenValid(token)){

                UserEntity userEntity = this.userServiceJWT.getUserFromUsername(this.userServiceJWT.getUsernameFromJWT(token));
                Authentication auth = new UsernamePasswordAuthenticationToken(userEntity.getUsername(), token, userEntity.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(auth);
            }

        }catch(Exception e){

            SecurityContextHolder.clearContext();
        }

        filterChain.doFilter(request, response);
	}
    
}