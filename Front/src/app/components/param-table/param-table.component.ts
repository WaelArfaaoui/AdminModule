import { Component, OnInit } from '@angular/core';

import {MessageService} from "primeng/api";
import {TableService} from "../../services/table/table.service";
import {HttpClient} from "@angular/common/http";
interface expandedRows {
  [key: string]: boolean;
}
@Component({
  selector: 'app-param-table',
  templateUrl: './param-table.component.html',
  styleUrls: ['./param-table.component.scss']
})

export class ParamTableComponent implements OnInit {


  expandedRows: expandedRows = {};
  isExpanded: boolean = false;

  productDialog: boolean = false;

  deleteProductDialog: boolean = false;

  deleteProductsDialog: boolean = false;
  showNewRow: boolean = false;

  tables: any[] = [];

  product: any = {};

  selectedProducts: any[] = [];

  submitted: boolean = false;
columns: string[] = ['id', 'customer', 'date', 'Limitesdetransaction', 'status'];
  orders: any[] = [];
  cols: any[] = [];
  totalRecords: number = 100; // Total number of records

  statuses: any[] = [];
  tablesArray: { name: string, columns: string[] }[] = [];
  selectedColumns: any[]=[];
    products:any[]=[];
  rowsPerPageOptions = [5, 10, 20];

  constructor(private messageService: MessageService, private tableService: TableService,private http:HttpClient) {

  }

  ngOnInit() {
    this.retrieveData();
    this.getProductsWithOrdersSmall().then(data => this.products = data);

  }
  // Rest of your component code

  retrieveData(): void {
    this.tableService.retrieveAllTablesAndColumns().subscribe(
      data => {
        console.log('Received data:', data);
        this.tablesArray = data.map((table: { name: any; columns: any[]; }) => ({
          name: table.name,
          columns: table.columns.map(column => ({ name: column }))
        }));
        console.log(this.tablesArray); // Check the content in the console
      },
      error => {
        console.error('Error:', error);
        // Handle error
      }
    );
  }
  toggleEditMode(order: any) {
    order.editMode = !order.editMode; // Toggle the edit mode flag
  }

  newRow: any = {};
  addNewRow(product: any) {
    // Initialize new row object
    this.newRow = {};
    // Set showNewRow flag to true to display the new row
    this.showNewRow = true;
  }
  getProductsWithOrdersSmall() {
    return this.http.get<any>('assets/demo/data/products-orders-small.json')
      .toPromise()
      .then(res => res.data as any[])
      .then(data => data);
  }



}
