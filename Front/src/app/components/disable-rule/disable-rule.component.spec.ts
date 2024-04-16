import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DisableRuleComponent } from './disable-rule.component';

describe('DisableRuleComponent', () => {
  let component: DisableRuleComponent;
  let fixture: ComponentFixture<DisableRuleComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ DisableRuleComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(DisableRuleComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
