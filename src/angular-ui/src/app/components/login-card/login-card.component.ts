import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { AccountService } from 'src/app/services/account.service';

@Component({
  selector: 'app-login-card',
  templateUrl: './login-card.component.html',
  styleUrls: ['./login-card.component.scss'],
})
export class LoginCardComponent implements OnInit {
  constructor(
    public accountService: AccountService,
    private router: Router,
    private route: ActivatedRoute
  ) {}

  ngOnInit(): void {
    const returnUrl =
      this.route.snapshot.queryParamMap.get('returnUrl') || '/home';
    this.accountService.keepSignedIn(returnUrl);
  }

  onLogout(): void {
    this.accountService.logout();
    this.router.navigate(['/']);
  }

  onLogin(): void {
    this.router.navigate(['/login']);
  }
}
