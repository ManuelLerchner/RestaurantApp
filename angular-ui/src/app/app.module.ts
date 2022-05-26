import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { HomeComponent } from './pages/home/home.component';
import { SideMenuComponent } from './components/side-menu/side-menu.component';
import { MapComponent } from './components/map/map.component';
import { TopbarComponent } from './components/topbar/topbar.component';
import { FormsModule } from '@angular/forms';
import { RestaurantFiltersComponent } from './components/restaurant-filters/restaurant-filters.component';
import { LoginComponent } from './components/login/login.component';

import { RestaurantListCardComponent } from './components/restaurant-list-card/restaurant-list-card.component';
import { RestaurantCardComponent } from './components/restaurant-card/restaurant-card.component';

@NgModule({
  declarations: [
    AppComponent,
    HomeComponent,
    SideMenuComponent,
    MapComponent,
    TopbarComponent,
    RestaurantFiltersComponent,
    LoginComponent,
    RestaurantListCardComponent,
    RestaurantCardComponent,
  ],
  imports: [BrowserModule, AppRoutingModule, FormsModule],
  providers: [],
  bootstrap: [AppComponent],
})
export class AppModule {}
