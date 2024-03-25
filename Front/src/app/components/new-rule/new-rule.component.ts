import { Component, OnInit } from '@angular/core';
import { FormArray, FormBuilder, FormGroup } from "@angular/forms";

@Component({
  selector: 'app-new-rule',
  templateUrl: './new-rule.component.html',
  styleUrls: ['./new-rule.component.scss']
})
export class NewRuleComponent implements OnInit {

  attributeForm!: FormGroup;
  attributes: string[] = ['Attribute 1', 'Attribute 2', 'Attribute 3']; // Sample attribute names

  constructor(private fb: FormBuilder) { }

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

}
