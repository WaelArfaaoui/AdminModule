// @ts-nocheck
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { CUSTOM_ELEMENTS_SCHEMA, NO_ERRORS_SCHEMA, Directive, Input, Pipe, PipeTransform } from '@angular/core';
import { Observable, of as observableOf } from 'rxjs';
import { By } from '@angular/platform-browser';

import { AllRulesComponent } from './all-rules.component';
import { RuleService } from '../../../open-api';
import { DialogService } from 'primeng/dynamicdialog';

class MockRuleService {
  findAllRules() { return observableOf({}); }
  searchRules() { return observableOf({}); }
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

describe('AllRulesComponent', () => {
  let fixture: ComponentFixture<AllRulesComponent>;
  let component: AllRulesComponent;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [FormsModule, ReactiveFormsModule],
      declarations: [
        AllRulesComponent,
        MockTranslatePipe,
        MockPhoneNumberPipe,
        MockSafeHtmlPipe,
        MockMyCustomDirective
      ],
      schemas: [CUSTOM_ELEMENTS_SCHEMA, NO_ERRORS_SCHEMA],
      providers: [
        { provide: RuleService, useClass: MockRuleService },
        DialogService
      ]
    }).compileComponents();

    fixture = TestBed.createComponent(AllRulesComponent);
    component = fixture.componentInstance;
  });

  afterEach(() => {
    fixture.destroy();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should call loadRules on onPageChange', () => {
    spyOn(component, 'loadRules');
    component.onPageChange({ first: {}, rows: {} });
    expect(component.loadRules).toHaveBeenCalled();
  });

  it('should call loadRules on ngOnInit', () => {
    spyOn(component, 'loadRules');
    component.ngOnInit();
    expect(component.loadRules).toHaveBeenCalled();
  });

  it('should call searchRules on search', () => {
    spyOn(component, 'searchRules');
    component.search();
    expect(component.searchRules).toHaveBeenCalled();
  });

  it('should call findAllRules on loadRules', () => {
    spyOn(component.ruleService, 'findAllRules').and.callThrough();
    component.loadRules();
    expect(component.ruleService.findAllRules).toHaveBeenCalled();
  });

  it('should call searchRules on searchRules', () => {
    spyOn(component.ruleService, 'searchRules').and.callThrough();
    component.searchRules();
    expect(component.ruleService.searchRules).toHaveBeenCalled();
  });

  xit('should call dialogService.open and loadRules on disableRule', () => {
    spyOn(component.dialogService, 'open').and.returnValue({ onClose: observableOf({}) });
    spyOn(component, 'loadRules');
    component.disableRule({}, {});
    expect(component.dialogService.open).toHaveBeenCalled();
    expect(component.loadRules).toHaveBeenCalled();
  });

  it('should call dialogService.open on updateRule', () => {
    spyOn(component.dialogService, 'open');
    component.updateRule({}, {});
    expect(component.dialogService.open).toHaveBeenCalled();
  });

  it('should call dialogService.open on openRuleHistory', () => {
    spyOn(component.dialogService, 'open');
    component.openRuleHistory({}, {});
    expect(component.dialogService.open).toHaveBeenCalled();
  });
});
