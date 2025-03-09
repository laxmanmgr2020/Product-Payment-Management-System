import { ComponentFixture, TestBed } from '@angular/core/testing';

import { FavouriteReportComponent } from './favourite-report.component';

describe('FavouriteReportComponent', () => {
  let component: FavouriteReportComponent;
  let fixture: ComponentFixture<FavouriteReportComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [FavouriteReportComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(FavouriteReportComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
