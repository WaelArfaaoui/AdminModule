import { Component, OnInit } from '@angular/core';
import {PageRuleDto, RuleControllerService, RuleDto} from "../../../app-api";

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

    constructor(private ruleService: RuleControllerService) {}

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
}
