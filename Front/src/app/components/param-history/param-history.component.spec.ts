import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ParamHistoryComponent } from './param-history.component';

describe('ParamHistoryComponent', () => {
  let component: ParamHistoryComponent;
  let fixture: ComponentFixture<ParamHistoryComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ParamHistoryComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ParamHistoryComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
