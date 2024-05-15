import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DeleteCascadeComponent } from './delete-cascade.component';

describe('DeleteCascadeComponent', () => {
  let component: DeleteCascadeComponent;
  let fixture: ComponentFixture<DeleteCascadeComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ DeleteCascadeComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(DeleteCascadeComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
