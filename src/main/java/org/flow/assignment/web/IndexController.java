package org.flow.assignment.web;

import lombok.RequiredArgsConstructor;
import org.flow.assignment.dto.ExtensionFilterResponseDto;
import org.flow.assignment.dto.ExtensionWithStateDto;
import org.flow.assignment.service.ExtensionFilterService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.stream.Collectors;

@RequiredArgsConstructor
@Controller
public class IndexController {

    private final ExtensionFilterService filterService;

    @GetMapping("/")
    public String index() {
        return "index";
    }



}
