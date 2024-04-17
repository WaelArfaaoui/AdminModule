import {Component, OnInit} from '@angular/core';
import {FormArray, FormBuilder, FormGroup, Validators} from "@angular/forms";
import {MessageService} from 'primeng/api';
import {AttributeControllerService} from "../../../app-api";
import {Router} from "@angular/router";
import {RuleControllerService} from "../../../app-api/api/ruleController.service";
import {CategoryControllerService} from "../../../app-api/api/categoryController.service";

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
  selectedAttributes: (string | undefined)[] = [];

  constructor(private fb: FormBuilder, private messageService: MessageService,
              private attributeService: AttributeControllerService,
              private ruleService: RuleControllerService,
              private categoryService: CategoryControllerService,
              private router:Router) {
  }

  ngOnInit(): void {
    this.initForm();
    this.categoryService.getAllCategories().subscribe({
      next: data => {
        this.categories = data.map(category => category.name);
      }, error: error => {
        // Handle error
      }
    });
    this.attributeService.getAllAttributes().subscribe({
      next: data => {
        this.attributes = data.map(attribute => attribute.name);
        this.selectedAttributes = new Array(this.attributes.length); // Initialize selectedAttributes array
      }, error: error => {
        // Handle error
      }
    });
  }

  initForm() {
    this.ruleForm = this.fb.group({
      name: ['',Validators.required],
      description: ['',Validators.required],
      category: ['',Validators.required],
      attributeDtos: this.fb.array([])
    });

    // Initially add one set of attribute inputs
    this.addAttribute();
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
      name: ['',Validators.required],
      percentage: ['',Validators.required],
      value: ['',Validators.required]
    });
  }

  getAttributeControls() {
    return (this.ruleForm.get('attributeDtos') as FormArray).controls;
  }
  onSubmit() {
    if (this.ruleForm.valid) {
      // Check for unique attribute names
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
          return; // Exit onSubmit function early
        }
        attributeNames.add(name);

        // Check if attribute value is between 1 and 10
        const value = parseInt(control.value.value);
        if (isNaN(value) || value < 1 || value > 10) {
          this.messageService.add({
            severity: 'error',
            summary: 'Error',
            detail: 'Attribute value must be between 1 and 10'
          });
          return; // Exit onSubmit function early
        }
      }

      let sum = 0;
      for (let control of this.getAttributeControls()) {
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
        return; // Exit onSubmit function early
      }

      const formData = this.ruleForm.value;
      console.log(this.ruleForm.value);
      // Submit formData to your backend service
      this.ruleService.saveRule(formData).subscribe({
        next: response => {
          this.messageService.add({
            severity: 'success',
            summary: 'Success',
            detail: 'Rule saved successfully'
          });
          setTimeout(() => {
            this.router.navigate(['rules']);
          }, 1000);

        },
        error: error => {
          console.error('Error saving rule:', error);
          // Handle error and show an appropriate message to the user
        }
      });
    } else {
      // Form is not valid, show error message
      this.messageService.add({
        severity: 'error',
        summary: 'Error',
        detail: 'Please fill all required fields correctly.'
      });
    }
  }

}
