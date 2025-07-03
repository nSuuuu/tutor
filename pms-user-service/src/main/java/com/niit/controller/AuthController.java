package com.niit.controller;

import com.niit.entity.User;
import com.niit.service.AuthService;
import com.niit.util.BusinessException;
import com.niit.repository.ChatMessageRepository;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.niit.feign.TeacherFeignClient;

@Controller
public class AuthController {
    @Autowired
    private AuthService authService;

    @Autowired
    private ChatMessageRepository chatMessageRepository;

    @Autowired
    private TeacherFeignClient teacherFeignClient;

    @GetMapping("/")
    public String home(HttpSession session, Model model) {
        User user = (User) session.getAttribute("user");
        int unreadCount = 0;
        if (user != null) {
            unreadCount = chatMessageRepository.findByToUserIdAndReadFalse(user.getId()).size();
        }
        model.addAttribute("unreadCount", unreadCount);
        return "index";
    }
    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

    @PostMapping("/login")
    public String login(
            @RequestParam String phone,
            @RequestParam String password,
            HttpSession session,
            Model model) {
        try {
            User user = authService.login(phone, password);
            session.setAttribute("user", user);
            return "redirect:/";
        } catch (BusinessException e) {
            model.addAttribute("error", e.getMessage());
            return "login";
        }
    }

    @GetMapping("/register")
    public String registerPage() {
        return "register";
    }

    @PostMapping("/register")
    public String register(
            @RequestParam String username,
            @RequestParam String password,
            @RequestParam String phone,
            @RequestParam Integer roleType,
            @RequestParam String extraInfo,
            Model model) {
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        user.setPhone(phone);

        try {
            authService.register(user, roleType, extraInfo);
            model.addAttribute("success", "注册成功，请登录");
            return "login";
        } catch (BusinessException e) {
            model.addAttribute("error", e.getMessage());
            model.addAttribute("username", username);
            model.addAttribute("phone", phone);
            model.addAttribute("roleType", roleType);
            model.addAttribute("extraInfo", extraInfo);
            return "register";
        }
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }
} 