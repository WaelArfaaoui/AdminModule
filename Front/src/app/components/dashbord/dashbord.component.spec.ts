import { ComponentFixture, TestBed } from '@angular/core/testing';
import { dashbordComponent } from './dashbord.component';
describe('dashbordComponent', () => {
  let component: dashbordComponent;
  let fixture: ComponentFixture<dashbordComponent>;
  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ dashbordComponent ]
    })
      .compileComponents();
    fixture = TestBed.createComponent(dashbordComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });
  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

