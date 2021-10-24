package com.ioasys.api.jwt;

import com.ioasys.api.jwt._config.JwtTokenUtil;
import com.ioasys.api.jwt._config.JwtUserDetailsService;
import com.ioasys.api.jwt._dtos.JwtResponse;
import com.ioasys.api.jwt._forms.JwtRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping(value = "/auth")
public class JwtController {

    @Autowired
    private JwtUserDetailsService jwtUserDetailsService;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @PostMapping
    public ResponseEntity<?> auth(@RequestBody JwtRequest authenticationRequest)
            throws Exception {

        jwtUserDetailsService.authenticate(
                authenticationRequest.getEmail().trim(),
                authenticationRequest.getPassword().trim());

        final UserDetails userDetails = jwtUserDetailsService
                .loadUserByUsername(authenticationRequest.getEmail().trim());

        final String token = jwtTokenUtil.generateToken(userDetails);
        return ResponseEntity.ok().body(new JwtResponse(token));
    }
}
