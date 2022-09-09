package com.music.sound.service;

import org.apache.taglibs.standard.tag.common.core.NullAttributeException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.music.sound.DAO.UserDAO;
import com.music.sound.DTO.UserDTO.ConvertUserDTO;
import com.music.sound.DTO.UserDTO.UserDTO;
import com.music.sound.DTO.UserDTO.UserRegisterDTO;
import com.music.sound.model.User;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
@Service
public class UserService {
    @Autowired
    private UserDAO userDAO; 

    @Autowired
    private ConvertUserDTO convertUserDTO;

    private Logger logger = LoggerFactory.getLogger(UserService.class); 

    public UserDTO runConvertEntityToDTO(){
        String username = "votiendatf";
        User user = userDAO.findByUsername(username);
        UserDTO userDTO = convertUserDTO.convertEntityToDTO(user);
        return userDTO;
    }

    public void create(UserRegisterDTO userRegisterDTO){
        String username = userRegisterDTO.getUsername(); 
        String password = userRegisterDTO.getPassword(); 
        String email = userRegisterDTO.getEmail(); 
        if(username == null || username.length() == 0 ){
            throw new NullPointerException("username is empty"); 
        }

        if(password == null || password.length() == 0 ){
            throw new NullPointerException("password is empty"); 
        }

        if(email == null || email.length() == 0 ){
            throw new NullPointerException("email is empty"); 
        }


        User user = convertUserDTO.convertDTOToEntity(userRegisterDTO);
        try{
            userDAO.save(user); 
        }
        catch(Exception ex){
            logger.info(ex.getMessage()); 
        }
    }
}
