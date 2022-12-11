package com.music.sound.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import com.music.sound.DAO.RoleDTO;
import com.music.sound.DAO.UserDAO;

import javax.servlet.http.HttpSession;
import com.music.sound.config.Constant;
import com.music.sound.DAO.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import com.music.sound.Exception.UsernamePasswordException;
import org.springframework.web.bind.annotation.ModelAttribute;
import com.music.sound.DTO.PasswordDTO;

@Controller
public class UserController {

    @Autowired
    private UserDAO userDAO;

    @RequestMapping(value = "/reset_password", method = RequestMethod.GET)
    public ModelAndView getResetPassword(
            @RequestParam(value = "message", required = false) String message,
            HttpSession session) {
        String idSession = session.getId();
        RoleDTO roleDTO = (RoleDTO) session.getAttribute(idSession);
        Boolean loginSuccess = roleDTO != null ? true : false;

        String urlRedirectLogin = "redirect:/login";
        String urlRedirectAdmin = "redirect:/admin/album";
        String fileView = "/page/reset_password/index";

        ModelAndView modelAndView = new ModelAndView(fileView);

        if (loginSuccess) {
            Boolean isRoleUser = roleDTO.getRoleUser().compareTo(Constant.ROLE_USER) == 0
                    ? true
                    : false;

            if (isRoleUser) {
                try {
                    String idUser = roleDTO.getIdUser();
                    UserDTO user = userDAO.readUserByIdUser(idUser);
                    String nameUser = user.getNameUser();
                    String pathImageUser = user.getPathImage();
                    String urlPathImageUser = Constant.DEFAULT_USER_IMAGE;
                    if (pathImageUser != null) {
                        urlPathImageUser = Constant.URL_STATIC_IMAGE + pathImageUser;
                    }
                    modelAndView.addObject("session_id", idSession);
                    modelAndView.addObject("name_user", nameUser);
                    modelAndView.addObject("path_image_user", urlPathImageUser);

                    modelAndView.addObject("PasswordDTO", new PasswordDTO());
                    modelAndView.addObject("message", message);

                } catch (Exception ex) {
                }
            } else {

                modelAndView.setViewName(urlRedirectAdmin);
            }
        } else {
            modelAndView.setViewName(urlRedirectLogin);
        }

        return modelAndView;
    }

    @RequestMapping(value = "/reset_password", method = RequestMethod.POST)
    public ModelAndView postResetPassword(@ModelAttribute(value = "PasswordDTO") PasswordDTO passwordDTO,
            HttpSession session) {
        String idSession = session.getId();
        RoleDTO roleDTO = (RoleDTO) session.getAttribute(idSession);
        Boolean loginSuccess = roleDTO != null ? true : false;

        String urlRedirectLogin = "redirect:/login";
        String urlRedirectAdmin = "redirect:/admin/album";
        String urlRedirectResetPassword = "redirect:/reset_password/";

        ModelAndView modelAndView = new ModelAndView();
        if (loginSuccess) {
            Boolean isRoleUser = roleDTO.getRoleUser().compareTo(Constant.ROLE_USER) == 0
                    ? true
                    : false;

            if (isRoleUser) {
                try {
                    String idUser = roleDTO.getIdUser();
                    UserDTO user = userDAO.readUserByIdUser(idUser);
                    String nameUser = user.getNameUser();
                    String pathImageUser = user.getPathImage();
                    String urlPathImageUser = Constant.DEFAULT_USER_IMAGE;
                    if (pathImageUser != null) {
                        urlPathImageUser = Constant.URL_STATIC_IMAGE + pathImageUser;
                    }
                    modelAndView.addObject("session_id", idSession);
                    modelAndView.addObject("name_user", nameUser);
                    modelAndView.addObject("path_image_user", urlPathImageUser);

                    String oldPassword = user.getPassword();
                    String currentPassword = passwordDTO.getCurrentPassword();
                    String newPassword = passwordDTO.getNewPassword();

                    // nếu không bằng nhau trả ra exception
                    if (oldPassword.compareTo(currentPassword) != 0) {
                        throw new UsernamePasswordException();
                    }

                    // sau khi đổi mật khẩu thành công
                    UserDTO updateUser = new UserDTO();
                    updateUser.setIdUser(idUser);
                    updateUser.setPassword(newPassword);
                    userDAO.updatePasswordbyIdUser(updateUser);
                    modelAndView.setViewName(urlRedirectResetPassword);

                } catch (UsernamePasswordException ex) {
                    String message = "Mật khẩu không khớp! vui lòng thử lại";
                    modelAndView.addObject("message", message);
                    modelAndView.setViewName(urlRedirectResetPassword);

                } catch (Exception ex) {
                    String message = "Đổi mật khẩu không thành công";
                    modelAndView.addObject("message", message);
                    modelAndView.setViewName(urlRedirectResetPassword);
                }
            } else {
                modelAndView.setViewName(urlRedirectAdmin);
            }
        } else {
            modelAndView.setViewName(urlRedirectLogin);
        }
        return modelAndView;
    }

    @RequestMapping(value = "/editor/", method = RequestMethod.GET)
    public ModelAndView getEditorUser() {
        ModelAndView modelAndView = new ModelAndView();
        return modelAndView;
    }

    // @RequestMapping(value = { "/user/*", "/user" }, method = RequestMethod.GET)
    // public ModelAndView getIndex() {
    // String pathRedirect = "redirect:/user/tracks";
    // ModelAndView modelAndView = new ModelAndView(pathRedirect);
    // return modelAndView;
    // }

    @RequestMapping(value = "/user/albums", method = RequestMethod.GET)
    public ModelAndView getAlbums() {
        String path = "/page/user/albums/index";
        ModelAndView modelAndView = new ModelAndView(path);
        return modelAndView;
    }

    @RequestMapping(value = "/user/tracks", method = RequestMethod.GET)
    public ModelAndView getTracks() {
        String path = "/page/user/tracks/index";
        ModelAndView modelAndView = new ModelAndView(path);
        return modelAndView;
    }

    @RequestMapping(value = "/user/playlists", method = RequestMethod.GET)
    public ModelAndView getPlaylists() {
        String path = "/page/user/playlists/index";
        ModelAndView modelAndView = new ModelAndView(path);
        return modelAndView;
    }

}
