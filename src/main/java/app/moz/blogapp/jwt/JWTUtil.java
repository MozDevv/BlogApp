package app.moz.blogapp.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.Map;
import java.util.function.Function;

@Service
public class JWTUtil {

    private static final String SECRET_KEY =
            "a1b2c3d4e5f6a1b2c3d4e5f6a1b2c3d4e5f6a1b2c3d4e5f6a1b2c3d4e5f6";

    public String issueToken(String subject){
        return issueToken(subject, Map.of());
    }
    public String issueToken(String subject, String ...scopes) {
        return issueToken(subject, Map.of("scopes", scopes));
    }
    public String issueToken(String subject, Map<String, Object> claims){

      String token =   Jwts.builder()
              .setClaims(claims)
              .setSubject(subject)
              .setIssuer("app.moz.blogApp")
              .setIssuedAt(Date.from(Instant.now()))
              .setExpiration(
                      Date.from(Instant.now().plus(15,ChronoUnit.DAYS))
              )
              .signWith(getSigningKey(), SignatureAlgorithm.HS256)
              .compact();

      return token;
    }

    //Todo ******************************************************************************

    public String extractUsername ( String token) {

        return extractClaim(token, Claims::getSubject);

    }



    //extract single claim
    public <T> T extractClaim (String token , Function<Claims, T> claimsResolver) {

        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    public Claims extractAllClaims (String token ) {

        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();


    }

    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = extractUsername(token);

        return (username.equals(userDetails.getUsername())) && isTokenExpired(token) ;

    }

    private boolean isTokenExpired(String token) {

        return  extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {

        return extractClaim(token,  Claims::getExpiration);
    }


    private Key getSigningKey () {
        return Keys.hmacShaKeyFor(SECRET_KEY.getBytes());
    }
}
