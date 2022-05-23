import { ComponentFixture, TestBed } from '@angular/core/testing';

import { RestaurantFiltersComponent } from './restaurant-filters.component';

describe('RestaurantFiltersComponent', () => {
  let component: RestaurantFiltersComponent;
  let fixture: ComponentFixture<RestaurantFiltersComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ RestaurantFiltersComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(RestaurantFiltersComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
