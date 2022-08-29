//package com.example.demo.util;
//
//import com.example.demo.dto.request.MyEntityRequestDto;
//import com.example.demo.dto.response.MyEntityResponseDto;
//import com.example.demo.model.MyEntity;
//import org.mapstruct.Mapper;
//import org.mapstruct.factory.Mappers;
//
//import java.util.List;
//
//@Mapper
//public abstract class MyEntityMapper {
//
//    public static final MyEntityMapper INSTANCE = Mappers.getMapper(MyEntityMapper.class);
//
//    public abstract List<MyEntityResponseDto> toMyEntityResponseDtoList(List<MyEntity> myEntityList);
//
//    public abstract MyEntityResponseDto toMyEntityResponseDto(MyEntity myEntity);
//
//    public abstract MyEntity toMyEntity(MyEntityRequestDto myEntityRequestDto);
//}
