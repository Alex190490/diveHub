package alexDavid.Auth;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.ArrayList;

@NoArgsConstructor
@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter{

    @Value("${jwt.secret-key}")
    private String jwtSecretKey;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authorizationHeader = request.getHeader("Authorization");
        if(authorizationHeader != null && authorizationHeader.contains("Bearer ")){
            String token = authorizationHeader.replaceAll("Bearer ", "");

            String[] tokenParts = token.split("\\.");
            if (tokenParts.length == 3) {
                DecodedJWT decodedJWT;
                try {
                    Algorithm algorithm = Algorithm.HMAC256(jwtSecretKey);
                    JWTVerifier verifier = JWT.require(algorithm)
                            .withIssuer("divehub")
                            .build();
                    decodedJWT = verifier.verify(token);

                    Authentication authentication = new UsernamePasswordAuthenticationToken(
                            decodedJWT.getSubject(),
                            "",
                            new ArrayList<>()
                    );
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                } catch (JWTVerificationException exception) {
                    System.out.println(exception.getMessage());
                }
            }
        }
        doFilter(request, response, filterChain);
    }
}
