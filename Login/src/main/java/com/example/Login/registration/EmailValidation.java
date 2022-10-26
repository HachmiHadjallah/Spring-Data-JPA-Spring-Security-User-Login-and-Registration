package com.example.Login.registration;

import java.util.function.Predicate;

import org.springframework.stereotype.Service;

@Service
public class EmailValidation implements Predicate<String> {

	@Override
	public boolean test(String t) {
		// TODO : Regex to validate email
		return true;
	}

}
