import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { HomeComponent } from './pages/home/home.component';
import { SideMenuComponent } from './components/side-menu/side-menu.component';
import { MapComponent } from './components/map/map.component';
import { TopbarComponent } from './components/topbar/topbar.component';
import { ReactiveFormsModule } from '@angular/forms';
import { RestaurantFiltersComponent } from './components/restaurant-filters/restaurant-filters.component';
import { LoginCardComponent } from './components/login-card/login-card.component';

import { RestaurantListCardComponent } from './components/restaurant-list-card/restaurant-list-card.component';
import { RestaurantCardComponent } from './components/restaurant-card/restaurant-card.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { NgxSliderModule } from '@angular-slider/ngx-slider';
import { MatDatepickerModule } from '@angular/material/datepicker';
import { MatMomentDateModule } from '@angular/material-moment-adapter';
import { MatInputModule } from '@angular/material/input';
import { MatIconModule } from '@angular/material/icon';
import { MAT_DATE_LOCALE } from '@angular/material/core';
import { LeafletModule } from '@asymmetrik/ngx-leaflet';
import { HttpClientModule } from '@angular/common/http';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { LoginComponent } from './pages/login/login.component';
import { RestaurantLayoutComponent } from './components/restaurant-layout/restaurant-layout.component';
@NgModule({
  declarations: [
    AppComponent,
    HomeComponent,
    SideMenuComponent,
    MapComponent,
    TopbarComponent,
    RestaurantFiltersComponent,
    LoginCardComponent,
    RestaurantListCardComponent,
    RestaurantCardComponent,
    LoginComponent,
    RestaurantLayoutComponent,
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    FormsModule,
    BrowserAnimationsModule,
    NgxSliderModule,
    MatDatepickerModule,
    MatMomentDateModule,
    MatInputModule,
    MatIconModule,
    LeafletModule,
    HttpClientModule,
    ReactiveFormsModule,
    CommonModule,
  ],
  providers: [{ provide: MAT_DATE_LOCALE, useValue: 'en-GB' }],
  bootstrap: [AppComponent],
})
export class AppModule {}
