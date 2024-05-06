import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DeleteParamComponent } from './delete-param.component';

describe('DeleteParamComponent', () => {
  let component: DeleteParamComponent;
  let fixture: ComponentFixture<DeleteParamComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ DeleteParamComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(DeleteParamComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
