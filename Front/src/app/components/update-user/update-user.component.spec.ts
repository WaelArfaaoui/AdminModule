import { ComponentFixture, TestBed } from '@angular/core/testing';

import { UpdateUserComponent } from './update-user.component';
import {FormBuilder, FormsModule, ReactiveFormsModule} from "@angular/forms";

describe('UpdateUserComponent', () => {
  let component: UpdateUserComponent;
  let fixture: ComponentFixture<UpdateUserComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ UpdateUserComponent ],
      imports: [FormsModule, ReactiveFormsModule], // Add FormsModule and ReactiveFormsModule to imports
      providers: [FormBuilder] // Add FormBuilder to providers
    })
    .compileComponents();

    fixture = TestBed.createComponent(UpdateUserComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
