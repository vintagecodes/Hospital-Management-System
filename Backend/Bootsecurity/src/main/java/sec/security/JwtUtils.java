package sec.security;

import java.io.UnsupportedEncodingException;
import java.security.Key;
import java.util.Date;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import sec.security.UserDetailsImpl;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.InvalidKeyException;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
@Component
@Slf4j
public class JwtUtils {
	private static final Logger logger = LoggerFactory.getLogger(JwtUtils.class);
	private static String SECRET = "JG5aH2Fb7cZlI9XpDtEjOvKu4nQ6rWx0YsRgTm1oUyLkC3qVwB8PzMiNfSdAhVefff357638792F423F4428472B4B6250655368566D597133743677397A2443264629";
//	 private static final Key SECRET_KEY = Keys.secretKeyFor(SignatureAlgorithm.HS512);
//	@Value("${jwtExpirationMs}")
//	private int jwtExpirationMs;
	@SuppressWarnings("deprecation")
	public String generateJwtToken(Authentication authentication) throws InvalidKeyException, UnsupportedEncodingException {
		UserDetailsImpl userPrincipal = (UserDetailsImpl) authentication.getPrincipal();
		 long expirationTimeMillis = System.currentTimeMillis() + 3600 * 1000;
		String Token = Jwts.builder()
				.setSubject((userPrincipal.getUsername()))
				.setIssuedAt(new Date())
				.setExpiration(new Date(expirationTimeMillis))
				.signWith(getSignKey(),SignatureAlgorithm.HS512)
				.compact();
		boolean b = validateJwtToken(Token);
		logger.info("Validated Token for JWt:",b);
		return Token; 
	}
	@SuppressWarnings("deprecation")
	public String getUserNameFromJwtToken(String token) throws io.jsonwebtoken.security.SignatureException, ExpiredJwtException, UnsupportedJwtException, MalformedJwtException, IllegalArgumentException, UnsupportedEncodingException {
		return Jwts.parser().setSigningKey(getSignKey()).parseClaimsJws(token).getBody().getSubject();
	}
	

	@SuppressWarnings("deprecation")
	public boolean validateJwtToken(String authToken) throws UnsupportedEncodingException {
		try {
			Jws<Claims> claimsJws = Jwts.parserBuilder()
                    .setSigningKey(getSignKey()).build()
                    .parseClaimsJws(authToken);
			if(claimsJws!=null)
			{
				log.info("Token Validated:"+claimsJws.getBody());
				return true;
			}
//			Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(authToken);
//			return true;
		} catch (SignatureException e) {
			logger.error("Invalid JWT signature: {}", e.getMessage());
		} catch (MalformedJwtException e) {
			logger.error("Invalid JWT token: {}", e.getMessage());
		} catch (ExpiredJwtException e) {
			logger.error("JWT token is expired: {}", e.getMessage());
		} catch (UnsupportedJwtException e) {
			logger.error("JWT token is unsupported: {}", e.getMessage());
		} catch (IllegalArgumentException e) {
			logger.error("JWT claims string is empty: {}", e.getMessage());
		}
		return false;
	}
	
	private Key getSignKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}