import { Component, OnInit } from '@angular/core';
import {RuleDto, RuleModificationDto, RuleService} from "../../../app-api";
import {DynamicDialogConfig} from "primeng/dynamicdialog";

@Component({
  selector: 'app-rule-history',
  templateUrl: './rule-history.component.html',
  styleUrls: ['./rule-history.component.scss']
})
export class RuleHistoryComponent implements OnInit {

  ruleModifications!:RuleModificationDto[] ;
  private rule!: RuleDto;
  private ruleId!: number;
  constructor(public config: DynamicDialogConfig ,private ruleService:RuleService) { }

  ngOnInit(): void {
    this.rule = this.config.data;
    if (this.rule.id !=null) this.ruleId = this.rule.id ;
    this.loadHistory(this.ruleId) ;
  }


  private loadHistory(ruleId: number) {
    this.ruleService.getModificationsByRuleId(ruleId).subscribe(data => {
      this.ruleModifications = data ;
    }, error => {
      console.error('Error fetching history:', error);
    });
  }
}
