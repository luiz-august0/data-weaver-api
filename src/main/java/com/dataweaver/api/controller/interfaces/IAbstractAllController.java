package com.dataweaver.api.controller.interfaces;

import com.dataweaver.api.pattern.dtos.AbstractDTO;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

public interface IAbstractAllController<DTO extends AbstractDTO> extends IAbstractAllGetController {
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    DTO insert(@RequestBody DTO dto);

    @PutMapping("/{id}")
    DTO update(@PathVariable("id") Integer id, @RequestBody DTO dto);
}