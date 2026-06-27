package api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import api.dto.LoginRequest;
import api.dto.LoginResponse;
import api.security.JwtUtil;


@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private JwtUtil jwtUtil;

    private static final String USERNAME = "admin@techstore";
    private static final String PASSWORD = "admin123";
    
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        if (USERNAME.equals(request.getUsername()) &&
            PASSWORD.equals(request.getPassword())) {
            String token = jwtUtil.generarToken(request.getUsername());
            return ResponseEntity.ok(new LoginResponse(token, "Bearer", "3600"));
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body("Credenciales invalidas");
    }
}
