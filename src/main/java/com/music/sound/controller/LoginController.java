package com.music.sound.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import com.music.sound.DAO.RoleDTO;
import com.music.sound.DAO.UserDAO;
import com.music.sound.DTO.UserDTO.UserLoginDTO;
import com.music.sound.DTO.UserDTO.UserRegisterDTO;
import com.music.sound.config.Constant;
import com.music.sound.model.User;
import com.music.sound.service.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import com.music.sound.DAO.UserDTO;
import java.util.List;
import org.springframework.dao.EmptyResultDataAccessException;
import com.music.sound.Exception.UsernamePasswordException;

@Controller
public class LoginController {

    @Autowired
    private AuthenticationService authenticationService;

    @Autowired
    private UserDAO userDAO;

    @RequestMapping(value = "/login/**", method = RequestMethod.GET)
    public ModelAndView getIndexRootLogin() {

        String urlRedirectRootLogin = "redirect:/login";
        ModelAndView modelAndView = new ModelAndView(urlRedirectRootLogin);
        return modelAndView;
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public ModelAndView getLogin(
            @RequestParam(value = "message", required = false) String message,
            @RequestParam(value = "invalid_username", required = false) String invalidUsername,
            @RequestParam(value = "invalid_password", required = false) String invalidPassword,
            @RequestParam(value = "message_sign_up", required = false) String messageSignUp,
            @RequestParam(value = "register", required = false) String isRegister,
            HttpSession session) {
        String pathFile = "/page/login/index";
        String urlRedirectHome = "redirect:/home";
        String urlRedirectAdmin = "redirect:/admin/album";
        ModelAndView modelAndView = new ModelAndView(pathFile);
        modelAndView.addObject("message", message);
        modelAndView.addObject("register", isRegister);
        modelAndView.addObject("message_sign_up", messageSignUp);
        String idSession = session.getId();
        if (session.getAttribute(idSession) == null) {
            modelAndView.addObject("userLoginDTO", new UserLoginDTO());
            modelAndView.addObject("userRegisterDTO", new UserRegisterDTO());
        } else {
            RoleDTO roleDTO = (RoleDTO) session.getAttribute(idSession);
            String roleUser = roleDTO.getRoleUser();
            switch (roleUser) {
                case Constant.ROLE_ADMIN:
                    modelAndView.setViewName(urlRedirectAdmin);
                    break;
                case Constant.ROLE_USER:
                    modelAndView.setViewName(urlRedirectHome);
                    break;
            }
        }
        return modelAndView;
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ModelAndView postLogin(@ModelAttribute("userLoginDTO") UserLoginDTO userLoginDTO,
            @ModelAttribute("userRegisterDTO") UserRegisterDTO userRegisterDTO, HttpServletRequest request) {

        String urlRedirectRole = "redirect:/role/";
        String urlRedirectHome = "redirect:/home";
        String urlRedirectAdmin = "redirect:/admin/album";
        String urlRedirectLogin = "redirect:/login";

        String pathFile = "/page/login/index";

        ModelAndView modelAndView = new ModelAndView(pathFile);

        String actionButton = request.getParameter("button");

        switch (actionButton) {

            // case: login
            case Constant.ACTION_SIGN_IN:

                try {
                    String username = userLoginDTO.getUsername();
                    String password = userLoginDTO.getPassword();

                    // kiêm tra tài khoàn bị trống hay không
                    if (username == "" || password == "") {
                        throw new NullPointerException();
                    }

                    // kiểm tra sự tồn tại của tài khoản
                    UserDTO user = userDAO.readUserByUsername(username);

                    // kiêm tra mật khẩu có khớp không
                    if (user.getPassword() != null) {
                        String passwordOld = user.getPassword();
                        // login is successed
                        if (password.compareTo(passwordOld) == 0) {

                            String idUser = user.getIdUser();
                            List<RoleDTO> roles = userDAO.readAllRoleByIdUserFromUserRole(idUser);
                            // solve: role user
                            if (roles.size() <= 0) {

                                HttpSession session = request.getSession();

                                String idSession = session.getId();

                                RoleDTO role = new RoleDTO();
                                role.setIdUser(idUser);
                                role.setRoleUser(Constant.ROLE_USER);

                                session.setAttribute(idSession, role);

                                modelAndView.setViewName(urlRedirectHome);
                            } else {
                                // solve: role admin and user
                                urlRedirectRole = urlRedirectRole + idUser;

                                RoleDTO role = new RoleDTO();
                                role.setIdUser(idUser);
                                role.setRoleUser(Constant.ROLE_ADMIN);
                                HttpSession session = request.getSession();
                                String idSession = session.getId();
                                session.setAttribute(idSession, role);
                                modelAndView.setViewName(urlRedirectAdmin);
                            }
                        } else {
                            throw new UsernamePasswordException();
                        }
                    }

                } catch (NullPointerException ex) {
                    String message = "tải khoản hiện không tồn tại";
                    modelAndView.addObject("message", message);
                    modelAndView.setViewName(urlRedirectLogin);
                    modelAndView.addObject("register", false);
                } catch (EmptyResultDataAccessException ex) {
                    String message = "tải khoản hiện không tồn tại";
                    modelAndView.addObject("message", message);
                    modelAndView.setViewName(urlRedirectLogin);
                    modelAndView.addObject("register", false);
                } catch (UsernamePasswordException ex) {
                    String message = "mật khẩu không đúng";
                    modelAndView.addObject("message", message);
                    modelAndView.setViewName(urlRedirectLogin);
                    modelAndView.addObject("register", false);
                } catch (Exception ex) {
                    String message = "lỗi không xác định";
                    modelAndView.addObject("message", message);
                    modelAndView.setViewName(urlRedirectLogin);
                    modelAndView.addObject("register", false);
                }

                break;

            // case: register
            case Constant.ACTION_SIGN_UP:
                try {
                    String username = userRegisterDTO.getUsername();
                    String nameuser = userRegisterDTO.getNameuser();
                    String email = userRegisterDTO.getEmail();
                    String password = userRegisterDTO.getPassword();

                    if (username == "" || password == "" || nameuser == "") {
                        throw new NullPointerException();
                    }

                    User user = new User();
                    user.setUserName(username);
                    user.setPassword(password);
                    user.setNameUser(nameuser);
                    user.setEmail(email);

                    // thêm một tài khoản
                    userDAO.insertUserByUsernameAndPasswordAndNameUser(user);

                    modelAndView.setViewName(urlRedirectLogin);

                } catch (NullPointerException ex) {
                    String message = "Kiểm tra lại các ô nhập thông tin";
                    modelAndView.addObject("message_sign_up", message);
                    modelAndView.addObject("register", true);
                    modelAndView.setViewName(urlRedirectLogin);
                } catch (Exception ex) {
                    String message = "user name or email is existed";
                    modelAndView.addObject("message_sign_up", message);
                    modelAndView.addObject("register", true);
                    modelAndView.setViewName(urlRedirectLogin);
                }

                break;
            default:
                break;
        }

        return modelAndView;
    }

    // only admin redirect to "/role"
    @RequestMapping(value = "/role/{id}", method = RequestMethod.GET)
    public ModelAndView getRole(@PathVariable("id") String idUser, HttpSession session) {
        String fileView = "/page/role/index";

        String urlRedirectLogin = "redirect:/login";
        String urlRedirectAdmin = "redirect:/admin/sound";
        String urlRedirectHome = "redirect:/home";

        ModelAndView modelAndView = new ModelAndView(fileView);

        try {
            UserDTO user = userDAO.readUserByIdUser(idUser);
            // xử lí khi user chưa có tài khoản
            if (user == null) {
                modelAndView.setViewName(urlRedirectLogin);
            } else {

                String idSession = session.getId();
                // kiểm tra session, nếu chưa có sesion thì trở về trang login
                if (session.getAttribute(idSession) == null) {
                    RoleDTO role = new RoleDTO();
                    role.setIdUser(idUser);
                    modelAndView.addObject("role", role);
                } else {
                    // nếu có session (có nghĩa là đã đăng nhập), kiểm tra role để vào
                    RoleDTO roleDTO = (RoleDTO) session.getAttribute(idSession);
                    String roleUser = roleDTO.getRoleUser();
                    switch (roleUser) {
                        case Constant.ROLE_ADMIN:
                            modelAndView.setViewName(urlRedirectAdmin);
                            break;
                        case Constant.ROLE_USER:
                            modelAndView.setViewName(urlRedirectHome);
                            break;
                    }
                }

            }
        } catch (Exception ex) {
            String message = ex.getMessage();
            System.out.println(message);
            modelAndView.setViewName(urlRedirectLogin);
        }
        return modelAndView;
    }

    @RequestMapping(value = "/confirm/role", method = RequestMethod.POST)
    public ModelAndView postConfirmRole(@ModelAttribute("role") RoleDTO roleDTO, HttpSession session,
            HttpServletRequest request) {

        String idSession = session.getId();

        String urlRedirectLogin = "redirect:/login";
        String urlRedirectHome = "redirect:/home";
        String urlRedirectAdmin = "redirect:/admin/sound";

        ModelAndView modelAndView = new ModelAndView(urlRedirectHome);

        String valueButtonAction = request.getParameter("role");

        String idUser = roleDTO.getIdUser();
        String roleUser = Constant.ROLE_USER;

        switch (valueButtonAction) {
            case Constant.ROLE_ADMIN:
                roleUser = Constant.ROLE_ADMIN;
                modelAndView.setViewName(urlRedirectAdmin);
                break;
            case Constant.ROLE_USER:
                roleUser = Constant.ROLE_USER;
                modelAndView.setViewName(urlRedirectHome);
                break;
            default:
                break;
        }

        try {
            RoleDTO role = new RoleDTO();

            role.setIdUser(idUser);
            role.setRoleUser(roleUser);
            session.setAttribute(idSession, role);

        } catch (Exception ex) {
            String message = ex.getMessage();
            System.out.println(message);
            modelAndView.setViewName(urlRedirectLogin);
        }

        return modelAndView;
    }
}
