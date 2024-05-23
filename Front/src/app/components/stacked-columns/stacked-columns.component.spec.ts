/*
import { ComponentFixture, TestBed, tick, fakeAsync } from '@angular/core/testing';
import { StackedColumnsComponent } from './stacked-columns.component';

describe('StackedColumnsComponent', () => {
  let component: StackedColumnsComponent;
  let fixture: ComponentFixture<StackedColumnsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [StackedColumnsComponent]
    }).compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(StackedColumnsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should update chart options when heatMapdata changes', fakeAsync(() => {
    const newHeatMapdata = [
      { x: 'data1x', y: 'data1y' },
      { x: 'data2x', y: 'data2y' }
    ];

    spyOn(component, 'updateChartOptions').and.callThrough();

    component.heatMapdata = newHeatMapdata;
    fixture.detectChanges();

    tick();

    expect(component.updateChartOptions).toHaveBeenCalled();
  }));

  it('should not update chart options on the first change of heatMapdata', fakeAsync(() => {
    const newHeatMapdata = [
      { x: 'data1x', y: 'data1y' },
      { x: 'data2x', y: 'data2y' }
    ];

    spyOn(component, 'updateChartOptions').and.callThrough();

    component.heatMapdata = newHeatMapdata;
    fixture.detectChanges();

    tick();

    expect(component.updateChartOptions).toHaveBeenCalledTimes(1);
  }));
});
*/
