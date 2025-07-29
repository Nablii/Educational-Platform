package tn.enis.mapper;

import tn.enis.models.User;
import tn.enis.dto.LoginDTO;

public class UserMapper {

    public static LoginDTO toDto(User user) {
        LoginDTO dto = new LoginDTO();
        dto.setId(user.getId());
        dto.setFullName(user.getFullName());
        dto.setPassword(user.getPassword());
        return dto;
    }
}
