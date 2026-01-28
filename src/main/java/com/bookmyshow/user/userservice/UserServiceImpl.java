package com.bookmyshow.user.userservice;


import com.bookmyshow.main.dto.OutputCommand;
import com.bookmyshow.security.config.JwtAuthenticationFilter;
import com.bookmyshow.user.dto.UserLoginRequest;
import com.bookmyshow.user.dto.UserSignUpRequest;
import com.bookmyshow.user.model.LoginType;
import com.bookmyshow.security.model.ClientAuthConfig;
import com.bookmyshow.permission.model.Permission;
import com.bookmyshow.user.model.User;
import com.bookmyshow.security.repository.ClientAuthConfigRepository;
import com.bookmyshow.role.repository.RoleRepository;
import com.bookmyshow.user.repository.UserRepository;
import com.bookmyshow.main.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.bookmyshow.role.model.Role;
import com.bookmyshow.main.dto.InputCommand;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {


    final UserRepository userRepository;

    final ClientAuthConfigRepository clientAuthConfigRepository;

    final RoleRepository roleRepository;

    final PasswordEncoder passwordEncoder;

    private static final String DEFAULT_ROLE = "END_USER";

    @Override
    public OutputCommand signUp(InputCommand inputCommand) {
        UserSignUpRequest userSignUpRequest = (UserSignUpRequest) inputCommand.getInputData();
        User user = new User();
        LoginType loginType = LoginType.valueOf(userSignUpRequest.getLoginType());
        String inputId = userSignUpRequest.getInputId();
        switch (loginType)
        {
            case USERNAME:
                user.setName(inputId);
                break;
            case EMAIL:
                user.setEmail(inputId);
                break;
            case PHONE:
                user.setPhone(inputId);
                break;
        }
        user.setPassword(passwordEncoder.encode(userSignUpRequest.getPassword()));
        Role role = roleRepository.findByRoleName(DEFAULT_ROLE);
        if(role != null)
            user.setRole(List.of(role));

        userRepository.save(user);
        return OutputCommand.builder().outputData(user).build();
    }

    @Override
    public OutputCommand login(InputCommand inputCommand) {

        UserLoginRequest userLoginRequest = (UserLoginRequest) inputCommand.getInputData();

        User user = findUser(inputCommand);

        if (user == null)
            throw new RuntimeException("User not found");

        if (!verifyPassword(userLoginRequest.getPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid credentials");
        }

        String clientType = userLoginRequest.getClientId();
        ClientAuthConfig clientAuthConfig = clientAuthConfigRepository.findByClientId(clientType);
        Integer expiry = clientAuthConfig.getTokenExpiryMillis();
        String secretKey = clientAuthConfig.getClientSecretKey();//JwtAuthenticationFilter.defaultSecretKey;

        List<Permission> permissionList =
                user.getRole().stream()
                .flatMap(role -> role.getPermissionList().stream()).distinct()
                .toList();

        Map<String, Object> claims = new HashMap<>();
        claims.put("permissions", permissionList.stream().map(Permission::getPermissionName).toList());
        String token = JwtUtil.generateToken(claims, user.getEmail(), expiry, secretKey, clientType);
        return OutputCommand.builder().outputData(token).build();
    }

    private User findUser(InputCommand inputCommand) {
        UserLoginRequest userLoginRequest = (UserLoginRequest) inputCommand.getInputData();
        LoginType loginType = LoginType.valueOf(userLoginRequest.getLoginType());

        switch (loginType)
        {
            case USERNAME:
                return userRepository.findByName(userLoginRequest.getInputId());
            case EMAIL:
                return userRepository.findByEmail(userLoginRequest.getInputId());
            case PHONE:
                return userRepository.findByPhone(userLoginRequest.getInputId());
            default:
                throw new RuntimeException("Invalid login type");
        }
    }

    private boolean verifyPassword(String rawPassword, String encodedPassword) {
        return passwordEncoder.matches(rawPassword, encodedPassword);
    }
}
