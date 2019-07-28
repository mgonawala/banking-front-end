package com.blueharvest.controller;

import com.blueharvest.model.Account;
import com.blueharvest.model.Product;
import com.blueharvest.service.AccountService;
import com.blueharvest.service.ProductService;
import com.blueharvest.util.Pager;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class HomeController {

    private static final int INITIAL_PAGE = 0;

    private final ProductService productService;

    private final AccountService accountService;

    @Autowired
    public HomeController(ProductService productService, AccountService accountService) {
        this.productService = productService;
        this.accountService = accountService;
    }

    @GetMapping("/home")
    public ModelAndView home(@RequestParam("page") Optional<Integer> page) {

        // Evaluate page. If requested parameter is null or less than 0 (to
        // prevent exception), return initial size. Otherwise, return value of
        // param. decreased by 1.
        int evalPage = (page.orElse(0) < 1) ? INITIAL_PAGE : page.get() - 1;

        Page<Product> products = productService.findAllProductsPageable(new PageRequest(evalPage, 5));
        Pager pager = new Pager(products);
        List<Account> accounts = accountService.findAllProductsPageable();
        ModelAndView modelAndView = new ModelAndView();
        //modelAndView.addObject("products", products);
        modelAndView.addObject("pager", pager);
        modelAndView.addObject("accounts", accounts);
        modelAndView.setViewName("/accounts");
        return modelAndView;
    }

}
