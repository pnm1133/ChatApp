package com.example.nguyephan.friendapp.util.TextConstrant;

import com.example.nguyephan.friendapp.R;

/**
 * Created by nguye phan on 5/11/2018.
 */

public class TextConstant {

    public static final String RESPONSE_OK = "OK";
    public static final String ERROR_NULL_EMAIL = "Email trống";
    public static final String ERROR_NULL_PASSWORD = "Password trống";
    public static final String ERROR_EMAIL_BADLY_FORMAT = "Email phải có dạng ex@gmail.com";
    public static final String ERROR_PASSWORD_BADLY_FORMAT = "Password phải có ít nhất 8 ký tự, 1 chữ , 1 số !";
    public static final String ERROR_NULL_RECORD_VE = "Email không tồn tại";
    public static final String ERROR_WRONG_PASSWORD_INVALID_VE = "Sai mật khẩu";
    public static final String ERROR_NULL_RECORD = "There is no user record corresponding to this identifier. The user may have been deleted.";
    public static final String ERROR_WRONG_INVALID = "The password is invalid or the user does not have a password.";
    public static final String ERROR_NULL_NAME= "UserName không được trống";

    public static boolean regexEmail(String email){
        String emailPattern = "(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])";
        return email.matches(emailPattern);
    }

    //Minimum eight characters, at least one letter and one number
    public static boolean regexPassword(String password){
        String passwordPattern = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$";
        return password.matches(passwordPattern);
    }

}
