// package com.music.sound.security;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.security.authentication.AuthenticationProvider;
// import org.springframework.security.core.Authentication;

// import com.music.sound.service.CustomUserDetailsService;
// import org.springframework.security.core.AuthenticationException;
// import com.music.sound.model.CustomUserDetails;
// import com.music.sound.Exception.NotMatchPassword;
// import org.slf4j.Logger;
// import org.slf4j.LoggerFactory;
// public class CustomAuthenticationProvider implements AuthenticationProvider{
   
//     private static final Logger logger = LoggerFactory.getLogger(CustomAuthenticationProvider.class);
    
//     @Autowired
//     private CustomUserDetailsService customUserDetailsService;

//     @Override
//     public Authentication authenticate(Authentication authentication) throws AuthenticationException{
//         String username = authentication.getPrincipal().toString();
//         String password = authentication.getCredentials().toString();
//         CustomUserDetails customUserDetails = customUserDetailsService.loadUserByUsername(username); 
//         logger.info(password);
//         if(customUserDetails.getPassword().compareTo(password) != 0 ){
//            throw new NotMatchPassword("Not Match password");
//         }
//         return new UsernamepasswordAuthenticationToken(username, password);
//     }

//     @Override
//     public boolean supports(Class<?> aClass){
//         return UsernamepasswordAuthenticationToken.class.equals(aClass);
//     }
    
// }
