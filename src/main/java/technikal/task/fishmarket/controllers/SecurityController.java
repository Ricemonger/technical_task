package technikal.task.fishmarket.controllers;

import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import technikal.task.fishmarket.models.ShopUserCredentialsDto;
import technikal.task.fishmarket.services.ShopUserService;

@Controller
public class SecurityController {

    private final ShopUserService shopUserService;

    public SecurityController(ShopUserService shopUserService) {
        this.shopUserService = shopUserService;
    }

    @GetMapping("/login")
    public String showLoginPage(Model model) {
        model.addAttribute("shopUserCredentialsDto", new ShopUserCredentialsDto("", ""));
        return "login";
    }

    @GetMapping("/register")
    public String showRegisterPage(Model model) {
        model.addAttribute("shopUserCredentialsDto", new ShopUserCredentialsDto("", ""));
        return "register";
    }

    @PostMapping("/register")
    public String registerShopUser(
            @ModelAttribute("shopUserCredentialsDto") @Valid ShopUserCredentialsDto requestDto,
            BindingResult result) {

        if (result.hasErrors()) {
            return "register";
        }
        shopUserService.registerUser(requestDto.getUsername(), requestDto.getPassword());
        return "redirect:/login";
    }
}
