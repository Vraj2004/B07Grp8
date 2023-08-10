package com.example.myfirstapp;

import androidx.appcompat.app.AppCompatActivity;

public class LoginPresenter extends AppCompatActivity implements Contract.Presenter{

    Contract.View view;
    Contract.Model model;
    public LoginPresenter(Contract.View view, Contract.Model model)
    {
        this.view = view;
        this.model = model;
    }

    @Override
    public void login(){
        model.login();
//        System.out.println("REACHED PRESENTER LOGIN");
//        if (owner == 1)
//        {
//            System.out.println("CUSTOMER"+ owner);
//            view.custLogin();
//        }
//        else if (owner == 0)
//        {
//            System.out.println("OWNERRRRR"+ owner);
//            view.storeLogin();
//        }
//        else
//        {
//            System.out.println("FAILED:("+ owner);
//            view.failure();
//        }

    }
}
