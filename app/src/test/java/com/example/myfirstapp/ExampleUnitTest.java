package com.example.myfirstapp;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.junit.Assert.*;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.MockitoRule;

import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
@RunWith(MockitoJUnitRunner.Silent.class)
public class ExampleUnitTest {

    @Mock
    Contract.View view;
    @Mock
    Contract.Model model;
    private Contract.Presenter presenter;

    @Before
    public void setUp() {
        presenter = new LoginPresenter(view, model);
    }

    @Rule
    public InstantTaskExecutorRule rule = new InstantTaskExecutorRule();
    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();


    @Test
    public void checkEmptyEmail() {
        when(view.getEmail()).thenReturn("");
        when(view.getPassword()).thenReturn("abcd");
        presenter.handleError();
        verify(view).handleError();
    }

    @Test
    public void checkEmptyPassword() {
        when(view.getEmail()).thenReturn("testabc@gmail.com");
        when(view.getPassword()).thenReturn("");
        presenter.login();
        verify(model).login();

    }

    @Test
    public void userFoundCorrectPass() {
        when(view.getPassword()).thenReturn("sinatraa");
        when(view.getEmail()).thenReturn("shopper@gmail.com");
        presenter.custLogin();
        verify(view).custLogin();

    }

    @Test
    public void userWrongPassWrong() {
        when(view.getPassword()).thenReturn("132123123");
        when(view.getEmail()).thenReturn("wrong@gmail.com");
        presenter.failure();
        verify(view).failure();

    }

    @Test
    public void ownerLogin() {
        when(view.getPassword()).thenReturn("testOwner@gmail.com");
        when(view.getEmail()).thenReturn("testing123");
        presenter.storeLogin();
        verify(view).storeLogin();

    }

    @Test
    public void userFoundWrongPass() {
        when(view.getEmail()).thenReturn("shopper@gmail.com");
        when(view.getPassword()).thenReturn("abcd");
        presenter.failure();
        verify(view).failure();

    }
}
