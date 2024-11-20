package com.dataweaver.api.controller;

import com.dataweaver.api.controller.interfaces.IAbstractAllController;
import com.dataweaver.api.infrastructure.converter.Converter;
import com.dataweaver.api.pattern.dtos.AbstractDTO;
import com.dataweaver.api.pattern.entities.AbstractEntity;
import com.dataweaver.api.service.AbstractService;

import java.io.Serializable;

public abstract class AbstractAllController<Service extends AbstractService, DTO extends AbstractDTO, Entity extends AbstractEntity>
        extends AbstractAllGetController implements IAbstractAllController<DTO>, Serializable {
    private final Service service;

    private final Entity entity;

    AbstractAllController(Service service, DTO dto, Entity entity) {
        super(service, dto);
        this.service = service;
        this.entity = entity;
    }

    public DTO insert(DTO dto) {
        return (DTO) Converter.convertEntityToDTO(service.insert(Converter.convertDTOToEntity(dto, entity.getClass())), dto.getClass());
    }

    public DTO update(Integer id, DTO dto) {
        return (DTO) Converter.convertEntityToDTO(service.update(id, Converter.convertDTOToEntity(dto, entity.getClass())), dto.getClass());
    }

}

