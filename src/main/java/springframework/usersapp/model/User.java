package springframework.usersapp.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Email(message = "Email is not valid", regexp = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$")
    @NotEmpty(message = "Email required")
    private String email;
    @NotEmpty(message = "First name required")
    private String firstName;
    @NotEmpty(message = "Last name required")
    private String lastName;
    @Getter
    @NotEmpty(message = "Birthdate required")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date birthDate;
    private String address;
    private String phoneNumber;

        public void setBirthDate(String date) {
            try {
                this.birthDate = new SimpleDateFormat("yyyy-MM-dd").parse(date);
            } catch (ParseException e) {

            }
        }

    public static Date parseDate(String date) {
        try {
            return new SimpleDateFormat("yyyy-MM-dd").parse(date);
        } catch (ParseException e) {
            return null;
        }
    }
}
