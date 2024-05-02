package jovelAsirot.YourHomeDB.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import jovelAsirot.YourHomeDB.entities.User;
import jovelAsirot.YourHomeDB.exceptions.UnauthorizedException;

import java.util.Date;

@Component
public class JwtTools {

    @Value("${jwt.secret}")
    private String secret;

    public String createToken(User user) {
        Date currenDate = new Date(System.currentTimeMillis());

        return Jwts.builder()
                .issuedAt(currenDate)
                .expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24 * 14))
                .subject(String.valueOf(user.getId()))
                .signWith(Keys.hmacShaKeyFor(secret.getBytes())).compact();
    }

    public void verifyToken(String token) {
        try {
            Jwts.parser().verifyWith(Keys.hmacShaKeyFor(secret.getBytes())).build().parse(token);
        } catch (Exception ex) {
            throw new UnauthorizedException("Error with token ᕙ(‾̀◡‾́)ᕗ");
        }
    }

    public String extractIdFromToken(String token) {
        return Jwts.parser().verifyWith(Keys.hmacShaKeyFor(secret.getBytes())).build().parseSignedClaims(token).getPayload().getSubject();
    }

}
