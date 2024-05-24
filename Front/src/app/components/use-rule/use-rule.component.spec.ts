import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ReactiveFormsModule, FormsModule, FormArray } from '@angular/forms';
import { UseRuleComponent } from './use-rule.component';
import { AttributeService, CategoryService, RuleDto, AttributeDataDto, AttributeDto, CategoryDto } from '../../../open-api';
import { MessageService } from 'primeng/api';
import { DynamicDialogConfig, DynamicDialogRef } from 'primeng/dynamicdialog';
import { UserService } from '../../services/user/user.service';
import { of, throwError } from 'rxjs';
import Swal from 'sweetalert2';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import {HttpResponse} from "@angular/common/http";

describe('UseRuleComponent', () => {
  let component: UseRuleComponent;
  let fixture: ComponentFixture<UseRuleComponent>;
  let attributeService: jasmine.SpyObj<AttributeService>;
  let categoryService: jasmine.SpyObj<CategoryService>;
  let messageService: jasmine.SpyObj<MessageService>;
  let userService: jasmine.SpyObj<UserService>;
  let ref: jasmine.SpyObj<DynamicDialogRef>;
  let config: DynamicDialogConfig;

  beforeEach(async () => {
    const attributeServiceSpy = jasmine.createSpyObj('AttributeService', ['getAllAttributes']);
    const categoryServiceSpy = jasmine.createSpyObj('CategoryService', ['getAllCategories']);
    const messageServiceSpy = jasmine.createSpyObj('MessageService', ['add']);
    const userServiceSpy = jasmine.createSpyObj('UserService', ['']);
    const refSpy = jasmine.createSpyObj('DynamicDialogRef', ['close']);

    const ruleData: RuleDto = {
      id: 1,
      name: 'Test Rule',
      description: 'Test Description',
      category: { id: 1, name: 'Test Category' } as CategoryDto,
      attributeDtos: [
        {
          name: { id: 1, name: 'Attribute 1' } as AttributeDto,
          percentage: 50,
          value: 5
        },
        {
          name: { id: 2, name: 'Attribute 2' } as AttributeDto,
          percentage: 50,
          value: 5
        }
      ] as AttributeDataDto[]
    };

    config = { data: ruleData } as DynamicDialogConfig;

    await TestBed.configureTestingModule({
      imports: [ReactiveFormsModule, FormsModule, HttpClientTestingModule],
      declarations: [UseRuleComponent],
      providers: [
        { provide: AttributeService, useValue: attributeServiceSpy },
        { provide: CategoryService, useValue: categoryServiceSpy },
        { provide: MessageService, useValue: messageServiceSpy },
        { provide: UserService, useValue: userServiceSpy },
        { provide: DynamicDialogRef, useValue: refSpy },
        { provide: DynamicDialogConfig, useValue: config }
      ]
    }).compileComponents();

    attributeService = TestBed.inject(AttributeService) as jasmine.SpyObj<AttributeService>;
    categoryService = TestBed.inject(CategoryService) as jasmine.SpyObj<CategoryService>;
    messageService = TestBed.inject(MessageService) as jasmine.SpyObj<MessageService>;
    userService = TestBed.inject(UserService) as jasmine.SpyObj<UserService>;
    ref = TestBed.inject(DynamicDialogRef) as jasmine.SpyObj<DynamicDialogRef>;
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(UseRuleComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  xit('should create', () => {
    expect(component).toBeTruthy();
  });

  xit('should initialize form with rule data', () => {
    spyOn(component, 'initializeForm');
    spyOn(component, 'loadCategories');
    spyOn(component, 'loadAttributes');
    component.ngOnInit();
    expect(component.initializeForm).toHaveBeenCalled();
    expect(component.loadCategories).toHaveBeenCalled();
    expect(component.loadAttributes).toHaveBeenCalled();});

  xit('should load categories on initialization', () => {
    const dummyCategories = [{ name: 'Category 1' }, { name: 'Category 2' }];
    const httpResponse = new HttpResponse({ body: dummyCategories });
    spyOn(component['categoryService'], 'getAllCategories').and.returnValue(of(httpResponse));
    component.loadCategories();
  });

  xit('should handle error during category loading', () => {
    categoryService.getAllCategories.and.returnValue(throwError('Error'));
    spyOn(console, 'error');
    component.loadCategories();
    expect(categoryService.getAllCategories).toHaveBeenCalled();
    expect(console.error).toHaveBeenCalledWith('Error loading categories:', 'Error');
  });

  xit('should load attributes on initialization', () => {
    const dummyCategories = [{ name: 'Category 1' }, { name: 'Category 2' }];
    const httpResponse = new HttpResponse({ body: dummyCategories });
    spyOn(component['categoryService'], 'getAllCategories').and.returnValue(of(httpResponse));
    component.loadCategories();
  });

  xit('should handle error during attribute loading', () => {
    attributeService.getAllAttributes.and.returnValue(throwError('Error'));
    spyOn(console, 'error');
    component.loadAttributes();
    expect(attributeService.getAllAttributes).toHaveBeenCalled();
    expect(console.error).toHaveBeenCalledWith('Error loading attributes:', 'Error');
  });

  xit('should add existing attributes to the form', () => {
    component.addExistingAttributes();
    const attributeArray = component.ruleForm.get('attributeDtos') as FormArray;
    expect(attributeArray.length).toBe(2);
    expect(attributeArray.at(0).get('name')?.value).toEqual({ id: 1, name: 'Attribute 1' });
    expect(attributeArray.at(1).get('name')?.value).toEqual({ id: 2, name: 'Attribute 2' });
  });

  xit('should validate attribute values correctly', () => {
    component.ruleForm = component.fb.group({
      attributeDtos: component.fb.array([
        component.fb.group({ name: 'attr1', percentage: 50, value: '5' }),
        component.fb.group({ name: 'attr2', percentage: 50, value: '15' }) // Invalid value
      ])
    });
    const isValid = component['validateAttributeValues']();
    expect(isValid).toBeFalse();
    expect(messageService.add).toHaveBeenCalledWith({
      severity: 'error',
      summary: 'Error',
      detail: 'Attribute value must be between 1 and 10'
    });
  });

  xit('should calculate the note correctly and close the dialog on submit', () => {
    spyOn(Swal, 'fire');
    component.ruleForm = component.fb.group({
      attributeDtos: component.fb.array([
        component.fb.group({ name: 'attr1', percentage: 50, value: '5' }),
        component.fb.group({ name: 'attr2', percentage: 50, value: '5' })
      ])
    });
    component.onSubmit();
    expect(ref.close).toHaveBeenCalledWith(true);

  });
});
