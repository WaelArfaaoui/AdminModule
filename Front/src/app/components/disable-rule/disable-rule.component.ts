import { Component, OnInit } from '@angular/core';
import {DynamicDialogConfig, DynamicDialogRef} from "primeng/dynamicdialog";
import {MessageService} from "primeng/api";
import {RuleDto, RuleService} from "../../../open-api";

@Component({
  selector: 'app-disable-rule',
  templateUrl: './disable-rule.component.html',
  styleUrls: ['./disable-rule.component.scss']
})
export class DisableRuleComponent implements OnInit {
  private rule!: RuleDto;

  constructor(public ref: DynamicDialogRef , public config: DynamicDialogConfig , private ruleService:RuleService , public messageService:MessageService) { }

  ngOnInit(): void {
    this.rule = this.config.data;
  }

  closeDialog() {
    this.ref.close() ;
  }

  disable() {
    if (this.rule.id != null) {
      this.ruleService.updateStatus(this.rule.id, false)
          .subscribe(
              (response) => {
                this.messageService.add({severity:'success', summary:'Disabled', detail:'Rule disabled successfully'});
                this.ref.close(true);
                console.log("Rule Disabled !")
              },
              (error) => {
                console.log("Error occured !")
              }
          );
    }
  }

}
