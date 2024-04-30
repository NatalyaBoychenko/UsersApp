package springframework.usersapp.service;

import org.springframework.stereotype.Service;
import springframework.usersapp.exception.ResourceNotFoundException;
import springframework.usersapp.model.User;
import springframework.usersapp.payload.UserDto;
import springframework.usersapp.repository.UserRepository;

import java.util.Date;
import java.util.List;
import java.util.Map;

public class UserServiceImpl implements UserService {
    private UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDto createUser(UserDto userDto) {
        User user = mapToEntity(new User(), userDto);

        User newUser = userRepository.save(user);

        return mapToDto(newUser);
    }

    @Override
    public UserDto getById(long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User", "id", id));

        return mapToDto(user);
    }

    @Override
    public UserDto updateSomeUserFields(long id, Map<String, Object> fields) {
        User user = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User", "id", id));
        replaceField(fields, user);
        User updatedUser = userRepository.save(user);

        return mapToDto(updatedUser);
    }


    @Override
    public UserDto updateUser(UserDto userDto, long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User", "id", id));
        mapToEntity(user, userDto);

        User updatedUser = userRepository.save(user);

        return mapToDto(updatedUser);
    }

    @Override
    public void deleteUserById(long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User", "id", id));
        userRepository.delete(user);
    }

    @Override
    public List<UserDto> findUserByBirthdateRange(Date fromDate, Date toDate) {
        List<User> users = userRepository.findAll();
        return users.stream()
                .filter(user -> user.getBirthDate().after(fromDate) && user.getBirthDate().before(toDate))
                .map(UserServiceImpl::mapToDto)
                .toList();
    }

    private static User mapToEntity(User user, UserDto userDto) {
        user.setEmail(userDto.getEmail());
        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        user.setBirthDate(String.valueOf(userDto.getBirthDate()));
        user.setAddress(userDto.getAddress());
        user.setPhoneNumber(userDto.getPhoneNumber());
        return user;
    }

    private static UserDto mapToDto(User newUser) {
        UserDto userResponse = new UserDto();
        userResponse.setId(newUser.getId());
        userResponse.setEmail(newUser.getEmail());
        userResponse.setFirstName(newUser.getFirstName());
        userResponse.setLastName(newUser.getLastName());
        userResponse.setBirthDate(newUser.getBirthDate());
        userResponse.setAddress(newUser.getAddress());
        userResponse.setPhoneNumber(newUser.getPhoneNumber());
        return userResponse;
    }

    private static void replaceField(Map<String, Object> fields, User user) {
        fields.forEach((k, v) -> {
            switch (k) {
                case ("email") ->
                        user.setEmail((String) v);
                case ("firstName") ->
                        user.setFirstName((String) v);
                case ("lastName") ->
                        user.setLastName((String) v);
                case ("birthDate") ->
                        user.setBirthDate((String) v);
                case ("address") ->
                        user.setAddress((String) v);
                case ("phoneNumber") ->
                        user.setPhoneNumber((String) v);
            }
        });
    }
}
