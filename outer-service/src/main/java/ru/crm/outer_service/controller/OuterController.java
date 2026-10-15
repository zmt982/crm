package ru.crm.outer_service.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import ru.crm.outer_service.dto.OuterRequestDto;
import ru.crm.outer_service.dto.OuterResponseDto;
import ru.crm.outer_service.service.OuterService;

@RestController
@RequestMapping("/outers")
@RequiredArgsConstructor
public class OuterController {

    private final OuterService outerService;

    @PostMapping
    public ResponseEntity<OuterResponseDto> create(@Valid @RequestBody OuterRequestDto outerRequestDto) {
        OuterResponseDto created = outerService.create(outerRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<OuterResponseDto> update(@PathVariable Long id,
                                                   @Valid @RequestBody OuterRequestDto outerRequestDto) {
        return ResponseEntity.ok(outerService.update(id, outerRequestDto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<OuterResponseDto> getById(@PathVariable Long id) {
        return ResponseEntity.ok(outerService.getById(id));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable Long id) {
        outerService.deleteById(id);
    }

}
