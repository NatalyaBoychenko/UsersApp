package springframework.usersapp.payload;

import lombok.Data;

import java.util.Date;

@Data
public class UserDto {

    private Long id;
    private String email;
    private String firstName;
    private String lastName;
    private Date birthDate;
    private String address;
    private String phoneNumber;
}
