package com.github.amyurov.cloudstorage.mapper;

public interface SimpleMapper<E, D> {

    E dtoToEntity(D d);

    D entityToDto(E e);
}
