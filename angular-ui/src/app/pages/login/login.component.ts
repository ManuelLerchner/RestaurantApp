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
  state: AuthMode = AuthMode.LOGIN;
  responseStatus: '' | 'Error' | 'Success' = '';
  responseText: string = '';

  constructor(
    private accountService: AccountService,
    private fb: FormBuilder,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.loginForm = this.fb.group({
      email: this.fb.control(null, [Validators.required]),
      password: this.fb.control(null, [Validators.required]),
      rememberMe: this.fb.control(true),
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

  async login() {
    let loginData = this.loginForm.value;

    try {
      await this.accountService
        .login(loginData.email, loginData.password, loginData.rememberMe)
        .toPromise();

      this.responseStatus = 'Success';
      this.responseText = 'Login successful';

      await sleep(500);

      this.router.navigate(['/home']);
    } catch (error: any) {
      console.log(error);

      this.responseStatus = 'Error';

      if (typeof error.error === 'string') {
        this.responseText = error.error;
      } else {
        this.responseText = 'An unknown error occured.';
      }
    }
  }

  async signup() {
    let signupData = this.signUpForm.value;

    try {
      let user = await this.accountService
        .register(signupData.email, signupData.username, signupData.password)
        .toPromise();

      this.responseStatus = 'Success';
      this.responseText = 'Register successful';

      await sleep(500);

      await this.accountService
        .login(signupData.email, signupData.password, true)
        .toPromise();

      this.router.navigate(['/home']);
    } catch (error: any) {
      console.log(error);

      this.responseStatus = 'Error';

      if (typeof error.error === 'string') {
        this.responseText = error.error;
      } else {
        this.responseText = 'An unknown error occured.';
      }
    }
  }

  forgotPassword() {
    console.log('forgot password', this.forgotPasswordForm.value);
  }

  showLogin() {
    this.responseStatus = '';
    this.responseText = '';
    this.state = AuthMode.LOGIN;
  }

  showSignUp() {
    this.responseStatus = '';
    this.responseText = '';
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

function sleep(time: number) {
  return new Promise<void>((resolve) => {
    setTimeout(() => {
      resolve();
    }, time);
  });
}
