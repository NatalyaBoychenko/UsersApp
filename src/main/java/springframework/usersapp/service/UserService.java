package springframework.usersapp.service;

import org.apache.catalina.User;
import org.springframework.stereotype.Service;
import springframework.usersapp.payload.UserDto;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public interface UserService {
    UserDto createUser(UserDto userDto);

    UserDto updateSomeUserFields(long id, Map<String, Object> fields);

    UserDto getById(long id);

    UserDto updateUser (UserDto userDto, long id);

    void deleteUserById (long id);

    List<UserDto> findUserByBirthdateRange(Date fromDate, Date toDate);
}
