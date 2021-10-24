package com.ioasys.api.jwt._config;

import com.ioasys.api.shared.exception.ApiException;
import com.ioasys.api.users.UserEntity;
import com.ioasys.api.users.UserRepository;
import com.ioasys.api.users._enums.UserStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class JwtUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        try {
            UserEntity userFounded = userRepository.findByEmail(email.trim())
                    .orElseThrow(()-> new ApiException("Usuário não encontrado com E-mail informado!"));

            if(userFounded.getUserStatus() == UserStatus.DISABLE)
                throw new ApiException("Usuário desativado. Por favor, faça o cadastro novamente!");
            List<GrantedAuthority> list = new ArrayList<>();

            list.add(new SimpleGrantedAuthority(userFounded.getUserType().name()));

            return new User(
                    userFounded.getEmail(),
                    userFounded.getPassword(),
                    list);
        } catch (ApiException e){
            throw e;
        }
    }

    public void authenticate(String username, String password) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (DisabledException e) {
            throw new Exception("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            e.printStackTrace();
            throw new ApiException("E-mail ou senha incorretos!");
        }
    }
}