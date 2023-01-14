package org.workable.mapper;

import org.mapstruct.Mapper;
import org.workable.controller.DTO.UserDTO;
import org.workable.entity.User;

import java.util.List;

@Mapper(componentModel = "spring",  uses = {ReviewMapper.class})
public interface UserMapper {

    List<UserDTO> usersEntityToUserDTOs(List<User> source);

    UserDTO userEntityToUserDTO(User source);

    User userDTOToUserEntity(UserDTO source);
}
