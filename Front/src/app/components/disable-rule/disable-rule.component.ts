import { Component, OnInit } from '@angular/core';
import {DynamicDialogRef} from "primeng/dynamicdialog";

@Component({
  selector: 'app-disable-rule',
  templateUrl: './disable-rule.component.html',
  styleUrls: ['./disable-rule.component.scss']
})
export class DisableRuleComponent implements OnInit {

  constructor(public ref: DynamicDialogRef) { }

  ngOnInit(): void {
  }

  closeDialog() {
    this.ref.close() ;
  }
}
