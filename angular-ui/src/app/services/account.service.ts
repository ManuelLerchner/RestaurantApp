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
        let list = await this.http
          .get<string[]>(`${environment.apiUrl}/loginWithAuthToken`, {
            params: {
              authtoken: localUserDataJson.authToken,
            },
          })
          .toPromise();

        let user: User = {
          email: list[0],
          name: list[1],
          authToken: list[2],
        };

        this.user$.next(user as User);

        this.router.navigate([returnUrl]);
      } catch (error: any) {
        console.log(error);
      }
    }
  }

  login(email: string, password: string, rememberMe: boolean) {
    return this.http
      .get<User>(`${environment.apiUrl}/login`, {
        params: {
          email,
          password,
        },
      })
      .pipe(
        map((list: any) => {
          console.log(list);

          let user: User = {
            email: list[0],
            name: list[1],
            authToken: list[2],
          };

          if (rememberMe) {
            localStorage.setItem('user', JSON.stringify(user));
          }

          this.user$.next(user as User);
          return user;
        })
      );
  }

  register(email: string, username: string, password: string) {
    return this.http.post(`${environment.apiUrl}/signUp`, {
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
