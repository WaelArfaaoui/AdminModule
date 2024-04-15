import { Component, OnInit } from '@angular/core';
import { FormArray, FormBuilder, FormGroup } from "@angular/forms";
import { MessageService } from 'primeng/api';

@Component({
  selector: 'app-new-rule',
  templateUrl: './new-rule.component.html',
  styleUrls: ['./new-rule.component.scss']
})
export class NewRuleComponent implements OnInit {

  attributeForm!: FormGroup;
  attributes!: string[]  ;
  categories!: string[]  ;
  selectedCategory!: string;
  selectedAttribute!: string ;

  constructor(private fb: FormBuilder , private messageService:MessageService) { }

  ngOnInit(): void {
    this.initForm();
  }

  initForm() {
    this.attributeForm = this.fb.group({
      attributes: this.fb.array([])
    });

    // Initially add one set of attribute inputs
    this.addAttribute();
  }

  addAttribute() {
    const attributeArray = this.attributeForm.get('attributes') as FormArray;
    attributeArray.push(this.createAttributeGroup());
  }

  removeAttribute(index: number) {
    const attributeArray = this.attributeForm.get('attributes') as FormArray;
    attributeArray.removeAt(index);
  }

  createAttributeGroup() {
    return this.fb.group({
      name: [''], // You can set default values if needed
      percentage: ['']
    });
  }

  getAttributeControls() {
    return (this.attributeForm.get('attributes') as FormArray).controls;
  }

  onSubmit() {
    let sum = 0;
    const controls = this.getAttributeControls();
    for (let control of controls) {
      const percentage = parseInt(control.value.percentage);
      if (!isNaN(percentage)) {
        sum += percentage;
      }
    }
    if (sum !== 100) {
      console.log("ok")
      this.messageService.add({ severity: 'error', summary: 'Error', detail: 'Sum of attribute percentages must be equal to 100' });

    } else {
      // Form submission logic here
      console.log('Form submitted successfully');
    }
  }

}
