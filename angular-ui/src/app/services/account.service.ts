import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { BehaviorSubject, Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { User } from '../models/User';
import { map } from 'rxjs/operators';
import { Router } from '@angular/router';

@Injectable({
  providedIn: 'root',
})
export class AccountService {
  public user$: BehaviorSubject<User>;

  constructor(private http: HttpClient, private router: Router) {
    this.user$ = new BehaviorSubject<User>(null as unknown as User);
  }

  get isLoggedIn(): boolean {
    return !!this.user$.value;
  }

  async keepSignedIn(returnUrl: string) {
    let localUserData = localStorage.getItem('user');

    if (localUserData) {
      let localUserDataJson = JSON.parse(localUserData);

      try {
        let user = await this.http
          .post<User>(`${environment.apiUrl}/auth/keep-signed-in`, {
            user: localUserDataJson,
          })
          .toPromise();

        this.user$.next(user as User);

        this.router.navigate([returnUrl]);
      } catch (error: any) {
        console.log(error.statusText);
      }
    }
  }

  login(email: string, password: string, rememberMe: boolean) {
    return this.http
      .post<User>(`${environment.apiUrl}/auth/login`, {
        email,
        password,
        rememberMe,
      })
      .pipe(
        map((user: User) => {
          if (rememberMe) {
            localStorage.setItem('user', JSON.stringify(user));
          }
          this.user$.next(user as User);
          return user;
        })
      );
  }

  register(email: string, username: string, password: string) {
    return this.http.post(`${environment.apiUrl}/auth/register`, {
      email,
      username,
      password,
    });
  }

  logout() {
    localStorage.removeItem('user');
    this.user$.next(null as unknown as User);
  }
}
