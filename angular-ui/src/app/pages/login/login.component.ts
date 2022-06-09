import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { AccountService } from 'src/app/services/account.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss'],
})
export class LoginComponent implements OnInit {
  loginForm!: FormGroup;
  signUpForm!: FormGroup;
  forgotPasswordForm!: FormGroup;
  state: AuthMode = AuthMode.LOGIN;

  constructor(
    private accountService: AccountService,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.loginForm = this.fb.group({
      email: this.fb.control(null, [Validators.required]),
      password: this.fb.control(null, [Validators.required]),
      rememberMe: this.fb.control(null),
    });

    this.signUpForm = this.fb.group({
      email: this.fb.control(null, [Validators.required, Validators.email]),
      username: this.fb.control(null, [Validators.required]),
      password: this.fb.control(null, [
        Validators.required,
        Validators.minLength(8),
      ]),
      passwordRepeat: this.fb.control(null, [
        Validators.required,
        Validators.minLength(8),
      ]),
    });

    this.forgotPasswordForm = this.fb.group({
      email: this.fb.control(null, [Validators.required]),
    });
  }

  isLoginState(): boolean {
    return this.state === AuthMode.LOGIN;
  }

  isSignUpState(): boolean {
    return this.state === AuthMode.SIGN_UP;
  }

  isForgotPasswordState(): boolean {
    return this.state === AuthMode.FORGOT_PASSWORD;
  }

  getStateText(): string {
    return this.state;
  }

  login() {
    console.log('login', this.loginForm.value);
  }

  signup() {
    console.log('signup', this.signUpForm.value);
  }

  forgotPassword() {
    console.log('forgot password', this.forgotPasswordForm.value);
  }

  showLogin() {
    this.state = AuthMode.LOGIN;
  }

  showSignUp() {
    this.state = AuthMode.SIGN_UP;
  }

  showForgotPassword() {
    this.state = AuthMode.FORGOT_PASSWORD;
  }
}

export enum AuthMode {
  LOGIN = 'Login',
  SIGN_UP = 'Sign up',
  FORGOT_PASSWORD = 'Forgot password',
}
