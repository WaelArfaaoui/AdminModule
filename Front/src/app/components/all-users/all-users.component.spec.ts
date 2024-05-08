import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AllUsersComponent } from './all-users.component';
import {HttpClientModule} from "@angular/common/http";


describe('AllUsersComponent', () => {
  let component: AllUsersComponent;
  let fixture: ComponentFixture<AllUsersComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({ // Move the configuration setup inside `TestBed.configureTestingModule`
      declarations: [ AllUsersComponent ],
      imports: [ HttpClientModule ]
    }).compileComponents(); // Compile the components before creating the fixture

    fixture = TestBed.createComponent(AllUsersComponent); // Create the component fixture
    component = fixture.componentInstance; // Get the component instance
    fixture.detectChanges(); // Trigger change detection
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });



});
