import {Component, ElementRef, OnInit, ViewChild} from '@angular/core';
import {Table} from "primeng/table";

@Component({
  selector: 'app-all-rules',
  templateUrl: './all-rules.component.html',
  styleUrls: ['./all-rules.component.scss']
})
export class AllRulesComponent implements OnInit {


  data:any   = [
    {
      name: 'Rule 1',
      description: 'Description of Rule 1',
      createdDate: new Date('2023-01-15'),
      enabled: true
    },
    {
      name: 'Rule 2',
      description: 'Description of Rule 2',
      createdDate: new Date('2023-03-22'),
      enabled: false
    },
    {
      name: 'Rule 3',
      description: 'Description of Rule 3',
      createdDate: new Date('2023-07-08'),
      enabled: true
    },
    {
      name: 'Rule 4',
      description: 'Description of Rule 4',
      createdDate: new Date('2023-09-01'),
      enabled: false
    },
    {
      name: 'Rule 5',
      description: 'Description of Rule 5',
      createdDate: new Date('2023-12-10'),
      enabled: true
    },
    {
      name: 'Rule 6',
      description: 'Description of Rule 6',
      createdDate: new Date('2024-02-28'),
      enabled: false
    },
    {
      name: 'Rule 7',
      description: 'Description of Rule 7',
      createdDate: new Date('2024-05-17'),
      enabled: true
    },
    {
      name: 'Rule 8',
      description: 'Description of Rule 8',
      createdDate: new Date('2024-08-04'),
      enabled: false
    },
    {
      name: 'Rule 9',
      description: 'Description of Rule 9',
      createdDate: new Date('2024-10-29'),
      enabled: true
    },
    {
      name: 'Rule 10',
      description: 'Description of Rule 10',
      createdDate: new Date('2024-12-05'),
      enabled: false
    },
    {
      name: 'Rule 11',
      description: 'Description of Rule 11',
      createdDate: new Date('2024-12-05'),
      enabled: false
    }
  ];

  constructor() {
  }
  ngOnInit(): void {
  }
}

