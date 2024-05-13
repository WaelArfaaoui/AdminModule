import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AllRulesComponent } from './all-rules.component';
import {AttributeDataDto, CategoryDto, PageRuleDto, RuleService} from "../../../open-api";
import {of, throwError} from "rxjs";
import {DialogService} from "primeng/dynamicdialog";
import {HttpEvent} from "@angular/common/http";

describe('AllRulesComponent', () => {
  let component: AllRulesComponent;
  let fixture: ComponentFixture<AllRulesComponent>;
  let ruleService: jasmine.SpyObj<RuleService>;
  let dialogService: jasmine.SpyObj<DialogService>;

  beforeEach(async () => {
    const ruleServiceSpy = jasmine.createSpyObj('RuleService', ['findAllRules', 'searchRules']);
    const dialogServiceSpy = jasmine.createSpyObj('DialogService', ['open']);

    await TestBed.configureTestingModule({
      declarations: [ AllRulesComponent ],
      providers: [
        { provide: RuleService, useValue: ruleServiceSpy },
        { provide: DialogService, useValue: dialogServiceSpy }
      ]
    })
        .compileComponents();

    ruleService = TestBed.inject(RuleService) as jasmine.SpyObj<RuleService>;
    dialogService = TestBed.inject(DialogService) as jasmine.SpyObj<DialogService>;
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(AllRulesComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should load rules', () => {
    const mockResponse: PageRuleDto = {
      content: [{ id: 1, name: 'Test Rule', description: 'This is a test rule', category: {} as CategoryDto, enabled: true, createDate: '2024-05-13T12:00:00Z', lastModified: '2024-05-13T12:00:00Z', createdBy: 1, lastModifiedBy: 1, attributeDtos: [{} as AttributeDataDto] }],
      totalElements: 1
    };

    const mockHttpEvent: HttpEvent<PageRuleDto> = {
      type: 4,
      body: mockResponse
    };

    ruleService.findAllRules.and.returnValue(of(mockHttpEvent));

    component.loadRules();

    expect(ruleService.findAllRules).toHaveBeenCalled();
    expect(component.rules).toEqual(mockResponse.content);
    expect(component.totalRecords).toEqual(mockResponse.totalElements);
  });

  it('should handle error when loading rules fails', () => {
    ruleService.findAllRules.and.returnValue(throwError('Error'));

    spyOn(console, 'error');

    component.loadRules();

    expect(console.error).toHaveBeenCalled();
  });

  it('should search rules', () => {
    const mockResponse: PageRuleDto = {
      content: [{ id: 1, name: 'Test Rule', description: 'This is a test rule', category: {} as CategoryDto, enabled: true, createDate: '2024-05-13T12:00:00Z', lastModified: '2024-05-13T12:00:00Z', createdBy: 1, lastModifiedBy: 1, attributeDtos: [{} as AttributeDataDto] }],
      totalElements: 1
    };

    const mockHttpEvent: HttpEvent<PageRuleDto> = {
      type: 4, // Adjust according to your implementation
      body: mockResponse
    };

    ruleService.searchRules.and.returnValue(of(mockHttpEvent));

    component.searchQuery = 'example';

    component.searchRules();

    expect(ruleService.searchRules).toHaveBeenCalled();
    expect(ruleService.searchRules).toHaveBeenCalledWith(component.first, component.rows, 'example');
    expect(component.rules).toEqual(mockResponse.content);
    expect(component.totalRecords).toEqual(mockResponse.totalElements);
  });

  it('should handle error when searching rules fails', () => {
    ruleService.searchRules.and.returnValue(throwError('Error'));

    spyOn(console, 'error');

    component.searchRules();

    expect(console.error).toHaveBeenCalled();
  });

});
