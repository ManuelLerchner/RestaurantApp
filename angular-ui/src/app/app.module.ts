import { NgxSliderModule } from '@angular-slider/ngx-slider';
import { CommonModule } from '@angular/common';
import { HttpClientModule } from '@angular/common/http';
import { NgModule } from '@angular/core';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { MatMomentDateModule } from '@angular/material-moment-adapter';
import { MAT_DATE_LOCALE } from '@angular/material/core';
import { MatDatepickerModule } from '@angular/material/datepicker';
import { MatIconModule } from '@angular/material/icon';
import { MatInputModule } from '@angular/material/input';
import { BrowserModule } from '@angular/platform-browser';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { LeafletModule } from '@asymmetrik/ngx-leaflet';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { LoginCardComponent } from './components/login-card/login-card.component';
import { MapComponent } from './components/map/map.component';
import { RestaurantCardComponent } from './components/restaurant-card/restaurant-card.component';
import { RestaurantFiltersComponent } from './components/restaurant-filters/restaurant-filters.component';
import { RestaurantLayoutComponent } from './components/restaurant-layout/restaurant-layout.component';
import { RestaurantListCardComponent } from './components/restaurant-list-card/restaurant-list-card.component';
import { SideMenuComponent } from './components/side-menu/side-menu.component';
import { TopbarComponent } from './components/topbar/topbar.component';
import { HomeComponent } from './pages/home/home.component';
import { LoginComponent } from './pages/login/login.component';
import { NgxLoadingModule } from 'ngx-loading';

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
    NgxLoadingModule.forRoot({}),
  ],
  providers: [{ provide: MAT_DATE_LOCALE, useValue: 'en-GB' }],
  bootstrap: [AppComponent],
})
export class AppModule {}
