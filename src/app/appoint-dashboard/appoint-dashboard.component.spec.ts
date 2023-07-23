import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AppointDashboardComponent } from './appoint-dashboard.component';

describe('AppointDashboardComponent', () => {
  let component: AppointDashboardComponent;
  let fixture: ComponentFixture<AppointDashboardComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ AppointDashboardComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(AppointDashboardComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
