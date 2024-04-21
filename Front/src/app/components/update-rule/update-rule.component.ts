import { Component, OnInit } from '@angular/core';
import { FormArray, FormBuilder, FormGroup, Validators } from "@angular/forms";
import { MessageService } from 'primeng/api';
import {
  AttributeControllerService, AttributeDataDto,
  AttributeDto,
  CategoryDto,
  CategoryService,
  RuleDto,
  RuleService
} from "../../../app-api";
import { Router, ActivatedRoute } from "@angular/router";
import {DynamicDialogConfig, DynamicDialogRef} from "primeng/dynamicdialog";

@Component({
  selector: 'app-update-rule',
  templateUrl: './update-rule.component.html',
  styleUrls: ['./update-rule.component.scss']
})
export class UpdateRuleComponent implements OnInit {

  ruleForm!: FormGroup;
  attributes!: AttributeDto[];
  categories!: CategoryDto[];
  selectedCategory!: CategoryDto;
  selectedAttributes!: Array<AttributeDataDto>;
  categoryVisible: boolean = false;
  attributeVisible: boolean = false;
  newCategoryName: string = '';
  newAttributeName: string = '';
  existingAttributes!:AttributeDataDto[] ;
  private rule!: RuleDto;

  constructor(
      private fb: FormBuilder,
      private messageService: MessageService,
      private attributeService: AttributeControllerService,
      private ruleService: RuleService,
      private categoryService: CategoryService,
      private router: Router ,
      public ref: DynamicDialogRef,
      public config: DynamicDialogConfig ,
  ) {}

  ngOnInit(): void {
    this.initForm();
    this.loadCategories();
    this.loadAttributes();
  }

  initForm() {
    this.rule = this.config.data ;
    if (this.rule.attributeDtos!=null){
      this.existingAttributes = this.rule.attributeDtos ;
    }
    this.ruleForm = this.fb.group({
      name: [this.rule.name, Validators.required],
      description: [this.rule.description, Validators.required],
      category: [this.rule.category, Validators.required],
      attributeDtos: this.fb.array([])
    });

    this.addAttribute();
    console.log( "existing",this.existingAttributes) ;
    console.log("selected",this.selectedAttributes) ;
  }

  loadCategories() {
    this.categoryService.getAllCategories().subscribe({
      next: data => {
        this.categories = data;
      },
      error: error => {
        console.error('Error loading categories:', error);
      }
    });
  }

  loadAttributes() {
    this.attributeService.getAllAttributes().subscribe({
      next: data => {
        this.attributes = data;
        this.selectedAttributes = new Array(this.attributes.length);
      },
      error: error => {
        console.error('Error loading attributes:', error);
      }
    });
  }

  addAttribute() {
    const attributeArray = this.ruleForm.get('attributeDtos') as FormArray;
    attributeArray.push(this.createAttributeGroup());
  }

  removeAttribute(index: number) {
    const attributeArray = this.ruleForm.get('attributeDtos') as FormArray;
    attributeArray.removeAt(index);
  }

  createAttributeGroup() {
    return this.fb.group({
      name: ['', Validators.required],
      percentage: ['', Validators.required],
      value: ['', Validators.required]
    });
  }

  getAttributeControls() {
    return (this.ruleForm.get('attributeDtos') as FormArray).controls;
  }

  private validateAttributeNames(): boolean {
    const attributeNames = new Set<string>();
    const attributeControls = this.getAttributeControls();
    for (const control of attributeControls) {
      const name = control.value.name;
      if (attributeNames.has(name)) {
        this.messageService.add({
          severity: 'error',
          summary: 'Error',
          detail: 'Attribute names must be unique'
        });
        return false;
      }
      attributeNames.add(name);
    }
    return true;
  }

  private validateAttributeValues(): boolean {
    const attributeControls = this.getAttributeControls();
    for (const control of attributeControls) {
      const value = parseInt(control.value.value);
      if (isNaN(value) || value < 1 || value > 10) {
        this.messageService.add({
          severity: 'error',
          summary: 'Error',
          detail: 'Attribute value must be between 1 and 10'
        });
        return false;
      }
    }
    return true;
  }

  private validateAttributePercentages(): boolean {
    let sum = 0;
    for (const control of this.getAttributeControls()) {
      const percentage = parseInt(control.value.percentage);
      if (!isNaN(percentage)) {
        sum += percentage;
      }
    }

    if (sum !== 100) {
      this.messageService.add({
        severity: 'error',
        summary: 'Error',
        detail: 'Sum of attribute percentages must be equal to 100'
      });
      return false;
    }
    return true;
  }

  showDialog(type:string) {
    if (type == 'category'){
      this.categoryVisible = true ;
    }
    else this.attributeVisible =true ;
  }

  addNewAttribute() {
    if (this.newAttributeName.trim() !== '') {
      const attributeExists = this.attributes.some(attr => attr.name === this.newAttributeName);
      if (!attributeExists) {
        const newAttribute: AttributeDto = {
          name: this.newAttributeName
        };
        this.attributes.push(newAttribute);
        this.attributeVisible =false ;
        this.messageService.add({severity: 'success', summary: 'success', detail: 'Attribute added'});
      } else {
        this.messageService.add({severity: 'error', summary: 'error', detail: 'Attribute exists '});
      }
    }
  }

  addNewCategory() {
    if (this.newCategoryName.trim() !== '') {
      const categoryExists = this.categories.some(cat => cat.name === this.newCategoryName);
      if (!categoryExists) {
        const newCategory: CategoryDto = {
          name: this.newCategoryName
        };
        this.categories.push(newCategory);
        this.categoryVisible = false;
        this.messageService.add({severity: 'success', summary: 'success', detail: 'Category added'});
      } else {
        this.messageService.add({severity: 'error', summary: 'error', detail: 'Category exists '});
      }
    }
  }
  onSubmit() {
    const formData = this.ruleForm.value;
    console.log(formData) ;
    for (let attributeData of this.existingAttributes) {
      formData.attributeDtos.push(attributeData);
    }
    console.log(formData) ;
    if (this.ruleForm.valid) {
      if (!this.validateAttributeNames() || !this.validateAttributeValues() || !this.validateAttributePercentages()) {
        return;
      }
      const formData = this.ruleForm.value;
      this.ruleService.saveRule(formData).subscribe({
        next: response => {
          this.messageService.add({severity: 'success', summary: 'Success', detail: 'Rule saved successfully'});
          setTimeout(() => {
            this.router.navigate(['rules']);
          }, 1000);
        },
        error: error => {
          console.error('Error saving rule:', error);
          this.messageService.add({severity: 'error', summary: 'Error', detail: 'Failed to save rule. Please try again.'});
        }
      });
    } else {
      this.messageService.add({severity: 'error', summary: 'Error', detail: 'Please fill all required fields correctly.'});
    }
  }

  deleteAttribute(id?: number) {
    if (id !== undefined) {
      const index = this.existingAttributes.findIndex(attr => attr.id === id);
      if (index !== -1) {
        this.existingAttributes.splice(index, 1);
      }
    }
  }
}
