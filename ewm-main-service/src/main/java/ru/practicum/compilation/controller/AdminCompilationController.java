package ru.practicum.compilation.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.compilation.service.AdminCompilationService;
import ru.practicum.compilation.dto.NewCompilationDto;
import ru.practicum.compilation.dto.UpdateCompilationRequest;

import javax.validation.Valid;

/**
 * @author Nikolay Radzivon
 * @Date 22.06.2024
 */
@RestController
@RequestMapping("/admin/compilations")
@RequiredArgsConstructor
@Slf4j
@Validated
public class AdminCompilationController {
    private final AdminCompilationService adminCompilationService;

    @PostMapping
    public ResponseEntity<Object> addCompilation(@RequestBody @Valid NewCompilationDto compilation) {
        log.info("Получен запрос: POST /admin/compilations");

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .contentType(MediaType.APPLICATION_JSON)
                .body(adminCompilationService.addCompilation(compilation));
    }

    @DeleteMapping("/{compId}")
    public ResponseEntity<Object> deleteCompilation(@PathVariable Long compId) {
        log.info("Получен запрос: DELETE /admin/compilations/{}", compId);

        adminCompilationService.deleteCompilation(compId);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{compId}")
    public ResponseEntity<Object> updateCompilation(@PathVariable Long compId, @RequestBody @Valid UpdateCompilationRequest compilation) {
        log.info("Получен запрос: PATCH /admin/compilations/{} body={}", compId, compilation);

        return ResponseEntity
                .status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(adminCompilationService.updateCompilation(compId, compilation));
    }
}
