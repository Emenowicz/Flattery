package zpi.flattery.dto;

import javax.validation.constraints.*;

public class RegistrationForm {
    @Size(min = 3, max = 20)
    @NotBlank
    private String firstName;
    @Size(min = 3, max = 30)
    @NotBlank
    private String lastName;
    @Size(min = 4, max = 20)
    @NotBlank
    private String userName;
    @Size(min = 6, max = 20)
    @NotBlank
    private String password;
    @NotNull
    private String confirmPassword;
    @Email
    @NotBlank
    @Size(min = 5, max = 30)
    private String emailAddress;

    public RegistrationForm(String firstName, String lastName, String userName, String password, String confirmPassword, String emailAddress) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.userName = userName;
        this.password = password;
        this.confirmPassword = confirmPassword;
        this.emailAddress = emailAddress;
    }

    @AssertTrue(message = "Password does not match")
    public boolean isPasswordMatching() {
        return confirmPassword.equals(password);
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public String getEmailAddress() {
        return emailAddress;
    }
}
