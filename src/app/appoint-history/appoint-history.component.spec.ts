import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AppointHistoryComponent } from './appoint-history.component';

describe('AppointHistoryComponent', () => {
  let component: AppointHistoryComponent;
  let fixture: ComponentFixture<AppointHistoryComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ AppointHistoryComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(AppointHistoryComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
