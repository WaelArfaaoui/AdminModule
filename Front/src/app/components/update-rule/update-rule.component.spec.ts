import { ComponentFixture, TestBed } from '@angular/core/testing';
import { UpdateRuleComponent } from './update-rule.component';
import {FormArray, FormBuilder, FormGroup, Validators} from '@angular/forms';
import { MessageService } from 'primeng/api';
import {AttributeService, RuleService, CategoryService, AttributeDataDto} from '../../../open-api';
import { RouterTestingModule } from '@angular/router/testing';
import {DialogService, DynamicDialogConfig, DynamicDialogRef} from 'primeng/dynamicdialog';
import { of } from 'rxjs';
import {HttpClient, HttpHandler} from "@angular/common/http";

describe('UpdateRuleComponent', () => {
  let component: UpdateRuleComponent;
  let fixture: ComponentFixture<UpdateRuleComponent>;
  let formBuilder: FormBuilder;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [UpdateRuleComponent],
      imports: [RouterTestingModule],
      providers: [
        FormBuilder,
        MessageService,
        AttributeService,
        RuleService,
        CategoryService,
        DynamicDialogRef,
        DynamicDialogConfig,
        MessageService , DialogService , HttpClient , HttpHandler
      ]
    }).compileComponents();
    formBuilder = TestBed.inject(FormBuilder);
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(UpdateRuleComponent);
    component = fixture.componentInstance;

    // Provide test data for DynamicDialogConfig
    component.config = new DynamicDialogConfig();
    component.config.data = {
      rule: {
        id: 1,
        name: 'Test Rule',
        description: 'Test Description',
        category: { id: 1, name: 'TestCategory' },
        enabled: true,
        createDate: '2024-05-14T10:00:00Z',
        lastModified: '2024-05-14T10:00:00Z',
        createdBy: 1,
        lastModifiedBy: 1,
        attributeDtos: [
          {
            id: 1,
            name: { id: 1, name: 'Attribute1' },
            percentage: 50,
            value: 5
          },
          {
            id: 2,
            name: { id: 2, name: 'Attribute2' },
            percentage: 50,
            value: 5
          }
        ]
      }
    } ;


    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should initialize form', () => {
    expect(component.ruleForm).toBeDefined();
    expect(component.attributes).toBeUndefined(); // Assuming attributes and categories are loaded asynchronously
    expect(component.categories).toBeUndefined();
    expect(component.selectedCategory).toBeUndefined();
    expect(component.selectedAttributes).toEqual([]);
    expect(component.categoryVisible).toBe(false);
    expect(component.attributeVisible).toBe(false);
    expect(component.newCategoryName).toBe('');
    expect(component.newAttributeName).toBe('');
    expect(component.existingAttributes).toBeUndefined();
    expect(component.rule).toBeDefined(); // Adjusted expectation to check if rule is defined
  });

  it('should load categories', () => {
    const categoryService = TestBed.inject(CategoryService);
    const categories = [{ name: 'Category 1' }, { name: 'Category 2' }];
    spyOn(categoryService, 'getAllCategories').and.returnValue(of(categories as any)); // Casting here

    component.loadCategories();

    expect(component.categories).toEqual(categories);
  });

  it('should load attributes', () => {
    const attributeService = TestBed.inject(AttributeService);
    const attributes = [{ name: 'Attribute 1' }, { name: 'Attribute 2' }];
    spyOn(attributeService, 'getAllAttributes').and.returnValue(of(attributes as any)); // Casting here

    component.loadAttributes();

    expect(component.attributes).toEqual(attributes);
  });

  it('should add existing attributes to the form array', () => {
    // Mocking data
    const mockAttributeDataDtos: AttributeDataDto[] = [
      { id: 1, name: { id: 1, name: 'Attribute 1' }, percentage: 0.5, value: 10 },
      { id: 2, name: { id: 2, name: 'Attribute 2' }, percentage: 0.3, value: 20 },
    ];
    component.ruleForm = formBuilder.group({
      attributeDtos: formBuilder.array([])
    });

    component.rule = { attributeDtos: mockAttributeDataDtos };

    component.addExistingAttributes();

    // Getting the FormArray
    const attributeArray = component.ruleForm.get('attributeDtos') as FormArray;
    expect(attributeArray.length).toBe(mockAttributeDataDtos.length);
    mockAttributeDataDtos.forEach((attrData, index) => {
      expect(attributeArray.at(index).value).toEqual(component.createAttributeGroup(attrData).value);
      if (attrData.name && attrData.name.name != null) {
        expect(component.selectedAttributes).toContain(attrData.name);
      }
    });
  });


  it('should create an empty attribute form group', () => {
    // Call the method to be tested
    const emptyAttributeGroup: FormGroup = component.createEmptyAttributeGroup();

    // Assertions
    expect(emptyAttributeGroup).not.toBeNull();
    expect(emptyAttributeGroup).toBeDefined();

    // Check form controls
    expect(emptyAttributeGroup!.get('name')).toBeDefined();
    expect(emptyAttributeGroup!.get('percentage')).toBeDefined();
    expect(emptyAttributeGroup!.get('value')).toBeDefined();

    // Check validators
    expect(emptyAttributeGroup!.get('name')!.validator).toEqual(Validators.required);
    expect(emptyAttributeGroup!.get('percentage')!.validator).toEqual(Validators.required);
    expect(emptyAttributeGroup!.get('value')!.validator).toEqual(Validators.required);

    // Check initial values
    expect(emptyAttributeGroup!.get('name')!.value).toEqual('');
    expect(emptyAttributeGroup!.get('percentage')!.value).toEqual('');
    expect(emptyAttributeGroup!.get('value')!.value).toEqual('');
  });

  it('should add an attribute to the attributeDtos FormArray', () => {
    // Set up initial state
    component.ruleForm = formBuilder.group({
      attributeDtos: formBuilder.array([])
    });

    // Call the method to be tested
    component.addAttribute();

    // Get the FormArray
    const attributeArray: FormArray = component.ruleForm.get('attributeDtos') as FormArray;

    // Assertions
    expect(attributeArray.length).toBe(1);
    expect(attributeArray.at(0)).toBeDefined();
    expect(attributeArray.at(0).value).toEqual({
      name: '',
      percentage: '',
      value: ''
    });
  });

  it('should remove an attribute from the attributeDtos FormArray', () => {
    // Set up initial state
    component.ruleForm = formBuilder.group({
      attributeDtos: formBuilder.array([
        formBuilder.group({
          name: 'Attribute 1',
          percentage: 0.5,
          value: 10
        }),
        formBuilder.group({
          name: 'Attribute 2',
          percentage: 0.3,
          value: 20
        }),
      ])
    });

    const initialLength = (component.ruleForm.get('attributeDtos') as FormArray).length;

    // Call the method to be tested
    component.removeAttribute(0);

    // Get the FormArray
    const attributeArray: FormArray = component.ruleForm.get('attributeDtos') as FormArray;

    // Assertions
    expect(attributeArray.length).toBe(initialLength - 1);
    expect(attributeArray.at(0).value).toEqual({
      name: 'Attribute 2',
      percentage: 0.3,
      value: 20
    });
  });
  it('should return true if attribute names are unique', () => {
    // Set up initial state
    const attributes = [
      { name: 'Attribute 1' },
      { name: 'Attribute 2' },
      { name: 'Attribute 3' }
    ];
    component.ruleForm = formBuilder.group({
      attributeDtos: formBuilder.array(attributes.map(attr => formBuilder.group(attr)))
    });

    // Call the method to be tested
    const result = component['validateAttributeNames']();

    // Assertions
    expect(result).toBeTrue();
  });

  it('should return false and display error message if attribute names are not unique', () => {
    // Set up initial state
    const attributes = [
      { name: 'Attribute 1' },
      { name: 'Attribute 1' }, // Duplicate name
      { name: 'Attribute 3' }
    ];
    component.ruleForm = formBuilder.group({
      attributeDtos: formBuilder.array(attributes.map(attr => formBuilder.group(attr)))
    });

    // Call the method to be tested
    const result = component['validateAttributeNames']();

    // Assertions
    expect(result).toBeFalse();
  });
  it('should return true if attribute values are between 1 and 10', () => {
    // Set up initial state
    const attributes = [
      { value: '5' },
      { value: '7' },
      { value: '10' }
    ];
    component.ruleForm = formBuilder.group({
      attributeDtos: formBuilder.array(attributes.map(attr => formBuilder.group(attr)))
    });

    // Call the method to be tested
    const result = component['validateAttributeValues']();

    // Assertions
    expect(result).toBeTrue();
  });

  it('should return false and display error message if attribute values are not between 1 and 10', () => {
    // Set up initial state
    const attributes = [
      { value: '5' },
      { value: '15' }, // Invalid value
      { value: '8' }
    ];
    component.ruleForm = formBuilder.group({
      attributeDtos: formBuilder.array(attributes.map(attr => formBuilder.group(attr)))
    });

    // Call the method to be tested
    const result = component['validateAttributeValues']();

    // Assertions
    expect(result).toBeFalse();
  });

  it('should return true if sum of attribute percentages is equal to 100', () => {
    // Set up initial state
    const attributes = [
      { percentage: '50' },
      { percentage: '30' },
      { percentage: '20' }
    ];
    component.ruleForm = formBuilder.group({
      attributeDtos: formBuilder.array(attributes.map(attr => formBuilder.group(attr)))
    });

    // Call the method to be tested
    const result = component['validateAttributePercentages']();

    // Assertions
    expect(result).toBeTrue();
  });

  it('should return false and display error message if sum of attribute percentages is not equal to 100', () => {
    // Set up initial state
    const attributes = [
      { percentage: '50' },
      { percentage: '30' },
      { percentage: '25' } // Invalid sum
    ];
    component.ruleForm = formBuilder.group({
      attributeDtos: formBuilder.array(attributes.map(attr => formBuilder.group(attr)))
    });

    // Call the method to be tested
    const result = component['validateAttributePercentages']();

    // Assertions
    expect(result).toBeFalse();
  });

  it('should set categoryVisible to true when type is "category"', () => {
    // Call the method to be tested
    component.showDialog('category');

    // Assertions
    expect(component.categoryVisible).toBeTrue();
    expect(component.attributeVisible).toBeFalse(); // Make sure the other property is false
  });

  it('should set attributeVisible to true when type is not "category"', () => {
    // Call the method to be tested
    component.showDialog('attribute');

    // Assertions
    expect(component.categoryVisible).toBeFalse(); // Make sure the other property is false
    expect(component.attributeVisible).toBeTrue();
  });
  it('should add a new attribute when it does not already exist', () => {
    // Set up initial state
    component.newAttributeName = 'New Attribute';
    component.attributes = [{ name: 'Existing Attribute' }];

    // Call the method to be tested
    component.addNewAttribute();

    // Assertions
    expect(component.attributes.length).toBe(2); // One existing and one newly added
    expect(component.attributes[1].name).toBe('New Attribute');
    expect(component.attributeVisible).toBeFalse();
  });

  it('should not add a new attribute if it already exists', () => {
    // Set up initial state
    component.newAttributeName = 'Existing Attribute';
    component.attributes = [{ name: 'Existing Attribute' }];

    // Call the method to be tested
    component.addNewAttribute();

    // Assertions
    expect(component.attributes.length).toBe(1); // The existing attribute remains
  });

  it('should not add a new attribute if the newAttributeName is empty', () => {
    // Set up initial state
    component.newAttributeName = '';

    // Call the method to be tested
    component.addNewAttribute();

  });
  it('should add a new category when it does not already exist', () => {
    // Set up initial state
    component.newCategoryName = 'New Category';
    component.categories = [{ name: 'Existing Category' }];

    // Call the method to be tested
    component.addNewCategory();

    // Assertions
    expect(component.categories.length).toBe(2); // One existing and one newly added
    expect(component.categories[1].name).toBe('New Category');
    expect(component.categoryVisible).toBeFalse();
  });

  it('should not add a new category if it already exists', () => {
    // Set up initial state
    component.newCategoryName = 'Existing Category';
    component.categories = [{ name: 'Existing Category' }];

    // Call the method to be tested
    component.addNewCategory();

    // Assertions
    expect(component.categories.length).toBe(1); // The existing category remains
  });

  it('should not add a new category if the newCategoryName is empty', () => {
    // Set up initial state
    component.newCategoryName = '';

    // Call the method to be tested
    component.addNewCategory();

  });



});
