package com.example.myfirstapp;

public interface Contract {

        public interface Model {
            public abstract void login();
            public abstract String getId();
        }

        public interface View {
            public abstract void makeToast(String toast);
            public abstract String getEmail();
            public abstract String getPassword();
            public abstract void custLogin();
            public abstract void storeLogin();
            public abstract void handleError(String error);
            public abstract void signUp();
            public abstract void login();
            public abstract void failure();
        }

        public interface Presenter {
            public abstract void login();
        }
}
