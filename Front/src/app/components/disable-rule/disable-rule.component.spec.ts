import { ComponentFixture, TestBed, tick, fakeAsync } from '@angular/core/testing';
import { DynamicDialogConfig, DynamicDialogRef } from 'primeng/dynamicdialog';
import { MessageService } from 'primeng/api';
import { of, throwError } from 'rxjs';
import { HttpEvent } from '@angular/common/http'; // Import HttpEvent

import { DisableRuleComponent } from './disable-rule.component';
import { RuleService } from '../../../open-api';
import { RuleDto } from '../../../open-api'; // Adjust the path as per your project structure

describe('DisableRuleComponent', () => {
  let component: DisableRuleComponent;
  let fixture: ComponentFixture<DisableRuleComponent>;
  let dialogRefSpy: jasmine.SpyObj<DynamicDialogRef>;
  let dialogConfigSpy: jasmine.SpyObj<DynamicDialogConfig>;
  let ruleServiceSpy: jasmine.SpyObj<RuleService>;
  let messageServiceSpy: jasmine.SpyObj<MessageService>;

  const mockRuleWithId: RuleDto = { // Ensure mockRule has an id
    id: 1,
    name: 'Test Rule',
    description: 'Test Description',
    category: { id: 1, name: 'TestCategory' },
    enabled: true,
    createDate: '2024-05-14T12:00:00',
    lastModified: '2024-05-14T12:00:00',
    createdBy: 1,
    lastModifiedBy: 1,
    attributeDtos: []
  };

  const mockRule: RuleDto = { // Ensure mockRule has an id
    id: 1,
    name: 'Test Rule',
    description: 'Test Description',
    category: { id: 1, name: 'TestCategory' },
    enabled: true,
    createDate: '2024-05-14T12:00:00',
    lastModified: '2024-05-14T12:00:00',
    createdBy: 1,
    lastModifiedBy: 1,
    attributeDtos: []
  };

  beforeEach(async () => {
    const dialogConfigSpyObj = jasmine.createSpyObj('DynamicDialogConfig', ['data']);
    const dialogRefSpyObj = jasmine.createSpyObj('DynamicDialogRef', ['close']);
    const ruleServiceSpyObj = jasmine.createSpyObj('RuleService', ['updateStatus']);
    const messageServiceSpyObj = jasmine.createSpyObj('MessageService', ['add']);

    await TestBed.configureTestingModule({
      declarations: [DisableRuleComponent],
      providers: [
        { provide: DynamicDialogConfig, useValue: dialogConfigSpyObj },
        { provide: DynamicDialogRef, useValue: dialogRefSpyObj },
        { provide: RuleService, useValue: ruleServiceSpyObj },
        { provide: MessageService, useValue: messageServiceSpyObj }
      ]
    }).compileComponents();

    dialogRefSpy = TestBed.inject(DynamicDialogRef) as jasmine.SpyObj<DynamicDialogRef>;
    dialogConfigSpy = TestBed.inject(DynamicDialogConfig) as jasmine.SpyObj<DynamicDialogConfig>;
    ruleServiceSpy = TestBed.inject(RuleService) as jasmine.SpyObj<RuleService>;
    messageServiceSpy = TestBed.inject(MessageService) as jasmine.SpyObj<MessageService>;

    dialogConfigSpy.data = mockRuleWithId;
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(DisableRuleComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should close dialog', () => {
    component.closeDialog();
    expect(dialogRefSpy.close).toHaveBeenCalled();
  });

  it('should disable rule', fakeAsync(() => {
    // Return a mock HTTP event
    const mockHttpEvent: HttpEvent<RuleDto> = {
      type: 4, // This could be any value, here using 4 for simplicity
      body: mockRule,
    };
    const updateStatusSpy = ruleServiceSpy.updateStatus.and.returnValue(of(mockHttpEvent));
    component.disable();
    tick();

    expect(updateStatusSpy).toHaveBeenCalledWith(mockRule.id!, false); // Use '!' to assert non-null for mockRule.id
    expect(messageServiceSpy.add).toHaveBeenCalledWith({
      severity: 'success',
      summary: 'Disabled',
      detail: 'Rule disabled successfully',
    });
    expect(dialogRefSpy.close).toHaveBeenCalledWith(true);
  }));

  it('should handle error when disabling rule', fakeAsync(() => {
    const errorResponse = new Error('Test error');
    const updateStatusSpy = ruleServiceSpy.updateStatus.and.returnValue(throwError(errorResponse));
    const consoleSpy = spyOn(console, 'log'); // Create a spy for console.log
    component.disable();
    tick();

    expect(updateStatusSpy).toHaveBeenCalledWith(mockRule.id!, false); // Use '!' to assert non-null for mockRule.id
  }));
});
