package com.luerde.luerdegeekshop.controller;

import com.luerde.luerdegeekshop.User;
import com.luerde.luerdegeekshop.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * Controlador para lidar com a autenticação de usuários, como registro e redefinição de senha.
 */
@Controller
public class AuthController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    /**
     * Exibe o formulário de registro.
     */
    @GetMapping("/register")
    public String showRegistrationForm() {
        return "register";
    }

    /**
     * Processa o formulário de registro.
     */
    @PostMapping("/register")
    public String registerUser(@ModelAttribute User user, RedirectAttributes redirectAttributes) {
        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            redirectAttributes.addFlashAttribute("error", "Email já cadastrado.");
            return "redirect:/register?error";
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setSecurityAnswer(passwordEncoder.encode(user.getSecurityAnswer()));
        userRepository.save(user);
        redirectAttributes.addFlashAttribute("success", "Usuário registrado com sucesso! Faça login.");
        return "redirect:/register?success";
    }

    /**
     * Exibe o formulário para inserir o e-mail para redefinição de senha.
     */
    @GetMapping("/forgot-password")
    public String showForgotPasswordForm() {
        return "forgot-password";
    }

    /**
     * Processa o e-mail e encontra a pergunta de segurança do usuário.
     */
    @PostMapping("/forgot-password")
    public String processForgotPassword(@RequestParam("email") String email, RedirectAttributes redirectAttributes) {
        return userRepository.findByEmail(email)
                .map(user -> "redirect:/reset-password?email=" + email)
                .orElseGet(() -> {
                    redirectAttributes.addFlashAttribute("message", "Usuário não encontrado.");
                    return "redirect:/forgot-password";
                });
    }

    /**
     * Exibe o formulário de redefinição de senha.
     */
    @GetMapping("/reset-password")
    public String showResetPasswordForm(@RequestParam("email") String email, Model model, RedirectAttributes redirectAttributes) {
        return userRepository.findByEmail(email).map(user -> {
            model.addAttribute("email", email);
            model.addAttribute("securityQuestion", user.getSecurityQuestion());
            return "reset-password";
        }).orElseGet(() -> {
            redirectAttributes.addFlashAttribute("message", "Usuário não encontrado.");
            return "redirect:/forgot-password";
        });
    }

    /**
     * Processa a resposta de segurança e redefinir a senha.
     */
    @PostMapping("/reset-password")
    public String resetPassword(@RequestParam("email") String email,
                                @RequestParam("securityAnswer") String securityAnswer,
                                @RequestParam("password") String password,
                                @RequestParam("confirmPassword") String confirmPassword,
                                RedirectAttributes redirectAttributes) {

        if (!password.equals(confirmPassword)) {
            redirectAttributes.addFlashAttribute("message", "As senhas não coincidem.");
            redirectAttributes.addFlashAttribute("error", true);
            return "redirect:/reset-password?email=" + email;
        }

        return userRepository.findByEmail(email).map(user -> {
            if (passwordEncoder.matches(securityAnswer, user.getSecurityAnswer())) {
                user.setPassword(passwordEncoder.encode(password));
                userRepository.save(user);
                redirectAttributes.addFlashAttribute("message", "Sua senha foi redefinida com sucesso!");
                return "redirect:/login";
            } else {
                redirectAttributes.addFlashAttribute("message", "Resposta de segurança incorreta.");
                redirectAttributes.addFlashAttribute("error", true);
                return "redirect:/reset-password?email=" + email;
            }
        }).orElseGet(() -> {
            redirectAttributes.addFlashAttribute("message", "Ocorreu um erro. Tente novamente.");
            return "redirect:/forgot-password";
        });
    }
}