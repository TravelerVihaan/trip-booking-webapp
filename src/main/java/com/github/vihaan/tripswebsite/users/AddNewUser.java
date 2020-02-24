package com.github.vihaan.tripswebsite.users;

import com.github.vihaan.tripswebsite.mappers.IMapper;
import com.github.vihaan.tripswebsite.validation.IValidation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class AddNewUser {

    private UserRepositoriesFacade userRepositoriesFacade;
    private IValidation<UserDTO> userValidator;

    @Autowired
    public AddNewUser(@Qualifier("userValidation") IValidation userValidator,
            UserRepositoriesFacade userRepositoriesFacade){
        this.userRepositoriesFacade = userRepositoriesFacade;
        this.userValidator = userValidator;
    }

    public List<String> addNewUser(UserDTO userDTO){
        List<String> errors = Collections.emptyList();
        errors.addAll(userValidator.isValid(userDTO));
        if(errors.isEmpty()) {
            userDTO.setRegisterDate(LocalDateTime.now());
            IMapper<User, UserDTO> mapper = userRepositoriesFacade.getUserMapper();
            User user = mapper.convertDtoToEntity(userDTO);
            Set<RoleDTO> rolesDtoSet = userDTO.getRoles();
            Set<Role> roles = rolesDtoSet.stream().map(role -> findAndConvertToRole(role.getRole())).collect(Collectors.toSet());
            user.setRoles(roles);
            userRepositoriesFacade.saveUser(user);
        }
        return errors;
    }

    private Role findAndConvertToRole(String role){
        return userRepositoriesFacade.getRoleByRoleName(role).orElseThrow();
    }
}
