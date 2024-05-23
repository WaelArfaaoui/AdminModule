// @ts-nocheck
import {ComponentFixture, TestBed} from '@angular/core/testing';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {CUSTOM_ELEMENTS_SCHEMA, Directive, Input, NO_ERRORS_SCHEMA, Pipe, PipeTransform} from '@angular/core';
import {of, of as observableOf, throwError} from 'rxjs';

import {AllRulesComponent} from './all-rules.component';
import {RuleDto, RuleService} from '../../../open-api';
import {DialogService, DynamicDialogRef} from 'primeng/dynamicdialog';
import {UseRuleComponent} from "../use-rule/use-rule.component";
import {DisableRuleComponent} from "../disable-rule/disable-rule.component";

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
  let mockDialogRef: jasmine.SpyObj<DynamicDialogRef>;

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

  it('should use rule', () => {
    // Arrange
    const mockRule: RuleDto = { id: 1, status: 'Enabled' };
    const mockRef = { onClose: of(true) };
    const mockDialogService = TestBed.inject(DialogService);
    const openSpy = spyOn(mockDialogService, 'open').and.returnValue(mockRef);

    spyOn(component, 'loadRules');

    // Act
    component.useRule(mockRule);

    // Assert
    expect(component.selectedRule).toEqual(mockRule);
    expect(openSpy).toHaveBeenCalledWith(UseRuleComponent, jasmine.objectContaining({
      header: 'Use Rule',
      width: '900px',
      height: '600px',
      contentStyle: { "background-color": "var(--color-white)", "color": "var(--color-dark)" },
      data: mockRule
    }));

    // Check if subscribe method is called
    mockRef.onClose.subscribe(() => {
      expect(true).toBeTruthy(); // Placeholder assertion
    });

    // Ensure that the loadRules method is called
    expect(component.loadRules).toHaveBeenCalled();
  });
  it('should disable rule', () => {
    // Arrange
    const mockRule: RuleDto = { id: 1, status: 'Enabled' };
    const mockRef = { onClose: of(true) };
    const mockDialogService = TestBed.inject(DialogService);
    const openSpy = spyOn(mockDialogService, 'open').and.returnValue(mockRef);

    spyOn(component, 'loadRules');

    // Act
    component.disableRule(mockRule);

    // Assert
    expect(component.selectedRule).toEqual(mockRule);
    expect(openSpy).toHaveBeenCalledWith(DisableRuleComponent, jasmine.objectContaining({
      header: 'Disable rule',
      width: '500px',
      contentStyle: { "background-color": "var(--color-white)", "color": "var(--color-dark)" },
      data: mockRule
    }));

    // Check if subscribe method is called
    mockRef.onClose.subscribe(() => {
      expect(component.loadRules).toHaveBeenCalled();
    });
  });


});
