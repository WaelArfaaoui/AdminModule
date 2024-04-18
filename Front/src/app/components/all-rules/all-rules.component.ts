import { Component, OnInit } from '@angular/core';
import {PageRuleDto, RuleDto} from "../../../app-api";
import {DialogService} from "primeng/dynamicdialog";
import {LockUserComponent} from "../lock-user/lock-user.component";
import {DisableRuleComponent} from "../disable-rule/disable-rule.component";
import {RuleControllerService} from "../../../app-api/api/ruleController.service";
import {UpdateUserComponent} from "../update-user/update-user.component";
import {UpdateRuleComponent} from "../update-rule/update-rule.component";
import {RuleHistoryComponent} from "../rule-history/rule-history.component";

interface PageEvent {
    first: number;
    rows: number;
    page: number;
    pageCount: number;
}

@Component({
    selector: 'app-all-rules',
    templateUrl: './all-rules.component.html',
    styleUrls: ['./all-rules.component.scss']
})
export class AllRulesComponent implements OnInit {
    first: number = 0;
    rows: number = 10;
    rules: Array<RuleDto> | undefined = [];
    totalRecords: number | undefined = 0;
    private selectedRule!: RuleDto;

    constructor(private ruleService: RuleControllerService  , private dialogService: DialogService) {}

    onPageChange(event: PageEvent) {
        this.first = event.first;
        this.rows = event.rows;
        this.loadRules();
    }

    ngOnInit(): void {
        this.loadRules();
    }

    loadRules() {
        this.ruleService.findAllRules(this.first, this.rows).subscribe({
            next: (response: PageRuleDto) => {
                this.rules = response.content;
                this.totalRecords = response.totalElements;
            },
            error: error => {
                console.error('Error fetching rules:', error);
            }
        });
    }

    disableRule(rule: RuleDto) {
        this.selectedRule = rule;
        const ref = this.dialogService.open(DisableRuleComponent, {
            header: 'Disable rule',
            width: '500px',
            contentStyle: {"background-color": "var(--color-white)","color": "var(--color-dark)"},
            data: this.selectedRule
        });
        ref.onClose.subscribe((result: any) => {
            if (result==true) {
                this.loadRules();
            }
        });
    }

    updateRule(rule: RuleDto) {
        this.selectedRule = rule;
        const ref = this.dialogService.open(UpdateRuleComponent, {
            header: 'Update Rule',
            width: '900px',
            height: '600px',
            contentStyle: {"background-color": "var(--color-white)","color": "var(--color-dark)"},
            data: this.selectedRule
        });

    }

    openRuleHistory(rule:RuleDto) {
        this.selectedRule = rule;
        const ref = this.dialogService.open(RuleHistoryComponent, {
            header: 'Rule history',
            width: '900px',
            height: '600px',
            contentStyle: {"background-color": "var(--color-white)","color": "var(--color-dark)"},
            data: this.selectedRule
        });

    }

}
