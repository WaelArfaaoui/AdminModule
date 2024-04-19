import { Component, OnInit } from '@angular/core';
import { FormArray, FormBuilder, FormGroup, Validators } from "@angular/forms";
import { MessageService } from 'primeng/api';
import {AttributeControllerService, CategoryService, RuleService} from "../../../app-api";
import { Router } from "@angular/router";

@Component({
  selector: 'app-new-rule',
  templateUrl: './new-rule.component.html',
  styleUrls: ['./new-rule.component.scss']
})
export class NewRuleComponent implements OnInit {

  ruleForm!: FormGroup;
  attributes!: (string | undefined)[];
  categories!: (string | undefined)[];
  selectedCategory: string | undefined;
  selectedAttributes: string[] = [];

  constructor(
      private fb: FormBuilder,
      private messageService: MessageService,
      private attributeService: AttributeControllerService,
      private ruleService: RuleService,
      private categoryService: CategoryService,
      private router: Router
  ) {}

  ngOnInit(): void {
    this.initForm();
    this.loadCategories();
    this.loadAttributes();
  }

  initForm() {
    this.ruleForm = this.fb.group({
      name: ['', Validators.required],
      description: ['', Validators.required],
      category: ['', Validators.required],
      attributeDtos: this.fb.array([])
    });

    this.addAttribute();
  }
  onSubmit() {
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

  loadCategories() {
    this.categoryService.getAllCategories().subscribe({
      next: data => {
        this.categories = data.map(category => category.name);
      },
      error: error => {
        console.error('Error loading categories:', error);
      }
    });
  }

  loadAttributes() {
    this.attributeService.getAllAttributes().subscribe({
      next: data => {
        this.attributes = data.map(attribute => attribute.name);
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
}
