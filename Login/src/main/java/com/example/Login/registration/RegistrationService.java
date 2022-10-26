package com.example.Login.registration;

import java.time.LocalDateTime;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.example.Login.appuser.AppUser;
import com.example.Login.appuser.AppUserRole;
import com.example.Login.appuser.AppUserService;
import com.example.Login.registration.token.ConfirmationToken;
import com.example.Login.registration.token.ConfirmationTokenService;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class RegistrationService {
	
	private final AppUserService appUserService;
	private final EmailValidation emailValidation;
	private final ConfirmationTokenService confirmationTokenService;
	

	public RegistrationService(AppUserService appUserService, EmailValidation emailValidation,ConfirmationTokenService confirmationTokenService) {
		
		this.appUserService = appUserService;
		this.emailValidation = emailValidation;
		this.confirmationTokenService= confirmationTokenService;
	}



	public String register(RegistrationRequest request) {
		boolean isValideEmail = emailValidation.test(request.getEmail());
		if (!isValideEmail) {
			throw new IllegalStateException("Email Not Valid");
		}
		return appUserService.signUpUser(new AppUser(request.getFirstName(),
				                                     request.getLastName(),
				                                     request.getEmail(),
				                                     request.getPassword(),
				                                     AppUserRole.USER));
	}
	
	@Transactional
    public String confirmToken(String token) {
        ConfirmationToken confirmationToken = confirmationTokenService
                .getToken(token)
                .orElseThrow(() ->
                        new IllegalStateException("token not found"));

        if (confirmationToken.getConfirmedAt() != null) {
            throw new IllegalStateException("email already confirmed");
        }

        LocalDateTime expiredAt = confirmationToken.getExpiresAt();

        if (expiredAt.isBefore(LocalDateTime.now())) {
            throw new IllegalStateException("token expired");
        }

        confirmationTokenService.setConfirmedAt(token);
        appUserService.enableAppUser(
                confirmationToken.getAppUser().getEmail());
        return "confirmed";
    }
	
	

}
