package gr.app.springbackend.service;

import java.util.Date;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import gr.app.springbackend.bean.AuthenticationBean;
import gr.app.springbackend.db.entity.UserEntity;
import gr.app.springbackend.db.repo.UserRepo;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
public class UserServiceJWT implements UserService {

    public UserServiceJWT(){
    }

    @Autowired
    UserRepo userRepo;
    
    @Value("${app.jwt.secret}")
    private String passphrase;

    @Value("${app.jwt.ttl}")
    private String ttl;

    @Override
    public AuthenticationBean authenticateUser(String username, String password) throws Exception {

        UserEntity userEntity = userRepo.getUser(username, password);
        String accessToken = createToken(userEntity);
        return new AuthenticationBean(accessToken);
    }

    public boolean isTokenValid(String token) throws Exception{

        if(token==null){
            return false;
        }

        Jws<Claims> claims = Jwts.parser().setSigningKey(this.passphrase).parseClaimsJws(token);
        
        if (claims.getBody().getExpiration().before(new Date())) {
            return false;
        }

        return true;
    }

    public String getUsernameFromJWT(String token){
        Jws<Claims> claims = Jwts.parser().setSigningKey(this.passphrase).parseClaimsJws(token);
        return (String) claims.getBody().get("username");
    }
    
    private String createToken(UserEntity userEntity) throws Exception{
        
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
        
        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);
        
        JwtBuilder builder = Jwts
            .builder()
            .setId(UUID.randomUUID().toString())
            .setIssuedAt(now)
            .claim("username", userEntity.getUsername())
            .claim("name", userEntity.getName())
            .claim("surname", userEntity.getSurname())
            .signWith(signatureAlgorithm, this.passphrase);

        if (Long.parseLong(ttl) > 0) {
            long expMillis = nowMillis + Long.parseLong(ttl);
            Date exp = new Date(expMillis);
            builder.setExpiration(exp);
        }

        return builder.compact();
    }

    public String resolveToken(String header) {
        if (header != null && header.startsWith("Bearer ")) {
            return header.substring(7, header.length());
        }
        return null;
    }

    public UserEntity getUserFromUsername(String username){
        return this.userRepo.getUserByUsername(username);
    }

}