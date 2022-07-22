import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AuthGuard } from './guards/auth.guard';
import { DocsComponent } from './pages/docs/docs.component';
import { HomeComponent } from './pages/home/home.component';
import { LoginComponent } from './pages/login/login.component';
import { MyReservationsComponent } from './pages/my-reservations/my-reservations.component';
import { RestaurantLayoutComponent } from './pages/restaurant-layout/restaurant-layout.component';

const routes: Routes = [
  { path: '', redirectTo: 'home', pathMatch: 'full' },
  { path: 'home', component: HomeComponent },
  { path: 'login', component: LoginComponent },
  { path: 'layout', component: RestaurantLayoutComponent },
  {
    path: 'reservations',
    component: MyReservationsComponent,
    canActivate: [AuthGuard],
  },
  {
    path: 'docs',
    component: DocsComponent,
  },
];

@NgModule({
  imports: [
    RouterModule.forRoot(routes, {
      useHash: true,
    }),
  ],
  exports: [RouterModule],
})
export class AppRoutingModule {}
