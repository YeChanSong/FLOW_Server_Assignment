package org.flow.assignment.api;

import lombok.RequiredArgsConstructor;
import org.flow.assignment.dto.ExtensionFilterRequestDto;
import org.flow.assignment.dto.ExtensionFilterResponseDto;
import org.flow.assignment.service.ExtensionFilterService;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.transaction.UnexpectedRollbackException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@RequiredArgsConstructor
@EnableWebMvc
@RestController
public class ExtensionFilterController {

    private final ExtensionFilterService filterService;

    @PostMapping("/api/assignment/filter/extension")
    public ExtensionFilterResponseDto addExtensionFilter(
            @RequestBody
            ExtensionFilterRequestDto requestDto
    ) {
        String message = "정상 처리되었습니다.";
        try {
            if (!requestDto.getExtension().isEmpty())
                filterService.addExtensionFilterSuccess(requestDto.getExtension());
            else message = "확장자를 입력해 주세요.";
        } catch (UnexpectedRollbackException | DataIntegrityViolationException e) {
            message = "중복 저장된 확장자입니다.";
        }

        return ExtensionFilterResponseDto.builder()
                .fixed(filterService.getFixedExtensions())
                .custom(filterService.getCustomExtensions())
                .message(message)
                .build();
    }

}
