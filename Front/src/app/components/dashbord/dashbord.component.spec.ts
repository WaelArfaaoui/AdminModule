// @ts-nocheck
import {ComponentFixture, TestBed} from '@angular/core/testing';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {CUSTOM_ELEMENTS_SCHEMA, Directive, Input, NO_ERRORS_SCHEMA, Pipe, PipeTransform} from '@angular/core';
import {of as observableOf} from 'rxjs';

import {DashbordComponent} from './dashbord.component';
import {LayoutService} from '../../layout/service/app.layout.service';

class MockLayoutService {
  configUpdate$ = observableOf({});
}

@Directive({ selector: '[myCustom]' })
class MockMyCustomDirective {
  @Input() myCustom;
}

@Pipe({ name: 'translate' })
class MockTranslatePipe implements PipeTransform {
  transform(value) { return value; }
}

@Pipe({ name: 'phoneNumber' })
class MockPhoneNumberPipe implements PipeTransform {
  transform(value) { return value; }
}

@Pipe({ name: 'safeHtml' })
class MockSafeHtmlPipe implements PipeTransform {
  transform(value) { return value; }
}

describe('DashbordComponent', () => {
  let fixture: ComponentFixture<DashbordComponent>;
  let component: DashbordComponent;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [FormsModule, ReactiveFormsModule],
      declarations: [
        DashbordComponent,
        MockTranslatePipe,
        MockPhoneNumberPipe,
        MockSafeHtmlPipe,
        MockMyCustomDirective
      ],
      schemas: [CUSTOM_ELEMENTS_SCHEMA, NO_ERRORS_SCHEMA],
      providers: [
        { provide: LayoutService, useClass: MockLayoutService }
      ]
    }).compileComponents();

    fixture = TestBed.createComponent(DashbordComponent);
    component = fixture.componentInstance;
  });

  afterEach(() => {
    fixture.destroy();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should call initCharts on ngOnInit', () => {
    spyOn(component, 'initCharts');
    component.ngOnInit();
    expect(component.initCharts).toHaveBeenCalled();
  });

  it('should run initCharts', () => {
    // Test the initCharts method
    component.initCharts();
    // Add your assertions here
  });

  it('should unsubscribe on ngOnDestroy', () => {
    component.subscription = { unsubscribe: jasmine.createSpy('unsubscribe') };
    component.ngOnDestroy();
    expect(component.subscription.unsubscribe).toHaveBeenCalled();
  });
});
