import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ListParamTablesComponent } from './list-param-tables.component';

describe('ListParamTablesComponent', () => {
  let component: ListParamTablesComponent;
  let fixture: ComponentFixture<ListParamTablesComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ListParamTablesComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ListParamTablesComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
