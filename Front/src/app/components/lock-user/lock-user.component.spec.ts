import { ComponentFixture, TestBed } from '@angular/core/testing';

import { LockUserComponent } from './lock-user.component';
import {DynamicDialogRef} from "primeng/dynamicdialog";
import {HttpClient} from "@angular/common/http";

describe('LockUserComponent', () => {
  let component: LockUserComponent;
  let fixture: ComponentFixture<LockUserComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ LockUserComponent ],
      imports : [DynamicDialogRef],
      providers: [DynamicDialogRef, HttpClient]
    })
    .compileComponents();

    fixture = TestBed.createComponent(LockUserComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
