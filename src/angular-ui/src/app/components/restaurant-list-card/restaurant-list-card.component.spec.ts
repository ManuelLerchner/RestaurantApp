import { ComponentFixture, TestBed } from '@angular/core/testing';

import { RestaurantListCardComponent } from './restaurant-list-card.component';

describe('RestaurantListCardComponent', () => {
  let component: RestaurantListCardComponent;
  let fixture: ComponentFixture<RestaurantListCardComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [RestaurantListCardComponent],
    }).compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(RestaurantListCardComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
