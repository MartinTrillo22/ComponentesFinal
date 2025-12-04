package com.example.demo.prestamos;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/prestamos")
public class PrestamoController {
    private final PrestamoService prestamoService;

}
