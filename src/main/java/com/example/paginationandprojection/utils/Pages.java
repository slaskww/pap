package com.example.paginationandprojection.utils;

public interface Pages {

    String HOME = "githome";

    interface Registration{

        String FORM = "register-form";
        String RESULT = "register-result";
    }

    interface Login{
        String FORM = "login-form";
    }

    interface User{
        String ACCOUNT = "user-account";
    }
}
