import {ComponentFixture, TestBed} from '@angular/core/testing';
import {FormArray, FormControl, FormsModule, ReactiveFormsModule} from '@angular/forms';
import {UseRuleComponent} from './use-rule.component';
import {MessageService} from 'primeng/api';
import {
  AttributeDataDto,
  AttributeService,
  CategoryService,
  RuleDto,
  RuleService,
  UserControllerService
} from '../../../open-api';
import {DynamicDialogConfig, DynamicDialogRef} from 'primeng/dynamicdialog';
import {Router} from '@angular/router';
import {UserService} from '../../services/user/user.service';
import {NO_ERRORS_SCHEMA} from '@angular/core';
import Swal from "sweetalert2";
import {of, throwError} from "rxjs";
describe('UseRuleComponent', () => {
  let component: UseRuleComponent;
  let fixture: ComponentFixture<UseRuleComponent>;
  let messageService: jasmine.SpyObj<MessageService>;
  let attributeService: jasmine.SpyObj<AttributeService>;
  let ruleService: jasmine.SpyObj<RuleService>;
  let categoryService: jasmine.SpyObj<CategoryService>;
  let userService: jasmine.SpyObj<UserService>;
  let userControllerService: jasmine.SpyObj<UserControllerService>;
  let router: jasmine.SpyObj<Router>;
  let config: DynamicDialogConfig;
  let ref: DynamicDialogRef;
  beforeEach(async () => {
    const messageServiceSpy = jasmine.createSpyObj('MessageService', ['add']);
    const attributeServiceSpy = jasmine.createSpyObj('AttributeService', ['getAllAttributes']);
    const ruleServiceSpy = jasmine.createSpyObj('RuleService', ['useRule']);
    const categoryServiceSpy = jasmine.createSpyObj('CategoryService', ['getAllCategories']);
    const userServiceSpy = jasmine.createSpyObj('UserService', ['']);
    const userControllerServiceSpy = jasmine.createSpyObj('UserControllerService', ['']);
    const routerSpy = jasmine.createSpyObj('Router', ['navigate']);
    await TestBed.configureTestingModule({
      declarations: [UseRuleComponent],
      imports: [ReactiveFormsModule, FormsModule],
      providers: [
        { provide: MessageService, useValue: messageServiceSpy },
        { provide: AttributeService, useValue: attributeServiceSpy },
        { provide: RuleService, useValue: ruleServiceSpy },
        { provide: CategoryService, useValue: categoryServiceSpy },
        { provide: UserService, useValue: userServiceSpy },
        { provide: UserControllerService, useValue: userControllerServiceSpy },
        { provide: Router, useValue: routerSpy },
        { provide: DynamicDialogConfig, useValue: {} },
        { provide: DynamicDialogRef, useValue: {} }
      ],
      schemas: [NO_ERRORS_SCHEMA]
    })
      .compileComponents();
    fixture = TestBed.createComponent(UseRuleComponent);
    component = fixture.componentInstance;
    messageService = TestBed.inject(MessageService) as jasmine.SpyObj<MessageService>;
    attributeService = TestBed.inject(AttributeService) as jasmine.SpyObj<AttributeService>;
    ruleService = TestBed.inject(RuleService) as jasmine.SpyObj<RuleService>;
    categoryService = TestBed.inject(CategoryService) as jasmine.SpyObj<CategoryService>;
    userService = TestBed.inject(UserService) as jasmine.SpyObj<UserService>;
    userControllerService = TestBed.inject(UserControllerService) as jasmine.SpyObj<UserControllerService>;
    router = TestBed.inject(Router) as jasmine.SpyObj<Router>;
    config = TestBed.inject(DynamicDialogConfig);
    ref = TestBed.inject(DynamicDialogRef);
  });
  describe('ngOnInit', () => {
    it('should call the necessary methods on initialization', () => {
      spyOn(component, 'initializeForm');
      spyOn(component, 'loadCategories');
      spyOn(component, 'loadAttributes');
      component.ngOnInit();
      expect(component.initializeForm).toHaveBeenCalled();
      expect(component.loadCategories).toHaveBeenCalled();
      expect(component.loadAttributes).toHaveBeenCalled();
    });
  });
  describe('validateAttributeValues', () => {
    it('should return true if all attribute values are valid', () => {
      // Mock necessary dependencies and setup component
      const control1 = new FormControl('5');
      const control2 = new FormControl('7');
      spyOn(component, 'getAttributeControls').and.returnValue([control1, control2]);
      // Invoke the method
      const result = component.validateAttributeValues();
      // Assert
      expect(result).toBeFalse();
    });
    it('should return false and show error message if any attribute value is invalid', () => {
      // Mock necessary dependencies and setup component
      const control1 = new FormControl('15'); // Invalid value
      const control2 = new FormControl('7');
      spyOn(component, 'getAttributeControls').and.returnValue([control1, control2]);
      // Invoke the method
      const result = component.validateAttributeValues();
      // Assert
      expect(result).toBeFalse();
      expect(messageService.add).toHaveBeenCalledWith({
        severity: 'error',
        summary: 'Error',
        detail: 'Attribute value must be between 1 and 10'
      });
    });
  });
  describe('initializeForm', () => {
    it('should initialize the form with given rule data', () => {
      component.rule = {
        name: 'Test Rule',
        description: 'Test Description',
        category: { name: 'Test Category' },
        attributeDtos: [{ name: { name: 'Attribute 1' }, percentage: 50, value: 5 }]
      } as RuleDto;
      component.initializeForm();
    });
  });
  describe('getAttributeControls', () => {
    it('should return the controls of the attribute form array', () => {
      const initialAttributes = [
        component.fb.group({ name: 'Attribute1', percentage: 50 }),
        component.fb.group({ name: 'Attribute2', percentage: 50 })
      ];
      component.ruleForm = component.fb.group({
        attributeDtos: component.fb.array(initialAttributes)
      });
      const attributeControls = component.getAttributeControls();
      expect(attributeControls.length).toBe(2);
      expect(attributeControls[0].value.name).toBe('Attribute1');
      expect(attributeControls[1].value.name).toBe('Attribute2');
    });
  });
  describe('addExistingAttributes', () => {
    it('should add existing attributes to the form', () => {
      component.rule = {
        attributeDtos: [
          { name: { name: 'Attribute 1' }, percentage: 50, value: 5 },
          { name: { name: 'Attribute 2' }, percentage: 50, value: 5 }
        ]
      } as RuleDto;
      component.ruleForm = component.fb.group({
        attributeDtos: component.fb.array([])
      });
      component.addExistingAttributes();
      const attributeArray = component.ruleForm.get('attributeDtos') as FormArray;
      expect(attributeArray.length).toBe(2);
    });
  });
});
