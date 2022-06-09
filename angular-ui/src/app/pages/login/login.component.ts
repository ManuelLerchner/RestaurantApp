import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
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
  state: AuthMode = AuthMode.SIGN_UP;

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
}

export enum AuthMode {
  LOGIN = 'Login',
  SIGN_UP = 'Sign up',
  FORGOT_PASSWORD = 'Forgot password',
}
