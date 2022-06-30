package com.alex.user.utility;

import org.modelmapper.ModelMapper;

public record MapperUtility () {
    public static <T> T map (Object source, Class<T> destination) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(source, destination);
    }
}
